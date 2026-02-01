package com.example.alwanyapp.Presentation.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alwanyapp.R;
import com.example.alwanyapp.databinding.FragmentViewUsersBinding;

public class ViewUsers extends Fragment {
    private FragmentViewUsersBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for t his fragment
        mBinding=FragmentViewUsersBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.card.setOnClickListener(v-> Navigation.findNavController(view).navigate(R.id.action_viewUsers_to_chat));

    }
}