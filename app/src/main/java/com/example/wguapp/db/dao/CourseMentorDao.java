package com.example.wguapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wguapp.db.entity.CourseMentorJoin;
import com.example.wguapp.db.entity.Mentor;

@Dao
public interface CourseMentorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseMentorJoin join);

    @Delete
    void delete(CourseMentorJoin join);

    @Query("SELECT mentors.* " +
            "FROM courses LEFT JOIN course_mentor_join ON courses.id = course_mentor_join.course_id " +
            "LEFT JOIN mentors ON mentors.id = course_mentor_join.mentor_id " +
            "WHERE courses.id = :courseId")
    LiveData<Mentor> getAllMentorsForCourseId(int courseId);



}