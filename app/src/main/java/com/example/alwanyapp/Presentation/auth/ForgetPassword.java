package com.example.alwanyapp.Presentation.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentForgitPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgetPassword extends Fragment {
    private FragmentForgitPasswordBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentForgitPasswordBinding.inflate(inflater,container,false);
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mBinding.emailEditText.getText().toString();
                if (email.isEmpty())
                {
                    funLoginField("قم بإدخال البريد الإلكتروني");
                }
                else{
                    sendEmail(email);
                }
            }
        });
        return mBinding.getRoot();
    }
    private void sendEmail(String email)
    {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           funLoginSuccessfully();
                        } else {
                          funLoginField("فشل فى إرسال البريد الإلكتروني الرجاء المحاولة مرة أخرى");
                        }
                    }
                });
    }
    private void funLoginSuccessfully() {
        SweetAlertDialog success = SweetDialog.success(getContext(), "تم إرسال البريد الإلكتروني بنجاح");
        success.show();
    }

    private void funLoginField(String message) {
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