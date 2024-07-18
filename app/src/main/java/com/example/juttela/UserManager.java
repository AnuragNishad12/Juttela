package com.example.juttela;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserManager {
    public interface RequestCheckListener {
        void onRequestCheckComplete(boolean requestExists);
    }

    public static void checkIfRequestExists(String senderId, String receiverId, RequestCheckListener listener) {
        DatabaseReference requestsRef = FirebaseDatabase.getInstance().getReference("requests");

        requestsRef.orderByChild("senderId").equalTo(senderId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean exists = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.child("receiverId").getValue(String.class).equals(receiverId)) {
                                exists = true;
                                break;
                            }
                        }
                        listener.onRequestCheckComplete(exists);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onRequestCheckComplete(false);
                    }
                });
    }
}
