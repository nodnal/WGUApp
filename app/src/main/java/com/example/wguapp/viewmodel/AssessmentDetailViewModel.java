package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Assessment;

import java.util.Date;

public class AssessmentDetailViewModel extends AndroidViewModel {

    private MediatorLiveData<Assessment> assessment;
    private Repository repo;
    private MutableLiveData<Integer> assessmentId;
    private MutableLiveData<Integer> courseId;
    private MutableLiveData<Boolean> editable;


    public AssessmentDetailViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        editable = new MutableLiveData<>();
        assessmentId = new MutableLiveData<>();
        courseId = new MutableLiveData<>();
        assessment = new MediatorLiveData<>();

        assessment.addSource(repo.getAssessment(assessmentId), (a) -> {
            if (a != null) {
                assessment.setValue(a);
                editable.setValue(false);
            }

        });
        assessment.addSource(courseId, (id) ->{
            Assessment a = new Assessment("", "", "", new Date(), false, id);
            assessment.setValue(a);
            editable.setValue(true);
        } );

    }


    public void loadAssessment(int id){
        assessmentId.setValue(id);
    }

    public  void loadNewAssessment(int id){
        courseId.setValue(id);
    }

    public LiveData<Assessment> getAssessment(){
        return assessment;
    }

    public void saveAssessment(Assessment a){
        repo.saveAssessment(a, assessmentId);
        }

    public LiveData<Boolean> isEditable(){
        return editable;
    }

    public void setEditable(boolean isEditable){
        editable.setValue(isEditable);
    }

    public void deleteAssessment(Assessment assessment, int cId) {
        loadNewAssessment(cId);
        repo.deleteAssessment(assessment);
    }

    public LiveData<Integer> getCourseId() {
        return courseId;
    }
}
