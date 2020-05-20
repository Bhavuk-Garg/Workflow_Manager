package com.mysqlExample.kuroko.service;

import com.mysqlExample.kuroko.database.model.TaskNameEntity;

public interface TaskNameService {
    Iterable<TaskNameEntity> getTaskNameEntities();
    void save(TaskNameEntity taskNameEntity);
    void override(int id,String inputTaskName);
}
