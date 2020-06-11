package com.executor.workflowExecutor.Task.decorator;

import com.executor.workflowExecutor.Task.ExecutableTask;

import java.util.List;
import java.util.Random;

public class TimeDecorator extends ExecutableTaskDecorator {

    int time=0;

    public TimeDecorator(ExecutableTask decoratedExecutableTask) {
        super(decoratedExecutableTask);
    }

    @Override
    public String execute() {
        List<String> outputs=decoratedExecutableTask.getOutputs();
        pauseExecution();
        System.out.println("Pause completed Successfully");
       if(outputs.size()==0)    return null;
        Random random=new Random();
        int idx=random.nextInt(outputs.size());
        return  outputs.get(idx);
    }

    private void pauseExecution() {
        try {
            System.out.println("starting sleep for : "+time);
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setTime(int time) {
        this.time = time;
    }
}

