package com.example.wguapp.ui.activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.CourseMentorJoin;
import com.example.wguapp.db.entity.Mentor;
import com.example.wguapp.db.entity.Note;
import com.example.wguapp.ui.adapters.CoursePagerAdapter;
import com.example.wguapp.viewmodel.CourseDetailViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    private CourseDetailViewModel vm;
    private LiveData<Course> course;
    private LiveData<List<Assessment>> assessments;
    private LiveData<List<Note>> notes;
    private LiveData<List<Mentor>> mentors;
    private LiveData<List<CourseMentorJoin>> assignedMentors;
    private boolean editable;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initViewModel();
        initBindings();
        initUI();
        initTabs();

        if(getIntent().hasExtra("term_id")) {
            vm.LoadNewCourse(getIntent().getExtras().getInt("term_id"));
        }
        else{
            int id = getIntent().getExtras().getInt("course_id");
            vm.LoadCourse(id);
        }

    }

    private void initTabs() {
        ViewPager viewPager = findViewById(R.id.course_detail_view_pager);
        TabLayout tabLayout = findViewById(R.id.course_detail_tab_layout);
        CoursePagerAdapter adapter = new CoursePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUI() {
        toolbar = findViewById(R.id.course_detail_toolbar);
        toolbar.setTitle("WGU -> Course Detail");
        setSupportActionBar(toolbar);
    }

    private void initBindings() {

        vm.isEditable().observe(this, (value) -> {
            editable = value;
            if (value) {
                toolbar.getMenu().setGroupVisible(R.id.group_save, true);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, false);
                toolbar.getMenu().setGroupVisible(R.id.group_delete, false);

            } else {
                toolbar.getMenu().setGroupVisible(R.id.group_save, false);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, true);
                toolbar.getMenu().setGroupVisible(R.id.group_delete, true);
            }
        });
    }

    private void initViewModel() {
        vm = ViewModelProviders.of(this).get(CourseDetailViewModel.class);
        course = vm.getCourse();
        course.observe(this,(c)-> {});
        vm.getRepoCourse().observe(this, (c) -> {});
        assessments = vm.getAssessments();
        notes = vm.getNotes();
        mentors = vm.getMentors();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.term_menu, menu);

        vm.setEditable(editable);
        return true;
    }
}
