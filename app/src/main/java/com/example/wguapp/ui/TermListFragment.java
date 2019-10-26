package com.example.wguapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.R;
import com.example.wguapp.TermListAdapter;
import com.example.wguapp.db.entity.Term;
import com.example.wguapp.viewmodel.TermListViewModel;

import java.util.Date;
import java.util.List;

public class TermListFragment extends Fragment {

    FragmentListener mListener;
    RecyclerView termListView;
    Button testButton;

    private TermListAdapter adapter;

    private TermListViewModel termListViewModel;

    public static TermListFragment newInstance() {
        return new TermListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_list, container, false);
        termListView = view.findViewById(R.id.term_list_view);
        testButton = view.findViewById(R.id.test_button);
        testButton.setOnClickListener((View v) -> termListViewModel.insertTerm(new Term("title", new Date(0), new Date(5000))));
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        termListViewModel = ViewModelProviders.of(getActivity()).get(TermListViewModel.class);
        termListViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.setTerms(terms);
            }
        });

    }

    public void NavigateToDetail() {
        if (mListener != null) {
            mListener.onNavigateToFragment("TermDetailFragment");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            mListener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initRecyclerView(View view) {
        adapter = new TermListAdapter();
        termListView.setAdapter(adapter);
        termListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

}
