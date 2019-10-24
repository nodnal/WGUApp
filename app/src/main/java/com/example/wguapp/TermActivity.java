package com.example.wguapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.ui.TermListFragment;
import com.example.wguapp.viewmodel.TermViewModel;

public class TermActivity extends AppCompatActivity {

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TermListFragment.newInstance())
                    .commitNow();
        }

        initViewModel();
    }

    private void initViewModel() {
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
    }
}
