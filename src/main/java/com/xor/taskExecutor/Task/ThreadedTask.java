package com.xor.taskExecutor.Task;

import com.xor.taskExecutor.util.RandomOutput;

public class ThreadedTask implements Task{
    @Override
    public String execute() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Threaded Task Executed");
        return RandomOutput.getOutput();
    }
}
