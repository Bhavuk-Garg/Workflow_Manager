package com.xor.taskExecutor.service;

import com.xor.taskExecutor.database.model.Workflow;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface WorkflowService {
    void saveWorkflow(Workflow inputWorkflow);
    Iterable<Workflow>  findAll();
    void executeWorkflow(Workflow inputWorkflow) throws IllegalAccessException;
    Workflow findByName(String name);
    List<Pair<Integer, String>> getFormattedResult(String res);
}
