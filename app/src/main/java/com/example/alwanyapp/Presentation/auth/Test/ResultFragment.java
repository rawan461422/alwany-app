package com.example.alwanyapp.Presentation.auth.Test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.databinding.FragmentResultBinding;

import java.util.Objects;

public class ResultFragment extends Fragment {
    private FragmentResultBinding binding;
    private static final String TAG = "ResultFragment";
    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (CommonData.answerTwo >=7){
            Log.e(TAG, "onViewCreated: ");
            //binding.resultContainer.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.result_boarder));
            binding.resultText.setText(getString(com.example.alwanyapp.R.string.green));
            binding.image.setImageDrawable(getActivity().getDrawable(R.drawable.green));
            CommonData.answerOne = 0;
            CommonData.answerTwo = 0;
            CommonData.answerThree = 0;
            CommonData.answerFour = 0;
        }else if (CommonData.answerThree >=7){
            Log.e(TAG, "onViewCreated: ");
          //  binding.resultContainer.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.result_boarder));
            binding.resultText.setText(getString(R.string.red));
            binding.image.setImageDrawable(getActivity().getDrawable(R.drawable.red));
            CommonData.answerOne = 0;
            CommonData.answerTwo = 0;
            CommonData.answerThree = 0;
            CommonData.answerFour = 0;
        }else if (CommonData.answerFour >=7){
            CommonData.answerOne = 0;
            CommonData.answerTwo = 0;
            CommonData.answerThree = 0;
            CommonData.answerFour = 0;
            Log.e(TAG, "onViewCreated: ");
            //binding.resultContainer.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.result_boarder));
            binding.resultText.setText(getString(R.string.blue));
            binding.image.setImageDrawable(getActivity().getDrawable(R.drawable.blue));
        }else {
            Log.e(TAG, "onViewCreated: good");
            CommonData.answerOne = 0;
            CommonData.answerTwo = 0;
            CommonData.answerThree = 0;
            CommonData.answerFour = 0;
        }
    }
}