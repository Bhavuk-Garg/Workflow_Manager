package com.executor.workflowExecutor.components.dependencyGraph;

import com.executor.workflowExecutor.Task.Executable;
import com.executor.workflowExecutor.Task.ExecutableTask;
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

    public ExecutableTask createTask(int id)
    {
        /*
         *   Load all dependent tasks and fetch possible outputs
        */
        List<TaskDependency> dependencies= taskDependencyService.findByFromId(id);
        List<String> outputs=dependencies.stream().map(dependency-> dependency.getOutput()).collect(Collectors.toList());

        /*
         *   info holds [id,name,type,time]. We will use it to get name and type, then set these fields in Executable Task
         */
        TaskInfo currentTaskInfo=taskInfoService.findById(id);

        ExecutableTask newExecutableTask =(ExecutableTask) applicationContext.getBean(currentTaskInfo.getName(),outputs,currentTaskInfo.getType());

        if(currentTaskInfo.getType()== Status.TIME_WAIT)
        {
            TimeDecorator newDecoratedExecutableTask=new TimeDecorator(newExecutableTask);
//            System.out.println("DecoratedTask made: "+newDecoratedExecutableTask);
            newDecoratedExecutableTask.setTime(currentTaskInfo.getTime());
            return newDecoratedExecutableTask;
        }
        return newExecutableTask;
    }
}
