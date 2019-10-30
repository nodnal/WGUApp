package com.example.wguapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.util.DateUtil;
import com.example.wguapp.viewmodel.AssessmentDetailViewModel;

import java.util.Date;

public class AssessmentDetailActivity extends AppCompatActivity {

    private AssessmentDetailViewModel vm;
    private LiveData<Assessment> assessment;
    private LiveData<Boolean> isEditable;

    private EditText title;
    private EditText goalDate;
    private CheckBox alert;
    private Toolbar toolbar;

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
        isEditable = vm.isEditable();
    }

    private void initUI() {
        title = findViewById(R.id.assessment_detail_title);
        goalDate = findViewById(R.id.assessment_detail_goal_date);
        alert = findViewById(R.id.assessment_detail_alert);
        toolbar = findViewById(R.id.assessment_detail_toolbar);
        setSupportActionBar(toolbar);
    }



    private void initBindings() {
        assessment.observe(this, (a) -> {
            title.setText(a.Title);
            goalDate.setText(DateUtil.toString(a.GoalDate));
            //TODO: alert.setChecked(a.Alert);
            //type.setText(a.Type);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.term_menu, menu);
        vm.setEditable(isEditable.getValue());
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

                default: return true;
        }


    }

    private void saveAssessment() {
        //TODO: catch errors? better validation?
        Date goal = new Date(goalDate.getText().toString());
        String newTitle = title.getText().toString().trim();
        boolean forAlert = alert.isChecked();
        Toast toast = Toast.makeText(this, "Changes Saved.", Toast.LENGTH_SHORT);

        if(newTitle.length() == 0){
            toast.setText("Title cannot be empty.");
        } else if(goal.after(new Date())) {
            //TODO: Can a goal date not be in past, or can you not set an alert on a goal date in past???
            toast.setText("Goal Date Cannot Be in Past.");
        }else {
            //TODO: add alert bool to DB/Entity
            //TODO: Should I make a new Entity and populate it with Integers that are set by live data changes instead?
            Assessment a = assessment.getValue();
            a.Title = newTitle;
            a.GoalDate = goal;
            a.Type = "type placeholder";
            //a.alert = forAlert
            //TODO: activity.setAlertUp

            if(a.getId()==0){
                toast.setText("New Assessment Created");
            }

            vm.saveAssessment(a);
            toast.show();
        }


    }
    }
