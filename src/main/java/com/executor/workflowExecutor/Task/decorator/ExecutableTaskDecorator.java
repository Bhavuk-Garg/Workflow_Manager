package com.executor.workflowExecutor.Task.decorator;

import com.executor.workflowExecutor.Task.ExecutableTask;

public abstract class ExecutableTaskDecorator extends ExecutableTask {
    protected ExecutableTask decoratedExecutableTask;

    public ExecutableTaskDecorator(ExecutableTask decoratedExecutableTask) {
        super(decoratedExecutableTask.getOutputs(),decoratedExecutableTask.getType());
        this.decoratedExecutableTask=decoratedExecutableTask;
    }
}
