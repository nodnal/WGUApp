package com.example.wguapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.R;
import com.example.wguapp.db.entity.Note;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private List<Note> notes;


    public OnViewHolderBindCallback OnViewHolderBind;

    public NoteListAdapter(OnViewHolderBindCallback cb) {
        this.OnViewHolderBind = cb;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item_layout, parent, false);
        NoteViewHolder holder = new NoteListAdapter.NoteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.Title);
        holder.noteBody.setText(note.Note);
        if (OnViewHolderBind != null) {
            OnViewHolderBind.onViewHolderBind(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (notes != null)
            return notes.size();
        else return 0;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView noteBody;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_list_title);
            noteBody = itemView.findViewById(R.id.note_list_note);
        }

    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }
}