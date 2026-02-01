package com.example.alwanyapp.Presentation.auth.SignIn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.alwanyapp.Data.AuthRepo.LoginRepo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    LoginRepo loginRepo;
    @Inject
    LoginViewModel(LoginRepo loginRepo)
    {
        this.loginRepo=loginRepo;
    }

    public LiveData<String> signIn(String email, String password) {
        return loginRepo.signIn(email, password);
    }
    public LiveData<String> getUserType(String userId) {
        return loginRepo.getUserType(userId);
    }
}
