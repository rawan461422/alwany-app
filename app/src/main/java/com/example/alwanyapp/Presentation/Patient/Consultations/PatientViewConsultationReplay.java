package com.example.alwanyapp.Presentation.Patient.Consultations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.alwanyapp.Domain.ConsultationsDataClass;
import com.example.alwanyapp.databinding.FragmentPatientViewConsultationReplayBinding;

public class PatientViewConsultationReplay extends Fragment {
    private FragmentPatientViewConsultationReplayBinding mBinding;
    private ConsultationsDataClass consultationsDataClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentPatientViewConsultationReplayBinding.inflate(inflater, container, false);
        consultationsDataClass = getArguments().getParcelable("con");
        mBinding.title.setText(consultationsDataClass.getTitle());
        mBinding.replay.setText(consultationsDataClass.getReplay());
        mBinding.content.setText(consultationsDataClass.getContent());
        return mBinding.getRoot();
    }
}