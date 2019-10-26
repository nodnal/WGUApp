package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Term;

import java.util.Date;
import java.util.List;

public class TermListViewModel extends AndroidViewModel {
    private Repository repo;
    private LiveData<List<Term>> allTerms;


    public TermListViewModel(@NonNull Application application) {
        super(application);

        repo = Repository.getInstance(application);
        allTerms = repo.getAllTerms();

    }

    public LiveData<List<Term>> getAllTerms(){
        return allTerms;
    }

    public void insertTerm(Term term){
        repo.insertTerm(term);
    }

}
