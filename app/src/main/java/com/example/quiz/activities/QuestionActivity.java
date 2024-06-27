package com.example.quiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz.Models.QuestionModel;
import com.example.quiz.R;
import com.example.quiz.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;
    ActivityQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        resetTimer();
        timer.start();

        String setName = getIntent().getStringExtra("set");

        // Load the appropriate set of questions
        switch (setName) {
            case "SET-1":
                setOne();
                break;
            case "SET-2":
                setTwo();
                break;
            case "SET-3":
                setThree();
                break;
            case "SET-4":
                setFour();
                break;
            case "SET-5":
                setFive();
                break;
            case "SET-6":
                setSix();
                break;
            case "SET-7":
                setSeven();
                break;
            case "SET-8":
                setEight();
                break;
            case "SET-9":
                setNine();
                break;
            case "SET-10":
                setTen();
                break;
            default:
                // Handle the case where setName does not match any known value
                break;
        }

        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setOnClickListener(view -> checkAnswer((Button) view));
        }

        playAnimation(binding.question, 0, list.get(position).getQuestion());

        binding.btnNext.setOnClickListener(view -> {
            if (timer != null) {
                timer.cancel();
            }
            timer.start();
            binding.btnNext.setEnabled(false);
            binding.btnNext.setAlpha(0.3f);
            enableOption(true);
            position++;

            if (position == list.size()) {
                Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("total", list.size());
                startActivity(intent);
                finish();
                return;
            }

            count = 0;
            playAnimation(binding.question, 0, list.get(position).getQuestion());
        });
    }

    private void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(QuestionActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.try_again).setOnClickListener(view -> {
                    Intent intent = new Intent(QuestionActivity.this, Set_Activity.class);
                    startActivity(intent);
                    finish();
                });
                dialog.show();
            }
        };
    }

    private void playAnimation(View view, int value, String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if (value == 0 && count < 4) {
                            String option = "";
                            if (count == 0) {
                                option = list.get(position).getOptionA();
                            } else if (count == 1) {
                                option = list.get(position).getOptionB();
                            } else if (count == 2) {
                                option = list.get(position).getOptionC();
                            } else if (count == 3) {
                                option = list.get(position).getOptionD();
                            }
                            playAnimation(binding.optionContainer.getChildAt(count), 0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        if (value == 0) {
                            try {
                                ((TextView) view).setText(data);
                                binding.totalQuestion.setText((position + 1) + "/" + list.size());
                            } catch (Exception e) {
                                ((Button) view).setText(data);
                            }
                            view.setTag(data);
                            playAnimation(view, 1, data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) { }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) { }
                });
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }
    }

    private void checkAnswer(Button selectedOption) {
        if (timer != null) {
            timer.cancel();
        }

        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())) {
            score++;
            selectedOption.setBackgroundResource(R.drawable.right_answer);
        } else {
            selectedOption.setBackgroundResource(R.drawable.wrong_answer);
            Button correctOption = (Button) binding.optionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.right_answer);
        }
    }


    public void setTwo(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));

    }

    public void setOne() {

        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));

    }

    public void setThree(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));

    }

    public void setFour(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));

    }

    public void setFive(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));


    }

    public void setSix(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));


    }

    public void setSeven(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));


    }

    public void setEight(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));


    }

    public void setNine(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));


    }

    public void setTen(){
        list.add(new QuestionModel("1. Which of the following has introduced text, list, box, margin, border, color, and background properties?",
                "A. HTML", "B. PHP", "C. CSS", "D. Ajax", "C. CSS"));
        list.add(new QuestionModel("2. CSS stands for - ",
                "A. Cascade style sheets", "B. Color and style sheets", "C. Cascading style sheets", "D. None of the above", "C. Cascading style sheets"));
        list.add(new QuestionModel("3. Which of the following is the correct syntax for referring the external style sheet?" ,
                "A. <style src = example.css>", "B. <style src = example.css >", "C. <stylesheet> example.css </stylesheet>", "D. <link rel=stylesheet type=text/css href=example.css>", "D. <link rel=stylesheet type=text/css href=example.css>"));
        list.add(new QuestionModel("4.  The property in CSS used to change the background color of an element is",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "C. background-color"));
        list.add(new QuestionModel("5. The property in CSS used to change the text color of an element is -",
                "A. bgcolor", "B. color", "C. background-color", "D. All of the above", "B. color"));
        list.add(new QuestionModel("6. The CSS property used to control the element's font-size is",
                "A. text-style", "B. text-color", "C. text-size", "D. font-size", "D. font-size"));
        list.add(new QuestionModel("7. The HTML attribute used to define the inline styles is ",
                "A. style", "B. styles", "C. class", "D. none of above", "A. style"));
        list.add(new QuestionModel("8.  The HTML attribute used to define the internal stylesheet is ",
                "A. <style>", "B. style", "C. <link>", "D. <script>", "A. <style>"));
        list.add(new QuestionModel("9. Which of the following CSS property is used to set the background image of an element?",
                "A. background-attachment", "B. background-attachment", "C. background-color", "D. None of the above", "B. background-attachment"));
        list.add(new QuestionModel("10. Which of the following is the correct syntax to make the background-color of all paragraph elements to yellow?",
                "A. all {background-color : yellow;}", "B. p {background-color : #yellow;}", "C. p {background-color : yellow;}", "D. all p {background-color : #yellow;}", "D. all p {background-color : #yellow;}"));


    }


}

