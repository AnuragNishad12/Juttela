package com.example.juttela.FrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.juttela.MainActivity;
import com.example.juttela.R;
import com.example.juttela.databinding.ActivityFrontPagesBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Front_Pages extends AppCompatActivity {


    ActivityFrontPagesBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFrontPagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Front_Pages.this, Sign_Up_Page.class);
                startActivity(intent);
            }
        });


        binding.logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Front_Pages.this, Login_activity.class);
                startActivity(intent);
            }
        });

        if (auth.getCurrentUser()!=null){
            Intent intent = new Intent(Front_Pages.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}