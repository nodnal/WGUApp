package com.example.wguapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.R;
import com.example.wguapp.ui.adapters.TermListAdapter;
import com.example.wguapp.viewmodel.TermListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermListActivity extends AppCompatActivity {
    RecyclerView termListView;
    private TermListAdapter adapter;
    public FloatingActionButton addTermButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        termListView = findViewById(R.id.term_list_view);
        addTermButton = findViewById(R.id.term_list_fab);
        toolbar = findViewById(R.id.term_list_toolbar);
        toolbar.setTitle("WGU -> All Terms");
        setSupportActionBar(toolbar);

        TermListViewModel vm = ViewModelProviders.of(this).get(TermListViewModel.class);
        vm.getAllTerms().observe(this, terms -> adapter.setTerms(terms));

        addTermButton.setOnClickListener((view) -> AddNewTerm());

        initRecyclerView();

    }



    private void AddNewTerm() {
        Intent intent = new Intent(this, TermDetailActivity.class);
        startActivity(intent);
    }

    private void initRecyclerView() {
        adapter = new TermListAdapter();
        termListView.setAdapter(adapter);
        termListView.setLayoutManager(new LinearLayoutManager(this));
    }
}
