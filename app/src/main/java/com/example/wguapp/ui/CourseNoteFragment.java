package com.example.wguapp.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.wguapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseNoteFragment extends Fragment {


    public CourseNoteFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new CourseNoteFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_note, container, false);
    }

}
