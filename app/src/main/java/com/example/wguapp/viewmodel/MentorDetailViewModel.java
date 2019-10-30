package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.wguapp.db.Repository;

public class MentorDetailViewModel extends AndroidViewModel {
    private Repository repo;
    public MentorDetailViewModel(@NonNull Application application) {
        super(application);
    }
}
