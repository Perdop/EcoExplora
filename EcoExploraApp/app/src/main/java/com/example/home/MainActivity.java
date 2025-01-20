package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.home.controller.NetworkUtilAnimaisExtintos;
import com.example.home.model.AnimaisExtintosModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AnimaisExtintosModel> animaisList;
    private NetworkUtilAnimaisExtintos networkUtilAnimaisExtintos;
    private boolean isDataLoaded = false; // Flag para verificar se os dados foram carregados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.iniciar);

        // Inicializar o NetworkUtil
        networkUtilAnimaisExtintos = new NetworkUtilAnimaisExtintos();

        // Tentar carregar os dados com uma tentativa repetida até que a lista seja carregada
        tryToLoadData();

        // Configurar o listener para o botão
        button.setOnClickListener(v -> {
            if (animaisList != null && !animaisList.isEmpty()) {
                // Passar a lista de animais para a HomeActivity
                Intent intent = new Intent(MainActivity.this, Home.class);
                intent.putParcelableArrayListExtra("ANIMAIS_LIST", new java.util.ArrayList<>(animaisList));
                startActivity(intent);
            } else {
                // Caso a lista ainda não tenha sido carregada corretamente
                Toast.makeText(MainActivity.this, "Nenhum animal encontrado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método que tenta carregar os dados repetidamente até conseguir
    private void tryToLoadData() {
        // Definir um tempo para tentar a requisição repetidamente (ex: 3 segundos entre as tentativas)
        final int retryDelay = 3000; // tempo de 3 segundos entre as tentativas

        // Usar um Handler para agendar as tentativas de maneira assíncrona
        new android.os.Handler().postDelayed(() -> {
            if (!isDataLoaded) {
                Log.d("MainActivity", "Tentando carregar os dados...");
                // Tentar a requisição da API
                networkUtilAnimaisExtintos.getRequestWithOkHttp();

                // Verificar se a lista foi carregada após a requisição
                animaisList = networkUtilAnimaisExtintos.getAnimaisList();

                // Se os dados foram carregados corretamente
                if (animaisList != null && !animaisList.isEmpty()) {
                    isDataLoaded = true;  // Atualiza a flag para indicar que os dados foram carregados
                    Toast.makeText(MainActivity.this, "Dados carregados com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "Número de animais carregados: " + animaisList.size());
                } else {
                    // Caso a lista ainda não tenha sido carregada, continue tentando
                    Log.e("MainActivity", "Falha ao carregar os dados, tentando novamente...");
                    tryToLoadData();  // Chama novamente o método para tentar carregar os dados
                }
            }
        }, retryDelay); // Chama o método novamente após o tempo de delay (3 segundos)
    }
}
