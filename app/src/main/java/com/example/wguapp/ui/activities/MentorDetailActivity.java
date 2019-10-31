package com.example.wguapp.ui.activities;

import android.os.Bundle;
import android.widget.EditText;

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
        toolbar.setTitle("WGU -> Mentor Details");
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
        vm.isEditable().observe(this, editable ->{
            this.editable = editable;
            if (editable) {
                toolbar.getMenu().setGroupVisible(R.id.group_save, true);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, false);
                title.setInputType(InputType.TYPE_CLASS_TEXT);
                noteText.setInputType(InputType.TYPE_CLASS_TEXT);
                shareBtn.hide();
            } else {
                toolbar.getMenu().setGroupVisible(R.id.group_save, false);
                toolbar.getMenu().setGroupVisible(R.id.group_edit, true);
                title.setInputType(InputType.TYPE_NULL);
                noteText.setInputType(InputType.TYPE_NULL);
                shareBtn.show();
            }
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
                saveNote();
                return (true);
        }
        return(super.onOptionsItemSelected(item));
    }

    private void saveNote() {
        //TODO: catch errors? better validation?
        String newTitle = title.getText().toString().trim();
        String newNoteText = noteText.getText().toString().trim();
        Toast toast = Toast.makeText(this, "Changes Saved.", Toast.LENGTH_SHORT);

        if(newTitle.length() == 0 || newNoteText.length() == 0){
            toast.setText("Title and Note cannot be empty.");
        }else {
            //TODO: Should I make a new Entity and populate it with Integers that are set by live data changes instead?
            Note n = mentor.getValue();
            n.Title = newTitle;
            n.Note = newNoteText;

            if(n.getId()==0){
                toast.setText("New Note Created");
            }

            vm.saveNote(n);
            toast.show();
        }
    }
}
