package com.example.juttela.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;
import io.paperdb.Paper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.juttela.FrontPage.FinalUser;
import com.example.juttela.FrontPage.UserAdapter;
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
    private String currentGenderFilter = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        Paper.init(requireContext());


        userList = new ArrayList<>();
        adapter = new UserAdapter(userList);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.recyclerView.setAdapter(adapter);
        fetchUsers();
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderOptions(view);
            }
        });



        return binding.getRoot();
    }

    private void fetchUsers() {
        // First, try to load data from Paper
        List<FinalUser> storedUsers = loadUsersFromPaper();

        if (storedUsers != null && !storedUsers.isEmpty()) {
            // If data is available in Paper, use it
            updateAdapter(storedUsers);
            Log.d("Home_Fragment", "Data loaded from Paper");
//            Toast.makeText(getContext(), "Data loaded from local storage", Toast.LENGTH_SHORT).show();
            binding.progressBar2.setVisibility(View.GONE);
        } else {
            // If data is not in Paper, fetch from Firebase
            fetchFromFirebase();
        }
        fetchFromFirebase();
    }

    private List<FinalUser> loadUsersFromPaper() {
        return Paper.book().read("users", null);
    }

    private void fetchFromFirebase() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("FinalUser");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = currentUser != null ? currentUser.getUid() : "";
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<FinalUser> newUserList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FinalUser user = snapshot.getValue(FinalUser.class);
                    if (user != null && !user.getUserId().equals(currentUserId)) {
                        newUserList.add(user);
                    }
                }
                // Store the fetched data in Paper
                Paper.book().write("users", newUserList);

                updateAdapter(newUserList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase", "loadUsers:onCancelled", databaseError.toException());
                Toast.makeText(getContext(), "Error: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAdapter(List<FinalUser> users) {
        adapter.updateUsers(users);
        // Reapply the current filter after updating users
        adapter.filterByGender(currentGenderFilter);
    }
    private void showGenderOptions(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customMenuView = inflater.inflate(R.layout.popmenu_layout, null);

        // Create the PopupWindow
        final PopupWindow popupWindow = new PopupWindow(
                customMenuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // Set up click listeners
        customMenuView.findViewById(R.id.male_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle male option selection
                currentGenderFilter = "male";
                adapter.filterByGender(currentGenderFilter);
                popupWindow.dismiss();
            }
        });

        customMenuView.findViewById(R.id.female_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle female option selection
                currentGenderFilter = "female";
                adapter.filterByGender(currentGenderFilter);
                popupWindow.dismiss();
            }
        });

        customMenuView.findViewById(R.id.both_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Home_Fragment", "Showing all users");
                currentGenderFilter = null;
                adapter.filterByGender(null);
                popupWindow.dismiss();
            }
        });
        // Set elevation for shadow effect
        popupWindow.setElevation(10);

        // Show the PopupWindow
        popupWindow.showAsDropDown(anchorView, -popupWindow.getContentView().getMeasuredWidth() + anchorView.getWidth(), -anchorView.getHeight() - popupWindow.getContentView().getMeasuredHeight());
    }
}