package com.mysqlExample.kuroko.service;

import com.mysqlExample.kuroko.database.model.TaskDependency;
import com.mysqlExample.kuroko.dto.TaskDependencyDTO;

public interface TaskDependencyService {
    void addTaskDependency(TaskDependencyDTO dto);
    Iterable<TaskDependency> getAllDependencies();
    void editTask(int id, TaskDependencyDTO dto);
}
