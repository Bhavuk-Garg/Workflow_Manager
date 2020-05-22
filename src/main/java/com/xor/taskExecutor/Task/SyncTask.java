package com.xor.taskExecutor.Task;

import com.xor.taskExecutor.util.RandomOutput;

public class SyncTask implements Task {
    String outputValues[];
    @Override
    public String execute() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sync Task Executed");
        return RandomOutput.getOutput();
    }
}
