package com.example.wguapp.ui.activities.fragments;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.util.Constants;
import com.example.wguapp.util.DateUtil;
import com.example.wguapp.util.NotificationPublisher;
import com.example.wguapp.viewmodel.CourseDetailViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseMainFragment extends Fragment {

    private EditText title;
    private EditText startDate;
    private EditText endDate;
    private CheckBox startAlert;
    private CheckBox endAlert;
    private Spinner status;
    private ArrayAdapter<String> statusAdapter;
    private String[] courseStatus = new String[]{"Not Started","In Progress", "Dropped", "Finished"};

    private CourseDetailViewModel vm;
    private LiveData<Course> course;
    private Course currentCourse;

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

    private void initViewModel() {
        vm = ViewModelProviders.of(getActivity()).get(CourseDetailViewModel.class);
        course = vm.getCourse();
    }

    private void initBindings() {
        course.observe(this, course -> {
            title.setText(course.Title);
            startDate.setText(DateUtil.toString(course.StartDate));
            endDate.setText(DateUtil.toString(course.EndDate));
            int pos = statusAdapter.getPosition(course.Status);
            status.setSelection(pos);
            startAlert.setChecked(course.StartAlert);
            endAlert.setChecked(course.EndAlert);
            currentCourse = course;

        });
        vm.isEditable().observe(this,(value) -> {
            startAlert.setClickable(value);
            endAlert.setClickable(value);
            status.setEnabled(value);
            title.setEnabled(value);
            startDate.setEnabled(value);
            endDate.setEnabled(value);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.action_edit:
                vm.setEditable(true);
            return(true);
        case R.id.action_save:

                saveCourse();
            return(true);
        case R.id.action_delete:
            deleteCourse();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    private void deleteCourse() {
        vm.deleteCourse(currentCourse);
        Toast.makeText(getContext(), "Course Deleted", Toast.LENGTH_SHORT).show();
        this.getActivity().finish();
    }

    private void saveCourse() {
        String newTitle = title.getText().toString().trim();
        Date start, end;

        try{
            start = DateFormat.getDateInstance(SimpleDateFormat.SHORT).parse(startDate.getText().toString());
        }catch (ParseException e){
            Toast toast = Toast.makeText(getActivity(),"Invalid start date format. Use mm/dd/yyyy.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        try{
            end = DateFormat.getDateInstance(SimpleDateFormat.SHORT).parse(endDate.getText().toString());
        }catch (ParseException e){
            Toast toast = Toast.makeText(getActivity(),"Invalid end date format. Use mm/dd/yyyy.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        String newStatus = status.getSelectedItem().toString();
        boolean newStartAlert = startAlert.isChecked();
        boolean newEndAlert = endAlert.isChecked();


        Boolean validTitle = newTitle.length() > 0;
        Boolean validDates = start.before(end);

        if (validTitle & validDates){



            Course c = currentCourse;
            c.Title = newTitle;
            c.Status = newStatus;
            c.StartDate = start;
            c.EndDate = end;
            c.StartAlert = newStartAlert;
            c.EndAlert = newEndAlert;

            vm.SaveCourse(c);
            if(newStartAlert){
                scheduleNotification(start, "Course Start Alert", "Course Start Date Alert for " + newTitle);
            }
            if(newEndAlert){
                scheduleNotification(end, "Course End Alert", "Course End Date Alert for " + newTitle);
            }
            vm.setEditable(false);
            Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
        }  else if(!validTitle){
            Toast.makeText(getContext(), "Please enter a title.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Start Date must be before End Date.", Toast.LENGTH_SHORT).show();
        }
    }

    private void scheduleNotification(Date goal, String title, String content) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getActivity(), Constants.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_school_primarydark_24dp)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = builder.build();

        Intent intent = new Intent(CourseMainFragment.this.getActivity(), NotificationPublisher.class);
        intent.putExtra(NotificationPublisher. NOTIFICATION_ID , goal.hashCode());
        intent.putExtra(NotificationPublisher. NOTIFICATION , notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseMainFragment.this.getActivity(), 0 , intent, 0);
        AlarmManager alarmManager = (AlarmManager)this.getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, goal.getTime(), pendingIntent);
    }

    private void initUI(View view) {
        title = view.findViewById(R.id.course_detail_title);
        startDate = view.findViewById(R.id.course_detail_start);
        endDate = view.findViewById(R.id.course_detail_end);
        status = view.findViewById(R.id.course_detail_status);
        statusAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_dropdown_item, courseStatus);
        status.setAdapter(statusAdapter);
        startAlert = view.findViewById(R.id.courese_detail_start_alert);
        endAlert = view.findViewById(R.id.course_detail_end_alert);
    }


}
