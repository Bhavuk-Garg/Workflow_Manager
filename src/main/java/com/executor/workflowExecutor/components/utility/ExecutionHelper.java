package com.executor.workflowExecutor.components.utility;

import com.executor.workflowExecutor.Task.ExecutableTask;
import com.executor.workflowExecutor.components.dependencyGraph.DependencyGraph;
import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.database.repository.WorkflowRepository;
import com.executor.workflowExecutor.eventListeners.event.WorkflowStatusChangeEvent;
import com.executor.workflowExecutor.service.TaskInfoService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExecutionHelper {
    @Autowired WorkflowRepository workflowRepository;
    @Autowired TaskInfoService taskInfoService;
    @Autowired ApplicationEventPublisher eventPublisher;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Async
    public void execute(int curTaskId, Workflow inputWorkflow, DependencyGraph dependencyGraph) {
        List<Pair<Integer,String>> edges= dependencyGraph.getEdges(curTaskId);


        //Access Task Instance from task ID
        ExecutableTask curExecutableTask = dependencyGraph.getTask(curTaskId);
        TaskInfo taskInfo=taskInfoService.findById(curTaskId);
        String taskName=taskInfo.getName();
        String type=curExecutableTask.getType().toString();
        String output;
        try
        {
            if(curExecutableTask.getType()==Status.TIME_WAIT)
                setStatus(inputWorkflow,Status.TIME_WAIT);

            output= curExecutableTask.execute();
            //Base Case for Leaf Node
            if(output==null) {
                System.out.println("Executed Successfully");
                String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+taskName+","+type+","+""+";";
                updateWorkflow(inputWorkflow,result,Status.SUCCESS);
                return;
            }

            for(Pair<Integer,String> edge: edges) {
                if (edge.getValue().equals(output)) {
                    String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+taskName+","+type+","+output+";";
                    switch (curExecutableTask.getType())
                    {
                        case NORMAL:
                            appendResult(inputWorkflow,result);
                            execute(edge.getKey(),inputWorkflow,dependencyGraph);
                            break;
                        case TIME_WAIT:
                            updateWorkflow(inputWorkflow,result,Status.NORMAL);
                            execute(edge.getKey(),inputWorkflow,dependencyGraph);
                            break;
                        case TRIGGER_WAIT:
                            updateWorkflow(inputWorkflow,result,Status.TRIGGER_WAIT);
                            break;
                        default:
                            throw new RuntimeException();
                    }
                }
            }
        }catch (Exception e)
        {
//            e.printStackTrace();
            String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+taskName+","+type+","+""+";";
            appendResult(inputWorkflow,result);
            boolean retried=dependencyGraph.getTaskRecovery().recover(curTaskId,inputWorkflow,dependencyGraph);
            if(retried==false)
            {
                setStatus(inputWorkflow,Status.FAILED);
            }
        }

    }




    private void updateWorkflow(Workflow workflow, String result, Status status) {
        workflow.setResult(workflow.getResult()+result);
        workflow.setStatus(status);
        workflowRepository.save(workflow);

        eventPublisher.publishEvent(new WorkflowStatusChangeEvent(this,workflow));
    }

    public void setStatus(Workflow workflow,Status status)
    {
        workflow.setStatus(status);
        workflowRepository.save(workflow);
        eventPublisher.publishEvent(new WorkflowStatusChangeEvent(this,workflow));
    }
    public void appendResult(Workflow workflow,String result)
    {
        workflow.setResult(workflow.getResult()+result);
        workflowRepository.save(workflow);
    }
}
