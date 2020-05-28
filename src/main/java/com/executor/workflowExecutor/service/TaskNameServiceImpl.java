package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.database.model.TaskName;
import com.executor.workflowExecutor.database.repository.TaskNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskNameServiceImpl implements TaskNameService{
    @Autowired
    TaskNameRepository taskNameRepository;

    @Override
    public Iterable<TaskName> getAll(){
        return taskNameRepository.findAll();
    }

    @Override
    public void save(TaskName inputTaskName)
    {
        taskNameRepository.save(inputTaskName);
    }

    @Override
    public void edit(int id, String inputTaskName) {
        TaskName inputTaskNameEntity =new TaskName(id,inputTaskName);
        taskNameRepository.save(inputTaskNameEntity);
    }

    @Override
    public TaskName findById(int id) {
        return taskNameRepository.findById(id).orElse(null);
    }

}
