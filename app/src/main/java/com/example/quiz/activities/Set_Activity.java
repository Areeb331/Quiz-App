package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.quiz.Adapters.SetAdapter;
import com.example.quiz.Models.SetModel;
import com.example.quiz.R;
import com.example.quiz.databinding.ActivitySetBinding;

import java.util.ArrayList;

public class Set_Activity extends AppCompatActivity {
    ActivitySetBinding binding;
    ArrayList<SetModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        list = new ArrayList<>();
        list.add(new SetModel("SET-1"));
        list.add(new SetModel("SET-2"));
        list.add(new SetModel("SET-3"));
        list.add(new SetModel("SET-4"));
        list.add(new SetModel("SET-5"));
        list.add(new SetModel("SET-6"));
        list.add(new SetModel("SET-7"));
        list.add(new SetModel("SET-8"));
        list.add(new SetModel("SET-9"));
        list.add(new SetModel("SET-10"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.setsRecy.setLayoutManager(linearLayoutManager);

        SetAdapter adapter = new SetAdapter(this, list);
        binding.setsRecy.setAdapter(adapter);

        binding.bckbtn.setOnClickListener(v -> {
            finish();
        });

    }
}
