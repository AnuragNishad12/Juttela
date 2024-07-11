package com.example.juttela.FrontPage;

import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.juttela.MainActivity;
import com.example.juttela.R;
import com.example.juttela.databinding.ActivityAvtarSettingBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class Avtar_Setting extends AppCompatActivity {

    ActivityAvtarSettingBinding binding;
    private ImageAdapter adapter;
    private List<Uri> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding  = ActivityAvtarSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageList = new ArrayList<>();
        adapter = new ImageAdapter(imageList, new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Uri uri) {
                Glide.with(Avtar_Setting.this).load(uri).into(binding.profileImage);
                binding.profileImage.setTag(uri);
            }
        });

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setAdapter(adapter);

        fetchImagesFromFirebase();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.avtarProgress.setVisibility(View.VISIBLE);
                String name = getIntent().getStringExtra("name");
                String age = getIntent().getStringExtra("age");
                String country = getIntent().getStringExtra("country");
                String selectedItem = getIntent().getStringExtra("selectedItem");
                String selectedCountry = getIntent().getStringExtra("selectedCountry");

                // Get the selected image URI
                Uri selectedImageUri = (Uri) binding.profileImage.getTag();
                String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : "";


                FinalUser user = new FinalUser(name, age, country, selectedItem, selectedCountry, imageUriString);


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("FinalUser");

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();




                myRef.child(currentUser.getUid()).setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                binding.avtarProgress.setVisibility(View.GONE);
                                Intent intent = new Intent(Avtar_Setting.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(Avtar_Setting.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save data
                                Toast.makeText(Avtar_Setting.this, "Failed to save profile", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    private void fetchImagesFromFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("Girls");

        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageList.add(uri);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

    }
}