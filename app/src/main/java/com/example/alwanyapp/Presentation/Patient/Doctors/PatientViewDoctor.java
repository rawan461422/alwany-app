package com.example.alwanyapp.Presentation.Patient.Doctors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alwanyapp.Domain.DoctorDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentPatientViewDoctorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PatientViewDoctor extends Fragment {
    private FragmentPatientViewDoctorBinding mBinding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private UserDoctorsAdapter adapter;
    private ArrayList<DoctorDataClass> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentPatientViewDoctorBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.doctorDatabaseTableName);
        funLoading();
        defineItems();
        clickItem();
        return mBinding.getRoot();
    }
    private void funLoading() {
        loading = SweetDialog.loading(getContext());
        loading.show();
    }


    private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new UserDoctorsAdapter(arrayList, getActivity());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(adapter);
        getDoctors();
    }

    private void getDoctors() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                arrayList.clear();
                if (snapshot.exists()) {
                    mBinding.empty.setVisibility(View.GONE);
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DoctorDataClass doc = data.getValue(DoctorDataClass.class);
                        if (doc.getStatus().equals("accepted")) {
                            arrayList.add(doc);
                        }
                    }
                    if (arrayList.isEmpty()) {
                        mBinding.empty.setVisibility(View.VISIBLE);
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
        adapter.onItemClickListener(new UserDoctorsAdapter.onItemClickListener() {
            @Override
            public void chat(int position) {
                Bundle b=new Bundle();
                b.putString("id",arrayList.get(position).getId());
                navigateTo(R.id.action_doctors_to_chat,b);
            }

            @Override
            public void consultations(int position) {
                Bundle b=new Bundle();
                b.putString("id",arrayList.get(position).getId());
                navigateTo(R.id.action_doctors_to_sendConsultation,b);
            }
        });
    }

    private void navigateTo(int id, Bundle b) {
        Navigation.findNavController(getActivity(), R.id.user_nav_host_fragment).navigate(id,b);
    }
}