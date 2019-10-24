package com.example.wguapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.db.entity.Term;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {

    private List<Term> terms;

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item_layout, parent, false);
        TermViewHolder holder = new TermViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        holder.title.setText(terms.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if (terms != null)
            return terms.size();
        else return 0;
    }

    public class TermViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.term_list_item_title)
        TextView title;
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setTerms(List<Term> terms){
        this.terms = terms;
        notifyDataSetChanged();
    }
}
