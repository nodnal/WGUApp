package com.example.wguapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.AssessmentListAdapter;
import com.example.wguapp.R;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.viewmodel.CourseDetailViewModel;

import java.security.cert.Extension;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseAssessmentFragment extends Fragment implements OnViewHolderBindCallback{

    private RecyclerView listView;
    private AssessmentListAdapter adapter;
    private LiveData<List<Assessment>> assessments;
    private CourseDetailViewModel vm;

    public CourseAssessmentFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(LiveData<Boolean> editable) {
        return new CourseAssessmentFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_assessment, container, false);
        listView = view.findViewById(R.id.course_detail_assesment_list);
        adapter = new AssessmentListAdapter(this);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        vm = ViewModelProviders.of(getActivity()).get(CourseDetailViewModel.class);
        assessments = vm.getAssessments();
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(this.getContext(), CourseDetailActivity.class);
            intent.putExtra("course_id", assessments.getValue().get(viewHolder.getAdapterPosition()).getId());
            startActivity(intent);
        });
    }
}
