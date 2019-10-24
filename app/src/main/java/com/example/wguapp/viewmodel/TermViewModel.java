package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Term;

import java.util.Date;
import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private Repository repo;
    private LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repo = new Repository(application);
        repo.insertTerm(new Term("title1", new Date(1), new Date(6)));
        allTerms = repo.getAllTerms();
    }



    public LiveData<List<Term>> getAllTerms(){
        return allTerms;
    }

    public void insertTerm(){
        int number = allTerms.getValue().size();
        repo.insertTerm(new Term("title"+number, new Date(1), new Date(6)));
    }
}
