package com.xor.taskExecutor.database.repository;

import com.xor.taskExecutor.database.model.TaskNameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskNameRepository extends CrudRepository<TaskNameEntity,Integer> {

}
