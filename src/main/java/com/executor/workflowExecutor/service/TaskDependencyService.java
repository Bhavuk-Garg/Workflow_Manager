package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.database.model.TaskDependency;
import com.executor.workflowExecutor.dto.TaskDependencyDTO;

import java.util.List;

public interface TaskDependencyService {
    void save(TaskDependencyDTO dto);
    Iterable<TaskDependency> getAll();
    void edit(int id, TaskDependencyDTO dto);
    List<TaskDependency> findByFromId(int id);
}
