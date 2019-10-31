package com.example.wguapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Mentor;

import java.util.List;

public class MentorListAdapter extends RecyclerView.Adapter<MentorListAdapter.MentorViewHolder> {
    private List<Mentor> mentors;


    public OnViewHolderBindCallback OnViewHolderBind;

    public MentorListAdapter(OnViewHolderBindCallback cb) {
        this.OnViewHolderBind = cb;
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mentor_list_item_layout, parent, false);
        MentorViewHolder holder = new MentorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        Mentor mentor = mentors.get(position);
        String name = mentor.FirstName + " " + mentor.LastName;
        holder.name.setText(name);
        holder.phone.setText(mentor.PhoneNumber);
        holder.email.setText(mentor.EmailAddress);
        if (OnViewHolderBind != null) {
            OnViewHolderBind.onViewHolderBind(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (mentors != null)
            return mentors.size();
        else return 0;
    }


    public class MentorViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phone;
        public TextView email;

        public MentorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mentor_list_name);
            phone = itemView.findViewById(R.id.mentor_list_phone);
            email = itemView.findViewById(R.id.mentor_list_email);
        }

    }

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
        notifyDataSetChanged();
    }
}
