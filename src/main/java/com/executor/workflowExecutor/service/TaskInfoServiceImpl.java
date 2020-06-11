package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.database.model.TimedWaitTasks;
import com.executor.workflowExecutor.database.repository.TaskInfoRepository;
import com.executor.workflowExecutor.database.repository.TimedWaitTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskInfoServiceImpl implements TaskInfoService {
    @Autowired
    TaskInfoRepository taskInfoRepository;

    @Override
    public Iterable<TaskInfo> getAll(){
        return taskInfoRepository.findAll();
    }

    @Override
    public void save(TaskInfo inputTaskInfo)
    {
        taskInfoRepository.save(inputTaskInfo);
    }

    @Override
    public TaskInfo findById(int id) {
        return taskInfoRepository.findById(id).orElse(null);
    }

    @Override
    public void edit(Integer id, TaskInfo taskInfo) {
        taskInfo.setId(id);
        taskInfoRepository.save(taskInfo);
    }

    @Override
    public Iterable<TaskInfo> findByType(Status type) {
        return taskInfoRepository.findByType(type);
    }
}
