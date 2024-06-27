package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quiz.R;
import com.example.quiz.databinding.ActivityQuestionBinding;
import com.example.quiz.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {
    ActivityScoreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        int totalScore = getIntent().getIntExtra("total", 0);
        int correctAnswers = getIntent().getIntExtra("score", 8);

        int wrong = totalScore - correctAnswers;


        binding.totalQuestions.setText(String.valueOf(totalScore));
        binding.rightAnswers.setText(String.valueOf(correctAnswers));
        binding.wrongAnswers.setText(String.valueOf(wrong));
        binding.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, Set_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.bckbtn.setOnClickListener(v -> {
            finish();
        });

        binding.btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }

}