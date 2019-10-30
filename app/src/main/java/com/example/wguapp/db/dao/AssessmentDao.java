package com.example.wguapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.db.entity.Term;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY goal_date")
    LiveData<List<Assessment>> getAssessments();
}
