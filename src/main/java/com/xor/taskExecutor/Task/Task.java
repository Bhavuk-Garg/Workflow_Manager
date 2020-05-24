package com.xor.taskExecutor.Task;

import java.util.List;
import java.util.Random;

public abstract class Task {
    List<String> outputs;

    public void setOutputs(List<String> outputs) {
        this.outputs = outputs;
    }

    String generateOutput(){
        Random random=new Random();
//        boolean throwException=random.nextBoolean();
//        if(throwException)  throw new RuntimeException();
//        else if(outputs.size()>0)
        {
            int idx=random.nextInt(outputs.size());
            return outputs.get(idx);
        }
//        else
//            return "";
    };


    abstract public String execute();

    @Override
    public String toString() {
        return "Task{" +
                "outputs=" + outputs +
                '}';
    }
}
