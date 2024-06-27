package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quiz.activities.Set_Activity;
import com.example.quiz.activities.SplashActivity;

public class MainActivity extends AppCompatActivity {
    CardView css, nts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure action bar is not null before hiding it
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        css = findViewById(R.id.css);
        nts = findViewById(R.id.nts);

        css.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Set_Activity.class);
                startActivity(intent);
            }
        });
    }
}

