package com.example.alwanyapp.Presentation.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentUpdateModelUrlBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateModelUrl extends Fragment {
    private FragmentUpdateModelUrlBinding mBinding;
    private DatabaseReference database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentUpdateModelUrlBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference("UrlModel");
        mBinding.updateUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mBinding.updateUrl.getText().toString();
                if (url.isEmpty()) {
                    funField("قم بإضافة رابط المودل(AI) بشكل صحيح");
                } else {
                    alertDialog();
                }
            }
        });
        return mBinding.getRoot();
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("تحديث رابط المودل(AI)")
                .setMessage("هل انت متأكد حقا من رغبتك تحديث رابط المودل(AI) ؟ ");
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String url = mBinding.url.getText().toString();
                database.child("url").setValue(url).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        CommonData.modelUrl = url;
                        funSuccessfully();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        funField("فشل فى تغيير رابط المودل (AI) الرجاء المحاولة مرة أخرى ");
                    }
                });
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void funSuccessfully() {
        SweetAlertDialog dialog = SweetDialog.success(getActivity(), "تم تغيير رابط المودل(AI) بنجاح");
        dialog.show();
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
}