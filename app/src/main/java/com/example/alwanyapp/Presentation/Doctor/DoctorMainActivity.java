package com.example.alwanyapp.Presentation.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.customview.widget.Openable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.alwanyapp.Presentation.auth.OuterActivity;
import com.example.alwanyapp.R;
import com.example.alwanyapp.databinding.ActivityAdminBinding;
import com.example.alwanyapp.databinding.ActivityDoctorMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class DoctorMainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDoctorMainBinding binding;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDoctorMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.mainView,R.id.viewUsers)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        binding.navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.mainView) {
                navController.navigate(R.id.mainView);
            } else if (item.getItemId() == R.id.viewUsers) {
                navController.navigate(R.id.viewUsers);
            } else if (item.getItemId()==R.id.logout) {
                startActivity(new Intent(DoctorMainActivity.this, OuterActivity.class));
                finish();
            }
            return true;
        });
        updateToken();
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
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
}