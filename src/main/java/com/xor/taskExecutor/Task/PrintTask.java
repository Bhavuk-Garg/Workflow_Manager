package com.xor.taskExecutor.Task;

public class PrintTask implements Task {
    public String execute()
    {
        System.out.println("print task executed");
        return RandomOutput.getOutput();
    }
}
