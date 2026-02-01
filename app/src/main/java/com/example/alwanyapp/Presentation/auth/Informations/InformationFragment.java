package com.example.alwanyapp.Presentation.auth.Informations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alwanyapp.Domain.InfoDataClass;
import com.example.alwanyapp.Presentation.admin.info.InformationsAdapter;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentInformationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InformationFragment extends Fragment {

    private FragmentInformationBinding binding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private UserInformationsAdapter adapter;
    private ArrayList<InfoDataClass> arrayList;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformationBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.infoDatabaseTableName);
        funLoading();
        defineItems();
        return binding.getRoot();
    }
    private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new UserInformationsAdapter(arrayList, getActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        getInf();
    }

    private void getInf() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                arrayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        InfoDataClass info = data.getValue(InfoDataClass.class);
                        arrayList.add(info);
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

    private void clickItem(View v) {
        adapter.setOnItemClick(new UserInformationsAdapter.onItemClickListener() {
            @Override
            public void View(int position) {
                Bundle b=new Bundle();
                b.putParcelable("info",arrayList.get(position));
                Navigation.findNavController(v).navigate(R.id.action_nav_info_to_nav_info_details,b);
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //binding.info1.setOnClickListener(v-> Navigation.findNavController(view).navigate(R.id.action_nav_info_to_nav_info_details));
        clickItem(view);
    }
    private void funLoading() {
        loading = SweetDialog.loading(getContext());
        loading.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}