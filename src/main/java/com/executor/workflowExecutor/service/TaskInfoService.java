package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.database.model.TaskInfo;

public interface TaskInfoService {
    Iterable<TaskInfo> getAll();
    void save(TaskInfo taskInfo);
    TaskInfo findById(int id);
}
