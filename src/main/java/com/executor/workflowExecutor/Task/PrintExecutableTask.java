package com.executor.workflowExecutor.Task;

import com.executor.workflowExecutor.components.utility.Status;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component("printTask")
@Scope("prototype")
public class PrintExecutableTask extends ExecutableTask {

    public PrintExecutableTask(List<String> outputs, Status type) {
        super(outputs, type);
    }

    public String execute()
    {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("print task executed");
        return generateOutput();
    }

    private String generateOutput() {
//        throw new RuntimeException();
        if(outputs.size()==0)   return null;
        Random random=new Random();
        int randomVal=random.nextInt(outputs.size()+1);

        if(randomVal==outputs.size())  throw new RuntimeException();

        int idx=random.nextInt(outputs.size());
        return outputs.get(idx);
    }
}
