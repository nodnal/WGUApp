package com.example.wguapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wguapp.db.entity.Term;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Term term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Term[] term);

    @Query("SELECT * FROM terms ORDER BY start_date")
    LiveData<List<Term>> getTerms();

    @Query("SELECT * FROM terms WHERE id = :termId")
    LiveData<Term> getTerm(int termId);

    @Delete
    void delete(Term term);
}
