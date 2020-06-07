package com.executor.workflowExecutor.Task;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

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

    private String generateOutput() {
        if(outputs.size()==0)   return null;
        Random random=new Random();
        int randomVal=random.nextInt(outputs.size()+1);

        if(randomVal==outputs.size())  throw new RuntimeException();

        int idx=random.nextInt(outputs.size());
        return outputs.get(idx);
    };
}
