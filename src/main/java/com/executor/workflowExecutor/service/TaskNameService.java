package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.database.model.TaskName;

public interface TaskNameService {
    Iterable<TaskName> getAll();
    void save(TaskName taskName);
    void edit(int id,String inputTaskName);
    TaskName findById(int id);
}
