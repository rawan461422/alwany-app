package com.example.alwanyapp.Presentation.admin.info;

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
import com.example.alwanyapp.Domain.HospitalDataClass;
import com.example.alwanyapp.Domain.InfoDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentAddInfoBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AddInfoFragment extends Fragment {
    private FragmentAddInfoBinding mBinding;
    private DatabaseReference database;
    private String image = "";
    private SweetAlertDialog loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAddInfoBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.infoDatabaseTableName);
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
        String title = mBinding.title.getText().toString();
        String description = mBinding.infoDescription.getText().toString();
        if (title.isEmpty()) {
            mBinding.title.setText("قم بإدخال عنوان المعلومة");
        } else if (description.isEmpty()) {
            mBinding.infoDescription.setText("قم بإدخال وصف المعلومة");
        }  else if (image.isEmpty()) {
            Toast.makeText(getActivity(), "قم بإختيار صورة المعلومة", Toast.LENGTH_SHORT).show();
        } else {
            funLoading();
            String id = UUID.randomUUID().toString();
            InfoDataClass info=new InfoDataClass(title,description,image,getDate(),id);
            saveToDatabase(info);
        }
    }

    private void saveToDatabase(InfoDataClass app) {
        database.child(app.getId()).setValue(app).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loading.dismiss();
                funSuccessfully("تم اضافة المعلومة بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                funField("فشل في اضافة المعلومة الرجاء المحاولة مرة أخرى");
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
                startActivityForResult(Intent.createChooser(intent, "إختر صورتة المعلومة "), 1);
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
        repo.saveImage(uri, "images").observe(getViewLifecycleOwner(), i -> {
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
    private String getDate()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year+"/"+month+"/"+day;
    }
}