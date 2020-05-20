package com.xor.taskExecutor.database.repository;

import com.xor.taskExecutor.database.model.Workflow;
import org.springframework.data.repository.CrudRepository;

public interface WorkflowRepository extends CrudRepository<Workflow,Integer> {
    Workflow findByName(String name);
}
