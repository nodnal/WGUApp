package com.example.wguapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguapp.R;
import com.example.wguapp.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn1;


    public void btnHandler(){
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }

    private ArrayList<String> mItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.button1);
        mItems = new ArrayList<String>();
        mItems.add("hi");
        mItems.add("bye");
        initRecyclerView();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHandler();
            }
        });
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
