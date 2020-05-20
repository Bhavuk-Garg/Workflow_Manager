package com.xor.taskExecutor.service;

import com.xor.taskExecutor.database.model.TaskDependency;
import com.xor.taskExecutor.dto.TaskDependencyDTO;

public interface TaskDependencyService {
    void addTaskDependency(TaskDependencyDTO dto);
    Iterable<TaskDependency> getAllDependencies();
    void editTask(int id, TaskDependencyDTO dto);
}
