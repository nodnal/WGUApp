package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.db.entity.Course;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private Repository repo;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessments;


    public MainViewModel(@NonNull Application application) {
        super(application);

        repo = Repository.getInstance(application);
        allCourses = repo.getAllCourses();
        allAssessments = repo.getAllAssessments();
    }

    public LiveData<List<Course>> getAllCourses(){
       return allCourses;
    }
    public LiveData<List<Assessment>> getAllAssessments(){
        return allAssessments;
    }
}
