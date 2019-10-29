package com.example.wguapp.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.wguapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseAssessmentFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_course_assessment, container, false);
    }

}
