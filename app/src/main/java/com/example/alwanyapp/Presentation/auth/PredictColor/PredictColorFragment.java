package com.example.alwanyapp.Presentation.auth.PredictColor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.alwanyapp.R;
import com.example.alwanyapp.databinding.FragmentPredictColorBinding;
import com.example.alwanyapp.databinding.ResultDialogBinding;
import com.example.alwanyapp.network.ApiClient;
import com.example.alwanyapp.network.ApiInterface;
import com.example.alwanyapp.network.ResponseClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictColorFragment extends Fragment {
    private FragmentPredictColorBinding binding;
    private static final int GalleryPick = 10;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 1001;
    private ApiInterface apiInterface;
    private Uri imageUri;
    private Bitmap photo;
    private String path;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPredictColorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiInterface = ApiClient.getInstance().create(ApiInterface.class);
        binding.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkThePermission(0);
            }
        });

        binding.camera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED)
            {
                checkThePermission(1);
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);

            }
         // requestCameraPermissions();
        });
    }

    private void checkThePermission(int i) {
        Log.e("test","tttt");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                if (i==0) {
                    openGallery();
                }
                else {
                    launchCamera();
                }
                Log.e("TAG1", "onViewCreated: openGallery ");
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);

            }
        }
        else {
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (i==0) {
                    openGallery();
                }
                else {
                    launchCamera();
                }
                Log.e("TAG", "onViewCreated: openGallery ");
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void launchCamera() {
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
         startActivityForResult(takePictureIntent, CAMERA_REQUEST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void openGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "إختر صورة"), GalleryPick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode == Activity.RESULT_OK ) {
            if (data != null) {
                imageUri = data.getData();
                binding.logoImage.setImageURI(imageUri);
                Log.e("TAG", "onActivityResult: " + imageUri);
                path = RealPathUtil.getRealPath(getActivity(), imageUri);
                uploadResource();
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                photo = (Bitmap) data.getExtras().get("data");
                binding.logoImage.setImageBitmap(photo);
                Log.e("TAG", "onActivityResult camera: " + imageUri);
                imageUri= saveImageToExternalStorage(photo);
                path = RealPathUtil.getRealPath(getActivity(), imageUri);

                uploadResource();
               // bitmap();
            }
        }
    }
    private Uri saveImageToExternalStorage(Bitmap imageBitmap) {
        File imagesFolder = new File(getActivity().getExternalFilesDir(null), "images");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }
        File imageFile = new File(imagesFolder, "image.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void uploadResource() {
        binding.progressBar.setVisibility(View.VISIBLE);

        if (path != null) {
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

            apiInterface.uploadImage(body).enqueue(new Callback<ResponseClass>() {
                @Override
                public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        binding.progressBar.setVisibility(View.GONE);
                        Log.e("onResponse", "uploadImage resource: " + response.body());
                        showResultDialog(response.body().getColor());
                    }
                }

                @Override
                public void onFailure(Call<ResponseClass> call, Throwable t) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Resource Added onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("onFailure", "resource onFailure: " + t.getMessage());
                }
            });

        }
    }
    private void requestCameraPermissions() {
        // Check if the camera and storage permissions have been granted
        boolean cameraPermissionGranted = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean storagePermissionGranted = ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermissionGranted || !storagePermissionGranted) {
            String[] permissionsToRequest = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES};
            ActivityCompat.requestPermissions(requireActivity(), permissionsToRequest, CAMERA_PERMISSIONS_REQUEST_CODE);
        }else {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                }
            }

            if (allPermissionsGranted) {
                // All permissions granted, start camera activity here
                launchCamera();
            } else {
                requestCameraPermissions();
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
                // Permission denied, show permission explanation or disable functionality that depends on this permission
            }

        }
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void showResultDialog(String result) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        @SuppressLint("InflateParams") View mView = LayoutInflater.from(getContext()).inflate(R.layout.result_dialog, null);
        ResultDialogBinding dialogBinding = ResultDialogBinding.bind(mView);
        dialog.setContentView(mView);
        switch (result) {
            case "Red":
                result = "الأحمر";
                break;
            case "Blue":
                result = "الأزرق";
                break;
            case "Green":
                result = "الأخضر";
                break;
            case "Yellow":
                result = "الأصفر";
                break;
        }
        String text = "الصورة التي قمت بالتحقق من لونها كانت تحتوي على اللون "+result;
        dialogBinding.resultText.setText(text);

        dialogBinding.okBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}