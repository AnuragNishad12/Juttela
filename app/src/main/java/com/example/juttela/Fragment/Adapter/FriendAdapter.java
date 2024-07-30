package com.example.juttela.Fragment.Adapter;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.juttela.Chat_Activity;
import com.example.juttela.FrontPage.FinalUser;
import com.example.juttela.R;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

   ArrayList<FinalUser> finalUsers;
    Context context;

    public FriendAdapter(ArrayList<FinalUser> finalUsers, Context context) {
        this.finalUsers = finalUsers;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendAdapter.FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.FriendViewHolder holder, int position) {
        FinalUser friend = finalUsers.get(position);
        holder.text.setText(friend.getName());
        Glide.with(holder.itemView.getContext())
                .load(friend.getImageUri())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Chat_Activity.class);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return finalUsers.size();
    }



    public class FriendViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            text= itemView.findViewById(R.id.message_Name);
            image = itemView.findViewById(R.id.message_Image);
        }
    }



}
