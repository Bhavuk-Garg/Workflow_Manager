package com.xor.taskExecutor.database.model;

import com.xor.taskExecutor.util.Status;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name="workflow")
public class Workflow {
    @Id
    String name;

    @Column(name = "status",columnDefinition = "varchar(32) default 'Waiting'")
    @Enumerated(value=EnumType.STRING)
    Status status=Status.Waiting;

    @Column(name="creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate=new Date();
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

    @Override
    public String toString() {
        return "Workflow{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", result='" + result + '\'' +
                '}';
    }
}
