package com.hfad.faceclassifier.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hfad.faceclassifier.HomeActivity;
import com.hfad.faceclassifier.R;

public class Login extends AppCompatActivity {

    // UI elements
    TextInputLayout username, password;
    RelativeLayout progressBar;

    String usernameStr, passwordStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password  = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.login_progress_bar);

        progressBar.setVisibility(View.GONE);
    }

    // User login to the app
    public void userLogIn(View view) {

        if(!validateFields())
            return;

        // Perform database query
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("username").equalTo(usernameStr);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    progressBar.setVisibility(View.GONE);
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String systemPassword = snapshot.child(usernameStr).child("password")
                            .getValue(String.class);

                    // Check if the password entered matches
                    if(systemPassword.equals(passwordStr)){
                        password.setError(null);
                        password.setErrorEnabled(false);


                        // Login successful
                        String fullName = snapshot.child(usernameStr).child("fullName")
                                .getValue(String.class);
                        String email = snapshot.child(usernameStr).child("email")
                                .getValue(String.class);

                        Toast.makeText(Login.this, "Login successful!",
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Login.this, HomeActivity.class));


                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Password does not match!",
                                Toast.LENGTH_SHORT).show();
                    }


                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "User does not exist",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validateFields(){
        progressBar.setVisibility(View.VISIBLE);
        usernameStr = username.getEditText().getText().toString().trim();
        passwordStr = password.getEditText().getText().toString().trim();

        if(usernameStr.isEmpty()){
            username.setError("Username cannot be empty");
            username.requestFocus();
            return false;
        } else if(passwordStr.isEmpty()){
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        } else {
            return true;
        }

    }

}