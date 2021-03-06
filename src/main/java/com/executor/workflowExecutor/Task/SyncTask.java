package com.executor.workflowExecutor.Task;

import com.executor.workflowExecutor.components.utility.Status;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component("syncTask")
@Scope("prototype")
public class SyncTask extends ExecutableTask {

    public SyncTask(List<String> outputs, Status type) {
        super(outputs, type);
    }

    @Override
    public String execute() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sync Task Executed");
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
