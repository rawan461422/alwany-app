package com.example.alwanyapp.Presentation.auth.Informations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.alwanyapp.Domain.InfoDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.databinding.FragmentInfoDetailsBinding;
import com.example.alwanyapp.databinding.FragmentInformationBinding;

public class InfoDetailsFragment extends Fragment {
    private FragmentInfoDetailsBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentInfoDetailsBinding.inflate(inflater,container,false);
        InfoDataClass info = getArguments().getParcelable("info");
        mBinding.title.setText(info.getTitle());
        mBinding.description.setText(info.getDescription());
        Glide.with(getActivity()).load(info.getImage()).into(mBinding.image);
        return mBinding.getRoot();
    }
}