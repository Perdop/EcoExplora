package com.example.home.controller;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FindUser {
    private static final String BASE_URL = "https://ecoexplora.onrender.com/"; // Substitua pelo endereço real da API

    public static boolean findUser(String username) {
        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL + "/findUser/" + username;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute(); // Fazendo a requisição síncrona
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return Boolean.parseBoolean(responseBody); // Converte a resposta para boolean
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

