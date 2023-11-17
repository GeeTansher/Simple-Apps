package com.example.trivia;

import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.trivia.data.AnsListAsyncResponse;
import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.example.trivia.model.ScoreClass;
import com.example.trivia.util.Prefs;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int currentQuesIndex = 0;
    private List<Question> questions;
    private int scoreCounter = 0;
    /*
     There is no actual need of helper because it is going to the next question
     automatically, so see to it if u need or not, it will be helpful when u need next button
     perform manually
    */
    private int helper = 0;
    private int helper1 = 0;
    private ScoreClass score;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = new Prefs(MainActivity.this);
        score = new ScoreClass();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        /*
         This use of interface is when android will be getting data it will not wait for
         further process instead run them in parallel thus we will get ArrayOutOfBound error
         as nothing is in questionArrayList so we call processFinished and put data in Repo class only
        */
        questions = new Repository().getQuestions(new AnsListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                binding.tvQuestion.setText(questionArrayList.get(currentQuesIndex)
                        .getStatement());
                updateCounter(questionArrayList);
            }
        });

        binding.tvScore.setText(String.format("Score: %s", score.getScore()));
        binding.tvHighScore.setText(MessageFormat.format("High Score: {0}",
                String.valueOf(prefs.getHighScore())));

        binding.btnNext.setOnClickListener(v -> {
            getNextQuestion();
        });

        binding.btnTrue.setOnClickListener(v -> {
            checkAns(true);
            updateQuestion();
        });

        binding.btnFalse.setOnClickListener(v -> {
            checkAns(false);
            updateQuestion();
        });

    }

    private void getNextQuestion() {
        currentQuesIndex = (currentQuesIndex + 1) % questions.size();
        helper = 0;
        updateQuestion();
    }

    private void checkAns(boolean b) {
        boolean ans = questions.get(currentQuesIndex).isAnsTrue();
        int snackMessageId;
        if (b == ans) {
            snackMessageId = R.string.correctAns;

            Toast.makeText(this, R.string.correctAns, Toast.LENGTH_SHORT).show();
            fadeAnimation();
            addPoints();
        } else {
            snackMessageId = R.string.incorrectAns;

            Toast.makeText(this, R.string.incorrectAns, Toast.LENGTH_SHORT).show();
            shakeAnimation();
            deductPoints();
        }
        Snackbar.make(binding.cvQuestion, snackMessageId, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.tvScore.setText(String.format("Score: %s", score.getScore()));
        binding.tvHighScore.setText(MessageFormat.format("High Score: {0}",
                String.valueOf(prefs.getHighScore())));
        if (helper1 == 1) {
            currentQuesIndex = prefs.getState();
            helper1 = 0;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.tvScore.setText(String.format("Score: %s", score.getScore()));
        binding.tvHighScore.setText(MessageFormat.format("High Score: {0}",
                String.valueOf(prefs.getHighScore())));

        helper1 = 1;
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs.saveHighestScore(score.getScore());
        prefs.setState(currentQuesIndex);
    }

    private void deductPoints() {
        if (helper == 0) {
            if (scoreCounter > 0) {
                scoreCounter -= 5;
                helper = 1;
            } else {
                scoreCounter = 0;
                helper = 1;
            }
            score.setScore(scoreCounter);
        }
        binding.tvScore.setText(String.format("Score: %s", score.getScore()));
    }

    private void addPoints() {
        if (helper == 0) {
            scoreCounter += 10;
            score.setScore(scoreCounter);
            helper = 1;
        }
        binding.tvScore.setText(String.format("Score: %s", score.getScore()));
    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.tvOutOfQuestion.setText(String.format(getString(R.string.formattedTextViewOutOfQuestion),
                currentQuesIndex + 1, questionArrayList.size() + 1));
    }

    private void updateQuestion() {
        binding.tvQuestion.setText(questions.get(currentQuesIndex).getStatement());
        updateCounter((ArrayList<Question>) questions);
    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cvQuestion.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.tvQuestion.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.tvQuestion.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);

        binding.cvQuestion.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.tvQuestion.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.tvQuestion.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}