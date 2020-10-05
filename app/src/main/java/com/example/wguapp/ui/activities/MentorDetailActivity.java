package com.example.wguapp.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Mentor;
import com.example.wguapp.viewmodel.MentorDetailViewModel;

public class MentorDetailActivity extends AppCompatActivity {
    private MentorDetailViewModel vm;
    private LiveData<Mentor> mentor;
    private Mentor currentMentor;
    private int currentCourseId;

    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private EditText email;
    private Toolbar toolbar;
    private Boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);

        initViewModel();
        initUI();
        initBindings();

        if(getIntent().hasExtra("course_id")){
            vm.loadNewMentor(getIntent().getIntExtra("course_id", 0));
        }else{
            vm.loadMentor(getIntent().getExtras().getInt("mentor_id"));
        }
    }

    private void initViewModel() {
        vm = ViewModelProviders.of(this).get(MentorDetailViewModel.class);
        mentor = vm.getMentor();
    }

    private void initUI() {
        firstName = findViewById(R.id.mentor_detail_first_name);
        lastName = findViewById(R.id.mentor_detail_last_name);
        phone = findViewById(R.id.mentor_detail_phone);
        email = findViewById(R.id.mentor_detail_email);
        toolbar = findViewById(R.id.mentor_detail_toolbar);
        toolbar.setTitle("WGU -> Course Mentor Details");
        setSupportActionBar(toolbar);
    }

    private void initBindings() {
        mentor.observe(this, (m) -> {
            firstName.setText(m.FirstName);
            lastName.setText(m.LastName);
            phone.setText(m.PhoneNumber);
            email.setText(m.EmailAddress);

            currentMentor = m;
        });
        vm.getCourseId().observe(this, cId -> currentCourseId = cId);
        vm.isEditable().observe(this, editable ->{
            this.editable = editable;
            toolbar.getMenu().setGroupVisible(R.id.group_save, editable);
            toolbar.getMenu().setGroupVisible(R.id.group_edit, !editable);
            toolbar.getMenu().setGroupVisible(R.id.group_delete, !editable);

            firstName.setEnabled(editable);
            lastName.setEnabled(editable);
            phone.setEnabled(editable);
            email.setEnabled(editable);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.term_menu, menu);
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
                saveMentor();
                return (true);
            case R.id.action_delete:
                deleteMentor();
                return (true);
        }
        return(super.onOptionsItemSelected(item));
    }

    private void deleteMentor() {
        vm.deleteMentor(currentMentor);
        finish();
    }

    private void saveMentor() {
        String newFirstName = firstName.getText().toString().trim();
        String newLastName = lastName.getText().toString().trim();
        String newPhone = phone.getText().toString().trim();
        String newEmail = email.getText().toString().trim();

        Toast toast = Toast.makeText(this, "Changes Saved.", Toast.LENGTH_SHORT);

        if(newFirstName.length() == 0 || newLastName.length() == 0 || newPhone.length() == 0  || newEmail.length() == 0 ){
            toast.setText("Cannot Save. Please Complete all Fields.");
            toast.show();
        }else {
            Mentor m = currentMentor;
            m.FirstName = newFirstName;
            m.LastName = newLastName;
            m.PhoneNumber = newPhone;
            m.EmailAddress = newEmail;

            if(m.getId()==0){
                toast.setText("New Mentor Created");
                vm.saveNewMentor(m, currentCourseId);
                toast.show();
                finish();
            }else{
                vm.saveMentor(m);
                toast.show();
            }

        }
    }
}
