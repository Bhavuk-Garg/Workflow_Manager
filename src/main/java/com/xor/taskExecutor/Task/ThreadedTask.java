package com.xor.taskExecutor.Task;

import com.xor.taskExecutor.util.RandomOutput;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("threadedTask")
@Scope("prototype")
public class ThreadedTask extends Task{

    @Override
    public String execute() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Threaded Task Executed");
        return generateOutput();
    }
}
