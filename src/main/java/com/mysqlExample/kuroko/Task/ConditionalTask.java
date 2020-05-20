package com.mysqlExample.kuroko.Task;

public class ConditionalTask implements Task {
    @Override
    public String execute() {
        System.out.println("conditional task Executed");
        return RandomOutput.getOutput();
    }
}
