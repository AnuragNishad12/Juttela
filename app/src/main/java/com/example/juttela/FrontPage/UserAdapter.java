package com.example.juttela.FrontPage;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.juttela.R;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<FinalUser> users;

    public UserAdapter(List<FinalUser> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_card_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        FinalUser user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView nameTextView;
        private TextView ageTextView;
        private TextView countryTextView;
        private TextView genderTextView, motherTounge;


        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.imageView4);
            nameTextView = itemView.findViewById(R.id.userName);
            ageTextView = itemView.findViewById(R.id.age_card);
            countryTextView = itemView.findViewById(R.id.country_card);
            genderTextView = itemView.findViewById(R.id.gender_card);
            motherTounge = itemView.findViewById(R.id.mother_card);

        }

        void bind(FinalUser user) {
            nameTextView.setText(user.name);
            ageTextView.setText(user.age);
            countryTextView.setText(user.country);
            genderTextView.setText(user.gender);
            motherTounge.setText(user.getSelectedCountry());

            if (user.imageUri != null && !user.imageUri.isEmpty()) {
                Uri imageUri = Uri.parse(user.imageUri);
                Glide.with(itemView.getContext()).load(imageUri).into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.avatar); // Set a default image
            }
        }
    }
}