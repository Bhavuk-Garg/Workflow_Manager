package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.Task.Task;
import com.executor.workflowExecutor.Task.decorator.TimeDecorator;
import com.executor.workflowExecutor.components.dependencyGraph.DependencyGraph;
import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.database.repository.WorkflowRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExecutionService {
    @Autowired
    WorkflowRepository workflowRepository;
    @Autowired
    ApplicationContext applicationContext;

    String execute(int curTaskId, Workflow inputWorkflow,DependencyGraph dependencyGraph) {
        List<Pair<Integer,String>> edges= dependencyGraph.getEdges(curTaskId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        //Access Task Instance from task ID
        Task curTask= dependencyGraph.getTask(curTaskId);
        String output;
        try
        {
            output=curTask.execute();
            System.out.println("Output in Execution Service : "+output);
            //Base Case for Leaf Node
            if(output==null) {
                String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+"S";
                updateWorkflow(inputWorkflow,result,Status.SUCCESS);
                return result;
            }

            for(Pair<Integer,String> edge: edges) {
                if (edge.getValue().equals(output)) {
                    String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+output+";";
                    switch (curTask.getType())
                    {
                        case NORMAL:
                            appendResult(inputWorkflow,result);
                            return result + execute(edge.getKey(),inputWorkflow,dependencyGraph);
                        case TIME_WAIT:
                            updateWorkflow(inputWorkflow,result,Status.TIME_WAIT);
                            ((TimeDecorator)curTask).pauseExecution();
                            setStauts(inputWorkflow,Status.NORMAL);
                            return result + execute(edge.getKey(),inputWorkflow,dependencyGraph);
                        case TRIGGER_WAIT:
                            updateWorkflow(inputWorkflow,result,Status.TRIGGER_WAIT);
                            return result;
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+"E";
            updateWorkflow(inputWorkflow,result,Status.FAILED);
            return result;
        }

        return "something went wrong";
    }

    private void updateWorkflow(Workflow inputWorkflow, String result, Status status) {
        inputWorkflow.setResult(inputWorkflow.getResult()+result);
        inputWorkflow.setStatus(status);
        workflowRepository.save(inputWorkflow);
    }

    public void setStauts(Workflow workflow,Status status)
    {
        workflow.setStatus(status);
        workflowRepository.save(workflow);
    }
    public void appendResult(Workflow workflow,String result)
    {
        workflow.setResult(workflow.getResult()+result);
        workflowRepository.save(workflow);
    }

    @Async
    public void resumeExecution(Workflow workflow,int time) throws InterruptedException {
        Thread.sleep(time);
        String res[]=workflow.getResult().split(";");
        String temp[]=res[(res.length-1)].split(",");
        String output=temp[2];
        DependencyGraph dependencyGraph=applicationContext.getBean("dependencyGraph",DependencyGraph.class);
        List<Pair<Integer,String>> edges= dependencyGraph.getEdges(Integer.parseInt(temp[1]));
        for(Pair<Integer,String> edge: edges) {
            if (edge.getValue().equals(output)) {
                System.out.println("Starting again execution for task id: "+ Integer.parseInt(temp[1]));
                System.out.println("output was: "+output);
                execute(edge.getKey(),workflow,dependencyGraph);
            }
        }
    }
}
