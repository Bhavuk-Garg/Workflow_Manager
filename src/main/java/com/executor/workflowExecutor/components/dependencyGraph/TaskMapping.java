package com.executor.workflowExecutor.components.dependencyGraph;

import com.executor.workflowExecutor.Task.Task;
import com.executor.workflowExecutor.database.model.TaskDependency;
import com.executor.workflowExecutor.service.TaskDependencyService;
import com.executor.workflowExecutor.service.TaskNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class TaskMapping {
    private Map<Integer, Task> taskIdRecord=new HashMap<>();;
    @Autowired
    TaskNameService taskNameService;

    @Autowired
    TaskDependencyService taskDependencyService;

    @Autowired
    ApplicationContext applicationContext;

    public void addTaskRecord(int id){
        if(taskIdRecord.containsKey(id))    return;
        taskIdRecord.put(id,createTask(id));
    }

    private Task createTask(int id)
    {
        /*
        *   Load all dependent tasks to set outputs
         */
       List<TaskDependency> dependencies= taskDependencyService.findByFromId(id);
       List<String> outputs=dependencies.stream().map(dependency-> dependency.getOutput()).collect(Collectors.toList());

        Task reqTask=applicationContext.getBean(taskNameService.findById(id).getName(),Task.class);
        reqTask.setOutputs(outputs);
        return reqTask;
    }


    public Task getTask(int id) {
        if(taskIdRecord.containsKey(id))
            return taskIdRecord.get(id);
        else
            throw new RuntimeException("No Records for task id: "+id);
    }
}
