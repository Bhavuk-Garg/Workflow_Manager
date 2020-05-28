package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.database.model.Workflow;

import java.util.List;

public interface WorkflowService {
    void saveWorkflow(Workflow inputWorkflow);
    Iterable<Workflow>  findAll();
    void executeWorkflow(Workflow inputWorkflowe);
    Workflow findByName(String name);
    List<Workflow> findByNameLike(String name);
}
