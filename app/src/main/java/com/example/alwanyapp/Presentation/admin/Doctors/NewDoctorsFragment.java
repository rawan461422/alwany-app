package com.example.alwanyapp.Presentation.admin.Doctors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alwanyapp.Domain.DoctorDataClass;
import com.example.alwanyapp.Presentation.admin.Doctors.Adapters.NewDoctorsAdapter;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentNewDoctorsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewDoctorsFragment extends Fragment {

    private FragmentNewDoctorsBinding binding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private NewDoctorsAdapter adapter;
    private ArrayList<DoctorDataClass> arrayList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewDoctorsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.acceptedBtn.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_nav_new_doctors_to_nav_accepted_doctors));
        database = FirebaseDatabase.getInstance().getReference(CommonData.doctorDatabaseTableName);
        funLoading();
        defineItems();
        clickItem();
    }
    private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new NewDoctorsAdapter(arrayList, getActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        getDoctors();
    }

    private void getDoctors() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                arrayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DoctorDataClass doc = data.getValue(DoctorDataClass.class);
                        if (doc.getStatus().equals("new")) {
                            arrayList.add(doc);
                        }
                    }
                    if (arrayList.isEmpty())
                    {
                        binding.empty.setVisibility(View.VISIBLE);
                    }
                    else{
                        binding.empty.setVisibility(View.GONE);
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
        adapter.onItemClickListener(new NewDoctorsAdapter.onItemClickListener() {
            @Override
            public void reject(int position) {
                alertDialog(arrayList.get(position).getId()
                        , " رفض الطبيب", "هل تريد رفض طلب إنضمام هذا الطبيب", "rejected");


            }

            @Override
            public void accept(int position) {
                alertDialog(arrayList.get(position).getId()
                        , "قبول الطبيب", "هل تريد قبول طلب إنضمام هذا الطبيب", "accepted");
            }
        });
    }

    private void alertDialog(String userId, String title, String message, String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message);
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                funLoading();
                changeStatus(userId, status);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void changeStatus(String id, String status) {
        database.child(id).child("status").setValue(status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if (status.equals("accepted")) {
                    funSuccessfully("تم قبول الطبيب بنجاح");
                } else {
                    funSuccessfully("تم رفض الطبيب بنجاح");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (status.equals("accepted")) {
                    funField("فشل في قبول الطبيب");
                } else {
                    funField("فشل فى وفض الطبيب");
                }
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