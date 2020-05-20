package com.xor.taskExecutor.database.repository;

import com.xor.taskExecutor.database.model.TaskDependency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDependencyRepository extends CrudRepository<TaskDependency,Integer> {
    int countByFromTaskIdAndToTaskId(int fromTaskId,int toTaskId);
    List<TaskDependency> findByOutput(String output);
}
