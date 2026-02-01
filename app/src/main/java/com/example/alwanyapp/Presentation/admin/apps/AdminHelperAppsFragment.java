package com.example.alwanyapp.Presentation.admin.apps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alwanyapp.Domain.HelperAppsDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Dialoge.SweetDialog;
import com.example.alwanyapp.databinding.FragmentAdminHelperAppsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminHelperAppsFragment extends Fragment {

    private FragmentAdminHelperAppsBinding binding;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private AppHelperAdapter adapter;
    private ArrayList<HelperAppsDataClass> arrayList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminHelperAppsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference(CommonData.appsDatabaseTableName);
        funLoading();
        defineItems();
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fabAdd.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_nav_admin_apps_to_nav_add_app));
        clickItem(view);
    }
    private void defineItems()
    {
        arrayList=new ArrayList<>();
        adapter=new AppHelperAdapter(arrayList,getActivity());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recyclerView.setAdapter(adapter);
        getApps();
    }
    private void getApps()
    {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                arrayList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot data:snapshot.getChildren())
                    {
                        HelperAppsDataClass app=data.getValue(HelperAppsDataClass.class);
                        arrayList.add(app);
                    }
                }else{
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void clickItem(View v)
    {
        adapter.setOnItemClick(new AppHelperAdapter.onItemClickListener() {
            @Override
            public void details(int position) {
                Bundle b=new Bundle();
                b.putParcelable("app",arrayList.get(position));
                Navigation.findNavController(v).navigate(R.id.action_nav_admin_apps_to_nav_admin_app_details,b);
            }
        });
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