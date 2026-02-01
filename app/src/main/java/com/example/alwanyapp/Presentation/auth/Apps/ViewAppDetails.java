package com.example.alwanyapp.Presentation.auth.Apps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.alwanyapp.Domain.HelperAppsDataClass;
import com.example.alwanyapp.R;
import com.example.alwanyapp.databinding.FragmentViewAppDetailsBinding;

public class ViewAppDetails extends Fragment {
    private FragmentViewAppDetailsBinding mBinding;
    private HelperAppsDataClass app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentViewAppDetailsBinding.inflate(inflater,container,false);
        app = getArguments().getParcelable("app");
        mBinding.appName.setText("اسم التطبيق: "+app.getName());
        mBinding.infoDescription.setText(app.getDescription());
        Glide.with(getActivity()).load(app.getImage()).into(mBinding.image);
        mBinding.loadApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = app.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        return mBinding.getRoot();
    }
}