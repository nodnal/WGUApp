package com.example.wguapp.ui.activities.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.wguapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseMentorFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_course_mentor, container, false);
    }

}
