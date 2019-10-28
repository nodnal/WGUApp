package com.example.wguapp.ui;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.CourseListAdapter;
import com.example.wguapp.R;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.Term;
import com.example.wguapp.util.DateUtil;
import com.example.wguapp.viewmodel.TermDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermDetailActivity extends AppCompatActivity {

    TermDetailViewModel vm;
    LiveData<Term> term;
    LiveData<List<Course>> courses;
    private MutableLiveData<Boolean> isEditable;
    private CourseListAdapter courseAdapter;

    @BindView(R.id.title_et)
    public TextView title;
    @BindView(R.id.start_date_et)
    public TextView startDate;
    @BindView(R.id.end_date_et)
    public TextView endDate;
    @BindView(R.id.term_detail_save_fab)
    public FloatingActionButton SaveBtn;
    @BindView(R.id.term_detail_edit_fab)
    public FloatingActionButton EditBtn;
    @BindView(R.id.term_detail_course_list)
    public RecyclerView CourseList;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        ButterKnife.bind(this);
        isEditable = new MutableLiveData<>();
        vm = ViewModelProviders.of(this).get(TermDetailViewModel.class);
        initUI();

        if(getIntent().getExtras() != null) {
           vm.LoadTerm(getIntent().getExtras().getInt("term_id"));
           isEditable.postValue(false);
        }else{
            isEditable.postValue(true);
        }


        term = vm.getTerm();
        term.observe(this, term -> {
            title.setText(term.Title);
            startDate.setText(DateUtil.toString(term.StartDate));
            endDate.setText(DateUtil.toString(term.EndDate));
        });

        courses = vm.getCourses();
        courses.observe(this, courses -> courseAdapter.setCourses(courses));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.term_menu, menu);
        isEditable.postValue(isEditable.getValue());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.action_edit:
            isEditable.postValue(true);
            return(true);
        case R.id.action_save:
            SaveTerm();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    private void initUI() {
        setSupportActionBar(toolbar);
        courseAdapter = new CourseListAdapter();
        CourseList.setAdapter(courseAdapter);
        CourseList.setLayoutManager(new LinearLayoutManager(this));
        EditBtn.setOnClickListener((btn) -> isEditable.postValue(true));
        SaveBtn.setOnClickListener((btn) -> SaveTerm());
        isEditable.observe(this, editable -> {
            if (editable){
                toolbar.getMenu().setGroupVisible(R.id.group_save, true);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, false);
                EditBtn.hide();
                SaveBtn.show();
                title.setInputType(InputType.TYPE_CLASS_TEXT);
                startDate.setInputType(InputType.TYPE_CLASS_DATETIME);
                endDate.setInputType(InputType.TYPE_CLASS_DATETIME);
            }else{
                toolbar.getMenu().setGroupVisible(R.id.group_save, false);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, true);
                EditBtn.show();
                SaveBtn.hide();
                title.setInputType(InputType.TYPE_NULL);
                startDate.setInputType(InputType.TYPE_NULL);
                endDate.setInputType(InputType.TYPE_NULL);
            }

        });
    }

    private void SaveTerm() {
        Term term = this.term.getValue();
        String title = this.title.getText().toString();
        Date start = new Date(startDate.getText().toString());
        Date end = new Date(endDate.getText().toString());
        Toast toast = Toast.makeText(this,"Changes Saved.", Toast.LENGTH_SHORT);
        if (term == null) {
             term = new Term(title,start,end);
             toast.setText("New Term Added");
            vm.SaveTerm(term);
            toast.show();
            finish();
        }else {
            term.Title = title;
            term.StartDate = start;
            term.EndDate = end;
            vm.SaveTerm(term);
            toast.show();
            isEditable.postValue(false);
        }

    }
}
