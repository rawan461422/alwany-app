package com.example.alwanyapp.Presentation.auth.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.alwanyapp.Domain.PatientDataClass;
import com.example.alwanyapp.Presentation.Patient.PatientMainActivity;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.Util.Validation.EmailValidator;
import com.example.alwanyapp.Util.Validation.PasswordValidation;
import com.example.alwanyapp.Util.Validation.ValidationPhoneNumber;
import com.example.alwanyapp.databinding.FragmentPatienSignUpBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PatientSignUpFragment extends Fragment {

    private FragmentPatienSignUpBinding mBinding;
    private SweetAlertDialog loading;
    private RegistrationViewModel viewModel;
    private int pStatus = 0;
    private int pStatus2 = 0;
    private PatientDataClass patient;
    public PatientSignUpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentPatienSignUpBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(RegistrationViewModel.class);
        showPassword();
        showPassword2();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.signInLink.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_outer).navigate(R.id.action_userSignUp_to_selectType);

        });
        mBinding.doctorBtn.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_outer).navigate(R.id.action_userSignUp_to_doctorSignIn);
        });
        mBinding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEnteredData();
            }
        });
    }

    public void checkEnteredData() {
        String name = mBinding.signUpFirstName.getText().toString();
        String email = mBinding.signUpEmail.getText().toString();
        String password = mBinding.password.getText().toString();
        String conPassword = mBinding.conPassword.getText().toString();
        String phone = mBinding.signUpPhone.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mBinding.signUpFirstName.setError("من فضلك قم بإدخال اسمك");
        } else if (!EmailValidator.isValidEmail(email)) {
            mBinding.signUpEmail.setError("قم بإدخال البريد الإلكتروني بشكل صحيح  'user@gmail.com'");
        } else if (!PasswordValidation.validatePassword(password)) {
            mBinding.password.setError("الرجاء إدخال كلمة مرور قوية تحتوي على حروف كبيرة وحروف صغيرة وحروف مميزة وأرقام");
        } else if (!conPassword.equals(password)) {
            mBinding.conPassword.setError("كلمة المرور غير متطابقة");
        }else if (!ValidationPhoneNumber.validatePhoneNumber(phone)) {
            mBinding.signUpPhone.setError("قم بإدخال رقم الهاتف بشكل صحيح بحث يتكون من 10 ارقام وبدأ ب (05)");
        } else {
            funLoading();
            patient=new PatientDataClass(name,email,"",phone,"");
            createAccount(email, password);
        }
    }


    private void funLoading() {
        loading = SweetDialog.loading(getActivity());
        loading.show();
    }

    private void createAccount(String email, String password) {
        viewModel.signUp(email, password).observe(getViewLifecycleOwner(), Id -> {
            loading.dismiss();
            if (Id.equals("failed")) {
                funField("فشل في إنشاء الحساب يرجى تغيير البريد الإلكتروني الخاص بك");
            } else {
                savePatientData(Id);
            }
        });
    }
    private void savePatientData(String id)
    {
        patient.setId(id);
        viewModel.savePatientData(patient).observe(getViewLifecycleOwner(), status -> {
            loading.dismiss();
            if (status.equals("failed")) {
                funField("فشل في إنشاء الحساب يرجى تغيير البريد الإلكتروني الخاص بك");
            } else {
                funSuccessfully();
            }
        });
    }
    private void funSuccessfully() {
        SweetAlertDialog dialog = SweetDialog.success(getActivity(), "تم إنشاء حساب جديد بنجاح");
        dialog.show();
        try {
            Thread.sleep(2000);
            CommonData.userType=2;
            startActivity(new Intent(getActivity(), PatientMainActivity.class));
            getActivity().finish();
        } catch (Exception e) {

        }

    }

    private void funField(String title) {
        SweetAlertDialog dialog = SweetDialog.failed(getActivity(), title);
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
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
                    mBinding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    Glide.with(getActivity())
                            .load(R.drawable.baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus = 0;
                    mBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void showPassword2() {
        mBinding.showPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pStatus2 == 0) {
                    Glide.with(getActivity())
                            .load(R.drawable.baseline_visibility_24)
                            .centerCrop()
                            .into(mBinding.showPassword2);
                    pStatus2 = 1;
                    mBinding.conPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    Glide.with(getActivity())
                            .load(R.drawable.baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword2);
                    pStatus2 = 0;
                    mBinding.conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}


