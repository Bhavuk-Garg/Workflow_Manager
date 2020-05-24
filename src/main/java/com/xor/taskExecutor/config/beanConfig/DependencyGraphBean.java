package com.xor.taskExecutor.config.beanConfig;

import com.xor.taskExecutor.database.model.TaskDependency;
import com.xor.taskExecutor.service.TaskDependencyService;
import com.xor.taskExecutor.util.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DependencyGraphBean {

//
//    @Autowired
//    Graph dependencyGraph;
//
//    @Bean("dependencyGraph")
//    @Scope("prototype")
//    public Graph getDependencyGraph(){
//
//
//        System.out.println(dependencyGraph.taskMapping);
//        return dependencyGraph;
//    }
}
