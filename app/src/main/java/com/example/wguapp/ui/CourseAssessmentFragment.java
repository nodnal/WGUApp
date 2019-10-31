package com.example.wguapp.ui;


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

import com.example.wguapp.AssessmentDetailActivity;
import com.example.wguapp.AssessmentListAdapter;
import com.example.wguapp.R;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.viewmodel.CourseDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseAssessmentFragment extends Fragment implements OnViewHolderBindCallback{

    private FloatingActionButton addAssessmentBtn;
    private RecyclerView listView;
    private AssessmentListAdapter adapter;
    private Course course;
    private LiveData<List<Assessment>> assessments;
    private CourseDetailViewModel vm;

    public CourseAssessmentFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new CourseAssessmentFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_assessment, container, false);
        initUI(view);

        return view;
    }

    private void initUI(View view) {
        listView = view.findViewById(R.id.course_detail_assesment_list);
        adapter = new AssessmentListAdapter(this);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        addAssessmentBtn = view.findViewById(R.id.course_detail_assessment_add_fab);
        addAssessmentBtn.setOnClickListener((btn) -> addAssessment());


    }

    private void addAssessment() {

        if (course != null && course.getId() != 0) {
            Intent intent = new Intent(this.getContext(), AssessmentDetailActivity.class);
            intent.putExtra("course_id", course.getId());
            startActivity(intent);

        }else{
            Log.e("CourseAssesmentFragment","no course selected."+course.toString());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        vm = ViewModelProviders.of(getActivity()).get(CourseDetailViewModel.class);
        assessments = vm.getAssessments();
        vm.getCourse().observe(getViewLifecycleOwner(),(c) -> course = c);
        assessments.observe(getViewLifecycleOwner(), a -> adapter.setAssessments(a));
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(this.getContext(), AssessmentDetailActivity.class);
            intent.putExtra("assessment_id", assessments.getValue().get(viewHolder.getAdapterPosition()).getId());
            startActivity(intent);
        });
    }
}
