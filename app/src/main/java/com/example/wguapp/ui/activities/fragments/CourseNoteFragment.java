package com.example.wguapp.ui.activities.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.ui.adapters.OnViewHolderBindCallback;
import com.example.wguapp.ui.activities.NoteDetailActivity;
import com.example.wguapp.R;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.Note;
import com.example.wguapp.ui.adapters.NoteListAdapter;
import com.example.wguapp.viewmodel.CourseDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseNoteFragment extends Fragment  implements OnViewHolderBindCallback {

    private FloatingActionButton addNoteBtn;
    private RecyclerView listView;
    private NoteListAdapter adapter;
    private Course course;
    private LiveData<List<Note>> notes;
    private CourseDetailViewModel vm;

    public CourseNoteFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new CourseNoteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_note, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        listView = view.findViewById(R.id.course_detail_note_list);
        adapter = new NoteListAdapter(this);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        addNoteBtn = view.findViewById(R.id.course_detail_note_add_fab);
        addNoteBtn.setOnClickListener((btn) -> addNote());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        vm = ViewModelProviders.of(getActivity()).get(CourseDetailViewModel.class);
        notes = vm.getNotes();
        vm.getCourse().observe(getViewLifecycleOwner(),(c) -> course = c);
        notes.observe(getViewLifecycleOwner(), a -> adapter.setNotes(a));
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(this.getContext(), NoteDetailActivity.class);
            intent.putExtra("note_id", notes.getValue().get(viewHolder.getAdapterPosition()).getId());
            startActivity(intent);
        });
    }
    private void addNote() {

        if (course != null && course.getId() != 0) {
            Intent intent = new Intent(this.getContext(), NoteDetailActivity.class);
            intent.putExtra("course_id", course.getId());
            startActivity(intent);

        }else{
            Log.e("CourseAssesmentFragment","no course selected."+course.toString());
        }
    }
}
