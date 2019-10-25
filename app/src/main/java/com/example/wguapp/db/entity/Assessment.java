package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Date;

@Entity(tableName = "assessments",indices = {@Index("course_id")}, foreignKeys = @ForeignKey(entity = Course.class, parentColumns = "id", childColumns = "course_id", onDelete = ForeignKey.CASCADE))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "course_id")
    private int courseId;
    private String title;
    private String type; //TODO: add enum for objective, performance, maybe practice
    @ColumnInfo(name = "goal_date")
    private Date goalDate;

    public Assessment(String title, String type, Date goalDate, int courseId) {
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

    public Date getGoalDate() {
        return goalDate;
    }
}
