package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Mentor;

public class MentorDetailViewModel extends AndroidViewModel {

    private Repository repo;

    private MediatorLiveData<Mentor> mentor;
    private MutableLiveData<Integer> mentorId;
    private MutableLiveData<Integer> courseId;
    private MutableLiveData<Boolean> editable;

    public MentorDetailViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        editable = new MutableLiveData<>();
        mentorId = new MutableLiveData<>();
        courseId = new MutableLiveData<>();
        mentor = new MediatorLiveData<>();

        mentor.addSource(repo.getMentor(mentorId), (m) -> {
            mentor.setValue(m);
            editable.setValue(false);
        });
        mentor.addSource(courseId, (id) ->{
            Mentor m = new Mentor("", "", "", "");
            mentor.setValue(m);
            editable.setValue(true);
        } );
    }
    public void loadMentor(int id){
        mentorId.setValue(id);
    }

    public  void loadNewMentor(int id){
        courseId.setValue(id);
    }

    public LiveData<Mentor> getMentor(){
        return mentor;
    }

    public void saveMentor(Mentor m){
        repo.saveMentor(m, mentorId, courseId);
    }

    public void saveNewMentor(Mentor m, int courseId){
        repo.saveNewMentor(m, courseId, mentorId);
    }

    public LiveData<Boolean> isEditable(){
        return editable;
    }

    public void setEditable(boolean isEditable){
        editable.setValue(isEditable);
    }

    public LiveData<Integer> getCourseId() {
        return courseId;
    }

    public void deleteMentor(Mentor currentMentor) {
        repo.deleteMentor(currentMentor);
    }
}
