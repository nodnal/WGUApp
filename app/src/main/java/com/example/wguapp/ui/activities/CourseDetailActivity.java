package com.example.wguapp.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

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
    private boolean isCourseFragmentSelected = true;
    private TabLayout tabLayout;

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
        tabLayout = findViewById(R.id.course_detail_tab_layout);
        CoursePagerAdapter adapter = new CoursePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position == 0) {
                    isCourseFragmentSelected = true;
                    invalidateOptionsMenu();
                }else{
                    isCourseFragmentSelected = false;
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void initUI() {
        toolbar = findViewById(R.id.course_detail_toolbar);
        toolbar.setTitle("WGU -> Course Detail");
        setSupportActionBar(toolbar);
    }

    private void initBindings() {

        vm.isEditable().observe(this, (value) -> {
            editable = value;
            tabLayout.setEnabled(editable);
            tabLayout.setSelectedTabIndicatorColor(getColor((editable) ? R.color.colorPrimary : R.color.colorPrimaryDark));
            LinearLayout tabs = (LinearLayout)tabLayout.getChildAt(0);
            toolbar.getMenu().setGroupVisible(R.id.group_save, editable);
            toolbar.getMenu().setGroupVisible(R.id.group_edit, !editable);
            toolbar.getMenu().setGroupVisible(R.id.group_delete, !editable);
            tabs.setEnabled(!editable);
            for(int i = 0; i < tabs.getChildCount(); i++){
                tabs.getChildAt(i).setClickable(!editable);
                tabs.getChildAt(i).setEnabled(!editable);
                tabs.getChildAt(i).setVisibility((editable) ? View.INVISIBLE : View.VISIBLE);
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
        if(isCourseFragmentSelected) {
            vm.setEditable(editable);
        }else {
            toolbar.getMenu().setGroupVisible(R.id.group_save, false);
            toolbar.getMenu().setGroupVisible(R.id.group_edit, false);
            toolbar.getMenu().setGroupVisible(R.id.group_delete, false);
        }
        return true;
    }

}
