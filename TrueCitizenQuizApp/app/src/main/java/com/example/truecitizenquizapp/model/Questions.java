package com.example.truecitizenquizapp.model;

public class Questions {
    private int ansResId;
    private boolean ansTrue;

    public Questions(int ansResId, boolean ansTrue) {
        this.ansResId = ansResId;
        this.ansTrue = ansTrue;
    }

    public int getAnsResId() {
        return ansResId;
    }

    public void setAnsResId(int ansResId) {
        this.ansResId = ansResId;
    }

    public boolean isAnsTrue() {
        return ansTrue;
    }

    public void setAnsTrue(boolean ansTrue) {
        this.ansTrue = ansTrue;
    }
}
