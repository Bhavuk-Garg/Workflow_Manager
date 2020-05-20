package com.xor.taskExecutor.service;

import com.xor.taskExecutor.database.model.TaskNameEntity;

public interface TaskNameService {
    Iterable<TaskNameEntity> getTaskNameEntities();
    void save(TaskNameEntity taskNameEntity);
    void override(int id,String inputTaskName);
}
