package com.example.alwanyapp.Presentation.admin.apps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.alwanyapp.Domain.HelperAppsDataClass;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentAdminHelperAppDetailsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminHelperAppDetailsFragment extends Fragment {
    private FragmentAdminHelperAppDetailsBinding mBinding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private HelperAppsDataClass app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAdminHelperAppDetailsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.appsDatabaseTableName);
        app = getArguments().getParcelable("app");
        mBinding.appName.setText(app.getName());
        mBinding.appLink.setText(app.getLink());
        mBinding.infoDescription.setText(app.getDescription());
        Glide.with(getActivity()).load(app.getImage()).into(mBinding.image);
        clickDelete();
        return mBinding.getRoot();
    }

    private void clickDelete() {
        mBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("حذف تطبيق")
                .setMessage("هل تريد حذف هذا التطبيق ؟");
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                funLoading();
                deleteApp();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteApp() {
        database.child(app.getAppId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loading.dismiss();
                funSuccessfully("تم حذف التطبيق بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                funField("فشل في حذف هذا التطبيق الرجاء المحاولة مرة أخرى");
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