package com.example.wguapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wguapp.db.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note note);

    @Query("SELECT * FROM notes ORDER BY title")
    LiveData<List<Note>> getNotes();

    @Delete
    void delete(Note note);
}
