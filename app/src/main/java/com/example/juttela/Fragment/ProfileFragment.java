package com.example.juttela.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juttela.R;
import com.example.juttela.Setting_Activity;
import com.example.juttela.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {



    public ProfileFragment() {

    }

    FragmentProfileBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater,container,false);
        binding.settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), Setting_Activity.class);
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }
}