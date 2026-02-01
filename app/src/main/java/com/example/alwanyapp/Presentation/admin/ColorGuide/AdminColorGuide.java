package com.example.alwanyapp.Presentation.admin.ColorGuide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alwanyapp.Domain.ColorsDataClass;
import com.example.alwanyapp.Domain.ColorsGuide;
import com.example.alwanyapp.Presentation.admin.Color.ColorsAdapter;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentAdminColorGuideBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminColorGuide extends Fragment {
    private FragmentAdminColorGuideBinding mBinding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private ColorsGuideAdapter adapter;
    private ArrayList<ColorsGuide> arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminColorGuideBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.colorsGuideDatabaseTableName);
        funLoading();
        defineItems();
        clickItem();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.fabAdd.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_colorGuide_to_addingColorGuide));
    }

    private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new ColorsGuideAdapter(arrayList, getActivity());
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mBinding.recyclerView.setAdapter(adapter);
        getColors();
    }

    private void getColors() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                arrayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ColorsGuide color = data.getValue(ColorsGuide.class);
                        arrayList.add(color);
                    }
                } else {
                    mBinding.empty.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clickItem() {
        adapter.setOnItemClick(new ColorsGuideAdapter.onItemClickListener() {
            @Override
            public void delete(int position) {
                alertDialog(position);
            }
        });
    }

    private void alertDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("حذف لون")
                .setMessage("هل تريد حذف هذا اللون ؟");
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                funLoading();
                delete(position);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void delete(int position) {
        database.child(arrayList.get(position).getColorId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loading.dismiss();
                funSuccessfully("تم حذف اللون بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                funField("فشل في حذف هذا اللون الرجاء المحاولة مرة أخرى");
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