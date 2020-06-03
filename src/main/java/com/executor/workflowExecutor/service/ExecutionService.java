package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.Task.Task;
import com.executor.workflowExecutor.components.dependencyGraph.DependencyGraph;
import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.database.repository.WorkflowRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.invoke.WrongMethodTypeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExecutionService {
    @Autowired
    WorkflowRepository workflowRepository;
    @Autowired
    DependencyGraph dependencyGraph;

    String execute(int curTaskId, Workflow inputWorkflow) {
        List<Pair<Integer,String>> edges= dependencyGraph.getEdges(curTaskId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        //Access Task Instance from task ID
        Task curTask= dependencyGraph.getTask(curTaskId);
        String output;
        try
        {
            output=curTask.execute();
            //Base Case for Leaf Node
            if(output==null) {
                String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+"S";
                appendResult(inputWorkflow,result);
                setStauts(inputWorkflow,Status.SUCCESS);
                return result;
            }
        //check if it is normal execution or trigger based or trigger wait
            for(Pair<Integer,String> edge: edges) {
                if (edge.getValue().equals(output)) {
                    String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+output+";";
                    appendResult(inputWorkflow,result);
                    switch (curTask.getType())
                    {
                        case NORMAL:
                            return result + execute(edge.getKey(),inputWorkflow);
                        case TIMED_WAIT:
                            setStauts(inputWorkflow,Status.TIMED_WAIT);
                            return result;
                        case TRIGGER_WAIT:
                            setStauts(inputWorkflow,Status.TRIGGER_WAIT);
                            return result;
                    }
                }
            }
        }catch (Exception e)
        {
//            e.printStackTrace();
            String result=dtf.format(LocalDateTime.now())+","+curTaskId+","+"E";
            appendResult(inputWorkflow,result);
            setStauts(inputWorkflow,Status.FAILED);
            return result;
        }

        return "something went wrong";
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
        List<Pair<Integer,String>> edges= dependencyGraph.getEdges(Integer.parseInt(temp[1]));
        for(Pair<Integer,String> edge: edges) {
            if (edge.getValue().equals(output)) {
                System.out.println("Starting again execution for task id: "+ Integer.parseInt(temp[1]));
                System.out.println("output was: "+output);
                execute(edge.getKey(),workflow);
            }
        }
    }
}
