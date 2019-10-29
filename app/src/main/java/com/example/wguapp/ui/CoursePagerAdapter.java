package com.example.wguapp.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;

public class CoursePagerAdapter extends FragmentPagerAdapter {
    LiveData<Boolean> editable;
    public CoursePagerAdapter(FragmentManager fm, LiveData<Boolean> isEditable) {
        super(fm);
        editable = isEditable;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return CourseMainFragment.newInstance(editable);
            case 1: return CourseAssessmentFragment.newInstance(editable);
            case 2: return CourseMentorFragment.newInstance(editable);
            case 3: return CourseNoteFragment.newInstance(editable);
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
