package com.executor.workflowExecutor.components.dependencyGraph;

import com.executor.workflowExecutor.Task.Task;
import com.executor.workflowExecutor.database.model.TaskDependency;
import com.executor.workflowExecutor.service.TaskDependencyService;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("dependencyGraph")
@Scope("prototype")
public class DependencyGraph {
    Map<Integer, List<Pair<Integer,String >>> adjList=new HashMap<>();
    @Autowired
    public TaskMapping taskMapping;

    @Autowired
    TaskDependencyService taskDependencyService;


    @PostConstruct
    void createGraph(){
        Iterable<TaskDependency> dependencies= taskDependencyService.getAll();
        for(TaskDependency dependency : dependencies)
        {
            int from=dependency.getFromTask().getId();
            int to=dependency.getToTask().getId();
            String output=dependency.getOutput();
            addEdge(from,to,output);
        }
    }

    public void addEdge(int from,int to,String output)
    {
        adjList.putIfAbsent(from,new ArrayList<>());
        adjList.get(from).add(new MutablePair<>(to,output));
        /*
        *   Create a Task Instance with given Id
         */
        taskMapping.addTaskRecord(from);
        taskMapping.addTaskRecord(to);
    }
    public List<Pair<Integer,String>> getEdges(int node) {
        return adjList.get(node);
    }

    public Task getTask(int id)
    {
        return taskMapping.getTask(id);
    }

}

