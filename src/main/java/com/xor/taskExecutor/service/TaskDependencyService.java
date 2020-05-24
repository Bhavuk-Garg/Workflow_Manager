package com.xor.taskExecutor.service;

import com.xor.taskExecutor.database.model.TaskDependency;
import com.xor.taskExecutor.dto.TaskDependencyDTO;

import java.util.List;

public interface TaskDependencyService {
    void addTaskDependency(TaskDependencyDTO dto);
    Iterable<TaskDependency> getAllDependencies();
    void editTask(int id, TaskDependencyDTO dto);
    List<TaskDependency> findDependentEntriesById(int id);
}
