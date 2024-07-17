package com.example.juttela.FrontPage;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.juttela.CustomDialog;
import com.example.juttela.R;
import com.example.juttela.Send_Request_dialog;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<FinalUser> allUsers;
    private List<FinalUser> filteredUsers;
    private String currentFilter = null;

    public UserAdapter(List<FinalUser> users) {
        this.allUsers = new ArrayList<>(users);
        this.filteredUsers = new ArrayList<>(users);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_card_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        FinalUser user = filteredUsers.get(position);
        holder.bind(user);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Showdialog(view.getContext());
            }
        });



    }
   public  void Showdialog(Context context){
       Send_Request_dialog customDialog = new Send_Request_dialog();
        customDialog.Request_dialog(context);
   }

    @Override
    public int getItemCount() {
        return filteredUsers.size();
    }
    public void filterByGender(String gender) {
        currentFilter = gender;
        applyFilter();
    }
    private void applyFilter() {
        filteredUsers.clear();
        if (currentFilter == null || currentFilter.isEmpty()) {
            filteredUsers.addAll(allUsers);
        } else {
            for (FinalUser user : allUsers) {
                if (user.getGender().equalsIgnoreCase(currentFilter)) {
                    filteredUsers.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }



    public void updateUsers(List<FinalUser> newUsers) {
        allUsers.clear();
        allUsers.addAll(newUsers);
        applyFilter();
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