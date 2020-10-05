package com.example.wguapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wguapp.db.entity.CourseMentorJoin;

import java.util.List;

@Dao
public interface CourseMentorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(CourseMentorJoin join);

    @Delete
    void delete(CourseMentorJoin join);

    @Query("SELECT * FROM course_mentor_join")
    LiveData<List<CourseMentorJoin>> getAllCourseMentorJoins();

    @Query("Select * FROM course_mentor_join where course_id = :courseId AND mentor_id =:mentorId")
    LiveData<CourseMentorJoin> getCourseMentorJoin(int courseId, int mentorId);

/*    @Query("SELECT mentors.* " +
            "FROM courses LEFT JOIN course_mentor_join ON courses.id = course_mentor_join.course_id " +
            "LEFT JOIN mentors ON mentors.id = course_mentor_join.mentor_id " +
            "WHERE courses.id = :courseId")
    LiveData<Mentor> getAllMentorsForCourseId(int courseId);*/



}
