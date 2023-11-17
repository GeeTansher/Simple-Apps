package com.example.trivia.model;

import androidx.annotation.NonNull;

public class Question {
    private String statement;
    private boolean ansTrue;

    public Question() {
    }

    public Question(String statement, boolean ansTrue) {
        this.statement = statement;
        this.ansTrue = ansTrue;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public boolean isAnsTrue() {
        return ansTrue;
    }

    public void setAnsTrue(boolean ansTrue) {
        this.ansTrue = ansTrue;
    }

    @NonNull
    @Override
    public String toString(){
        return "Question{" +
                "statement='" + statement +'\'' +
                "result=" + ansTrue + '}';
    }
}
