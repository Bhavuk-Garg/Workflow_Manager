package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.database.model.TaskDependency;
import com.executor.workflowExecutor.database.repository.TaskDependencyRepository;
import com.executor.workflowExecutor.database.repository.TaskInfoRepository;
import com.executor.workflowExecutor.dto.TaskDependencyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDependencyServieImpl implements TaskDependencyService{
    @Autowired
    TaskInfoRepository taskInfoRepository;
    @Autowired
    TaskDependencyRepository taskDependencyRepository;

    @Override
    public void add(TaskDependencyDTO inputDTO) {
        validate(inputDTO);
        TaskDependency newDependency=createEntityFromDTO(inputDTO);
        taskDependencyRepository.save(newDependency);
    }

    private void validate(TaskDependencyDTO inputDTO) {
        int fromTask=inputDTO.getFromTask();
        int toTask=inputDTO.getToTask();
        int prevOccurances=  taskDependencyRepository.countByFromTaskIdAndToTaskId(fromTask,toTask);
        if(prevOccurances>0 )
            throw new RuntimeException("Edge exists between "+inputDTO.getFromTask()+" and "+inputDTO.getToTask() );
        else if(fromTask==toTask)
            throw new RuntimeException("from and To should be different" );
    }


    private TaskDependency createEntityFromDTO(TaskDependencyDTO inputDTO){
        /*
        * converting DTO object to TaskDependency because TaskDependency is required to store in database
        */
        TaskDependency newDependency=new TaskDependency();

        newDependency.setFromTask(taskInfoRepository.findById(inputDTO.getFromTask()).orElse(null));
        newDependency.setToTask(taskInfoRepository.findById(inputDTO.getToTask()).orElse(null));
        newDependency.setOutput(inputDTO.getOutput());
        return newDependency;
    }

    @Override
    public Iterable<TaskDependency> getAll(){
        return taskDependencyRepository.findAll();
    }

    @Override
    public void edit(int id, TaskDependencyDTO inputDTO) {
        if(taskDependencyRepository.findById(id).equals(null))
            return;
        TaskDependency newDependency=createEntityFromDTO(inputDTO);
        newDependency.setId(id);
        taskDependencyRepository.save(newDependency);
    }

    @Override
    public List<TaskDependency> findByFromId(int id) {
        return taskDependencyRepository.findByFromTaskId(id);
    }
}
