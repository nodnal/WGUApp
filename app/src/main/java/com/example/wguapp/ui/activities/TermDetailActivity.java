package com.example.wguapp.ui.activities;

import android.content.Intent;
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

import com.example.wguapp.ui.adapters.OnViewHolderBindCallback;
import com.example.wguapp.ui.adapters.CourseListAdapter;
import com.example.wguapp.R;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.Term;
import com.example.wguapp.util.DateUtil;
import com.example.wguapp.viewmodel.TermDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermDetailActivity extends AppCompatActivity implements OnViewHolderBindCallback {

    private static final int NEW_COURSE_CODE = 2;
    private static final int EDIT_COURSE_CODE = 1;
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
    @BindView(R.id.term_detail_course_list)
    public RecyclerView CourseList;
    @BindView(R.id.term_detail_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.add_course_fab)
    public FloatingActionButton addCourseBtn;
    private Term currentTerm;
    private ArrayList<Course> currentCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        ButterKnife.bind(this);
        vm = ViewModelProviders.of(this).get(TermDetailViewModel.class);
        currentCourses = new ArrayList<>();
        initViewModelBindings();
        initUI();

        if(getIntent().getExtras() != null) {
           vm.LoadTerm(getIntent().getExtras().getInt("term_id"));
           isEditable.postValue(false);
        }else{
            isEditable.postValue(true);
        }
    }

    private void initViewModelBindings() {
        isEditable = new MutableLiveData<>();

        term = vm.getTerm();
        term.observe(this, term -> {
            if (term == null) {
                title.setText("");
                startDate.setText("");
                endDate.setText("");
            }else {
                currentTerm = term;
                title.setText(term.Title);
                startDate.setText(DateUtil.toString(term.StartDate));
                endDate.setText(DateUtil.toString(term.EndDate));
            }
        });

        courses = vm.getCourses();
        courses.observe(this, courses -> {
            courseAdapter.setCourses(courses);
            currentCourses.clear();
            currentCourses.addAll(courses);
        });

        isEditable.observe(this, editable -> {
            if (editable){

                toolbar.getMenu().setGroupVisible(R.id.group_save, true);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, false);
                toolbar.getMenu().setGroupVisible(R.id.group_delete, false);
                title.setInputType(InputType.TYPE_CLASS_TEXT);
                startDate.setInputType(InputType.TYPE_CLASS_DATETIME);
                endDate.setInputType(InputType.TYPE_CLASS_DATETIME);
            }else{
                toolbar.getMenu().setGroupVisible(R.id.group_save, false);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, true);
                toolbar.getMenu().setGroupVisible(R.id.group_delete, true);
                title.setInputType(InputType.TYPE_NULL);
                startDate.setInputType(InputType.TYPE_NULL);
                endDate.setInputType(InputType.TYPE_NULL);
            }

        });
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
        case R.id.action_delete:
            DeleteTerm();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    private void DeleteTerm() {
        if(currentCourses.isEmpty()){
            vm.DeleteTerm(currentTerm);
            finish();
        }else{
            Toast.makeText(this, "Cannot Delete Term with Existing Courses", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Toast toast;
            if (requestCode == NEW_COURSE_CODE) {
                toast = Toast.makeText(this, "Course Added!", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(this, "Changes Saved!", Toast.LENGTH_SHORT);
            }
            toast.show();
        }
    }

    private void initUI() {
        toolbar = findViewById(R.id.term_detail_toolbar);
        toolbar.setTitle("WGU -> Term Details");
        setSupportActionBar(toolbar);
        courseAdapter = new CourseListAdapter(this);
        CourseList.setAdapter(courseAdapter);
        CourseList.setLayoutManager(new LinearLayoutManager(this));
        addCourseBtn.setOnClickListener((btn)->{
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("term_id", term.getValue().getId());
            startActivityForResult(intent, NEW_COURSE_CODE);

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

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("course_id", courses.getValue().get(viewHolder.getAdapterPosition()).getId());
            startActivityForResult(intent, EDIT_COURSE_CODE);
        });
    }


}

