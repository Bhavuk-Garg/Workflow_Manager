package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.database.model.Workflow;

import java.util.List;

public interface WorkflowService {
    void saveWorkflow(Workflow inputWorkflow);
    Iterable<Workflow>  findAll();
    Workflow findByName(String name);
    List<Workflow> findByNameLike(String name);
}
