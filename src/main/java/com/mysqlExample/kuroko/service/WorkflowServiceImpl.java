package com.mysqlExample.kuroko.service;

import com.mysqlExample.kuroko.Task.Task;
import com.mysqlExample.kuroko.config.beanConfig.Graph;
import com.mysqlExample.kuroko.database.model.Status;
import com.mysqlExample.kuroko.database.model.TaskNameEntity;
import com.mysqlExample.kuroko.database.model.Workflow;
import com.mysqlExample.kuroko.database.repository.TaskNameRepository;
import com.mysqlExample.kuroko.database.repository.WorkflowRepository;
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
        String res=dfs(1,graph);
        System.out.println(res);
        updateStatus(inputWorkflow,res);
        workflowRepository.save(inputWorkflow);
    }

    private void updateStatus(Workflow inputWorkflow, String res) {
        if(res.endsWith("X"))
            inputWorkflow.setStatus(Status.Success);
        else    inputWorkflow.setStatus(Status.Failed);
        inputWorkflow.setResult(res);
    }

    String dfs(int curTaskId,Graph graph) {
        List<Pair<Integer,String>> edges=graph.getEdges(curTaskId);

        //Base Case for Leaf Node
        if(edges==null)  return curTaskId+","+"X";

        Task curTask=getTask(curTaskId);
        String output=curTask.execute();
        System.out.println("at node: "+curTaskId+" got output "+output);
        for(Pair<Integer,String> edge: edges)
            if(edge.getValue().equals(output))
                return curTaskId+","+output+";"+dfs(edge.getKey(),graph);

        for(Pair<Integer,String> edge:edges)
            if(edge.getValue().equals(""))
                return curTaskId+",;"+dfs(edge.getKey(),graph);
         //Take this output as exception
        return curTaskId+","+"Exception"+";";
    }

    private Task getTask(int curTaskId) {
        TaskNameEntity taskDbInstance=taskNameRepository.findById(curTaskId).orElse(null);
        if(taskDbInstance==null)    throw new RuntimeException("ID "+curTaskId+" have no instance in database");

        return applicationContext.getBean(taskDbInstance.getName(),Task.class);
    }

    @Override
    public Workflow findByName(String name) {
        return workflowRepository.findByName(name);
    }
}
