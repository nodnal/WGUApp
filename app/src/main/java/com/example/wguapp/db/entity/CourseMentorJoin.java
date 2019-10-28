package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "course_mentor_join",
primaryKeys = {"course_id", "mentor_id"},
foreignKeys = {@ForeignKey(entity = Course.class,
                parentColumns = "id",
                childColumns = "course_id"),
                @ForeignKey(entity = Mentor.class,
                parentColumns = "id",
                childColumns = "mentor_id")},
                indices = {@Index("course_id"), @Index("mentor_id")})
public class CourseMentorJoin {
    @ColumnInfo(name = "course_id")
    private int courseId;
    @ColumnInfo(name = "mentor_id")
    private int mentorId;

    public CourseMentorJoin() { }

    public void setMentorId(int id) {
        this.mentorId = id;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setCourseId(int id) {
        this.courseId = id;
    }

    public int getCourseId() {
        return courseId;
    }

}
