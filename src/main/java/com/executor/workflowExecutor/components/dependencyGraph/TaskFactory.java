package com.executor.workflowExecutor.components.dependencyGraph;

import com.executor.workflowExecutor.Task.Task;
import com.executor.workflowExecutor.Task.decorator.TimeDecorator;
import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.TaskDependency;
import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.service.TaskDependencyService;
import com.executor.workflowExecutor.service.TaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskFactory {
    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    TaskDependencyService taskDependencyService;

    @Autowired
    ApplicationContext applicationContext;

    public Task createTask(int id)
    {
        /*
         *   Load all dependent tasks and set possible outputs
         */
        List<TaskDependency> dependencies= taskDependencyService.findByFromId(id);
        List<String> outputs=dependencies.stream().map(dependency-> dependency.getOutput()).collect(Collectors.toList());

        TaskInfo curTaskInfo=taskInfoService.findById(id);
        Task reqTask=applicationContext.getBean(curTaskInfo.getName(),Task.class);
        reqTask.setOutputs(outputs);
        reqTask.setType(curTaskInfo.getType());
        System.out.println("Set the type of id: "+id+" to "+curTaskInfo.getType());
        if(curTaskInfo.getType()== Status.TIME_WAIT)
        {
            TimeDecorator wrappedTask=new TimeDecorator(reqTask);
            wrappedTask.setTime(curTaskInfo.getTime()*1000);
            return wrappedTask;
        }
        return reqTask;
    }
}
