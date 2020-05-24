package com.xor.taskExecutor.Task;

import com.xor.taskExecutor.util.RandomOutput;
import org.springframework.stereotype.Component;

@Component("dataTask")
public class DatabaseTask implements Task {
    @Override
    public String execute() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DataBase Task Executed");
        return RandomOutput.getOutput();
    }
}
