package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Note;

public class NoteDetailViewModel extends AndroidViewModel {

    private MediatorLiveData<Note> note;
    private Repository repo;
    private MutableLiveData<Integer> noteId;
    private MutableLiveData<Integer> courseId;
    private MutableLiveData<Boolean> editable;


    public NoteDetailViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        editable = new MutableLiveData<>();
        noteId = new MutableLiveData<>();
        courseId = new MutableLiveData<>();
        note = new MediatorLiveData<>();

        note.addSource(repo.getNote(noteId), (n) -> {
            note.setValue(n);
            editable.setValue(false);
        });
        note.addSource(courseId, (id) ->{
            Note n = new Note("", "", id);
            note.setValue(n);
            editable.setValue(true);
        } );

    }

    public void loadNote(int id){
        noteId.setValue(id);
    }

    public  void loadNewNote(int id){
        courseId.setValue(id);
    }

    public LiveData<Note> getNote(){
        return note;
    }

    public void saveNote(Note n){
        repo.saveNote(n, noteId);
    }

    public LiveData<Boolean> isEditable(){
        return editable;
    }

    public void setEditable(boolean isEditable){
        editable.setValue(isEditable);
    }

    public void deleteNote(Note currentNote, int cId) {
        loadNewNote(cId);
        repo.deleteNote(currentNote);
    }

    public LiveData<Integer> getCourseId() {
        return courseId;
    }
}
