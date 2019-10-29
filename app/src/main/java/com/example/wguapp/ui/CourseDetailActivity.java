package com.example.wguapp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.CourseMentorJoin;
import com.example.wguapp.db.entity.Mentor;
import com.example.wguapp.db.entity.Note;
import com.example.wguapp.viewmodel.CourseDetailViewModel;

import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    private CourseDetailViewModel vm;
    private LiveData<Course> course;
    private LiveData<List<Assessment>> assessments;
    private LiveData<List<Note>> notes;
    private LiveData<List<Mentor>> mentors;
    private LiveData<List<CourseMentorJoin>> assignedMentors;
    private MutableLiveData isEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initViewModel();
        initBindings();
        initUI();
    }

    private void initUI() {

    }

    private void initBindings() {

    }

    private void initViewModel() {
        vm = ViewModelProviders.of(this).get(CourseDetailViewModel.class);
        course = vm.getCourse();
        assessments = vm.getAssessments();
        notes = vm.getNotes();
        mentors = vm.getMentors();
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
            SaveCourse();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    private void SaveCourse() {
        vm.SaveCourse(course.getValue());
    }
}
