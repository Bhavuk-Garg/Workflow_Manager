package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.database.repository.TaskNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskInfoServiceImpl implements TaskInfoService {
    @Autowired
    TaskNameRepository taskNameRepository;

    @Override
    public Iterable<TaskInfo> getAll(){
        return taskNameRepository.findAll();
    }

    @Override
    public void save(TaskInfo inputTaskInfo)
    {
        taskNameRepository.save(inputTaskInfo);
    }

    @Override
    public TaskInfo findById(int id) {
        return taskNameRepository.findById(id).orElse(null);
    }

}
