package com.example.wguapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.db.entity.Term;
import com.example.wguapp.ui.TermDetailActivity;
import com.example.wguapp.ui.TermListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {

    private List<Term> terms;
    public View.OnClickListener ViewHolderOnClickListener;

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item_layout, parent, false);
        TermViewHolder holder = new TermViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        Term term = terms.get(position);
        holder.title.setText(term.getTitle());
        holder.startDate.setText(term.getStartDate().toString());
        holder.endDate.setText(term.getEndDate().toString());
    }

    @Override
    public int getItemCount() {
        if (terms != null)
            return terms.size();
        else return 0;
    }


    public class TermViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.term_list_item_title)
        TextView title;
        @BindView(R.id.term_list_item_start_date)
        TextView startDate;
        @BindView(R.id.term_list_item_end_date)
        TextView endDate;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Term term = terms.get(position);
            Intent intent = new Intent(v.getContext(), TermDetailActivity.class);
            intent.putExtra("term_id", term.getId());
            v.getContext().startActivity(intent);

        }
    }

    public void setTerms(List<Term> terms){
        this.terms = terms;
        notifyDataSetChanged();
    }

}
