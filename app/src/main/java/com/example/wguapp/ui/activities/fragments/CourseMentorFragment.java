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

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.Mentor;
import com.example.wguapp.ui.activities.MentorDetailActivity;
import com.example.wguapp.ui.adapters.MentorListAdapter;
import com.example.wguapp.ui.adapters.OnViewHolderBindCallback;
import com.example.wguapp.viewmodel.CourseDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseMentorFragment extends Fragment implements OnViewHolderBindCallback {


    private RecyclerView listView;
    private MentorListAdapter adapter;
    private FloatingActionButton addMentorBtn;
    private CourseDetailViewModel vm;
    private LiveData<List<Mentor>> mentors;
    private Course course;

    public CourseMentorFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new CourseMentorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_mentor, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        listView = view.findViewById(R.id.course_detail_mentor_list);
        adapter = new MentorListAdapter(this);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        addMentorBtn = view.findViewById(R.id.course_detail_mentor_add_fab);
        addMentorBtn.setOnClickListener((btn) -> addMentor());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        vm = ViewModelProviders.of(getActivity()).get(CourseDetailViewModel.class);
        mentors = vm.getMentors();
        vm.getCourse().observe(getViewLifecycleOwner(),(c) -> course = c);
        mentors.observe(getViewLifecycleOwner(), m -> adapter.setMentors(m));
    }



    private void addMentor() {
        if (course != null && course.getId() != 0) {
            Intent intent = new Intent(this.getContext(), MentorDetailActivity.class);
            intent.putExtra("course_id", course.getId());
            startActivity(intent);
        }else{
            //TODO: replace this in each fragment.
            Log.e("CourseAssesmentFragment","no course selected."+course.toString());
        }
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(this.getContext(), MentorDetailActivity.class);
            intent.putExtra("mentor_id", mentors.getValue().get(viewHolder.getAdapterPosition()).getId());
            startActivity(intent);
        });
    }
}
