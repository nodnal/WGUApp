package com.example.wguapp;

import androidx.room.Entity;

import java.sql.Timestamp;

@Entity
public class Assessment {
    private int id;
    private String title;
    private String type; //TODO: add enum for objective, performance, maybe practice
    private Timestamp goalDate;

    public Assessment(String title, String type, Timestamp goalDate) {
        this.title = title;
        this.type = type;
        this.goalDate = goalDate;
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

    public String getType() {
        return type;
    }

    public Timestamp getGoalDate() {
        return goalDate;
    }
}
