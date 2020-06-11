package com.executor.workflowExecutor.Task;

import com.executor.workflowExecutor.components.utility.Status;
import java.util.List;

public abstract class ExecutableTask implements Executable{
    List<String> outputs;
    Status type;

    public ExecutableTask(List<String> outputs, Status type) {
        this.outputs = outputs;
        this.type = type;
    }
    public Status getType() {
        return type;
    }

    public List<String> getOutputs() {
        return outputs;
    }
}
