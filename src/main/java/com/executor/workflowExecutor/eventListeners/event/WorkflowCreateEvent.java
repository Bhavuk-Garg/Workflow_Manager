package com.executor.workflowExecutor.eventListeners.event;

public class WorkflowCreateEvent {
    String message;

    public WorkflowCreateEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
