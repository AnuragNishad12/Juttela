package com.example.juttela.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juttela.CustomDialog;
import com.example.juttela.R;
import com.example.juttela.databinding.FragmentMessageBinding;

public class Message_Fragment extends Fragment {

    public Message_Fragment() {

    }

    FragmentMessageBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentMessageBinding.inflate(inflater,container,false);



        return binding.getRoot();
    }
}