package com.example.wguapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.wguapp.R;
import com.example.wguapp.TermListAdapter;
import com.example.wguapp.db.entity.Term;
import com.example.wguapp.viewmodel.TermListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;

public class TermListActivity extends AppCompatActivity {
    RecyclerView termListView;
    private TermListAdapter adapter;
    private TermListViewModel termListViewModel;
    public FloatingActionButton addTermButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        termListView = this.findViewById(R.id.term_list_view);
        addTermButton = this.findViewById(R.id.term_list_fab);
        termListViewModel = ViewModelProviders.of(this).get(TermListViewModel.class);
        termListViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.setTerms(terms);
            }
        });
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
