package com.example.alwanyapp.Presentation.admin.apps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.alwanyapp.Data.SaveImageRepo;
import com.example.alwanyapp.Domain.HelperAppsDataClass;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentAddHelperAppBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddHelperAppFragment extends Fragment {
    private FragmentAddHelperAppBinding mBinding;
    private DatabaseReference database;
    private String image = "";
    private SweetAlertDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAddHelperAppBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.appsDatabaseTableName);
        clickAdd();
        addImage();
        return mBinding.getRoot();
    }

    private void clickAdd() {
        mBinding.addAdviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    private void checkData() {
        String name = mBinding.appName.getText().toString();
        String link = mBinding.appLink.getText().toString();
        String description = mBinding.infoDescription.getText().toString();
        if (name.isEmpty()) {
            mBinding.appName.setError("قم بإدخال اسم التطبيق");
        } else if (link.isEmpty()) {
            mBinding.appLink.setError("قم بإدخال رابط التطبيق");
        } else if (description.isEmpty()) {
            mBinding.infoDescription.setError("قم بإدخال وصف التطبيق");
        } else if (image.isEmpty()) {
            Toast.makeText(getActivity(), "قم بإختيار صورة التطبيق", Toast.LENGTH_SHORT).show();
        } else {
            funLoading();
            String id = UUID.randomUUID().toString();
            HelperAppsDataClass app = new HelperAppsDataClass(name, link, description, image, id);
            saveToDatabase(app);
        }
    }

    private void saveToDatabase(HelperAppsDataClass app) {
        database.child(app.getAppId()).setValue(app).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loading.dismiss();
                funSuccessfully("تم اضافة  التطبيق بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                funField("فشل في اضافة التطبيق الرجاء المحاولة مرة أخرى");
            }
        });
    }

    private void addImage() {
        mBinding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "إختر صورتة التطبيق "), 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                funLoading();
                saveImage(data.getData());
            }
        }
    }

    private void saveImage(Uri uri) {
        SaveImageRepo repo = new SaveImageRepo();
        repo.saveImage(uri, "HelperApps").observe(getViewLifecycleOwner(), i -> {
            loading.dismiss();
            if (i.equals("failed")) {
                funField("حدث خطاء في إضافة الصورة الرجاء المحاولة مرة أخرى");
            } else {
                image = i;
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