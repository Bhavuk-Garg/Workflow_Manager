package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.TaskInfo;

public interface TaskInfoService {
    Iterable<TaskInfo> getAll();
    void save(TaskInfo taskInfo);
    TaskInfo findById(int id);
    void edit(Integer id, TaskInfo taskInfo);
    Iterable<TaskInfo> findByType(Status type);
}
