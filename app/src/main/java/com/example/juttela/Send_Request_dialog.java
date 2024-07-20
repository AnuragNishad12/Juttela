package com.example.juttela;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.juttela.Fragment.Model.FriendRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class Send_Request_dialog {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public void Request_dialog(Context context, String receiverId) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.send_request);

        Button textButton = dialog.findViewById(R.id.accept_button);
        ImageView reject = dialog.findViewById(R.id.reject_button);

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFriendRequest(context, receiverId);
                dialog.dismiss();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Request Cancelled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void sendFriendRequest(Context context, String receiverId) {
        String senderId = mAuth.getCurrentUser().getUid();

        // Check if request already sent
        mDatabase.child("friend_requests").child(receiverId).child(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(context, "Friend request already sent", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if they are already friends
                    mDatabase.child("friends").child(senderId).child(receiverId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(context, "You are already friends", Toast.LENGTH_SHORT).show();
                            } else {
                                // Send the friend request
                                FriendRequest request = new FriendRequest(senderId, receiverId);
                                mDatabase.child("friend_requests").child(receiverId).child(senderId).setValue(request);
                                Toast.makeText(context, "Friend request sent", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(context, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}