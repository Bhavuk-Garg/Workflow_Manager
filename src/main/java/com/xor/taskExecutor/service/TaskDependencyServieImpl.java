package com.xor.taskExecutor.service;

import com.xor.taskExecutor.database.model.TaskDependency;
import com.xor.taskExecutor.database.repository.TaskNameRepository;
import com.xor.taskExecutor.database.repository.TaskDependencyRepository;
import com.xor.taskExecutor.dto.TaskDependencyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDependencyServieImpl implements TaskDependencyService{
    @Autowired
    TaskNameRepository taskNameRepository;
    @Autowired
    TaskDependencyRepository taskDependencyRepository;

    @Override
    public void addTaskDependency(TaskDependencyDTO inputDTO) {
        checkPreviousOccurance(inputDTO);
        TaskDependency newDependency=createEntityFromDTO(inputDTO);
        taskDependencyRepository.save(newDependency);
    }

    private void checkPreviousOccurance(TaskDependencyDTO inputDTO) {
        int fromTask=inputDTO.getFromTask();
        int toTask=inputDTO.getToTask();
        int prevOccurances=  taskDependencyRepository.countByFromTaskIdAndToTaskId(fromTask,toTask);
        if(prevOccurances>0)
            throw new RuntimeException("from "+inputDTO.getFromTask()+" to "+inputDTO.getToTask()+" edge exists" );
    }


    private TaskDependency createEntityFromDTO(TaskDependencyDTO inputDTO){
        TaskDependency newDependency=new TaskDependency();

        newDependency.setFromTask(taskNameRepository.findById(inputDTO.getFromTask()).orElse(null));
        newDependency.setToTask(taskNameRepository.findById(inputDTO.getToTask()).orElse(null));
        newDependency.setOutput(inputDTO.getOutput());
        return newDependency;
    }

    @Override
    public Iterable<TaskDependency> getAllDependencies(){
        return taskDependencyRepository.findAll();
    }

    @Override
    public void editTask(int id, TaskDependencyDTO inputDTO) {
        if(taskDependencyRepository.findById(id).equals(null))
            return;
        TaskDependency newDependency=createEntityFromDTO(inputDTO);
        newDependency.setId(id);
        taskDependencyRepository.save(newDependency);
    }
}
