package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.example.home.controller.NetworkUtilAnimaisExtintos;
import com.example.home.model.AnimaisExtintosModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<AnimaisExtintosModel> animaisList; // Modelo da lista de animaisExtintos
    private NetworkUtilAnimaisExtintos networkUtilAnimaisExtintos; //
    Toast searchAnimals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map config = new HashMap();
        config.put("cloud_name", "dixozqlb4");
        MediaManager.init(this, config);

        Button button = findViewById(R.id.iniciar);

        searchAnimals = Toast.makeText(MainActivity.this, "Procurando dados dos animais", Toast.LENGTH_SHORT); // Sinaliza a procura pelos dados
        searchAnimals.show();

        networkUtilAnimaisExtintos = new NetworkUtilAnimaisExtintos();
        tryToLoadData();

        button.setOnClickListener(v -> {
            // Verifica se os dados foram carregados corretamente
            if (animaisList != null && !animaisList.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Nenhum animal encontrado, tentando novamente...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void tryToLoadData() {
        final int retryDelay = 100;

        new android.os.Handler().postDelayed(() -> {
                networkUtilAnimaisExtintos.getRequestWithOkHttp();
                animaisList = networkUtilAnimaisExtintos.getAnimaisList();

                // Se os dados foram carregados corretamente
                if (animaisList != null && !animaisList.isEmpty()) {
                    searchAnimals.cancel();
                    DataStorage.getInstance().setAnimaisList(animaisList);
                    Toast.makeText(MainActivity.this, "Dados carregados com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    tryToLoadData();
                }
        }, retryDelay);
    }


}
