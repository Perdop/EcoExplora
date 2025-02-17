package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.home.controller.NetworkUtilAnimaisExtintos;
import com.example.home.model.AnimaisExtintosModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AnimaisExtintosModel> animaisList; // Modelo da lista de animaisExtintos
    private NetworkUtilAnimaisExtintos networkUtilAnimaisExtintos; //
    private boolean isDataLoaded = false; // Verifica se os dados foram carregados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.iniciar);

        Toast.makeText(MainActivity.this, "Procurando dados dos animais", Toast.LENGTH_SHORT).show(); // Sinaliza a procura pelos dados

        networkUtilAnimaisExtintos = new NetworkUtilAnimaisExtintos();

        tryToLoadData();
        button.setOnClickListener(v -> {
            // Verifica se os dados foram carregados corretamente
            if (isDataLoaded && animaisList != null && !animaisList.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                intent.putParcelableArrayListExtra("ANIMAIS_LIST", new java.util.ArrayList<>(animaisList));
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Nenhum animal encontrado, tentando novamente...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void tryToLoadData() {
        final int retryDelay = 100;

        new android.os.Handler().postDelayed(() -> {
            if (!isDataLoaded) {
                networkUtilAnimaisExtintos.getRequestWithOkHttp();
                animaisList = networkUtilAnimaisExtintos.getAnimaisList();

                // Se os dados foram carregados corretamente
                if (animaisList != null && !animaisList.isEmpty()) {
                    isDataLoaded = true;
                    DataStorage.getInstance().setAnimaisList(animaisList);
                    Toast.makeText(MainActivity.this, "Dados carregados com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    tryToLoadData();
                }
            }
        }, retryDelay);
    }


}
