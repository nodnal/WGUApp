package com.example.wguapp.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes", foreignKeys = @ForeignKey(entity = Course.class, parentColumns = "id", childColumns = "courseId", onDelete = ForeignKey.CASCADE))
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int courseId;
    private String title;
    private String note;

    public Note(String title, String note, int courseId) {
        this.title = title;
        this.note = note;
        this.courseId = courseId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCourseId(){
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }
}
