package com.hfad.faceclassifier.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.faceclassifier.Database.UserHelper;
import com.hfad.faceclassifier.R;

public class Signup3rd_page extends AppCompatActivity {

    TextInputLayout phoneNumber;

    String fullNameStr, usernameStr, emailStr, passwordStr, selectedGenderStr, birthday,
            phoneNumberStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3rd_page);

        phoneNumber = findViewById(R.id.signup_phonenumber);

        fullNameStr = getIntent().getExtras().getString("Full Name");
        usernameStr = getIntent().getExtras().getString("Username");
        emailStr = getIntent().getExtras().getString("Email");
        passwordStr = getIntent().getExtras().getString("Password");
        selectedGenderStr = getIntent().getExtras().getString("Gender");
        birthday = getIntent().getExtras().getString("Birthday");

    }

    private boolean validatePhoneNumber(){
        phoneNumberStr = phoneNumber.getEditText().getText().toString().trim();

        if(usernameStr.isEmpty()) {
            phoneNumber.setError("Field can not be empty");
            return false;
        }
        else
            return true;
    }


    public void SignupButtonClicked(View view) {

        if(!validatePhoneNumber())
            return;

        storeNewUserData();
    }

    private void storeNewUserData() {

        // Points to firebase database
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        // Points to specific reference (table)
        DatabaseReference usersReference = rootNode.getReference("Users");

        UserHelper newUser = new UserHelper(fullNameStr, usernameStr, emailStr, phoneNumberStr,
                passwordStr, birthday, selectedGenderStr);

        // Write to database
        usersReference.child(phoneNumberStr).setValue(newUser);


    }
}