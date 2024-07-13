package com.example.juttela;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.juttela.FrontPage.FinalUser;
import com.example.juttela.FrontPage.UserAdapter;
import com.example.juttela.databinding.ActivityMainBinding;
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
    private UserAdapter adapter;
    private List<FinalUser> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userList = new ArrayList<>();
        adapter = new UserAdapter(userList);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerView.setAdapter(adapter);
        fetchUsers();

    }

    private void fetchUsers() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("FinalUser");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = currentUser != null ? currentUser.getUid() : "";
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   FinalUser user = snapshot.getValue(FinalUser.class);
                    if (user != null && !user.getUserId().equals(currentUserId)) {
                        userList.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase", "loadUsers:onCancelled", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to load users.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}