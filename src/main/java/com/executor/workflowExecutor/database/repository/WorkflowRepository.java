package com.executor.workflowExecutor.database.repository;

import com.executor.workflowExecutor.database.model.Workflow;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface WorkflowRepository extends PagingAndSortingRepository<Workflow,Integer> {
    Workflow findByName(String name);
    List<Workflow> findByNameLikeOrderByCreationDateDesc(String name);
}
