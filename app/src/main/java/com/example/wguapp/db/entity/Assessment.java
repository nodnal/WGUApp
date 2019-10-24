package com.example.wguapp.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.sql.Timestamp;

@Entity(tableName = "assessments", foreignKeys = @ForeignKey(entity = Course.class, parentColumns = "id", childColumns = "courseId", onDelete = ForeignKey.CASCADE))
public class Assessment {
    private int id;
    private int courseId;
    private String title;
    private String type; //TODO: add enum for objective, performance, maybe practice
    private Timestamp goalDate;

    public Assessment(String title, String type, Timestamp goalDate, int courseId) {
        this.title = title;
        this.type = type;
        this.goalDate = goalDate;
        this.courseId = courseId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
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
