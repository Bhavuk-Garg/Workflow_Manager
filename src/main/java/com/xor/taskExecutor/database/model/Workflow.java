package com.xor.taskExecutor.database.model;

import javax.persistence.*;

@Entity
@Table(name="workflow")
public class Workflow {
    @Id
    String name;

    @Column(name = "status",columnDefinition = "varchar(32) default 'Waiting'")
    @Enumerated(value=EnumType.STRING)
    Status status=Status.Waiting;

    String result;

    public Workflow(){}
    public Workflow(String name, Status status, String result) {
        this.name = name;
        this.status = status;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
