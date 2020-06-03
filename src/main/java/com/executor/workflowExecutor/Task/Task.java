package com.executor.workflowExecutor.Task;

import com.executor.workflowExecutor.components.utility.Status;

import java.util.List;
import java.util.Random;

public abstract class Task {
    List<String> outputs;
    Status type;

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }

    public void setOutputs(List<String> outputs) {
        this.outputs = outputs;
    }
    abstract String generateOutput();
    abstract public String execute();

}
