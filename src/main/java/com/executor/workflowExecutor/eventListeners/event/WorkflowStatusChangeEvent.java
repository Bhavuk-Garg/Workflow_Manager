package com.executor.workflowExecutor.eventListeners.event;

import com.executor.workflowExecutor.database.model.Workflow;
import org.springframework.context.ApplicationEvent;

public class WorkflowStatusChangeEvent extends ApplicationEvent {
    private Workflow workflow;

    public WorkflowStatusChangeEvent(Object source, Workflow workflow) {
        super(source);
        this.workflow=workflow;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

}
