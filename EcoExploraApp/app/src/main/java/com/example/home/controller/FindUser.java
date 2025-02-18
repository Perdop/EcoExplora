package com.example.home.controller;

import com.example.home.BuildConfig;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Classe que conecta a API para a funcao findUser, ela verifica a existencia do usuario
public class FindUser {
    public static boolean findUser(String username) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://ecoexplora.onrender.com/findUser/" + username;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-API-KEY", BuildConfig.API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) { // Try-with-resources, fecha no final do bloco
            String responseBody = response.body().string().trim();
            return Boolean.parseBoolean(responseBody); // Converte o texto em boolean
        } catch (IOException e) {
            e.printStackTrace();
            return true; // Retorna true em caso de erro, assim sendo identificado como se o usuario existisse e barrando os outros processos
        }
    }


}

