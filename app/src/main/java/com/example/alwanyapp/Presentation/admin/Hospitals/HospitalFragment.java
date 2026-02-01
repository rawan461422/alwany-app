package com.example.alwanyapp.Presentation.admin.Hospitals;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alwanyapp.Domain.HospitalDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentAdminHospitalBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HospitalFragment extends Fragment {
    private FragmentAdminHospitalBinding binding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private HospitalAdapter adapter;
    private ArrayList<HospitalDataClass> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminHospitalBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.hospitalDatabaseTableName);
        funLoading();
        defineItems();
        clickItem();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fabAdd.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_nav_admin_hospital_to_nav_add_hospital));
    }
    private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new HospitalAdapter(arrayList, getActivity());
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
        adapter.setOnItemClick(new HospitalAdapter.onItemClickListener() {
            @Override
            public void delete(int position) {
                alertDialog(position);
            }

            @Override
            public void location(int position) {
                displayLocation(arrayList.get(position));
            }
        });
    }
    private void displayLocation( HospitalDataClass hospital) {
        LatLng location = new LatLng(Double.parseDouble(hospital.getLatitude()), Double.parseDouble(hospital.getLongitude()));
        Uri gmmIntentUri = Uri.parse("geo:" + location.latitude + "," + location.longitude + "?q=" + Uri.encode(hospital.getName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(getActivity(), " Your phone does not have Google Map services", Toast.LENGTH_SHORT).show();
        }
    }
    private void alertDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("حذف مستشفى")
                .setMessage("هل تريد حذف هذة مستشفى ؟");
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                funLoading();
                deleteApp(position);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteApp(int position) {
        database.child(arrayList.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loading.dismiss();
                funSuccessfully("تم حذف المستشفى بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                funField("فشل في حذف هذة المستشفى الرجاء المحاولة مرة أخرى");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}