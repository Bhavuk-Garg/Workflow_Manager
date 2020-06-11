package com.executor.workflowExecutor.components.dependencyGraph;

import com.executor.workflowExecutor.Task.ExecutableTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("taskMapping")
@Scope("prototype")
public class TaskMapping {
    private Map<Integer, ExecutableTask> taskIdRecord=new HashMap<>();;

    @Autowired
    TaskFactory taskFactory;

    public void addTaskRecord(int id){
        if(taskIdRecord.containsKey(id))    return;
        taskIdRecord.put(id,taskFactory.createTask(id));
    }

    public ExecutableTask getTask(int id) {
        if(taskIdRecord.containsKey(id))
            return taskIdRecord.get(id);
        else
            throw new RuntimeException("No Records for task id: "+id);
    }
}
