package com.example.wguapp;

import androidx.room.Entity;
import java.sql.Timestamp;

@Entity
public class Term {
    private int id;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;

    public Term(String title, Timestamp startDate, Timestamp endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
