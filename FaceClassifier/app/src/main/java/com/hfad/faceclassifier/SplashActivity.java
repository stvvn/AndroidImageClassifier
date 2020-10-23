package com.hfad.faceclassifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    // Time of splash animation
    private static int SPLASH_TIME = 5000;

    // UI in splash screen
    ImageView background_iv;
    // Animations
    Animation fadeInAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Initialize UI components
        background_iv = findViewById(R.id.logo);

        // Load Animations
        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Set Animations to UI elements
        background_iv.setAnimation(fadeInAnim);

        // Delay starting a new activity for 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);

                // Destroy this activity
                finish();

            }
        }, SPLASH_TIME);
    }
}