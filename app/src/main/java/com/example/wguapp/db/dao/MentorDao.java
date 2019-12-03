package com.example.wguapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wguapp.db.entity.Mentor;

import java.util.List;

@Dao
public interface MentorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Mentor mentor);

    @Query("SELECT * FROM mentors")
    LiveData<List<Mentor>> getAllMentors();

    @Delete
    void delete(Mentor mentor);
}
