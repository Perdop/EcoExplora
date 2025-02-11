package com.example.home.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.home.BuildConfig;
import com.example.home.model.AnimaisExtintosModel;
import com.example.home.model.Users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;

public class GetUser {
    public static String getUser(String username) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://ecoexplora.onrender.com" + "/getUser/" + username;
        String apiKey = BuildConfig.API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-API-KEY", apiKey)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string().trim();
            Log.d("usertest", "getUser: " + responseBody);
            return responseBody; // Converte o texto em boolean
        } catch (IOException e) {
            e.printStackTrace();
            return "Usuario não encontrado";
        }
    }
}
