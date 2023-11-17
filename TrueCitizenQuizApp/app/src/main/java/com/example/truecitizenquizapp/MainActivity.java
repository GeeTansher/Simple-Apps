package com.example.truecitizenquizapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.truecitizenquizapp.databinding.ActivityMainBinding;
import com.example.truecitizenquizapp.model.Questions;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private final Questions[] questionBank = new Questions[]{
            new Questions(R.string.question_amendments, false),
            new Questions(R.string.question_constitution, true),
            new Questions(R.string.question_declaration, true),
            new Questions(R.string.question_independence_rights, true),
            new Questions(R.string.question_religion, true),
            new Questions(R.string.question_government, false),
            new Questions(R.string.question_government_feds, false),
            new Questions(R.string.question_government_senators, false),
    };
    private ActivityMainBinding binding;
    private int currentQuesIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.tvQuestion.setText(questionBank[currentQuesIndex].getAnsResId());

        binding.btnTrue.setOnClickListener(view -> checkAns(true));

        binding.btnFalse.setOnClickListener(view -> checkAns(false));

        binding.btnNext.setOnClickListener(view -> {
//            Log.d("Main","onCreate: " + questionBank[currentQuesIndex++].getAnsResId());
            currentQuesIndex = (currentQuesIndex + 1) % questionBank.length;
            updateQuestion();
        });

        binding.btnPrev.setOnClickListener(view -> {
            if (currentQuesIndex > 0) {
                currentQuesIndex = (currentQuesIndex - 1) % questionBank.length;
                updateQuestion();
            }
        });
    }

    private void checkAns(boolean userChoice) {
        boolean ansIsCorrect = questionBank[currentQuesIndex].isAnsTrue();
        int messageId;
        if (ansIsCorrect == userChoice) {
            messageId = R.string.correct_answer;
        } else {
            messageId = R.string.wrong_answer;
        }
        Snackbar.make(binding.imageView, messageId, Snackbar.LENGTH_SHORT)
//                .setAction("Next", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        currentQuesIndex = (currentQuesIndex + 1) % questionBank.length;
//                        updateQuestion();
//                    }
//                })
                .show();
    }

    private void updateQuestion() {
        binding.tvQuestion.setText(questionBank[currentQuesIndex].getAnsResId());
    }
}