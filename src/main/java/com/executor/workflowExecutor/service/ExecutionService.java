package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.components.dependencyGraph.DependencyGraph;
import com.executor.workflowExecutor.components.utility.ExecutionHelper;
import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.database.repository.WorkflowRepository;
import com.executor.workflowExecutor.eventListeners.event.WorkflowCreateEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExecutionService {
    @Autowired WorkflowRepository workflowRepository;
    @Autowired ApplicationContext applicationContext;
    @Autowired WorkflowService workflowService;
    @Autowired ExecutionHelper executionHelper;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public void startExecution(String inputWorkflowName){
        Workflow inputWorkflow=new Workflow();
        inputWorkflow.setName(inputWorkflowName.trim());
        /*
         *   Create a default entry with NORMAL Status
         */
        workflowService.saveWorkflow(inputWorkflow);
        /*
         *   Asynchronous call to execute method
         */
        DependencyGraph dependencyGraph=applicationContext.getBean("dependencyGraph",DependencyGraph.class);
        eventPublisher.publishEvent(new WorkflowCreateEvent(inputWorkflowName+" created"));
        executionHelper.execute(1,inputWorkflow,dependencyGraph);
    }

    public void resumeExecution(String workflowName){
        Workflow workflow=workflowService.findByName(workflowName);
            String fullResult[]=workflow.getResult().split(";");
            String lastResult[]=fullResult[(fullResult.length-1)].split(",",-1);
        /*
            resUnit contains [date,id,name,type,output] information
         */
            String output=lastResult[4];
            int id=Integer.parseInt(lastResult[1]);

            workflow.setTriggers(workflow.getTriggers()+id+","+(dtf.format(LocalDateTime.now()))+";");
            workflowRepository.save(workflow);

            DependencyGraph dependencyGraph=applicationContext.getBean("dependencyGraph",DependencyGraph.class);
            List<Pair<Integer,String>> edges= dependencyGraph.getEdges(id);
            for(Pair<Integer,String> edge: edges) {
                if (edge.getValue().equals(output)) {
                    executionHelper.setStatus(workflow,Status.NORMAL);
                    executionHelper.execute(edge.getKey(),workflow,dependencyGraph);
                }
            }
    }
}
