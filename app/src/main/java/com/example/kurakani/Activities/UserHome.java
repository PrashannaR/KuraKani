package com.example.kurakani.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.kurakani.Adapters.UsersAdapter;
import com.example.kurakani.Models.User;
import com.example.kurakani.R;
import com.example.kurakani.databinding.ActivityUserHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;

public class UserHome extends AppCompatActivity {

    ActivityUserHomeBinding binding;
    FirebaseDatabase database;
    ArrayList<User> users;
    UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.botNav.setSelectedItemId(R.id.chat);

        binding.botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.chat){
                    startActivity(new Intent(UserHome.this, UserHome.class));
                }else if (id == R.id.contacts){
                    startActivity(new Intent(UserHome.this, Contacts.class));
                }else if (id == R.id.settings){
                    startActivity(new Intent(UserHome.this, Settings.class));
                }
                return false;
            }
        });

        binding.botNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.chat){
                    startActivity(new Intent(UserHome.this, UserHome.class));
                }else if (id == R.id.contacts){
                    startActivity(new Intent(UserHome.this, Contacts.class));
                }else if (id == R.id.settings){
                    startActivity(new Intent(UserHome.this, Settings.class));
                }
            }
        });



        database = FirebaseDatabase.getInstance();
        users = new ArrayList<>();

        usersAdapter = new UsersAdapter(this, users);
        binding.recyclerView.setAdapter(usersAdapter);

        binding.recyclerView.showShimmerAdapter();

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    if(!user.getUid().equals(FirebaseAuth.getInstance().getUid()))
                        users.add(user);

                }
                binding.recyclerView.hideShimmerAdapter();
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }






}