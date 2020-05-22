package com.xor.taskExecutor.service;

import com.xor.taskExecutor.Task.Task;
import com.xor.taskExecutor.util.Graph;
import com.xor.taskExecutor.util.Status;
import com.xor.taskExecutor.database.model.TaskNameEntity;
import com.xor.taskExecutor.database.model.Workflow;
import com.xor.taskExecutor.database.repository.TaskNameRepository;
import com.xor.taskExecutor.database.repository.WorkflowRepository;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
        return workflowRepository.findAll(Sort.by("creationDate").descending());
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
        System.out.println("no edge from "+curTaskId+" with output: "+output);
        return curTaskId+","+"Exception"+";";
    }

    private Task getTask(int curTaskId) {
        TaskNameEntity taskNameEntity=taskNameRepository.findById(curTaskId).orElse(null);
        if(taskNameEntity==null)    throw new RuntimeException("TaskName ID: "+curTaskId+" do not exist in database");

        return applicationContext.getBean(taskNameEntity.getName(),Task.class);
    }


    public List<Pair<Integer, String>> getFormattedResult(String res) {
        List<Pair<Integer, String>> formatResult=new ArrayList<>();
        String []executionPaths=res.split(";");
        System.out.println(Arrays.toString(executionPaths));
        for(String s: executionPaths)
        {
            String []executionUnit=s.split(",");
            String pathTaken="";
            if(executionUnit.length == 1)
                pathTaken="Default Path Taken";
            else if(executionUnit[1].equals("X"))
                pathTaken="Successfully Terminated";
            else if(executionUnit[1].equals("Exception"))
                pathTaken="Caught Exception Failed to Execute";
            else
                pathTaken="Output Generated "+executionUnit[1];

            formatResult.add(new MutablePair<Integer, String>(Integer.parseInt(executionUnit[0]),pathTaken));

        }
        return formatResult;
    }

    @Override
    public Workflow findByName(String name) {
        return workflowRepository.findByName(name);
    }
}
