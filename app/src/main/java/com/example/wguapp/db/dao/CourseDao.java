package com.example.wguapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wguapp.db.entity.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);

    @Query("SELECT * FROM courses ORDER BY start_date")
    LiveData<List<Course>> getCourses();

    @Query("Select * FROM courses WHERE id = :id")
    LiveData<Course> getCourse(Integer id);
}
