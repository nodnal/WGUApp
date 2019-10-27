package com.example.wguapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Term;
import com.example.wguapp.viewmodel.TermDetailViewModel;
import com.example.wguapp.viewmodel.TermListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermDetailActivity extends AppCompatActivity {

    TermDetailViewModel vm;
    MutableLiveData<Term> term;
    @BindView(R.id.term_detail_title)
    public TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        ButterKnife.bind(this);
        int termid = 0;
        if(getIntent().getExtras() != null) {
            termid = getIntent().getExtras().getInt("term_id");  
        }
        vm = ViewModelProviders.of(this).get(TermDetailViewModel.class);
        term = vm.Term;
        term.observe(this, new Observer<Term>() {
            @Override
            public void onChanged(Term term) {
                title.setText(term.getTitle());
            }
        });
        if(termid != 0){
            vm.LoadTerm(termid);
        }

    }
}
