package com.example.juttela.Fragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juttela.Fragment.Model.Request;
import com.example.juttela.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestAdpater extends RecyclerView.Adapter<RequestAdpater.RequestViewHolder> {
    private List<Request> requests = new ArrayList<>();
    private OnRequestActionListener listener;

    public interface OnRequestActionListener {
        void onAccept(Request request);
        void onReject(Request request);
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
        notifyDataSetChanged();
    }

    public void setListener(OnRequestActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_dialog_box, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.bind(request);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView senderNameTextView;
        Button acceptButton;
       ImageView rejectButton;

        RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            senderNameTextView = itemView.findViewById(R.id.senderNameTextView);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }

        void bind(Request request) {
            // Fetch sender's name from Firebase and set it to senderNameTextView
            FirebaseDatabase.getInstance().getReference("users")
                    .child(request.getSenderId())
                    .child("name")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String senderName = dataSnapshot.getValue(String.class);
                            senderNameTextView.setText(senderName);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            acceptButton.setOnClickListener(v -> {
                if (listener != null) listener.onAccept(request);
            });

            rejectButton.setOnClickListener(v -> {
                if (listener != null) listener.onReject(request);
            });
        }
    }
}
