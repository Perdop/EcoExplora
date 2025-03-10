package com.example.home;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import org.json.JSONArray;

import android.graphics.Typeface;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import android.Manifest;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import com.example.home.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

public class PagAnimais extends AppCompatActivity {
    private String uploadPreset = "EcoExplora"; //NEW - Name of unsigned upload preset";

    private ActivityMainBinding binding; //New - Activity binding

    private String userName;
    private String nomeAnimal;
    private List<String> avistamentosList;

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
        nomeAnimal = getIntent().getStringExtra("NOME_ANIMAL");
        String descricaoAnimal = getIntent().getStringExtra("DESCRICAO_ANIMAL");
        String estadoAnimal = getIntent().getStringExtra("ESTADO_ANIMAL");
        String existentesAnimal = String.valueOf(getIntent().getIntExtra("EXISTENTES_ANIMAL", 0));  // Converte o inteiro para string
        String imgAnimal = getIntent().getStringExtra("IMG_ANIMAL");


        // Botao que chama a camera
        ImageButton camBtn = findViewById(R.id.camBtn);
        camBtn.setOnClickListener(v -> {
            // Verifica login
            if (userName == "") {
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
        Log.d("img", "img" + imageUrl + "img2" + imgAnimal);

        // Configura os botões de navegação
        TextView animaisVoltar = findViewById(R.id.setaVoltar);


        // Alterando o comportamento do botão de voltar para considerar o tipo do animal
        animaisVoltar.setOnClickListener(v -> {
            // Chama o método onBackPressed(), que simula o comportamento do botão "voltar" do celular
            onBackPressed();
        });

        // Avistamentos
        getAvistamentos();
        ImageButton refreshAvistamentos = findViewById(R.id.refreshAvistamentos);
        refreshAvistamentos.setOnClickListener(v -> {
            getAvistamentos();
            Log.d("teaads", "onCreate: ");
        });

    }

    private void getAvistamentos() {
        new Thread(() -> {
            try {
                URL url = new URL("https://ecoexplora.onrender.com/getAllSightings");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("X-API-KEY", BuildConfig.API_KEY);
                conn.setDoInput(true);

                // Código de resposta HTTP
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Lendo a resposta da API
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Converte para JSONArray e filtra os avistamentos
                    JSONArray avistamentos = new JSONArray(response.toString());
                    List<JSONObject> filtrados = filtrarPorAnimal(avistamentos, nomeAnimal);

                    // Atualiza a UI no thread principal
                    runOnUiThread(() -> {
                        createAvistamentos(filtrados);
                        Log.d("testeagr", "onCreate: " + avistamentosList);
                    });

                } else {
                    Log.e("API_ERROR", "Erro na requisição: " + responseCode);
                }

                conn.disconnect();
            } catch (Exception e) {
                Log.e("API_EXCEPTION", "Erro ao conectar na API", e);
            }
        }).start();
    }




    public static List<JSONObject> filtrarPorAnimal(JSONArray avistamentos, String nomeAnimal) {
        List<JSONObject> filtrados = new ArrayList<>();
        try {
            for (int i = 0; i < avistamentos.length(); i++) {
                JSONObject avistamento = avistamentos.getJSONObject(i);
                if (avistamento.has("animal") && avistamento.getString("animal").equalsIgnoreCase(nomeAnimal)) {
                    filtrados.add(avistamento); // Adiciona o JSON corrigido à lista
                }
            }
        } catch (JSONException e) {
            e.printStackTrace(); // Mostra o erro no Logcat
        }
        return filtrados;
    }

    private int dpToPx(int dp) { // Converte dp to pixel
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void createAvistamentos(List<JSONObject> listAvistamentos){
        for (JSONObject avistamentos: listAvistamentos) {
            try {
                String user = avistamentos.getString("user");
                String location = avistamentos.getString("location");
                String date = avistamentos.getString("date");
                String photo = avistamentos.getString("photo").replace("\\/", "/");

                // Criar um novo ConstraintLayout
                // Supondo que você já tenha uma referência para o layout existente
                ConstraintLayout avistamentosLayout = findViewById(R.id.avistamentos);

                // Criar novo ConstraintLayout
                ConstraintLayout constraintLayout = new ConstraintLayout(this);
                constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                // Gerar um ID para o novo layout
                int newLayoutId = View.generateViewId();
                constraintLayout.setId(newLayoutId);


                // Criar LayoutParams para o novo ConstraintLayout
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                // Se já houver um layout existente, conectar o novo layout ao existente
                if (avistamentosLayout.getChildCount() > 1) {
                    // Obter o ID do último filho existente
                    View lastChild = avistamentosLayout.getChildAt(avistamentosLayout.getChildCount() - 1);
                    params.topToBottom = lastChild.getId(); // Conectar ao topo do último filho
                    params.topMargin = dpToPx(25);
                }

                // Configurar outras propriedades do LayoutParams conforme necessário
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;

                // Definir as LayoutParams para o novo ConstraintLayout
                constraintLayout.setLayoutParams(params);


                // Criar CardView
                CardView cardView = new CardView(this);
                cardView.setId(View.generateViewId());
                cardView.setCardBackgroundColor(Color.parseColor("#1B485F"));
                cardView.setRadius(dpToPx(15));


                ConstraintLayout.LayoutParams cardParams = new ConstraintLayout.LayoutParams(dpToPx(150), 0);
                cardParams.dimensionRatio = "1:1";
                cardParams.horizontalBias = 0.0f;
                cardParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                cardParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                cardParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                cardView.setLayoutParams(cardParams);


                // Criar ImageView dentro do CardView
                ImageView imageView = new ImageView(this);
                imageView.setId(View.generateViewId());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(R.drawable.anfibios);
                Glide.with(PagAnimais.this) // Adiciona imagem atraves do glide, pegando o URL
                            .load(photo)
                        .into(imageView);

                cardView.addView(imageView);


                // Criar TextView Nome
                TextView nomeTextView = new TextView(this);
                nomeTextView.setId(View.generateViewId());
                nomeTextView.setText(user);
                nomeTextView.setTextSize(20);
                nomeTextView.setTypeface(null, Typeface.BOLD);
                nomeTextView.setTextColor(Color.parseColor("#1B455B"));
                ConstraintLayout.LayoutParams nomeParams = new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                nomeParams.startToEnd = cardView.getId();
                nomeParams.topToTop = cardView.getId();
                nomeParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                nomeParams.leftMargin = dpToPx(10);
                nomeParams.topMargin = dpToPx(5);
                nomeParams.horizontalBias = 0.0f;
                nomeTextView.setLayoutParams(nomeParams);

                // Criar TextView Data
                TextView dataTextView = new TextView(this);
                dataTextView.setId(View.generateViewId());
                dataTextView.setText("Data: " + date);
                dataTextView.setTextSize(20);
                dataTextView.setTextColor(Color.parseColor("#1B455B"));
                ConstraintLayout.LayoutParams dataParams = new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                dataParams.startToEnd = cardView.getId();
                dataParams.bottomToBottom = cardView.getId();
                dataParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                dataParams.leftMargin = dpToPx(10);
                dataParams.bottomMargin = dpToPx(5);
                dataParams.horizontalBias = 0.0f;
                dataTextView.setLayoutParams(dataParams);


                // Criar TextView Local
                TextView localTextView = new TextView(this);
                localTextView.setId(View.generateViewId());
                localTextView.setText(location);
                localTextView.setTextSize(20);
                localTextView.setTextColor(Color.parseColor("#1B455B"));
                ConstraintLayout.LayoutParams localParams = new ConstraintLayout.LayoutParams(
                        0,
                        0
                );
                localParams.startToEnd = cardView.getId();
                localParams.topToBottom = nomeTextView.getId();
                localParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                localParams.bottomToTop = dataTextView.getId();
                localParams.leftMargin = dpToPx(10);
                localParams.topMargin = dpToPx(5);
                localParams.horizontalBias = 0.0f;
                localParams.verticalBias = 0.0f;
                localTextView.setLayoutParams(localParams);

// Adicionar views ao novo ConstraintLayout
                constraintLayout.addView(cardView);
                constraintLayout.addView(nomeTextView);
                constraintLayout.addView(dataTextView);
                constraintLayout.addView(localTextView);

// Adicionar o novo ConstraintLayout ao avistamentosLayout existente
                avistamentosLayout.addView(constraintLayout);

            } catch (Exception e) {
                Log.e("JSON_ERROR", "Erro ao converter avistamento para JSON: " , e);
            }
        }
    }


}
