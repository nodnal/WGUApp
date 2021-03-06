package com.example.wguapp.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wguapp.ui.activities.fragments.CourseAssessmentFragment;
import com.example.wguapp.ui.activities.fragments.CourseMainFragment;
import com.example.wguapp.ui.activities.fragments.CourseMentorFragment;
import com.example.wguapp.ui.activities.fragments.CourseNoteFragment;

public class CoursePagerAdapter extends FragmentPagerAdapter {
    public CoursePagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return CourseMainFragment.newInstance();
            case 1: return CourseAssessmentFragment.newInstance();
            case 2: return CourseMentorFragment.newInstance();
            case 3: return CourseNoteFragment.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "Course";
            case 1: return "Assessments";
            case 2: return "Mentors";
            case 3: return "Notes";
            default: return null;
        }
    }
}
