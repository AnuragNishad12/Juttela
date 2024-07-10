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
import com.example.juttela.databinding.ActivitySignUpPageBinding;

public class Sign_Up_Page extends AppCompatActivity {


    ActivitySignUpPageBinding binding;

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




    }
}