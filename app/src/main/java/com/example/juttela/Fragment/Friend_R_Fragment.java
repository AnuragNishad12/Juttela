package com.example.juttela.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juttela.Fragment.Adapter.FriendRequestAdapter;
import com.example.juttela.Fragment.Model.FriendRequest;
import com.example.juttela.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Friend_R_Fragment extends Fragment {



    public Friend_R_Fragment() {

    }
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private List<FriendRequest> friendRequests;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend__r_,container,false);
        recyclerView = view.findViewById(R.id.friend_requests_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        friendRequests = new ArrayList<>();
        adapter = new FriendRequestAdapter(friendRequests,getContext());
        recyclerView.setAdapter(adapter);

        loadFriendRequests();
        return view;
    }
    private void loadFriendRequests() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference requestsRef = FirebaseDatabase.getInstance().getReference().child("friend_requests").child(currentUserId);

        requestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendRequests.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FriendRequest request = snapshot.getValue(FriendRequest.class);
                    if (request != null) {
                        friendRequests.add(request);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

}