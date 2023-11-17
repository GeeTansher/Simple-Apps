package com.example.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.trivia.R;

public class Prefs {
    public static final String TRIVIA_STATE = "triviaState";
    public static final String HIGHEST_SCORE = "highestScore";
    private SharedPreferences preferences;

    public Prefs(Activity context){
        /*
         getSharedPreferences() — Use this if you need multiple shared preference files
         identified by name, which you specify with the first parameter. You can call this
         from any Context in your app. getPreferences() — Use this from an Activity if you
         need to use only one shared preference file for the activity.
        */
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);
    }

    public void saveHighestScore(int score){
        int lastScore = preferences.getInt(HIGHEST_SCORE, 0);
        if(score>lastScore)
            preferences.edit().putInt(HIGHEST_SCORE, score).apply();

    }

    public int getHighScore(){

        return preferences.getInt(HIGHEST_SCORE,0);
    }

    public void setState(int index){
        preferences.edit().putInt(TRIVIA_STATE, index).apply();
    }

    public int getState(){
        return preferences.getInt(TRIVIA_STATE,0);
    }
}
