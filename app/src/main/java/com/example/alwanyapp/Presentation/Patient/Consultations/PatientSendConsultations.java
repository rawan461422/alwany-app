package com.example.alwanyapp.Presentation.Patient.Consultations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.alwanyapp.Domain.ConsultationsDataClass;
import com.example.alwanyapp.Domain.PatientDataClass;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentPatientSendConsultationsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PatientSendConsultations extends Fragment {
    private FragmentPatientSendConsultationsBinding mBinding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private String patientId, doctorId;
    private PatientDataClass patient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentPatientSendConsultationsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.consultationDatabaseTableName);
        doctorId = getArguments().getString("id");
        patientId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        getUserData();
        clickAdd();
        return mBinding.getRoot();
    }

    private void getUserData() {
        FirebaseDatabase.getInstance().getReference(CommonData.patientDatabaseTableName).child(patientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String image = snapshot.child("image").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                patient = new PatientDataClass(name, email, image, phone, patientId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        String title = mBinding.title.getText().toString();
        String content = mBinding.content.getText().toString();
        if (title.isEmpty()) {
            mBinding.title.setError("قم بإدخال عنوان الاستشارة");
        } else if (content.isEmpty()) {
            mBinding.content.setError("قم بإدخال محتوى الاستشارة");
        } else {
            funLoading();
            String id = UUID.randomUUID().toString();
            ConsultationsDataClass c = new ConsultationsDataClass(patient.getName(), patient.getImage(), title, content, date(), "", "new", patientId, doctorId, id);
            saveToDatabase(c);
        }
    }

    private String date() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "/" + month + "/" + dayOfMonth;
    }

    private void saveToDatabase(ConsultationsDataClass c) {
        database.child(c.getConsultationsId()).setValue(c).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loading.dismiss();
                funSuccessfully("تم إرسال الاستشارة بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                funField("فشل في  إرسال الاستشارة الرجاء المحاولة مرة أخرى");
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