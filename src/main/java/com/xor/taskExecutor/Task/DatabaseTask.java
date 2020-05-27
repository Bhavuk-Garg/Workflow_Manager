package com.xor.taskExecutor.Task;

import com.xor.taskExecutor.util.RandomOutput;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("dataTask")
@Scope("prototype")
public class DatabaseTask extends Task {

    @Override
    public String execute() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DataBase Task Executed");
        return generateOutput();
    }
    @Override
    String generateOutput() {
        if(outputs.size()==0)   return null;
        Random random=new Random();
        int randomVal=random.nextInt(outputs.size()+1);

        if(randomVal==outputs.size())  throw new RuntimeException();

        int idx=random.nextInt(outputs.size());
        return outputs.get(idx);
    };

}
