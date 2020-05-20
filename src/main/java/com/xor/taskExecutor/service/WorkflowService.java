package com.xor.taskExecutor.service;

import com.xor.taskExecutor.database.model.Workflow;

public interface WorkflowService {
    void saveWorkflow(Workflow inputWorkflow);
    Iterable<Workflow>  findAll();
    void executeWorkflow(Workflow inputWorkflow) throws IllegalAccessException;
    Workflow findByName(String name);
}
