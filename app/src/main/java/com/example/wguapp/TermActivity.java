package com.example.wguapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.ui.FragmentListener;
import com.example.wguapp.ui.TermDetailFragment;
import com.example.wguapp.ui.TermListFragment;
import com.example.wguapp.viewmodel.TermListViewModel;

public class TermActivity extends AppCompatActivity implements FragmentListener {

    private TermListViewModel termListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        initViewModel();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TermListFragment.newInstance())
                    .commitNow();
        }
    }

    private void initViewModel() {
        termListViewModel = ViewModelProviders.of(this).get(TermListViewModel.class);
    }

    @Override
    public void onNavigateToFragment(String fragment) {
            switch(fragment){
                case("TermListFragment"):
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, TermListFragment.newInstance())
                        .commitNow();
                case("TermDetailFragment"):
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, TermDetailFragment.newInstance())
                            .commitNow();

        }
    }
}
