package com.hfad.faceclassifier.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.hfad.faceclassifier.R;

public class StartUpScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_screen);
    }

    public void LoginButtonClicked(View view) {

        Intent intent = new Intent(StartUpScreen.this, Login.class);

        // Animation
        Pair[] pairs = new Pair[1]; // One element to animate
        pairs[0] = new Pair<View, String>(findViewById(R.id.login_btn), "transition_login");

        // Apply animation only for Lollipop or higher
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    StartUpScreen.this, pairs);
            startActivity(intent, options.toBundle());
        } else
            startActivity(intent);


    }

    public void SignupButtonClicked(View view) {

        Intent intent = new Intent(StartUpScreen.this, SignUp.class);

        // Animation
        Pair[] pairs = new Pair[1]; // One element to animate
        pairs[0] = new Pair<View, String>(findViewById(R.id.signup_btn), "transition_signup");

        // Apply animation only for Lollipop or higher
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    StartUpScreen.this, pairs);
            startActivity(intent, options.toBundle());
        }

        else {
            startActivity(intent);
        }
    }
}