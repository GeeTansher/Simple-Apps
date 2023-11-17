package com.example.frameanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private AnimationDrawable batAnimation;
    private ImageView batImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batImage = findViewById(R.id.imageView);
        // for framing anim
//        batImage.setBackgroundResource(R.drawable.bat_anim);
//        batAnimation = (AnimationDrawable) batImage.getBackground();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        batAnimation.start();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // for fading anim
                Animation startAnim = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fadein_animation);
                batImage.startAnimation(startAnim);

                // for framing anim
                // stop the anim
//                batAnimation.stop();
            }
        }, 50);  // 5000 for framing
        return super.onTouchEvent(event);
    }
}