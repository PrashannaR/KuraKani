package com.example.kurakani.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.kurakani.Adapters.MainAdapter;
import com.example.kurakani.Models.ContactModel;
import com.example.kurakani.R;
import com.example.kurakani.databinding.ActivityContactsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    ActivityContactsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        //bottom nav functioning
        binding.botNav.setSelectedItemId(R.id.contacts);


        binding.botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                if(id== R.id.chat){
                    startActivity(new Intent(Contacts.this, UserHome.class));
                }else if (id == R.id.contacts){
                    startActivity(new Intent(Contacts.this, Contacts.class));
                }else if (id == R.id.settings){
                    startActivity(new Intent(Contacts.this, Settings.class));
                }
                return false;
            }
        });

        binding.botNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.chat){
                    startActivity(new Intent(Contacts.this, UserHome.class));
                }else if (id == R.id.contacts){
                    startActivity(new Intent(Contacts.this, Contacts.class));
                }else if (id == R.id.settings){
                    startActivity(new Intent(Contacts.this, Settings.class));
                }
            }
        });

        binding.firstBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contacts.this, ToDo.class);
                startActivity(intent);
            }
        });

    }








}
