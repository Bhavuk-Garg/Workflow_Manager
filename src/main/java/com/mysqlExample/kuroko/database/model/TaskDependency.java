package com.mysqlExample.kuroko.database.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="task_dependency")
public class TaskDependency {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="fromTask")
    @NotNull
    private TaskNameEntity fromTask;

    @Column(name="output")
    private String output;

    @ManyToOne
    @JoinColumn(name="toTask")
    @NotNull
    private TaskNameEntity toTask;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskNameEntity getFromTask() {
        return fromTask;
    }

    public void setFromTask(TaskNameEntity fromTask) {
        this.fromTask = fromTask;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public TaskNameEntity getToTask() {
        return toTask;
    }

    public void setToTask(TaskNameEntity toTask) {
        this.toTask = toTask;
    }
}
