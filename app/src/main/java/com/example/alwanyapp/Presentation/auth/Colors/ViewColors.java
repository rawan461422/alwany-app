package com.example.alwanyapp.Presentation.auth.Colors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.alwanyapp.Domain.ColorsDataClass;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewColors extends Fragment {

    private FragmentDashboardBinding binding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private UserColorsAdapter adapter;
    private ArrayList<ColorsDataClass> arrayList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.colorsDatabaseTableName);
        funLoading();
        defineItems();
        return binding.getRoot();
    } private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new UserColorsAdapter(arrayList, getActivity());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.recyclerView.setAdapter(adapter);
        getColors();
    }
    private void funLoading() {
        loading = SweetDialog.loading(getContext());
        loading.show();
    }

    private void getColors() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                arrayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ColorsDataClass color = data.getValue(ColorsDataClass.class);
                        arrayList.add(color);
                    }
                } else {
                    binding.empty.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}