package com.xor.taskExecutor.service;

import com.xor.taskExecutor.Task.Task;
import com.xor.taskExecutor.util.Graph;
import com.xor.taskExecutor.util.Status;
import com.xor.taskExecutor.database.model.TaskNameEntity;
import com.xor.taskExecutor.database.model.Workflow;
import com.xor.taskExecutor.database.repository.TaskNameRepository;
import com.xor.taskExecutor.database.repository.WorkflowRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    @Autowired
    WorkflowRepository workflowRepository;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    TaskNameRepository taskNameRepository;

    @Override
    public void saveWorkflow(Workflow inputWorkflow){
            workflowRepository.save(inputWorkflow);
    }

    @Override
    public Iterable<Workflow> findAll() {
        return workflowRepository.findAll();
    }

    @Override
    @Async
    public void executeWorkflow(Workflow inputWorkflow)  {
        Graph graph=applicationContext.getBean("dependencyGraph",Graph.class);
        String res=execute(1,graph);

        updateStatus(inputWorkflow,res);
        workflowRepository.save(inputWorkflow);
    }

    private void updateStatus(Workflow inputWorkflow, String res) {
        /*
        *   A successful execution returns result ending with "X"
        *   Unsuccessful execution ends with "Exception"
         */
        if(res.endsWith("X"))
            inputWorkflow.setStatus(Status.Success);
        else    inputWorkflow.setStatus(Status.Failed);
        inputWorkflow.setResult(res);
    }

    String execute(int curTaskId,Graph graph) {
        List<Pair<Integer,String>> edges=graph.getEdges(curTaskId);

        //Base Case for Leaf Node
        if(edges==null)  return curTaskId+","+"X";

        Task curTask=getTask(curTaskId);
        String output=curTask.execute();
//        System.out.println("at node: "+curTaskId+" got output "+output);
        for(Pair<Integer,String> edge: edges)
            if(edge.getValue().equals(output))
                return curTaskId+","+output+";"+execute(edge.getKey(),graph);

        for(Pair<Integer,String> edge:edges)
            if(edge.getValue().equals(""))
                return curTaskId+",;"+execute(edge.getKey(),graph);

        //If no edge matches take it as exception
        return curTaskId+","+"Exception"+";";
    }

    private Task getTask(int curTaskId) {
        TaskNameEntity taskNameEntity=taskNameRepository.findById(curTaskId).orElse(null);
        if(taskNameEntity==null)    throw new RuntimeException("TaskName ID: "+curTaskId+" do not exist in database");

        return applicationContext.getBean(taskNameEntity.getName(),Task.class);
    }

    @Override
    public Workflow findByName(String name) {
        return workflowRepository.findByName(name);
    }
}
