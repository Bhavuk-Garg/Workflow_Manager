package com.executor.workflowExecutor.database.model;

import com.executor.workflowExecutor.components.utility.Status;
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

    @Column(name="time")
    int time=5;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
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
