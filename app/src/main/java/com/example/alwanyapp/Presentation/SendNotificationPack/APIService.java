package com.example.alwanyapp.Presentation.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                     "Content-Type:application/json",
                    "Authorization:key=AAAAxZW64Tg:APA91bGhlL2OgM8dV6aXcaMlDx_AKhyEK8JlvVyTBI8UlQHTDOv9cBHE9WKHCSqzY4BFKDBRZ2djQ6nfICbpIbkV9vNGEaWoJFsH65_46PED1bLahoJgOZgGNTKzaj08ti5rtFwaWxPV" // Your server key refer to video for finding your server key
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

