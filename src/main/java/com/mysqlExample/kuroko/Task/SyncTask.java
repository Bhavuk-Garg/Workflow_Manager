package com.mysqlExample.kuroko.Task;

public class SyncTask implements Task {
    @Override
    public String execute() {
        System.out.println("Sync Task Executed");
        return RandomOutput.getOutput();
    }
}
