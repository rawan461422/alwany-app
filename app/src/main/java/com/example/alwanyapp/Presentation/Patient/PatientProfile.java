package com.example.alwanyapp.Presentation.Patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alwanyapp.Domain.PatientDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.databinding.FragmentPatientProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientProfile extends Fragment {
    private FragmentPatientProfileBinding mBinding;
    private PatientDataClass patientDataClass;
    private DatabaseReference Database;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentPatientProfileBinding.inflate(inflater,container,false);
        Database= FirebaseDatabase.getInstance().getReference(CommonData.patientDatabaseTableName);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        getUserName();

        return mBinding.getRoot();
    }
    private void getUserName()
    {
        Database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName=snapshot.child("name").getValue().toString();
                String email=snapshot.child("email").getValue().toString();
                String image=snapshot.child("image").getValue().toString();
                String phone=snapshot.child("phone").getValue().toString();
                mBinding.name.setText(userName);
                mBinding.email.setText(email);
                mBinding.phone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}