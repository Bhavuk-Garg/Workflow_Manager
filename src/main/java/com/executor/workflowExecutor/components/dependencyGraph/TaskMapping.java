package com.executor.workflowExecutor.components.dependencyGraph;

import com.executor.workflowExecutor.Task.Task;
import com.executor.workflowExecutor.database.model.TaskDependency;
import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.service.TaskDependencyService;
import com.executor.workflowExecutor.service.TaskInfoService;
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
    TaskInfoService taskInfoService;

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
        System.out.println("creating record for task id: "+id);
        /*
        *   Load all dependent tasks and set possible outputs
         */
       List<TaskDependency> dependencies= taskDependencyService.findByFromId(id);
       List<String> outputs=dependencies.stream().map(dependency-> dependency.getOutput()).collect(Collectors.toList());

       TaskInfo curTaskInfo=taskInfoService.findById(id);
        Task reqTask=applicationContext.getBean(curTaskInfo.getName(),Task.class);
        reqTask.setOutputs(outputs);
        reqTask.setType(curTaskInfo.getType());
        return reqTask;
    }


    public Task getTask(int id) {
        if(taskIdRecord.containsKey(id))
            return taskIdRecord.get(id);
        else
            throw new RuntimeException("No Records for task id: "+id);
    }
}
