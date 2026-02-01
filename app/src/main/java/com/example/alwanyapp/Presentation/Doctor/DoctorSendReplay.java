package com.example.alwanyapp.Presentation.Doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alwanyapp.Domain.ConsultationsDataClass;
import com.example.alwanyapp.Domain.PatientDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentDoctorSendReplayBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DoctorSendReplay extends Fragment {
    private FragmentDoctorSendReplayBinding mBinding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private ConsultationsDataClass consultationsDataClass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentDoctorSendReplayBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.consultationDatabaseTableName);
        consultationsDataClass = getArguments().getParcelable("con");
        clickAdd();
        return mBinding.getRoot();
    }
    private void clickAdd() {
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    private void checkData() {
        String replay = mBinding.replay.getText().toString();
        if (replay.isEmpty()) {
            mBinding.replay.setError("قم بإدخال الرد على الاستشارة");
        }  else {
            funLoading();
            consultationsDataClass.setStatus("replay");
            consultationsDataClass.setReplay(replay);
            saveToDatabase(consultationsDataClass);
        }
    }
    private void saveToDatabase(ConsultationsDataClass c) {
        database.child(c.getConsultationsId()).setValue(c).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loading.dismiss();
                funSuccessfully("تم إرسال الرد بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                funField("فشل في  إرسال الرد الرجاء المحاولة مرة أخرى");
            }
        });
    }

    private void funLoading() {
        loading = SweetDialog.loading(getContext());
        loading.show();
    }

    private void funSuccessfully(String title) {
        SweetAlertDialog success = SweetDialog.success(getContext(), title);
        success.show();
        Navigation.findNavController(getView()).popBackStack();
    }

    private void funField(String message) {
        SweetAlertDialog field = SweetDialog.failed(getContext(), message);
        field.show();
        field.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                field.dismiss();
            }
        });
    }
}