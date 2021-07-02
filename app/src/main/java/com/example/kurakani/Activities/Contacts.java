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

    RecyclerView recyclerView;
    ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();
    MainAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        recyclerView = binding.recyclerView;

        checkPermission();


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
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(Contacts.this, Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(Contacts.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 100);
        }else {
            getContactsList();
        }
    }

    private void getContactsList() {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +"ASC";

        Cursor cursor = getContentResolver().query(
                uri, null, null, null, sort
        );

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        + "=?";

                 Cursor phoneCursor = getContentResolver().query(
                         uriPhone, null, selection,
                         new String[]{id}, null
                 );
                 if(phoneCursor.moveToNext()){
                     String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                     ContactModel model = new ContactModel();

                     model.setName(name);
                     model.setNumber(number);

                     arrayList.add(model);

                     phoneCursor.close();
                 }
            }
            cursor.close();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MainAdapter(this,arrayList);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] ==
        PackageManager.PERMISSION_GRANTED){
            getContactsList();
        }else {
            Toast.makeText(Contacts.this, "Permission Denied", Toast.LENGTH_SHORT).show();

            checkPermission();
        }

    }
}
