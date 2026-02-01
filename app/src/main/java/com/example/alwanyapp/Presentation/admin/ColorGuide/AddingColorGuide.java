package com.example.alwanyapp.Presentation.admin.ColorGuide;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alwanyapp.Data.SaveImageRepo;
import com.example.alwanyapp.Domain.ColorsDataClass;
import com.example.alwanyapp.Domain.ColorsGuide;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentAddingColorGuideBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddingColorGuide extends Fragment {
    private FragmentAddingColorGuideBinding mBinding;
    private DatabaseReference database;
    private String image = "";
    private SweetAlertDialog loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAddingColorGuideBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.colorsGuideDatabaseTableName);
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
        String name=mBinding.colorName.getText().toString();
        if (name.isEmpty())
        {
            mBinding.colorName.setError("قم بإدخال اسم اللون");
        }
        else if (image.isEmpty()) {
            Toast.makeText(getActivity(), "قم بإختيار صورة اللون", Toast.LENGTH_SHORT).show();
        } else {
            funLoading();
            String id = UUID.randomUUID().toString();
            ColorsGuide color=new ColorsGuide(name,image,id);

            saveToDatabase(color);
        }
    }

    private void saveToDatabase( ColorsGuide data) {
        database.child(data.getColorId()).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loading.dismiss();
                funSuccessfully("تم اضافة اللون بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                funField("فشل في اضافة اللون الرجاء المحاولة مرة أخرى");
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
                startActivityForResult(Intent.createChooser(intent, "إختر صورة اللون "), 1);
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