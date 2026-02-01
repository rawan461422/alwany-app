package com.example.alwanyapp.Presentation.auth;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.Util.Helper.HelperLocalLanguage;
import com.example.alwanyapp.databinding.ActivityOuterBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class OuterActivity extends AppCompatActivity {

    private ActivityOuterBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelperLocalLanguage.setLocale(this, "ar");
        binding = ActivityOuterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //drawerLayout=(DrawerLayout) findViewById(R.id.drowable);
        setSupportActionBar(binding.toolbar);
        getModelUrl();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_test,R.id.nav_predication, R.id.nav_about_us, R.id.login,
                R.id.nav_hospital,R.id.colorsGuide,R.id.nav_helper_apps ,R.id.nav_info,R.id.nav_color,R.id.viewUsers,R.id.consultations)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_outer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.color2));

        binding.navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_test) {
                binding.drawerLayout.setVisibility(View.GONE);
                navController.navigate(R.id.nav_test);
            } else if (item.getItemId() == R.id.nav_predication) {
                binding.drawerLayout.setVisibility(View.GONE);
                navController.navigate(R.id.nav_predication);
            } else if (item.getItemId()==R.id.aboutApp) {
                binding.drawerLayout.setVisibility(View.GONE);
                navController.navigate(R.id.nav_about_us);
            } else if (item.getItemId() == R.id.login) {
                binding.drawerLayout.setVisibility(View.GONE);
                navController.navigate(R.id.login);
            } else if (item.getItemId() == R.id.menu) {
                binding.drawerLayout.setVisibility(View.VISIBLE);
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        });
        sideMenu();
    }

    private void sideMenu() {
        View v = binding.sideMenu.getHeaderView(0);
        v.findViewById(R.id.hospitals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_hospital);
                binding.drawerLayout.setVisibility(View.GONE);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        v.findViewById(R.id.apps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_helper_apps);
                binding.drawerLayout.setVisibility(View.GONE);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        v.findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_info);
                binding.drawerLayout.setVisibility(View.GONE);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        v.findViewById(R.id.colors).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_color);
                binding.drawerLayout.setVisibility(View.GONE);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        v.findViewById(R.id.colorsGuide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.colorsGuide);
                binding.drawerLayout.setVisibility(View.GONE);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void getModelUrl() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("UrlModel");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CommonData.modelUrl = snapshot.child("url").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}