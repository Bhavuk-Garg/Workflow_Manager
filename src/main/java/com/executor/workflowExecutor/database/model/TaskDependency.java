package com.executor.workflowExecutor.database.model;

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
    private TaskName fromTask;

    @Column(name="output")
    @NotNull
    private String output;

    @ManyToOne
    @JoinColumn(name="toTask")
    @NotNull
    private TaskName toTask;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskName getFromTask() {
        return fromTask;
    }

    public void setFromTask(TaskName fromTask) {
        this.fromTask = fromTask;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public TaskName getToTask() {
        return toTask;
    }

    public void setToTask(TaskName toTask) {
        this.toTask = toTask;
    }

}
