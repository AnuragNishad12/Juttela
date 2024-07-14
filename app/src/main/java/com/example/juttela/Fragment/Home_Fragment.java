package com.example.juttela.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.juttela.FrontPage.FinalUser;
import com.example.juttela.FrontPage.UserAdapter;
import com.example.juttela.MainActivity;
import com.example.juttela.R;
import com.example.juttela.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Home_Fragment extends Fragment {



    public Home_Fragment() {

    }

    FragmentHomeBinding binding;
    private UserAdapter adapter;
    private List<FinalUser> userList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        userList = new ArrayList<>();
        adapter = new UserAdapter(userList);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.recyclerView.setAdapter(adapter);
        fetchUsers();



        return binding.getRoot();
    }

    private void fetchUsers() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("FinalUser");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = currentUser != null ? currentUser.getUid() : "";
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.progressBar2.setVisibility(View.GONE);
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
                Toast.makeText(getContext(), "Error :"+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

    }
}