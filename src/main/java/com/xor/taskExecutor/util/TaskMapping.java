package com.xor.taskExecutor.util;

import com.xor.taskExecutor.Task.Task;
import com.xor.taskExecutor.database.model.TaskDependency;
import com.xor.taskExecutor.database.model.TaskNameEntity;
import com.xor.taskExecutor.service.TaskDependencyService;
import com.xor.taskExecutor.service.TaskNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
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
    private Map<Integer, Task> taskIdRecord;
    @Autowired
    TaskNameService taskNameService;

    @Autowired
    TaskDependencyService taskDependencyService;

    @Autowired
    ApplicationContext applicationContext;

    TaskMapping(){
        taskIdRecord=new HashMap<>();
    }

    public void addTaskRecord(int id){
        if(taskIdRecord.containsKey(id))    return;
        taskIdRecord.put(id,createTask(id));
    }

    private Task createTask(int id)
    {
       List<TaskDependency> dependencies= taskDependencyService.findDependentEntriesById(id);
       List<String> outputs=dependencies.stream().map(dependency-> dependency.getOutput()).collect(Collectors.toList());

        Task reqTask=applicationContext.getBean(taskNameService.findById(id).getName(),Task.class);
        reqTask.setOutputs(outputs);
        return reqTask;
    }

    @Override
    public String toString() {
        return "TaskMapping{" +
                "taskIdRecord=" + taskIdRecord +
                '}';
    }

    public Task getTask(int id) {
        if(taskIdRecord.containsKey(id))
            return taskIdRecord.get(id);
        else
            throw new RuntimeException("No Records for task id: "+id);
    }
}
