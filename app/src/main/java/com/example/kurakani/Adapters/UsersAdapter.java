package com.example.kurakani.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kurakani.Activities.ChatActivity;
import com.example.kurakani.R;
import com.example.kurakani.Models.User;
import com.example.kurakani.databinding.RowConvBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> users;

    public UsersAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creates chat view with row_conv as its template
        View view = LayoutInflater.from(context).inflate(R.layout.row_conv, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        String senderId = FirebaseAuth.getInstance().getUid();

        String senderRoom = senderId + user.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);
                           // long time = snapshot.child("lastMsgTime").getValue(Long.class);
                            holder.binding.lastMsg.setText(lastMsg);
                            //SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                           // holder.binding.time.setText(dateFormat.format(new Date(time)));
                        }else {
                            holder.binding.lastMsg.setText("Tap to start the conversation");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        holder.binding.username.setText(user.getName());
        Glide.with(context).load(user.getProfilePhoto())
                .placeholder(R.drawable.avatar)
                .into(holder.binding.sampleDP);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("name", user.getName());
                    intent.putExtra("uid", user.getUid());
                    context.startActivity(intent);
                }
            });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        RowConvBinding binding;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowConvBinding.bind(itemView);
        }
    }

}
