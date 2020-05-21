package com.xor.taskExecutor.Task;

import com.xor.taskExecutor.util.RandomOutput;

public class PrintTask implements Task {
    public String execute()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("print task executed");
        return RandomOutput.getOutput();
    }
}
