package com.example.wguapp;

import androidx.room.Entity;

import java.sql.Timestamp;

@Entity
public class Course {
    private int id;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
    private String Status; //TODO: add enum

    public Course(String title, Timestamp startDate, Timestamp endDate, String status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        Status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return Status;
    }
}
