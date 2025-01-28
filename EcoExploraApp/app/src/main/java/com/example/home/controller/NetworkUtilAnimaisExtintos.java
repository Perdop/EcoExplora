package com.example.home.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.home.model.AnimaisExtintosModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;

public class NetworkUtilAnimaisExtintos {

    private List<AnimaisExtintosModel> animaisList;  // Lista modelo

    // Requisição GET
    public void getRequestWithOkHttp() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://ecoexplora.onrender.com/getAllExtinctAnimals")
                .build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseBody = response.body().string();

                    Gson gson = new Gson(); // Usa Gson para converter o Json para String
                    animaisList = gson.fromJson(responseBody, new TypeToken<List<AnimaisExtintosModel>>(){}.getType());

                } else {
                    Log.e("GET Error", "Request failed with status: " + response.code());
                }
            } catch (IOException e) {
                Log.e("GET Error", "Error during GET request", e);
            }
        }).start();
    }

    public List<AnimaisExtintosModel> getAnimaisList() {
        return animaisList;
    }
}
