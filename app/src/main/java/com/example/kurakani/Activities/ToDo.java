package com.example.kurakani.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kurakani.databinding.ActivityToDoBinding;

public class ToDo extends AppCompatActivity {

    ActivityToDoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToDoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
    }
}