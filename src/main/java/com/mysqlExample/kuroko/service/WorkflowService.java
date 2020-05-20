package com.mysqlExample.kuroko.service;

import com.mysqlExample.kuroko.database.model.Workflow;

import java.util.List;

public interface WorkflowService {
    void saveWorkflow(Workflow inputWorkflow);
    Iterable<Workflow>  findAll();
    void executeWorkflow(Workflow inputWorkflow) throws IllegalAccessException;
    Workflow findByName(String name);
}
