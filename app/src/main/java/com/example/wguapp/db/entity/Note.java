package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes",indices = {@Index("course_id")}, foreignKeys = @ForeignKey(entity = Course.class, parentColumns = "id", childColumns = "course_id", onDelete = ForeignKey.CASCADE))
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "course_id")
    private int courseId;
    public String Title;
    public String Note;

    public Note(){}

    public Note(String title, String note, int courseId) {
        this.Title = title;
        this.Note = note;
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
    public void setCourseId(int courseId){
        this.courseId = courseId;
    }

}
