package com.example.wguapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Update;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wguapp.db.entity.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Course course);

    @Query("SELECT count() FROM courses WHERE id = :id")
    int getCourseCount(Integer id);

    @Update()
    void updateCourse(Course course);

    @Query("SELECT * FROM courses ORDER BY start_date")
    LiveData<List<Course>> getCourses();

    @Query("Select * FROM courses WHERE id = :id")
    Course getCourse(Integer id);

    @Delete
    void delete(Course course);
}
