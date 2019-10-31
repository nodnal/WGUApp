package com.example.wguapp.ui;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.util.DateUtil;
import com.example.wguapp.viewmodel.CourseDetailViewModel;

import java.util.Date;

public class CourseMainFragment extends Fragment {

    private EditText title;
    private EditText startDate;
    private EditText endDate;
    private EditText status;

    private CourseDetailViewModel vm;
    private LiveData<Course> course;

    public CourseMainFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        CourseMainFragment fragment = new CourseMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_course_main, container, false);

        initUI(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initBindings();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.action_edit:
                vm.setEditable(true);
            return(true);
        case R.id.action_save:
                vm.setEditable(false);
                saveCourse();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    private void saveCourse() {
        String newTitle = title.getText().toString().trim();
        Date start = new Date(startDate.getText().toString());
        Date end = new Date(endDate.getText().toString());
        String newStatus = status.getText().toString().trim();

        Boolean valid = (newTitle.length() > 0 && status.length() > 0 && start.before(end));

        if (valid){
            Course c = course.getValue();
            c.Title = newTitle;
            c.Status = newStatus;
            c.StartDate = start;
            c.EndDate = end;

            vm.SaveCourse(c);
            Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void initBindings() {
        course.observe(this, course -> {
            title.setText(course.Title);
            startDate.setText(DateUtil.toString(course.StartDate));
            endDate.setText(DateUtil.toString(course.EndDate));
            status.setText(course.Status);

        });
        vm.isEditable().observe(this,(value) -> {
            if (value){
                title.setInputType(InputType.TYPE_CLASS_TEXT);
                startDate.setInputType(InputType.TYPE_CLASS_DATETIME);
                endDate.setInputType(InputType.TYPE_CLASS_DATETIME);
            }else{
                title.setInputType(InputType.TYPE_NULL);
                startDate.setInputType(InputType.TYPE_NULL);
                endDate.setInputType(InputType.TYPE_NULL);
            }
        });
    }

    private void initViewModel() {
        vm = ViewModelProviders.of(getActivity()).get(CourseDetailViewModel.class);
        course = vm.getCourse();
    }

    private void initUI(View view) {
        title = view.findViewById(R.id.course_detail_title);
        startDate = view.findViewById(R.id.course_detail_start);
        endDate = view.findViewById(R.id.course_detail_end);
        status = view.findViewById(R.id.course_detail_status);
    }


}
