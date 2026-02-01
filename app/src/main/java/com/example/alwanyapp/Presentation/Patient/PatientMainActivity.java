package com.example.alwanyapp.Presentation.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.alwanyapp.Presentation.auth.OuterActivity;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Helper.HelperLocalLanguage;
import com.example.alwanyapp.databinding.ActivityPationtMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class PatientMainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPationtMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelperLocalLanguage.setLocale(this,"ar");
        binding=ActivityPationtMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_test,R.id.nav_predication, R.id.nav_about_us, R.id.doctors,R.id.profile,
                R.id.nav_hospital,R.id.colorsGuide,R.id.nav_helper_apps ,R.id.nav_info,R.id.nav_color,R.id.viewUsers,R.id.consultations)
                .build();
        navController = Navigation.findNavController(this, R.id.user_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        binding.navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_test) {
                binding.drawerLayout.setVisibility(View.GONE);
                navController.navigate(R.id.nav_test);
            } else if (item.getItemId() == R.id.nav_predication) {
                binding.drawerLayout.setVisibility(View.GONE);
                navController.navigate(R.id.nav_predication);
            } else if (item.getItemId()==R.id.consultations) {
                binding.drawerLayout.setVisibility(View.GONE);
                navController.navigate(R.id.consultations);
            } else if (item.getItemId() == R.id.doctors) {
                binding.drawerLayout.setVisibility(View.GONE);
                navController.navigate(R.id.doctors);
            } else if (item.getItemId() == R.id.menu) {
                binding.drawerLayout.setVisibility(View.VISIBLE);
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        });
        binding.drawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.setVisibility(View.GONE);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sideMenu();
    }

    private void sideMenu() {
        View v = binding.sideMenu.getHeaderView(0);
        v.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(PatientMainActivity.this, OuterActivity.class));
              finish();
            }
        });
        v.findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.profile);
                binding.drawerLayout.setVisibility(View.GONE);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
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
        v.findViewById(R.id.about_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_about_us);
                binding.drawerLayout.setVisibility(View.GONE);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        v.findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.viewUsers);
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
        updateToken();
    }

    private void updateToken()
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String userToken = task.getResult();
                    //database.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(profile);
                    FirebaseDatabase.getInstance().getReference("tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").setValue(userToken);
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}