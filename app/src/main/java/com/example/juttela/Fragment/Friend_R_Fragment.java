package com.example.juttela.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juttela.Fragment.Adapter.RequestAdpater;
import com.example.juttela.Fragment.Chat.Chat_Activity;
import com.example.juttela.Fragment.Model.Request;
import com.example.juttela.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Friend_R_Fragment extends Fragment implements RequestAdpater.OnRequestActionListener{



    public Friend_R_Fragment() {

    }
    private RecyclerView requestsRecyclerView;
    private RequestAdpater requestAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend__r_, container, false);
        requestsRecyclerView = view.findViewById(R.id.recyclerview);
        setupRecyclerView();
        loadRequests();
        return view;
    }

    private void setupRecyclerView() {
        requestAdapter = new RequestAdpater();
        requestAdapter.setListener(this);
        requestsRecyclerView.setAdapter(requestAdapter);
        requestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadRequests() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference("requests").child(currentUserId);

        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Request request = dataSnapshot.getValue(Request.class);
                if (request != null && "pending".equals(request.getStatus())) {
                    List<Request> requests = new ArrayList<>();
                    requests.add(request);
                    requestAdapter.setRequests(requests);
                    Log.d("Friend_R_Fragment", "Loaded request: " + request.toString());
                } else {
                    requestAdapter.setRequests(new ArrayList<>());
                    Log.d("Friend_R_Fragment", "No pending requests");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Friend_R_Fragment", "Error loading requests", databaseError.toException());
            }
        });
    }

    @Override
    public void onAccept(Request request) {
        updateRequestStatus(request, "active");
    }

    @Override
    public void onReject(Request request) {
        updateRequestStatus(request, "rejected");
    }

    private void updateRequestStatus(Request request, String newStatus) {
        if (request == null || request.getReceiverId() == null || request.getReceiverId().isEmpty()) {
            Log.e("Friend_R_Fragment", "Invalid request or receiver ID");
            return;
        }

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (!currentUserId.equals(request.getReceiverId())) {
            Log.e("Friend_R_Fragment", "Current user is not the receiver of this request");
            return;
        }

        DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference("requests")
                .child(currentUserId);

        requestRef.child("status").setValue(newStatus)
                .addOnSuccessListener(aVoid -> {
                    if ("active".equals(newStatus)) {
                        addFriend(request.getSenderId());
                        startChatActivity(request.getSenderId());
                    }
                    Log.d("Friend_R_Fragment", "Request status updated successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("Friend_R_Fragment", "Failed to update request status", e);
                    // Handle failure, maybe show a toast to the user
                });
    }

    private void addFriend(String friendId) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference("friends");

        // Add friend for current user
        friendsRef.child(currentUserId).child(friendId).setValue(true);

        // Add current user as friend for the other user
        friendsRef.child(friendId).child(currentUserId).setValue(true);
    }

    private void startChatActivity(String otherUserId) {
        Intent intent = new Intent(getContext(), Chat_Activity.class);
        intent.putExtra("otherUserId", otherUserId);
        startActivity(intent);
    }
}