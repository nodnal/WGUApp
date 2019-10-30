package com.example.wguapp.viewmodel;

import android.app.Application;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Assessment;

public class AssessmentDetailViewModel extends AndroidViewModel {
    private LiveData<Assessment> assessment;
    private Repository repo;
    private MutableLiveData<Integer> assessmentId;
    private MutableLiveData<Integer> courseId;
    private MutableLiveData<Boolean> editable;


    public AssessmentDetailViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        editable = new MutableLiveData<>();
        assessmentId = new MutableLiveData<>();
        assessment = repo.getAssessment(assessmentId);

    }


    public void loadAssessment(int id){
        assessmentId.postValue(id);
    }

    public  void loadNewAssessment(int id){
        courseId.postValue(id);
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
        editable.postValue(isEditable);
    }

}
