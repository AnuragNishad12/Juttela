package com.example.juttela.FrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.juttela.MainActivity;
import com.example.juttela.R;
import com.example.juttela.databinding.ActivitySignUpPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.abt.FirebaseABTesting;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Sign_Up_Page extends AppCompatActivity {


    ActivitySignUpPageBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    binding.backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Sign_Up_Page.this, Front_Pages.class);
            startActivity(intent);
        }
    });

    binding.signButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.progressBar.setVisibility(View.VISIBLE);

            String name = binding.fullname.getText().toString();
            String email =  binding.email.getText().toString();
            String password = binding.password.getText().toString();
            if (name.isEmpty() && email.isEmpty() && password.isEmpty()){
                Toast.makeText(Sign_Up_Page.this, "Fill All the Field", Toast.LENGTH_SHORT).show();
            }else {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            binding.progressBar.setVisibility(View.GONE);
                            FirebaseUser user = auth.getCurrentUser();

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("name", name);
                            map.put("email", email);


                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                            databaseReference.child("users").child(user.getUid()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Sign_Up_Page.this, "User Added Succefully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Sign_Up_Page.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Sign_Up_Page.this, "Error" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(Sign_Up_Page.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    });
    }
}