package com.example.alwanyapp.Data.AuthRepo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.alwanyapp.Domain.DoctorDataClass;
import com.example.alwanyapp.Domain.PatientDataClass;
import com.example.alwanyapp.Util.Common.CommonData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

public class RegistrationRepo {
    FirebaseAuth auth;
    DatabaseReference database;
    @Inject
    public RegistrationRepo(FirebaseAuth auth,DatabaseReference database)
    {
        this.auth=auth;
        this.database=database;
    }
    public LiveData<String> signUp(String email, String password) {
        MutableLiveData<String> userLiveData = new MutableLiveData<>();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    userLiveData.setValue(authResult.getUser().getUid().toString());
                })
                .addOnFailureListener(e -> {
                    userLiveData.setValue("failed");
                    // Handle authentication failure
                });

        return userLiveData;
    }
    public LiveData<String> savePatientData(PatientDataClass patient)
    {
        MutableLiveData<String> userLiveData = new MutableLiveData<>();
        database.child(CommonData.patientDatabaseTableName).child(patient.getId()).setValue(patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                userLiveData.setValue("success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                userLiveData.setValue("failed");
            }
        });

        return userLiveData;
    }
    public LiveData<String> saveDoctorData(DoctorDataClass doctor)
    {
        MutableLiveData<String> userLiveData = new MutableLiveData<>();
        database.child(CommonData.doctorDatabaseTableName).child(doctor.getId()).setValue(doctor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                userLiveData.setValue("success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                userLiveData.setValue("failed");
            }
        });

        return userLiveData;
    }
}
