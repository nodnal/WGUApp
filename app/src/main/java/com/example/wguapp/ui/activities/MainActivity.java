package com.example.wguapp.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.R;
import com.example.wguapp.viewmodel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView coursesInProgress, coursesNotStarted, coursesDropped, coursesFinished;
    private TextView assessmentsPassed, assessmentsFailed, assessmentsNotAttempted;

    private ArrayList<String> mItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGUI();
        initVM();


    }

    public void initVM(){
        MainViewModel vm = ViewModelProviders.of(this).get(MainViewModel.class);
        vm.getAllAssessments().observe(this, assessments -> {

            assessmentsPassed.setText("Passed: " + Long.toString(assessments.stream().filter(a -> a.Status.equals("Passed")).count()));
            assessmentsFailed.setText("Failed: " + Long.toString(assessments.stream().filter(a -> a.Status.equals("Failed")).count()));
            assessmentsNotAttempted.setText("Not Attempted: " + Long.toString(assessments.stream().filter(a -> a.Status.equals("Not Attempted")).count()));
        });
        vm.getAllCourses().observe(this, courses -> {
            coursesInProgress.setText("In Progress: " + Long.toString(courses.stream().filter(c -> c.Status.equals("In Progress")).count()));
            coursesNotStarted.setText("Not Started: " + Long.toString(courses.stream().filter(c -> c.Status.equals("Not Started")).count()));
            coursesDropped.setText("Dropped: " + Long.toString(courses.stream().filter(c -> c.Status.equals("Dropped")).count()));
            coursesFinished.setText("Finshed: " + Long.toString(courses.stream().filter(c -> c.Status.equals("Finished")).count()));
        });
    }

    public void initGUI(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 4;
        params.leftMargin = 10;
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.bottomMargin = 10;

        LinearLayout.LayoutParams headingParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        headingParams.topMargin = 20;
        headingParams.bottomMargin = 10;
        headingParams.leftMargin = 10;

        View toolbarView = View.inflate(this, R.layout.toolbar, null);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView tvTitle = new TextView(this);
        tvTitle.setText("Western Governors University");
        tvTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvTitle.setLayoutParams(titleParams);
        tvTitle.setTextSize(24);
        tvTitle.setTextColor(Color.parseColor("#000000"));

        TextView tvSubtitle = new TextView(this);
        tvSubtitle.setText("Scheduling Application");
        tvSubtitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvSubtitle.setLayoutParams(titleParams);
        tvSubtitle.setTextSize(18);
        tvSubtitle.setTextColor(Color.parseColor("#000000"));

        TextView tvAssessments = new TextView(this);
        tvAssessments.setText("Assessments");
        tvAssessments.setLayoutParams(headingParams);
        tvAssessments.setTextSize(16);
        tvAssessments.setTextColor(Color.parseColor("#000000"));

        TextView tvCourses = new TextView(this);
        tvCourses.setText("Courses");
        tvCourses.setLayoutParams(headingParams);
        tvCourses.setTextSize(16);
        tvCourses.setTextColor(Color.parseColor("#000000"));

        coursesInProgress = new TextView(this);
        coursesNotStarted = new TextView(this);
        coursesDropped = new TextView(this);
        coursesFinished = new TextView(this);
        assessmentsPassed = new TextView(this);
        assessmentsFailed = new TextView(this);
        assessmentsNotAttempted = new TextView(this);

        coursesInProgress.setLayoutParams(params);
        coursesNotStarted.setLayoutParams(params);
        coursesDropped.setLayoutParams(params);
        coursesFinished.setLayoutParams(params);
        assessmentsPassed.setLayoutParams(params);
        assessmentsFailed.setLayoutParams(params);
        assessmentsNotAttempted.setLayoutParams(params);


        Button btnTermList = new Button(this);
        btnTermList.setText("View Terms");
        btnTermList.setLayoutParams(params);

        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 4);
        dividerParams.bottomMargin = 10;

        View divider = new View(this);
        divider.setLayoutParams(dividerParams);
        divider.setBackgroundColor(Color.parseColor("#000000"));

        View divider2 = new View(this);
        divider2.setLayoutParams(dividerParams);
        divider2.setBackgroundColor(Color.parseColor("#000000"));

        //---Add all elements to the layout
        linearLayout.addView(toolbarView);
        linearLayout.addView(tvTitle);
        linearLayout.addView(tvSubtitle);
        linearLayout.addView(tvCourses);
        linearLayout.addView(divider);
        linearLayout.addView(coursesInProgress);
        linearLayout.addView(coursesNotStarted);
        linearLayout.addView(coursesDropped);
        linearLayout.addView(coursesFinished);
        linearLayout.addView(tvAssessments);
        linearLayout.addView(divider2);
        linearLayout.addView(assessmentsPassed);
        linearLayout.addView(assessmentsFailed);
        linearLayout.addView(assessmentsNotAttempted);
        linearLayout.addView(btnTermList);

        //---Create a layout param for the layout-----------------
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        this.addContentView(linearLayout, layoutParams);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("WGU");
        setSupportActionBar(toolbar);

        btnTermList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHandler();
            }
        });
    }


    public void btnHandler(){
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }


}
