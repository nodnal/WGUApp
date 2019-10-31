package com.example.wguapp.ui.activities;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.util.DateUtil;
import com.example.wguapp.viewmodel.AssessmentDetailViewModel;

import java.util.Date;

public class AssessmentDetailActivity extends AppCompatActivity {

    private AssessmentDetailViewModel vm;
    private LiveData<Assessment> assessment;

    private EditText title;
    private EditText goalDate;
    private EditText type;
    private CheckBox alert;
    private Toolbar toolbar;
    private boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        initViewModel();
        initUI();
        initBindings();

        if(getIntent().hasExtra("course_id")){
            vm.loadNewAssessment(getIntent().getIntExtra("course_id", 0));
        }else{
            vm.loadAssessment(getIntent().getExtras().getInt("assessment_id"));
        }

    }

    private void initViewModel() {
        vm = ViewModelProviders.of(this).get(AssessmentDetailViewModel.class);
        assessment = vm.getAssessment();
    }

    private void initUI() {
        title = findViewById(R.id.assessment_detail_title);
        goalDate = findViewById(R.id.assessment_detail_goal_date);
        type = findViewById(R.id.assessment_detail_type);
        alert = findViewById(R.id.assessment_detail_alert);
        toolbar = findViewById(R.id.assessment_detail_toolbar);
        toolbar.setTitle("WGU -> Assessment Details");
        setSupportActionBar(toolbar);
    }



    private void initBindings() {
        assessment.observe(this, (a) -> {
            title.setText(a.Title);
            goalDate.setText(DateUtil.toString(a.GoalDate));
            alert.setChecked(a.Alert);
            type.setText(a.Type);
        });
        vm.isEditable().observe(this, editable ->{
            this.editable = editable;
            if (editable) {
                toolbar.getMenu().setGroupVisible(R.id.group_save, true);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, false);
                title.setInputType(InputType.TYPE_CLASS_TEXT);
                type.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setClickable(true);
                goalDate.setInputType(InputType.TYPE_CLASS_DATETIME);
            } else {
                toolbar.getMenu().setGroupVisible(R.id.group_save, false);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, true);
                title.setInputType(InputType.TYPE_NULL);
                type.setInputType(InputType.TYPE_NULL);
                goalDate.setInputType(InputType.TYPE_NULL);
                alert.setClickable(false);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.term_menu, menu);
        // reset editable to trigger menu icon hide on observe;
        vm.setEditable(editable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit:
                vm.setEditable(true);
                return (true);
            case R.id.action_save:
                saveAssessment();
                return (true);
        }
        return(super.onOptionsItemSelected(item));
    }

    private void saveAssessment() {
        //TODO: catch errors? better validation?
        Date goal = new Date(goalDate.getText().toString());
        String newTitle = title.getText().toString().trim();
        String newType = type.getText().toString().trim();
        boolean forAlert = alert.isChecked();
        Toast toast = Toast.makeText(this, "Changes Saved.", Toast.LENGTH_SHORT);

        if(newTitle.length() == 0 || newType.length() == 0){
            toast.setText("Title and Type cannot be empty.");
        } else if(goal.before(new Date())) {
            //TODO: Can a goal date not be in past, or can you not set an alert on a goal date in past???
            toast.setText("Goal Date Cannot Be in Past.");
        }else {
            //TODO: add alert bool to DB/Entity
            //TODO: Should I make a new Entity and populate it with Integers that are set by live data changes instead?
            Assessment a = assessment.getValue();
            a.Title = newTitle;
            a.GoalDate = goal;
            a.Type = "type placeholder";
            a.Alert = forAlert;
            //TODO: activity.setAlertUp

            if(a.getId()==0){
                toast.setText("New Assessment Created");
            }

            vm.saveAssessment(a);
            toast.show();
        }


    }
    }
