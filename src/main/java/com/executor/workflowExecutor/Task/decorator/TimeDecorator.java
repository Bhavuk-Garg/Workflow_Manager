package com.executor.workflowExecutor.Task.decorator;

import com.executor.workflowExecutor.Task.Task;

public class TimeDecorator extends TaskDecorator{

    int time=0;

    public TimeDecorator(Task decoratedTask) {
        super(decoratedTask);
    }

    @Override
    public String execute() {
        return decoratedTask.execute();
    }

    public void pauseExecution() {
        try {
            System.out.println("starting sleep for : "+time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setTime(int time) {
        this.time = time;
    }
}

