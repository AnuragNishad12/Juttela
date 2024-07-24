package com.example.juttela.Fragment.Chat;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.juttela.Fragment.Adapter.ChatAdapter;
import com.example.juttela.Fragment.Model.MessageModel;
import com.example.juttela.R;
import com.example.juttela.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat_Activity extends AppCompatActivity {

    private String currentUserId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ActivityChatBinding binding;
    private String otherUserId;
    final ArrayList<MessageModel> messageModels = new ArrayList<>();
    final ChatAdapter chatAdapter = new ChatAdapter(messageModels,this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        otherUserId = getIntent().getStringExtra("otherUserId");

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        otherUserId = getIntent().getStringExtra("otherUserId");
        binding.recyclerView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        checkIfFriends();
        final String senderRoom = currentUserId+otherUserId;
        final String receiverRoom = otherUserId+currentUserId;

        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                binding.progressBar3.setVisibility(View.GONE);
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    MessageModel model = snapshot1.getValue(MessageModel.class);
                    messageModels.add(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message =  binding.messageText.getText().toString();
                final MessageModel model = new MessageModel(currentUserId,message);
                binding.messageText.setText("");
                database.getReference().child("chats")
                        .child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats")
                                        .child(receiverRoom).push().setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }
        });

    }

    private void checkIfFriends() {
        DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference().child("friends").child(currentUserId).child(otherUserId);
        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Users are friends, allow chat
                    initializeChat();
                } else {
                    // Users are not friends, show message or finish activity
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void initializeChat() {
        // Initialize your chat UI and functionality here
    }


}