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

        // Botao que chama a camera
        ImageButton camBtn = findViewById(R.id.camBtn);
        camBtn.setOnClickListener(v -> {
            abrirOpcoes();
        });

        // Recebe os dados passados pela Intent
        String nomeAnimal = getIntent().getStringExtra("NOME_ANIMAL");
        String descricaoAnimal = getIntent().getStringExtra("DESCRICAO_ANIMAL");
        String estadoAnimal = getIntent().getStringExtra("ESTADO_ANIMAL");
        String existentesAnimal = String.valueOf(getIntent().getIntExtra("EXISTENTES_ANIMAL", 0));  // Converte o inteiro para string
        String imgAnimal = getIntent().getStringExtra("IMG_ANIMAL");

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

    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private void abrirOpcoes() {
        if( userName == ""){
            Toast.makeText(PagAnimais.this, "Faça login antes de enviar", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma opção")
                .setItems(new CharSequence[]{"Tirar Foto", "Selecionar da Galeria", "Cancelar"},
                        (dialog, which) -> {
                            if (which == 0) { // Tirar foto
                                verificarPermissaoCamera();
                            } else if (which == 1) { // Escolher da galeria
                                selecionarImagemGaleria();
                            }
                        }).show();
    }
    private void verificarPermissaoCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            abrirCamera();
        }
    }

    private void abrirCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            abrirCameraLauncher.launch(intent);
        }
    }
    private void selecionarImagemGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        abrirGaleriaLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> abrirCameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = (Bitmap) extras.get("data"); // Captura o Bitmap da foto
                            // Chame o método para fazer o upload, se necessário
                            uploadImageBit(bitmap);
                        }
                    }
                }
            }
    );


    private final ActivityResultLauncher<Intent> abrirGaleriaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Processar resultado da galeria aqui
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri imageUri = data.getData(); // URI da imagem selecionada
                        uploadImageUri(imageUri); // New - call upload image method
                    }
                }
            }
    );

    // New - upload image function
    private void uploadImageUri(Uri uri) {
        MediaManager.get().upload(uri).unsigned(uploadPreset).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d("Cloudinary Quickstart", "Upload start");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d("Cloudinary Quickstart", "Upload progress");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d("Cloudinary Quickstart", "Upload success");
                String url = (String) resultData.get("secure_url");
                Log.d("requestId", "onSuccess: " + url);
                enviarDados(userName,"test","test",url);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d("Cloudinary Quickstart", "Upload failed");
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

    private void uploadImageBit(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Envia o ByteArray para o Cloudinary
        MediaManager.get().upload(byteArray).unsigned(uploadPreset).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d("Cloudinary Quickstart", "Upload start");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d("Cloudinary Quickstart", "Upload progress");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                String url = (String) resultData.get("url"); // Obtém a URL da imagem
                Log.d("Cloudinary", "Imagem enviada com sucesso: " + url);
                enviarDados(userName,"test","test",url);

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d("Cloudinary Quickstart", "Upload failed");
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

    private void enviarDados(String user, String location, String date, String photo) {
        new Thread(() -> {
            try {
                // URL da API
                URL url = new URL("https://ecoexplora.onrender.com/addSightings");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("X-API-KEY", BuildConfig.API_KEY);
                conn.setDoOutput(true);

                // Criar o objeto JSON
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("user", user);
                jsonParam.put("location", location);
                jsonParam.put("date", date);
                jsonParam.put("photo", photo);

                // Enviar o JSON
                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes(StandardCharsets.UTF_8));
                os.close();

                // Verificar a resposta
                int responseCode = conn.getResponseCode();
                runOnUiThread(() -> {
                    if (responseCode == 200) {
                        Toast.makeText(PagAnimais.this, "Informação enviada!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PagAnimais.this, "Erro ao enviar!", Toast.LENGTH_SHORT).show();
                    }
                });

                conn.disconnect();
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(PagAnimais.this, "Erro no envio!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }



}
