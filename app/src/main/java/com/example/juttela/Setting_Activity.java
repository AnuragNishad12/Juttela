package com.example.juttela;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.juttela.databinding.ActivitySettingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        binding.saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String name= binding.editNameText.getText().toString().trim();
             String age = binding.editAge.getText().toString().trim();
             String country = binding.editCountry.getText().toString().trim();
             String gender = binding.editGender.getText().toString().trim();

                Map<String, Object> updates = new HashMap<>();
                updates.put("name",name);
                updates.put("age", age);
                updates.put("gender", gender);
                updates.put("country", country);

                database.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Setting_Activity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Setting_Activity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        binding.settingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}