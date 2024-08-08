package com.example.juttela.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.juttela.R;
import com.example.juttela.Setting_Activity;
import com.example.juttela.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProfileFragment extends Fragment {



    public ProfileFragment() {

    }

    FragmentProfileBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater,container,false);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference database =  FirebaseDatabase.getInstance().getReference().child("FinalUser").child(uid);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> list = new ArrayList<>();

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String age = dataSnapshot.child("age").getValue(String.class);
                    String country = dataSnapshot.child("country").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String selectedCountry = dataSnapshot.child("selectedCountry").getValue(String.class);
                    String imageUri = dataSnapshot.child("imageUri").getValue(String.class);
                    binding.editName.setText(name);
                    binding.profileAge.setText(age);
                    binding.profileCountry.setText(country);
                    binding.profileGender.setText(gender);
                    binding.profileLan.setText(selectedCountry);
                    Glide.with(requireContext())
                            .load(imageUri)
                            .into(binding.profileImage);

                    DataSnapshot additionalDataSnapshot = dataSnapshot.child("additionalData");
                    if (additionalDataSnapshot.exists()) {
                        for (DataSnapshot languageSnapshot : additionalDataSnapshot.getChildren()) {
                            String language = languageSnapshot.getValue(String.class);
                            if (language != null) {
                                list.add(language);
                            }
                        }

                        if (!list.isEmpty()) {
                            String languagesString =String.join(", ", list);
                            binding.profileLearningLan.setText(languagesString);
                        } else {
                            binding.profileLearningLan.setVisibility(View.GONE);
                        }
                    } else {
                        binding.profileLearningLan.setVisibility(View.GONE);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), Setting_Activity.class);
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }
}