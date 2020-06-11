package com.executor.workflowExecutor.database.model;

import com.executor.workflowExecutor.components.utility.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="workflow")
public class Workflow {
    @Id
    String name;

    @Column(name = "status",columnDefinition = "varchar(32) default 'NORMAL'")
    @Enumerated(value=EnumType.STRING)
    Status status=Status.NORMAL;

    @Column(name="creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date creationDate=new Date();
    @Column(columnDefinition="text")
    String result="";
    String triggers="";

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTriggers() {
        return triggers;
    }

    public void setTriggers(String triggers) {
        this.triggers = triggers;
    }

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
