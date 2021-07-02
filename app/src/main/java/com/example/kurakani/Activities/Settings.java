package com.example.kurakani.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.bumptech.glide.Glide;
import com.example.kurakani.Models.User;
import com.example.kurakani.R;
import com.example.kurakani.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity {

    ActivitySettingsBinding binding;

    DatabaseReference database;
    FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
    Uri selectedImage;
    StorageReference storage;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        String name = getIntent().getStringExtra("name");
        String uid = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance().getReference().child("users");
        storage = FirebaseStorage.getInstance().getReference();

        binding.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImage != null){
                    StorageReference reference = storage.child("Profiles").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                       reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {

                               final Map<String, Object> map = new HashMap<>();
                               map.put("profilePhoto", uri.toString());

                               database.child(uid).addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                       if (snapshot.exists()) {
                                           database.child(uid).updateChildren(map);
                                       } else {
                                           database.child(uid).setValue(map);
                                       }
                                   }
                                   @Override
                                   public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                   }
                               });

                           }
                       });
                        }
                    });
                }
            }
        });


        binding.botNav.setSelectedItemId(R.id.settings);

        binding.botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                if(id== R.id.chat){
                    startActivity(new Intent(Settings.this, UserHome.class));
                }else if (id == R.id.contacts){
                    startActivity(new Intent(Settings.this, Contacts.class));
                }else if (id == R.id.settings){
                    startActivity(new Intent(Settings.this, Settings.class));
                }
                return false;
            }
        });

        binding.botNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.chat){
                    startActivity(new Intent(Settings.this, UserHome.class));
                }else if (id == R.id.contacts){
                    startActivity(new Intent(Settings.this, Contacts.class));
                }else if (id == R.id.settings){
                    startActivity(new Intent(Settings.this, Settings.class));
                }
            }
        });


        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, RegisterOrLogin.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        database.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
           if(snapshot.exists()){
               binding.name.setText(snapshot.child("name").getValue().toString());
               Glide.with(getApplicationContext()).load(snapshot.child("profilePhoto").getValue().toString()).into(binding.profilePhoto);
           }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}



