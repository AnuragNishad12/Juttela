package com.example.juttela;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.juttela.databinding.ActivitySettingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Setting_Activity extends AppCompatActivity {


    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference database =  FirebaseDatabase.getInstance().getReference().child("FinalUser").child(uid);


        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String age = dataSnapshot.child("age").getValue(String.class);
                    String country = dataSnapshot.child("country").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    binding.editNameText.setText(name);
                    binding.editAge.setText(age);
                    binding.editCountry.setText(country);
                    binding.editGender.setText(gender);
                    String imageUri = dataSnapshot.child("imageUri").getValue(String.class);
//                    binding.profileLan.setText(selectedCountry);
                    Glide.with(Setting_Activity.this)
                            .load(imageUri)
                            .into(binding.profileImageEdit);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}