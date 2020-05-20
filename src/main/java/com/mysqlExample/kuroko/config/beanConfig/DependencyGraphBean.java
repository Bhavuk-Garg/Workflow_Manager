package com.mysqlExample.kuroko.config.beanConfig;

import com.mysqlExample.kuroko.database.model.TaskDependency;
import com.mysqlExample.kuroko.service.TaskDependencyService;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DependencyGraphBean {

    @Autowired
    TaskDependencyService taskDependencyService;

    @Bean("dependencyGraph")
    public Graph getDependencyGraph(){
        Graph dependencyGraph=new Graph();
        Iterable<TaskDependency> dependencies= taskDependencyService.getAllDependencies();
        for(TaskDependency dependency : dependencies)
        {
            int from=dependency.getFromTask().getId();
            int to=dependency.getToTask().getId();
            String output=dependency.getOutput();
            dependencyGraph.addEdge(from,to,output);
        }
        return dependencyGraph;
    }
}
