package com.example.juttela.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juttela.Chat_Activity;
import com.example.juttela.Fragment.Adapter.FriendAdapter;
import com.example.juttela.FrontPage.FinalUser;
import com.example.juttela.databinding.FragmentMessageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Message_Fragment extends Fragment {

    public Message_Fragment() {

    }

    FragmentMessageBinding binding;
    FriendAdapter adapter;
    ArrayList<FinalUser> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentMessageBinding.inflate(inflater,container,false);
        userList = new ArrayList<>();
        adapter = new FriendAdapter(userList, getContext(), new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FinalUser user) {
                // Start Chat Activity
                Intent intent = new Intent(getActivity(), Chat_Activity.class);
                intent.putExtra("otherUserId", user.getUserId());
                startActivity(intent);
            }
        });
        binding.messageRecyclerView.setAdapter(adapter);
        binding.messageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFriendsData();
        return binding.getRoot();
    }

    private void loadFriendsData() {
        DatabaseReference friendsDataRef = FirebaseDatabase.getInstance().getReference().child("friendsData");
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        friendsDataRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FinalUser friend = snapshot.getValue(FinalUser.class);
                    if (friend != null) {
                       userList.add(friend);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
}