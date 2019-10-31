package com.example.wguapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.ui.OnViewHolderBindCallback;
import com.example.wguapp.util.DateUtil;

import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentViewHolder> {

    private List<Assessment> assessments;


    public OnViewHolderBindCallback OnViewHolderBind;

    public AssessmentListAdapter(OnViewHolderBindCallback cb){
        this.OnViewHolderBind = cb;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_list_item_layout, parent, false);
        AssessmentViewHolder holder = new AssessmentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        Assessment assessment = assessments.get(position);
        holder.title.setText(assessment.Title);
        holder.type.setText(assessment.Type);
        holder.goalDate.setText(DateUtil.toString(assessment.GoalDate));
        if (OnViewHolderBind != null){
            OnViewHolderBind.onViewHolderBind(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (assessments != null)
            return assessments.size();
        else return 0;
    }


    public class AssessmentViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView type;
        public TextView goalDate;


        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.assessment_list_item_title);
            type = itemView.findViewById(R.id.assessment_list_item_type);
            goalDate = itemView.findViewById(R.id.assessment_list_item_goal_date);


        }

    }

    public void setAssessments(List<Assessment> assessments){
        this.assessments= assessments;
        notifyDataSetChanged();
    }

}
