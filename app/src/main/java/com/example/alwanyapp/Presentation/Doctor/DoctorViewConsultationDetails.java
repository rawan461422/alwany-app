package com.example.alwanyapp.Presentation.Doctor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alwanyapp.Domain.ConsultationsDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.databinding.FragmentDoctorViewConsultationDetailsBinding;

public class DoctorViewConsultationDetails extends Fragment {
    private FragmentDoctorViewConsultationDetailsBinding mBinding;
    private ConsultationsDataClass consultationsDataClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentDoctorViewConsultationDetailsBinding.inflate(inflater,container,false);

        mBinding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("id",consultationsDataClass.getPatientId());
                navigateTo(R.id.action_consDetails_to_chat,b );
            }
        });
        mBinding.replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putParcelable("con",consultationsDataClass);
                navigateTo(R.id.action_consDetails_to_replay,b);
            }
        });
        consultationsDataClass = getArguments().getParcelable("con");
        mBinding.title.setText(consultationsDataClass.getTitle());
        mBinding.content.setText(consultationsDataClass.getContent());
        return mBinding.getRoot();
    }
    private void navigateTo(int id, Bundle b)
    {
        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_user).navigate(id,b);
    }
}