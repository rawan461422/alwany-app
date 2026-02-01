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
import com.example.alwanyapp.Presentation.admin.Doctors.Adapters.AcceptedDoctorsAdapter;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentAcceptedDoctorsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AcceptedDoctorsFragment extends Fragment {

    private FragmentAcceptedDoctorsBinding binding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private AcceptedDoctorsAdapter adapter;
    private ArrayList<DoctorDataClass> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAcceptedDoctorsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.doctorDatabaseTableName);
        funLoading();
        defineItems();
        clickItem();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.newBtn.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_nav_accepted_doctors_to_nav_new_doctors));
    }

    private void defineItems() {
        arrayList = new ArrayList<>();
        adapter = new AcceptedDoctorsAdapter(arrayList, getActivity());
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
                    binding.empty.setVisibility(View.GONE);
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DoctorDataClass doc = data.getValue(DoctorDataClass.class);
                        if (doc.getStatus().equals("accepted") || doc.getStatus().equals("block")) {
                            arrayList.add(doc);
                        }
                    }
                    if (arrayList.isEmpty())
                    {
                        binding.empty.setVisibility(View.VISIBLE);

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
        adapter.onItemClickListener(new AcceptedDoctorsAdapter.onItemClickListener() {
            @Override
            public void block(int position) {
                if (arrayList.get(position).getStatus().equals("block")) {
                    alertDialog(arrayList.get(position).getId()
                            , " فك حظر الطبيب", "هل تريد فك حظر هذا الطبيب", "accepted");

                } else {
                    alertDialog(arrayList.get(position).getId()
                            , "حظر الطبيب", "هل تريد حظر هذا الطبيب", "block");
                }
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
                block(userId, status);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void block(String userId, String status) {
        database.child(userId).child("status").setValue(status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if (status.equals("block")) {
                    funSuccessfully("تم حظر الطبيب بنجاح");
                } else {
                    funSuccessfully("تم فك حظر الطبيب بنجاح");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (status.equals("block")) {
                    funField("فشل في حظر الطبيب");
                } else {
                    funField("فشل فى  فك حظر الطبيب");
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