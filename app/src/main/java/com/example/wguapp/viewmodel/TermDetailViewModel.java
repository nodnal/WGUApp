package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Term;

import java.util.List;

public class TermDetailViewModel extends AndroidViewModel {
    public MutableLiveData<Term> Term;
    private Repository repo;


    public TermDetailViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        Term = new MutableLiveData<>();

    }

    public void LoadTerm(int id){
        repo.getTerm(id, Term);
    }

    public void SaveTerm(Term term){
        repo.insertTerm(term);
    }


}
