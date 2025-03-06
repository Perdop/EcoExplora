package com.example.home;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.Manifest;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import com.example.home.databinding.ActivityMainBinding;

import org.json.JSONObject;

public class PagAnimais extends AppCompatActivity {
    private String uploadPreset = "EcoExplora"; //NEW - Name of unsigned upload preset";

    private ActivityMainBinding binding; //New - Activity binding

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pag_animais);


        SharedPreferences userState = getSharedPreferences("userState", MODE_PRIVATE);
        userName = userState.getString("user", "");

        binding = ActivityMainBinding.inflate(getLayoutInflater()); // New - inflate view binding

        // Pede permissao da camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }

        // Recebe os dados passados pela Intent
        String nomeAnimal = getIntent().getStringExtra("NOME_ANIMAL");
        String descricaoAnimal = getIntent().getStringExtra("DESCRICAO_ANIMAL");
        String estadoAnimal = getIntent().getStringExtra("ESTADO_ANIMAL");
        String existentesAnimal = String.valueOf(getIntent().getIntExtra("EXISTENTES_ANIMAL", 0));  // Converte o inteiro para string
        String imgAnimal = getIntent().getStringExtra("IMG_ANIMAL");


        // Botao que chama a camera
        ImageButton camBtn = findViewById(R.id.camBtn);
        camBtn.setOnClickListener(v -> {
            // Verifica login
            if( userName == ""){
                Toast.makeText(PagAnimais.this, "Faça login antes de enviar", Toast.LENGTH_SHORT).show();
                return;
            }
            // Envia os dados pra proxima tela
            Intent detailIntent = new Intent(PagAnimais.this, PagAnimaisPhoto.class);
            detailIntent.putExtra("NOME_ANIMAL", nomeAnimal);
            startActivity(detailIntent);
        });

        // Exibe os dados na interface
        TextView animalNome = findViewById(R.id.animalName);
        TextView animalDescricao = findViewById(R.id.animalDescricao);
        TextView animalEstado = findViewById(R.id.animalEstado);
        TextView animalExistentes = findViewById(R.id.animalExistentes);
        ImageView animalImage = findViewById(R.id.animalImage);
        String imageUrl = imgAnimal;

        animalNome.setText(nomeAnimal);
        animalDescricao.setText(descricaoAnimal);
        animalEstado.setText(estadoAnimal);
        animalExistentes.setText(existentesAnimal);
        Picasso.get()
                .load(imageUrl)
                .into(animalImage);
        Log.d("img","img" + imageUrl + "img2" + imgAnimal);

        // Configura os botões de navegação
        TextView animaisVoltar = findViewById(R.id.setaVoltar);


        // Alterando o comportamento do botão de voltar para considerar o tipo do animal
        animaisVoltar.setOnClickListener(v -> {
            // Chama o método onBackPressed(), que simula o comportamento do botão "voltar" do celular
            onBackPressed();
        });
    }
}
