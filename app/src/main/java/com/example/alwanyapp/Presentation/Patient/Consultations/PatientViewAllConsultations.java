package com.example.alwanyapp.Presentation.Patient.Consultations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alwanyapp.Domain.ConsultationsDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentPatientViewAllConsultationsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PatientViewAllConsultations extends Fragment {
    private FragmentPatientViewAllConsultationsBinding mBinding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private UserConsultationsAdapter adapter;
    private ArrayList<ConsultationsDataClass> arrayList;
    private String patientId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentPatientViewAllConsultationsBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.consultationDatabaseTableName);
        patientId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
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
        adapter = new UserConsultationsAdapter(arrayList, getActivity());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(adapter);
        getConsultations();
    }

    private void getConsultations() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                arrayList.clear();
                if (snapshot.exists()) {
                    mBinding.empty.setVisibility(View.GONE);
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ConsultationsDataClass doc = data.getValue(ConsultationsDataClass.class);
                        if (doc.getStatus().equals("replay")&&doc.getPatientId().equals(patientId)) {
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
        adapter.onItemClickListener(new UserConsultationsAdapter.onItemClickListener() {
            @Override
            public void replay(int position) {
                Bundle b=new Bundle();
                b.putParcelable("con",arrayList.get(position));
                navigateTo(R.id.action_consultations_to_consultationsReplay,b);
            }
        });
    }
    private void navigateTo(int id, Bundle b)
    {
        Navigation.findNavController(getActivity(),R.id.user_nav_host_fragment).navigate(id,b);
    }
}