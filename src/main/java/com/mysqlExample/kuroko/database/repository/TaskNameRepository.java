package com.mysqlExample.kuroko.database.repository;

import com.mysqlExample.kuroko.database.model.TaskNameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskNameRepository extends CrudRepository<TaskNameEntity,Integer> {

}
