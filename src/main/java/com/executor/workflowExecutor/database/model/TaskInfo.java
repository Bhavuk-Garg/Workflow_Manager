package com.executor.workflowExecutor.database.model;

import com.executor.workflowExecutor.components.utility.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="task_name")
public class TaskInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="task_id")
    private int id;

    @Column(name="task_name")
    @NotNull
    private String name;

    @NotNull
    @Enumerated(value=EnumType.STRING)
    private Status type;

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }


    public TaskInfo(){}

    public TaskInfo(int id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this. name = name;
    }
}
