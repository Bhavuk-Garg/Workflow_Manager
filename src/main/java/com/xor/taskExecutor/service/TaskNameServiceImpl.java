package com.xor.taskExecutor.service;

import com.xor.taskExecutor.database.model.TaskNameEntity;
import com.xor.taskExecutor.database.repository.TaskNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskNameServiceImpl implements TaskNameService{
    @Autowired
    TaskNameRepository taskNameRepository;

    @Override
    public Iterable<TaskNameEntity> getTaskNameEntities(){
        return taskNameRepository.findAll();
    }

    @Override
    public void save(TaskNameEntity inputTaskNameEntity)
    {
        taskNameRepository.save(inputTaskNameEntity);
    }

    @Override
    public void override(int id, String inputTaskName) {
        TaskNameEntity inputTaskNameEntity =new TaskNameEntity(id,inputTaskName);
        taskNameRepository.save(inputTaskNameEntity);
    }

    @Override
    public TaskNameEntity findById(int id) {
        return taskNameRepository.findById(id).orElse(null);
    }

}
