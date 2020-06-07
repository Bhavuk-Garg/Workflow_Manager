package com.executor.workflowExecutor.database.repository;

import com.executor.workflowExecutor.database.model.TimedWaitTasks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimedWaitTasksRepository extends CrudRepository<TimedWaitTasks,Integer> {

}
