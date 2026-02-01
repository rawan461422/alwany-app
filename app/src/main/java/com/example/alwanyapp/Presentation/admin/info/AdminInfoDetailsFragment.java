package com.example.alwanyapp.Presentation.admin.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.alwanyapp.Domain.InfoDataClass;
import com.example.alwanyapp.databinding.FragmentAdminInfoDetailsBinding;

public class AdminInfoDetailsFragment extends Fragment {
    private FragmentAdminInfoDetailsBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAdminInfoDetailsBinding.inflate(inflater, container, false);
        InfoDataClass info = getArguments().getParcelable("info");
        mBinding.title.setText(info.getTitle());
        mBinding.description.setText(info.getDescription());
        Glide.with(getActivity()).load(info.getImage()).into(mBinding.image);
        return mBinding.getRoot();
    }
}