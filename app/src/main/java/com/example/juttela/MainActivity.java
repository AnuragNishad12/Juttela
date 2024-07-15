package com.example.juttela;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.juttela.Fragment.Friend_R_Fragment;
import com.example.juttela.Fragment.Home_Fragment;
import com.example.juttela.Fragment.Message_Fragment;
import com.example.juttela.Fragment.ProfileFragment;
import com.example.juttela.FrontPage.FinalUser;
import com.example.juttela.FrontPage.UserAdapter;
import com.example.juttela.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home_Fragment()).commit();
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
               if (menuItem.getItemId()==R.id.nav_home){
                   selectedFragment = new Home_Fragment();
               } else if (menuItem.getItemId() == R.id.message) {
                    selectedFragment = new Message_Fragment();
               }else if (menuItem.getItemId()==R.id.friend){
                   selectedFragment = new Friend_R_Fragment();
               }else if (menuItem.getItemId()==R.id.Profile){
                   selectedFragment = new ProfileFragment();
               }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
                }
               return true;

            }
        });



    }


}