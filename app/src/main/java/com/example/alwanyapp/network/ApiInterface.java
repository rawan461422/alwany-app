package com.example.alwanyapp.network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    //https://dd9a-34-141-167-171.ngrok-free.app/color
    @Multipart
    @POST("/color")
    Call<ResponseClass> uploadImage(@Part MultipartBody.Part image);
}
