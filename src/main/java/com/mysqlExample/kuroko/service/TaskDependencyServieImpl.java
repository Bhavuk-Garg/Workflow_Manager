package com.mysqlExample.kuroko.service;

import com.mysqlExample.kuroko.database.model.TaskDependency;
import com.mysqlExample.kuroko.database.repository.TaskNameRepository;
import com.mysqlExample.kuroko.database.repository.TaskDependencyRepository;
import com.mysqlExample.kuroko.dto.TaskDependencyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TaskDependencyServieImpl implements TaskDependencyService{
    @Autowired
    TaskNameRepository taskNameRepository;
    @Autowired
    TaskDependencyRepository taskDependencyRepository;

    @Override
    public void addTaskDependency(TaskDependencyDTO dto) {
        checkPreviousOccurance(dto);

        TaskDependency newDependency=createEntityFromDTO(dto);
        System.out.println(newDependency.getId());
        taskDependencyRepository.save(newDependency);
    }

    private void checkPreviousOccurance(TaskDependencyDTO dto) {
        int fromTask=dto.getFromTask();
        int toTask=dto.getToTask();
        int prevOccurances=  taskDependencyRepository.countByFromTaskIdAndToTaskId(fromTask,toTask);
        if(prevOccurances>0)
            throw new DataIntegrityViolationException("from "+dto.getFromTask()+" to "+dto.getToTask()+" edge exists" );
    }

    private TaskDependency createEntityFromDTO(TaskDependencyDTO dto) throws NullPointerException{
        TaskDependency newDependency=new TaskDependency();

        newDependency.setFromTask(taskNameRepository.findById(dto.getFromTask()).orElse(null));
        newDependency.setToTask(taskNameRepository.findById(dto.getToTask()).orElse(null));
        newDependency.setOutput(dto.getOutput());
        return newDependency;
    }

    @Override
    public Iterable<TaskDependency> getAllDependencies(){
        return taskDependencyRepository.findAll();
    }

    @Override
    public void editTask(int id, TaskDependencyDTO dto) {
        if(taskDependencyRepository.findById(id).equals(null))
            return;
        TaskDependency newDependency=createEntityFromDTO(dto);
        newDependency.setId(id);
        taskDependencyRepository.save(newDependency);
    }
}
