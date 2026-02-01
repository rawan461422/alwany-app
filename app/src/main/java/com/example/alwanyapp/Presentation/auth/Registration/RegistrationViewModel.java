package com.example.alwanyapp.Presentation.auth.Registration;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.alwanyapp.Data.AuthRepo.RegistrationRepo;
import com.example.alwanyapp.Data.SaveImageRepo;
import com.example.alwanyapp.Domain.DoctorDataClass;
import com.example.alwanyapp.Domain.PatientDataClass;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegistrationViewModel extends ViewModel {
    RegistrationRepo repo;

    @Inject
    RegistrationViewModel(RegistrationRepo repo)
    {
        this.repo=repo;
       // this.saveImageRepo=saveImageRepo;
    }
    public LiveData<String> signUp(String email, String password) {
        return repo.signUp(email, password);
    }

    /*public LiveData<String> saveImage(Uri uri, String category)
    {
        return saveImageRepo.saveImage(uri,category);
    }*/
    public LiveData<String> savePatientData(PatientDataClass patient)
    {
        return repo.savePatientData(patient);
    }
    public LiveData<String> saveDoctorData(DoctorDataClass doctor)
    {
        return repo.saveDoctorData(doctor);
    }
}
