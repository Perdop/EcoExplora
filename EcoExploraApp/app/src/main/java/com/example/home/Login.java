package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView voltarLogin = findViewById(R.id.setaVoltar);
        ImageButton devsLogin = findViewById(R.id.fundoBottom21);
        TextView toRegistro = findViewById(R.id.textContaLogin1);
        Button loginBtn = findViewById(R.id.acessarButton1);
        EditText editTextUser = findViewById(R.id.editTextNome1);
        EditText editTextPassword = findViewById(R.id.editTextSenha1);

        voltarLogin.setOnClickListener(v -> {
            onBackPressed();
        });
        devsLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Desenvolvedores.class);
                startActivity(intent);
            }
        });
        toRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
            }
        });



        loginBtn.setOnClickListener(v -> {
            String username = editTextUser.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (username.isEmpty()){
                Toast.makeText(this, "Por favor, insira um nome de usuário.", Toast.LENGTH_SHORT).show();
            }
            if(password.isEmpty()){
                Toast.makeText(this, "Por favor, insira uma senha", Toast.LENGTH_SHORT).show();
            }

            loginUser(username, password);
        });
    }


    private void loginUser(String nome, String senha) {
        new Thread(() -> {
            try {
                // URL da API
                URL url = new URL("https://ecoexplora.onrender.com/loginUser");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("X-API-KEY", BuildConfig.API_KEY);
                conn.setDoOutput(true);

                // Criar o objeto JSON
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("user", nome);
                jsonParam.put("password", senha);

                // Enviar o JSON
                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes(StandardCharsets.UTF_8));
                os.close();

                // Verificar resposta
                int responseCode = conn.getResponseCode();
                InputStream is = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String responseMessage = response.toString();
                runOnUiThread(() -> {
                    if (responseCode == 200) {
                        Toast.makeText(Login.this, responseMessage, Toast.LENGTH_SHORT).show();
                        // Salvando usuario
                        SharedPreferences userState = getSharedPreferences("userState", MODE_PRIVATE);
                        SharedPreferences.Editor editor = userState.edit();
                        editor.putBoolean("logado", true); // Salva um número inteiro
                        editor.putString("user", nome);
                        editor.apply();
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, responseMessage, Toast.LENGTH_SHORT).show();
                    }
                });

                conn.disconnect();
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(Login.this, "Erro ao conectar à API.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }



}