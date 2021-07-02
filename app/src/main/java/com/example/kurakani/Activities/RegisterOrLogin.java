package com.example.kurakani.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kurakani.databinding.ActivityRegisterOrLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterOrLogin extends AppCompatActivity {

    ActivityRegisterOrLoginBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterOrLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(RegisterOrLogin.this, UserHome.class));
            finish();
        }

        binding.phoneNumber.requestFocus();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterOrLogin.this, OTP.class);
                intent.putExtra("phoneNumber", binding.phoneNumber.getText().toString());
                startActivity(intent);
            }
        });

    }
}