package com.executor.workflowExecutor.Task.decorator;

import com.executor.workflowExecutor.Task.Task;
import com.executor.workflowExecutor.components.utility.Status;

public abstract class TaskDecorator extends Task {
    protected  Task decoratedTask;

    public TaskDecorator(Task decoratedTask) {
        this.decoratedTask = decoratedTask;
    }

    @Override
    public Status getType() {
        return decoratedTask.getType();
    }
}
