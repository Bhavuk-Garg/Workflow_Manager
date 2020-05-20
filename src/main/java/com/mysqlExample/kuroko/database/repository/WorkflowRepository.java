package com.mysqlExample.kuroko.database.repository;

import com.mysqlExample.kuroko.database.model.Workflow;
import org.springframework.data.repository.CrudRepository;

public interface WorkflowRepository extends CrudRepository<Workflow,Integer> {
    Workflow findByName(String name);
}
