package com.xor.taskExecutor.Task;

import com.xor.taskExecutor.util.RandomOutput;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("printTask")
@Scope("prototype")
public class PrintTask extends Task {

    public String execute()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("print task executed");
        return generateOutput();
    }

    @Override
    String generateOutput() {
        Random random=new Random();
        int randomVal=random.nextInt(outputs.size()+1);

        if(randomVal==outputs.size())  throw new RuntimeException();

        int idx=random.nextInt(outputs.size());
        return outputs.get(idx);
    };
}
