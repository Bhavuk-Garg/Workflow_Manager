package com.mysqlExample.kuroko.Task;

public class DatabaseTask implements Task {
    @Override
    public String execute() {
        System.out.println("DataBase Task Executed");
        return RandomOutput.getOutput();
    }
}
