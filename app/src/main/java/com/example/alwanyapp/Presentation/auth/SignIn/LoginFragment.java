package com.example.alwanyapp.Presentation.auth.SignIn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.alwanyapp.Presentation.Doctor.DoctorMainActivity;
import com.example.alwanyapp.Presentation.Patient.PatientMainActivity;
import com.example.alwanyapp.Presentation.admin.AdminActivity;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentLoginBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private FragmentLoginBinding mBinding;
    private SweetAlertDialog loading;
    private int pStatus = 0;
    private LoginViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentLoginBinding.inflate(inflater,container,false);

        mBinding.signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScreen(R.id.action_login_to_userSignUp);
            }
        });
        mBinding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScreen(R.id.action_login_to_forgitPassword);
            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        login();
        showPassword();
        return mBinding.getRoot();
    }
    private void funLoading() {
        loading = SweetDialog.loading(getContext());
        loading.show();
    }

    private void login() {
        mBinding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mBinding.signInEmail.getText().toString();
                String password = mBinding.signInPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    mBinding.signInEmail.setError("قم بإدخال البريد الإلكتروني");
                } else if (TextUtils.isEmpty(password)) {
                    mBinding.signInEmail.setError("قم بإدخال كلمة المرور");
                }
                else if (email.equals("admin@gmail.com")&& password.equals("12345678")) {
                    startActivity(new Intent(getActivity(), AdminActivity.class));
                    getActivity().finish();
                }
                else {
                    funLoading();
                    checkAccount(email, password);
                }

            }
        });
    }

    private void checkAccount(String email, String password) {
        viewModel.signIn(email, password).observe(getViewLifecycleOwner(), status -> {
            if (status.equals("failed")) {
                funLoginField("فشل تسجيل الدخول إلى حسابك، يرجى التحقق مما إذا كانت كلمة المرور صحيحة أم لا");
            } else {
                validateUserType(status);
            }
        });

    }

    private void validateUserType(String userId) {
        viewModel.getUserType(userId).observe(getViewLifecycleOwner(),type->{
            loading.dismiss();
            if (type.equals("patient"))
            {
                funLoginSuccessfully(1);
            }
            else  if (type.equals("doctor"))
            {
                funLoginSuccessfully(2);
            }
            else  if (type.equals("block"))
            {
                funLoginField("عذرا لقد قام المشرف بحظر حسابك من التطبيق");
            }
            else  if (type.equals("new"))
            {
                funLoginField("عذرا لم يقم المشرف بقبول طلب إنضمامك الي التطبيق للان الرجاء الانتظار");
            }
        });
    }

    private void funLoginSuccessfully(int i) {
        SweetAlertDialog success = SweetDialog.success(getContext(), "تم تسجيل الدخول بنجاح");
        success.show();
        try {
            Thread.sleep(2000);
            if (i==1) {
                CommonData.userType=2;
                startActivity(new Intent(getActivity(), PatientMainActivity.class));
                getActivity().finish();
            }
            else if (i==2) {
                CommonData.userType=3;
                startActivity(new Intent(getActivity(), DoctorMainActivity.class));
                getActivity().finish();
            }
        } catch (Exception e) {

        }

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
    private void showPassword() {
        mBinding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pStatus == 0) {
                    Glide.with(getActivity())
                            .load(R.drawable.baseline_visibility_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus = 1;
                    mBinding.signInPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    Glide.with(getActivity())
                            .load(R.drawable.baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus = 0;
                    mBinding.signInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
    private void moveToScreen(int id)
    {
        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_activity_outer).navigate(id);
    }
}