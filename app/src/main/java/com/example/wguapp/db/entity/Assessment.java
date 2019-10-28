package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments",indices = {@Index("course_id")}, foreignKeys = @ForeignKey(entity = Course.class, parentColumns = "id", childColumns = "course_id", onDelete = ForeignKey.CASCADE))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "course_id")
    private int courseId;
    public String Title;
    public String Type; //TODO: add enum for objective, performance, maybe practice
    @ColumnInfo(name = "goal_date")
    public Date GoalDate;

    public Assessment(){}

    public Assessment(String title, String type, Date goalDate, int courseId) {
        this.Title = title;
        this.Type = type;
        this.GoalDate = goalDate;
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

    public void setCourseId(int id) {
        this.courseId = id;
    }
}
