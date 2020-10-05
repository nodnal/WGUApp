package com.example.wguapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.app.ShareCompat.IntentBuilder;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Note;
import com.example.wguapp.viewmodel.NoteDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailActivity extends AppCompatActivity {

    private NoteDetailViewModel vm;
    private LiveData<Note> note;
    private LiveData<Integer> courseId;
    private Note currentNote;
    private int currentCourseId;

    private EditText title;
    private EditText noteText;
    private FloatingActionButton shareBtn;
    private Toolbar toolbar;
    private Boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        initViewModel();
        initUI();
        initBindings();

        if(getIntent().hasExtra("course_id")){
            vm.loadNewNote(getIntent().getIntExtra("course_id", 0));
        }else{
            vm.loadNote(getIntent().getExtras().getInt("note_id"));
        }
    }

    private void initViewModel() {
        vm = ViewModelProviders.of(this).get(NoteDetailViewModel.class);
        note = vm.getNote();
        courseId = vm.getCourseId();
    }

    private void initUI() {
        title = findViewById(R.id.note_detail_title);
        noteText = findViewById(R.id.note_detail_note);
        shareBtn = findViewById(R.id.note_detail_share_fab);
        shareBtn.setOnClickListener(btn -> shareNote());
        toolbar = findViewById(R.id.note_detail_toolbar);
        toolbar.setTitle("WGU -> Assessment Details");
        setSupportActionBar(toolbar);
    }

    private void shareNote() {
        if (currentNote == null) {
            Toast.makeText(this, "Problem sharing note. Try again later.", Toast.LENGTH_SHORT);
        }else {
            String text = currentNote.Title + ": \n\n" + currentNote.Note;
            IntentBuilder builder = ShareCompat.IntentBuilder.from(this);
            builder.setType("text/plain");
            builder.setText(text);
            builder.setSubject("Note sent from WGU App");
            builder.setChooserTitle("Share via");
            Intent intent  = builder.createChooserIntent();
            startActivity(intent);
        }
    }

    private void initBindings() {
        note.observe(this, (n) -> {
            title.setText(n.Title);
            noteText.setText(n.Note);
            currentNote = n;
        });
        vm.isEditable().observe(this, editable ->{
            this.editable = editable;
            toolbar.getMenu().setGroupVisible(R.id.group_save, editable);
            toolbar.getMenu().setGroupVisible(R.id.group_edit, !editable);
            title.setEnabled(editable);
            noteText.setEnabled(editable);
            if(editable) shareBtn.hide(); else shareBtn.show();

        });
        courseId.observe(this, (id) -> currentCourseId = id);

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
            case R.id.action_delete:
                deleteNote();
                return (true);
        }
        return(super.onOptionsItemSelected(item));
    }

    private void deleteNote() {
        vm.deleteNote(currentNote, currentCourseId);
        finish();
    }

    private void saveNote() {
        //TODO: catch errors? better validation?
        String newTitle = title.getText().toString().trim();
        String newNoteText = noteText.getText().toString().trim();
        Toast toast = Toast.makeText(this, "Changes Saved.", Toast.LENGTH_SHORT);

        if(newTitle.length() == 0 || newNoteText.length() == 0){
            toast.setText("Title and Note cannot be empty.");
            toast.show();
        }else {
            //TODO: Should I make a new Entity and populate it with Integers that are set by live data changes instead?
           Note n = note.getValue();
            n.Title = newTitle;
            n.Note = newNoteText;

            if(n.getId()==0){
                toast.setText("New Note Created");
                vm.saveNote(n);
                toast.show();
                finish();
            }else{
                vm.saveNote(n);
                toast.show();
            }


        }
    }
}
