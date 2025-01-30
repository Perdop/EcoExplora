package com.example.home.controller;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FindUser {
    private static final String BASE_URL = "https://ecoexplora.onrender.com"; // Substitua pelo endereço real da API

    public static boolean findUser(String username) {
        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + "/findUser/" + username;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return false; // Retorna false se a requisição falhar
            }

            String responseBody = response.body().string().trim();

            return Boolean.parseBoolean(responseBody); // Converte o texto em boolean
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Retorna false em caso de erro
        }
    }


}

