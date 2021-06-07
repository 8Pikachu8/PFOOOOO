package com.example.mymessenger.Fragments;

import Notifications.Myresponse;
import Notifications.Sender;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAuaaEJGc:APA91bHYCYDFrE_whUNr9mKlIptUtCvefwGv9hTeGM8NaQq4tqOlrWo6yZg97idNNu7-hP1LJ48sS04l8B1tLVtbZlgjBjT7bXRvDqPTW1WtzpJ8TubmA4SJSJ7NxGzIFFixStt2cahP"
            }
    )

    @POST("fcm/send")
    Call<Myresponse> sendNotification(@Body Sender body);
}
