package com.example.wguapp.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "courses", foreignKeys = @ForeignKey(entity = Term.class, parentColumns = "id", childColumns = "termId", onDelete = ForeignKey.RESTRICT))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int termId;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
    private String status; //TODO: add enum

    public Course(String title, Timestamp startDate, Timestamp endDate, String status, int termId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termId = termId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTermId(){
        return termId;
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
        return status;
    }
}
