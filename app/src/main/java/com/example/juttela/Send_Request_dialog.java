package com.example.juttela;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.juttela.FrontPage.FinalUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Send_Request_dialog {
    public interface OnRequestSentListener {
        void onRequestSent(FinalUser receiver);
    }
    public void Request_dialog(Context context, FinalUser receiver, OnRequestSentListener listener){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.send_request);

        Button textButton = dialog.findViewById(R.id.acceptButton);
        ImageView reject = dialog.findViewById(R.id.rejectButton);

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Request Sended", Toast.LENGTH_SHORT).show();
                sendRequest(receiver, listener);
                dialog.dismiss();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Not Accepted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void sendRequest(FinalUser receiver, OnRequestSentListener listener) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String senderId = auth.getCurrentUser().getUid();

        DatabaseReference requestsRef = FirebaseDatabase.getInstance().getReference("requests");

        String requestId = senderId;

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("senderId", senderId);
        requestData.put("receiverId", receiver.getUserId());
        requestData.put("status", "pending");

        requestsRef.child(requestId).setValue(requestData)
                .addOnSuccessListener(aVoid -> {
                    if (listener != null) {
                        listener.onRequestSent(receiver);
                    }
                })
                .addOnFailureListener(e -> {

                });
    }
}