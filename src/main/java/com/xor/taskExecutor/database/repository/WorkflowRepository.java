package com.xor.taskExecutor.database.repository;

import com.xor.taskExecutor.database.model.Workflow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface WorkflowRepository extends PagingAndSortingRepository<Workflow,Integer> {
    Workflow findByName(String name);
    List<Workflow> findByNameLike(String name);

}
