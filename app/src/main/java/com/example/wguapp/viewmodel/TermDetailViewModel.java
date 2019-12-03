package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.Term;

import java.util.List;
import java.util.stream.Collectors;

public class TermDetailViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> termId;
    private LiveData<Term> term;
    private LiveData<List<Course>> courses;
    private Repository repo;


    public TermDetailViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        termId = new MutableLiveData<>();
        term = Transformations.switchMap(termId,(id) -> repo.getTerm(id));
        courses = Transformations.switchMap(term, (term) -> {
            if(term != null) {
                return Transformations.map(repo.getAllCourses(), (courseList) -> courseList.stream()
                        .filter(course -> course.getTermId() == term.getId())
                        .collect(Collectors.toList()));
            }else{
                return null;
        }
        });
    }

    public void LoadTerm(int id){
      termId.postValue(id);
    }

    public void SaveTerm(Term term){
        repo.insertTerm(term);
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public LiveData<Term> getTerm() {
        return term;
    }

    public void DeleteTerm(Term term) {
        repo.deleteTerm(term);
    }
}
