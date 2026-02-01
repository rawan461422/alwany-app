package com.example.alwanyapp.Presentation.auth.ColorsGuide;

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

import com.example.alwanyapp.Presentation.admin.ColorGuide.ColorsGuideAdapter;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentColorsGuideBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ColorsGuide extends Fragment {
    private FragmentColorsGuideBinding mBinding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private UserColorsGuideAdapter adapter;
    private ArrayList<com.example.alwanyapp.Domain.ColorsGuide> arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentColorsGuideBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.colorsGuideDatabaseTableName);
        funLoading();
        defineItems();
        return mBinding.getRoot();
    }
    private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new UserColorsGuideAdapter(arrayList, getActivity());
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
                        com.example.alwanyapp.Domain.ColorsGuide color = data.getValue(com.example.alwanyapp.Domain.ColorsGuide.class);
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
    private void funLoading() {
        loading = SweetDialog.loading(getContext());
        loading.show();
    }

}