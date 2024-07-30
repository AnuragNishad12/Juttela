package com.example.juttela.Fragment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.juttela.Chat_Activity;
import com.example.juttela.Fragment.Model.FriendRequest;
import com.example.juttela.FrontPage.FinalUser;
import com.example.juttela.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {

    private List<FriendRequest> friendRequests;
    private Context context;

    public FriendRequestAdapter(List<FriendRequest> friendRequests, Context context) {
        this.friendRequests = friendRequests;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_dialog_box, parent, false);
        return new FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        FriendRequest friendRequest = friendRequests.get(position);
        holder.bind(friendRequest);
    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
    }

    class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTextView;
        private ImageView image;
        private Button acceptButton;
        private ImageView rejectButton;

        public FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.user_name_text_view);
            acceptButton = itemView.findViewById(R.id.accept_button);
            rejectButton = itemView.findViewById(R.id.reject_button);
            image = itemView.findViewById(R.id.profile_image);
        }

        public void bind(FriendRequest friendRequest) {
            // You may need to fetch the user's name from Firebase using the senderId
            userNameTextView.setText(friendRequest.getName());
            Glide.with(itemView.getContext()).
                    load(friendRequest.getImage()).error(R.drawable.friend).into(image);
            acceptButton.setOnClickListener(v -> acceptFriendRequest(friendRequest));
            rejectButton.setOnClickListener(v -> rejectFriendRequest(friendRequest));
        }

        private void acceptFriendRequest(FriendRequest friendRequest) {
            DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference().child("friends");
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("FinalUser"); // Reference to the users node
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            friendsRef.child(currentUserId).child(friendRequest.getSenderId()).setValue(true);
            friendsRef.child(friendRequest.getSenderId()).child(currentUserId).setValue(true);

            usersRef.child(friendRequest.getSenderId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FinalUser friend = dataSnapshot.getValue(FinalUser.class);
                    if (friend != null) {

                        DatabaseReference friendsDataRef = FirebaseDatabase.getInstance().getReference().child("friendsData");
                        friendsDataRef.child(currentUserId).child(friendRequest.getSenderId()).setValue(friend);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle possible errors.
                }
            });



            DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference()
                    .child("friend_requests").child(currentUserId).child(friendRequest.getSenderId());
            requestRef.removeValue();

            // Remove from local list and notify adapter
            friendRequests.remove(friendRequest);
            notifyDataSetChanged();

            // Show a toast message
            Toast.makeText(context, "Friend request accepted", Toast.LENGTH_SHORT).show();

            // Move to Chat Activity
            Intent intent = new Intent(context, Chat_Activity.class);
            intent.putExtra("otherUserId", friendRequest.getSenderId());
            context.startActivity(intent);
        }

        private void rejectFriendRequest(FriendRequest friendRequest) {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference()
                    .child("friend_requests").child(currentUserId).child(friendRequest.getSenderId());
            requestRef.removeValue();

            // Remove from local list and notify adapter
            friendRequests.remove(friendRequest);
            notifyDataSetChanged();

            // Show a toast message
            Toast.makeText(context, "Friend request rejected", Toast.LENGTH_SHORT).show();
        }
    }
}