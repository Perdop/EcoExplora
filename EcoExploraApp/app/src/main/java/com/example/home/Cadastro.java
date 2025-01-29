package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.home.controller.FindUser;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class    Cadastro extends AppCompatActivity {

    int profileSelected;
    String profileUrl;

    String user;
    String password;
    boolean dadosValidos;
    boolean userExists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificao de login para definir o layout
        SharedPreferences userState = getSharedPreferences("userState", MODE_PRIVATE);
//        SharedPreferences.Editor editor = userState.edit();
//        editor.putBoolean("logado", false); // Salva um número inteiro
//        editor.apply();
        boolean logado = userState.getBoolean("logado", false);

        if (logado == true) {
            setContentView(R.layout.activity_logado);
        } else{
            setContentView(R.layout.activity_cadastro);
        }

        // Botao Voltar
        TextView setaVoltar = findViewById(R.id.setaVoltar);
        setaVoltar.setOnClickListener(v -> {
            onBackPressed();
        });

        // Botao login
        TextView textContaLogin = findViewById(R.id.textContaLogin);
        textContaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cadastro.this, Login.class);
                startActivity(intent);
            }
        });

        // Botao desenvolvedores
        ImageView fundoBottom = findViewById(R.id.fundoBottom2);
        fundoBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cadastro.this, Desenvolvedores.class);
                startActivity(intent);
            }
        });

        // Hover e selacao da imagem
        ImageView imageProfile1 = findViewById(R.id.imageProfile1);
        ImageView imageProfile2 = findViewById(R.id.imageProfile2);
        ImageView imageProfile3 = findViewById(R.id.imageProfile3);


        imageProfile1.setOnClickListener(v -> {
            profileSelected = 1;
            imageProfile1.setAlpha(1.0f);
            imageProfile2.setAlpha(0.6f);
            imageProfile3.setAlpha(0.6f);
            profileUrl = "https://res.cloudinary.com/dixozqlb4/image/upload/v1738181622/profileImgCrab_eejbvr.png";
        });
        imageProfile2.setOnClickListener(v -> {
            profileSelected = 2;
            imageProfile2.setAlpha(1.0f);
            imageProfile3.setAlpha(0.6f);
            imageProfile1.setAlpha(0.6f);
            profileUrl = "https://res.cloudinary.com/dixozqlb4/image/upload/v1738181623/profileImgDuck_mihp1x.png";
        });
        imageProfile3.setOnClickListener(v -> {
            profileSelected = 3;
            imageProfile3.setAlpha(1.0f);
            imageProfile2.setAlpha(0.6f);
            imageProfile1.setAlpha(0.6f);
            profileUrl = "https://res.cloudinary.com/dixozqlb4/image/upload/v1738181621/profileImgBear_rafydg.png";
        });

        // Botao Cadastrar
        EditText editTextNome = findViewById(R.id.editTextNome);
        EditText editTextSenha = findViewById(R.id.editTextSenha);
        Button cadastrarButton = findViewById(R.id.cadastrarButton);
        cadastrarButton.setOnClickListener(v -> {
            user = editTextNome.getText().toString();
            password = editTextSenha.getText().toString();
            verificarUser(user);
            if (userExists == false){
                dadosValidos = true;
                Log.d("teste", "onCreate: " + userExists);
            } else {
                dadosValidos = false;
                Toast.makeText(Cadastro.this, "Usuário já existe, mude o nome.", Toast.LENGTH_SHORT).show();
            }

            if (dadosValidos == true){
                enviarCadastro(user, password, profileUrl);
            }

        });

    }

    private void verificarUser(String nome){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            userExists = FindUser.findUser(nome);
            Log.d("teste2", "onCreate: " + userExists);
        });
    }

    private void enviarCadastro(String nome, String senha, String urlPhoto) {
        new Thread(() -> {
            try {
                // URL da API
                URL url = new URL("https://ecoexplora.onrender.com/addUser");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);

                // Criar o objeto JSON
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("user", nome);
                jsonParam.put("password", senha);
                jsonParam.put("userPhoto", urlPhoto);

                // Enviar o JSON
                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes("UTF-8"));
                os.close();

                // Verificar a resposta
                int responseCode = conn.getResponseCode();
                runOnUiThread(() -> {
                    if (responseCode == 200) {
                        Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        abrirTelaLogin(); // Abrir a tela de login após cadastro
                    } else {
                        Toast.makeText(Cadastro.this, "Erro ao cadastrar. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Cadastro.this, "Erro ao conectar à API.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void abrirTelaLogin() {
        Intent intent = new Intent(Cadastro.this, Login.class);
        startActivity(intent);
        finish();
    }

}

//
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
//            // Para versões mais recentes do Android
//            getWindow().setStatusBarColor(Color.parseColor("#8FBE9E")); // Definindo a barra de status como transparente
//        } else {
//            // Para versões anteriores do Android
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//
//        TextView voltarLogin = findViewById(R.id.setaAnimais);
//        ImageButton devsLogin = findViewById(R.id.fundoBottom2);
//        TextView toLogin = findViewById(R.id.textContaLogin);
//
//        editTextNome = findViewById(R.id.editTextNome);
//        editTextSenha = findViewById(R.id.editTextSenha);
//        acessarButton = findViewById(R.id.acessarButton);
//        imageProfile1 = findViewById(R.id.imageProfile1);
//        imageProfile2 = findViewById(R.id.imageProfile2);
//        imageProfile3 = findViewById(R.id.imageProfile3);
//
//        cardPerfil1 = findViewById(R.id.cardPerfil1);
//        cardPerfil2 = findViewById(R.id.cardPerfil2);
//        cardPerfil3 = findViewById(R.id.cardPerfil3);
//
//        // Configurar seleção de imagem de perfil
//        imageProfile1.setOnClickListener(view -> selecionarPerfil("Perfil 1"));
//        imageProfile2.setOnClickListener(view -> selecionarPerfil("Perfil 2"));
//        imageProfile3.setOnClickListener(view -> selecionarPerfil("Perfil 3"));
//
//        // Listener do botão "ACESSAR"
//        acessarButton.setOnClickListener(view -> {
//            String nome = editTextNome.getText().toString().trim();
//            String senha = editTextSenha.getText().toString().trim();
//
//            if (nome.isEmpty() || senha.isEmpty()) {
//                Toast.makeText(Cadastro.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
//            } else {
//                enviarCadastro(nome, senha);
//            }
//        });
//
//        voltarLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Cadastro.this, Home.class);
//                startActivity(intent);
//            }
//        });
//        devsLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Cadastro.this, Desenvolvedores.class);
//                startActivity(intent);
//            }
//        });
//        toLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Cadastro.this, Login.class);
//                startActivity(intent);
//            }
//        });
//
//        imageProfile1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setImageSelected(cardPerfil1);
//            }
//        });
//        imageProfile2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setImageSelected(cardPerfil2);
//            }
//        });
//        imageProfile3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setImageSelected(cardPerfil3);
//            }
//        });
//    }
//
//    private void setImageSelected(CardView selectedCard) {
//        // Remove o efeito de seleção dos outros cards (remover borda)
//        cardPerfil1.setCardBackgroundColor(Color.TRANSPARENT);
//        cardPerfil2.setCardBackgroundColor(Color.TRANSPARENT);
//        cardPerfil3.setCardBackgroundColor(Color.TRANSPARENT);
//
//        // Remove a borda dos outros cards (volta para o estado inicial)
//        cardPerfil1.setBackgroundResource(0); // Remove o fundo do card
//        cardPerfil2.setBackgroundResource(0); // Remove o fundo do card
//        cardPerfil3.setBackgroundResource(0); // Remove o fundo do card
//
//        // Aplica a borda azul clara no card selecionado
//        selectedCard.setCardBackgroundColor(Color.parseColor("#ADD8E6")); // Cor de fundo azul claro
//        selectedCard.setBackgroundResource(R.drawable.card_border); // Aplica a borda do drawable
//    }
//
//
//    private void selecionarPerfil(String perfil) {
//        Toast.makeText(this, "Perfil selecionado: " + perfil, Toast.LENGTH_SHORT).show();
//    }
//
//    private void enviarCadastro(String nome, String senha) {
//        new Thread(() -> {
//            try {
//                // URL da API
//                URL url = new URL("https://testspring-g2nh.onrender.com/add");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                conn.setDoOutput(true);
//
//                // Criar o objeto JSON
//                JSONObject jsonParam = new JSONObject();
//                jsonParam.put("user", nome);
//                jsonParam.put("password", senha);
//
//                // Enviar o JSON
//                OutputStream os = conn.getOutputStream();
//                os.write(jsonParam.toString().getBytes("UTF-8"));
//                os.close();
//
//                // Verificar a resposta
//                int responseCode = conn.getResponseCode();
//                runOnUiThread(() -> {
//                    if (responseCode == 200) {
//                        Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
//                        abrirTelaLogin(); // Abrir a tela de login após cadastro
//                    } else {
//                        Toast.makeText(Cadastro.this, "Erro ao cadastrar. Tente novamente.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                conn.disconnect();
//            } catch (Exception e) {
//                e.printStackTrace();
//                runOnUiThread(() -> Toast.makeText(Cadastro.this, "Erro ao conectar à API.", Toast.LENGTH_SHORT).show());
//            }
//        }).start();
//    }
//
//    private void abrirTelaLogin() {
//        Intent intent = new Intent(Cadastro.this, Login.class);
//        startActivity(intent);
//        finish(); // Finaliza a Activity atual
//    }

