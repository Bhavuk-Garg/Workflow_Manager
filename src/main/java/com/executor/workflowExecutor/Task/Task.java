package com.executor.workflowExecutor.Task;

import java.util.List;
import java.util.Random;

public abstract class Task {
    List<String> outputs;
    public void setOutputs(List<String> outputs) {
        this.outputs = outputs;
    }
    abstract String generateOutput();
    abstract public String execute();

}
