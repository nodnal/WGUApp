package com.example.wguapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.db.entity.Course;
import com.example.wguapp.ui.CourseDetailActivity;
import com.example.wguapp.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseListAdapter extends RecyclerView.Adapter<com.example.wguapp.CourseListAdapter.CourseViewHolder>{


        private List<Course> courses;
        public View.OnClickListener ViewHolderOnClickListener;

        @NonNull
        @Override
        public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item_layout, parent, false);
            CourseViewHolder holder = new CourseViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
            Course course = courses.get(position);
            holder.title.setText(course.Title);
            holder.startDate.setText(DateUtil.toString(course.StartDate));
            holder.endDate.setText(DateUtil.toString(course.EndDate));
        }

        @Override
        public int getItemCount() {
            if (courses != null)
                return courses.size();
            else return 0;
        }


        public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            @BindView(R.id.term_list_item_title)
            TextView title;
            @BindView(R.id.term_list_item_start_date)
            TextView startDate;
            @BindView(R.id.term_list_item_end_date)
            TextView endDate;

            public CourseViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this::onClick);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Course course = courses.get(position);
                Intent intent = new Intent(v.getContext(), CourseDetailActivity.class);
                intent.putExtra("term_id", course.getId());
                v.getContext().startActivity(intent);

            }
        }

        public void setCourses(List<Course> courses){
            this.courses = courses;
            notifyDataSetChanged();
        }



}
