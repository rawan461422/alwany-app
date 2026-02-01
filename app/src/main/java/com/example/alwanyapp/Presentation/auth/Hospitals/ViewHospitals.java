package com.example.alwanyapp.Presentation.auth.Hospitals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alwanyapp.Domain.HospitalDataClass;
import com.example.alwanyapp.Presentation.admin.Hospitals.HospitalAdapter;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentHomeBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewHospitals extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private UserHospitalAdapter adapter;
    private ArrayList<HospitalDataClass> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.hospitalDatabaseTableName);
        funLoading();
        defineItems();
        clickItem();
        return binding.getRoot();
    }
    private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new UserHospitalAdapter(arrayList, getActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        getHospitals();
    }

    private void getHospitals() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                arrayList.clear();
                if (snapshot.exists()) {

                    for (DataSnapshot data : snapshot.getChildren()) {
                        HospitalDataClass hospital = data.getValue(HospitalDataClass.class);
                        arrayList.add(hospital);
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

    private void clickItem() {
        adapter.setOnItemClick(new UserHospitalAdapter.onItemClickListener() {
            @Override
            public void location(int position) {
                displayLocation(arrayList.get(position));
            }
        });
    }

    private void funLoading() {
        loading = SweetDialog.loading(getContext());
        loading.show();
    }
    private void displayLocation( HospitalDataClass hospital) {
        LatLng location = new LatLng(Double.parseDouble(hospital.getLatitude()), Double.parseDouble(hospital.getLongitude()));
        Uri gmmIntentUri = Uri.parse("geo:" + location.latitude + "," + location.longitude + "?q=" + Uri.encode(hospital.getName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(getActivity(), "هاتفك لا يدعم خدمات جوجل", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}