package com.xor.taskExecutor.service;

import com.xor.taskExecutor.Task.Task;
import com.xor.taskExecutor.util.DependencyGraph;
import com.xor.taskExecutor.util.Status;
import com.xor.taskExecutor.database.model.Workflow;
import com.xor.taskExecutor.database.repository.TaskNameRepository;
import com.xor.taskExecutor.database.repository.WorkflowRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DependencyGraph dependencyGraph =applicationContext.getBean("dependencyGraph", DependencyGraph.class);
        String res=execute(1, dependencyGraph);

        updateStatus(inputWorkflow,res);
        workflowRepository.save(inputWorkflow);
    }

    private void updateStatus(Workflow inputWorkflow, String res) {
        /*
        *   A successful execution returns result ending with "X"
        *   Unsuccessful execution ends with "Exception"
         */
        if(res.endsWith("S"))
            inputWorkflow.setStatus(Status.Success);
        else    inputWorkflow.setStatus(Status.Failed);
        inputWorkflow.setResult(res);
    }

    String execute(int curTaskId, DependencyGraph dependencyGraph) {
        List<Pair<Integer,String>> edges= dependencyGraph.getEdges(curTaskId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        Task curTask= dependencyGraph.getTask(curTaskId);
        String output;
        try
        {
            output=curTask.execute();
            //Base Case for Leaf Node
            if(output==null)  return dtf.format(LocalDateTime.now())+","+curTaskId+","+"S";

            for(Pair<Integer,String> edge: edges) {
                if (edge.getValue().equals(output))
                    return dtf.format(LocalDateTime.now())+","+curTaskId + "," + output+ ";" + execute(edge.getKey(), dependencyGraph);
            }
        }catch (Exception e)
        {
            return dtf.format(LocalDateTime.now())+","+curTaskId+","+"E";
        }

//        System.out.println("for task : "+curTask+" output generated: "+output);
        return "something went wrong";
    }

    @Override
    public List<Workflow> findByNameLike(String name) {
        return workflowRepository.findByNameLikeOrderByCreationDateDesc("%"+name+"%");
    }

    @Override
    public Workflow findByName(String name) {
        return workflowRepository.findByName(name);
    }
}
