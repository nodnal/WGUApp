package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments",indices = {@Index("course_id")}, foreignKeys = @ForeignKey(entity = Course.class, parentColumns = "id", childColumns = "course_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "course_id")
    private int courseId;
    @ColumnInfo(name = "title")
    public String Title;
    @ColumnInfo(name = "type")
    public String Type; //TODO: add enum for objective, performance, maybe practice
    @ColumnInfo(name = "goal_date")
    public Date GoalDate;
    @ColumnInfo(name = "alert")
    public boolean Alert;
    @ColumnInfo(name = "status")
    public String Status;

    public Assessment(){}

    public Assessment(String title, String type, String status, Date goalDate, boolean alert, int courseId) {
        this.Title = title;
        this.Type = type;
        this.Status = status;
        this.GoalDate = goalDate;
        this.courseId = courseId;
        this.Alert = alert;
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

    public void setCourseId(int id) {
        this.courseId = id;
    }
}
