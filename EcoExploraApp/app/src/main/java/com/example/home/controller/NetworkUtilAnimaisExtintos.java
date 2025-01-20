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

    private List<AnimaisExtintosModel> animaisList;  // Lista para armazenar os dados dos animais

    // Método que faz a requisição GET com OkHttp
    public void getRequestWithOkHttp() {
        // Criando uma instância do OkHttpClient
        OkHttpClient client = new OkHttpClient();

        // Criando a requisição com a URL desejada
        Request request = new Request.Builder()
                .url("https://testspring-g2nh.onrender.com/getAllAnimais")  // Substitua pela URL da sua API
                .build();

        // Executando a requisição de forma assíncrona em uma nova thread
        new Thread(() -> {
            try {
                // Fazendo a requisição e obtendo a resposta
                Response response = client.newCall(request).execute();

                // Verificando se a requisição foi bem-sucedida (código HTTP 200)
                if (response.isSuccessful()) {
                    // Obtendo o corpo da resposta como String
                    String responseBody = response.body().string();

                    // Usando o Gson para deserializar a resposta JSON
                    Gson gson = new Gson();
                    // Deserializa a resposta JSON para uma lista de AnimaisExtintosModel
                    animaisList = gson.fromJson(responseBody, new TypeToken<List<AnimaisExtintosModel>>(){}.getType());

                    // Aqui, você pode manipular a lista de animais como quiser
                    // Exemplo de log
                    for (AnimaisExtintosModel animal : animaisList) {
                        Log.d("Animais Data", "ID: " + animal.getId() +
                                ", Nome: " + animal.getNome() +
                                ", Sobre: " + animal.getSobre() +
                                ", Classe: " + animal.getClasse() +
                                ", Existentes: " + animal.getExistentes() +
                                ", Estado: " + animal.getEstado() +
                                ", Imagem: " + animal.getImg());
                    }

                    // Se você precisar atualizar a UI, use Handler para rodar na thread principal
                    new Handler(Looper.getMainLooper()).post(() -> {
                        // Exemplo de atualizar a UI com os dados (se necessário)
                        // textView.setText(responseBody); // Atualize a UI aqui!
                    });
                } else {
                    // Caso a requisição falhe (não retorne código 200)
                    Log.e("GET Error", "Request failed with status: " + response.code());
                }
            } catch (IOException e) {
                // Tratando exceções de rede ou outras falhas
                Log.e("GET Error", "Error during GET request", e);
            }
        }).start();  // Executa a requisição em uma thread separada para não bloquear a UI
    }

    // Método para obter a lista de animais extintos
    public List<AnimaisExtintosModel> getAnimaisList() {
        return animaisList;
    }
}
