package com.executor.workflowExecutor.database.repository;

import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.TaskInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskInfoRepository extends CrudRepository<TaskInfo,Integer> {
        Iterable<TaskInfo> findByType(Status type);
}
