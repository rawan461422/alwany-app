package com.example.alwanyapp.Data.AuthRepo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alwanyapp.Util.Common.CommonData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

public class LoginRepo {
    FirebaseAuth auth;
    DatabaseReference database;
    @Inject
    public LoginRepo(FirebaseAuth auth, DatabaseReference database)
    {
        this.auth=auth;
        this.database=database;
    }
    public LiveData<String> signIn(String email, String password) {
        MutableLiveData<String> userLiveData = new MutableLiveData<>();

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    userLiveData.setValue(authResult.getUser().getUid().toString());
                })
                .addOnFailureListener(e -> {
                    userLiveData.setValue("failed");
                    // Handle authentication failure
                });

        return userLiveData;
    }
    public LiveData<String> getUserType(String userId)
    {
        MutableLiveData<String> userTypeLiveData = new MutableLiveData<>();
        database.child(CommonData.patientDatabaseTableName).child(userId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    userTypeLiveData.setValue("patient");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.child(CommonData.doctorDatabaseTableName).child(userId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String status=snapshot.child("status").getValue().toString();
                  if (status.equals("accepted")) {
                      userTypeLiveData.setValue("doctor");
                  }
                  else{
                      userTypeLiveData.setValue(status);
                  }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  userTypeLiveData;
    }
}
