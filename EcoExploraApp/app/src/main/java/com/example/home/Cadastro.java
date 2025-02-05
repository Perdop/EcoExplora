package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.home.controller.FindUser;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class    Cadastro extends AppCompatActivity {

    int profileSelected = 0;
    String profileUrl;

    String user;
    String password;
    boolean dadosValidos;
    boolean userExists;

    public interface UserCallback {
        void onUserChecked(boolean exists);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificao de login para definir o layout
        SharedPreferences userState = getSharedPreferences("userState", MODE_PRIVATE);
//        SharedPreferences.Editor editor = userState.edit();
//        editor.putBoolean("logado", false); // Salva um número inteiro
//        editor.apply();

        boolean logado = userState.getBoolean("logado", false);

        if (logado) {
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



        editTextNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) { // Se perdeu o foco
                    user = editTextNome.getText().toString().trim();
                    verificarUser(user, new UserCallback() {
                        @Override
                        public void onUserChecked(boolean exists) {
                            runOnUiThread(() -> { // Executa na thread principal para atualizar UI
                                if (exists) {
                                    Toast.makeText(getApplicationContext(), "Usuário já existe! Digite outro nome.", Toast.LENGTH_SHORT).show();
                                    editTextNome.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E25D5D")));
                                } else{
                                    editTextNome.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                                }
                                if (Objects.equals(user, "")){
                                    Toast.makeText(Cadastro.this, "Usuário vazio, por favor digite algo", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });

        editTextSenha.setFilters(new InputFilter[] { new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    // Impede a inserção do dígito '5'
                    if (source.charAt(i) == ' ') {
                        Toast.makeText(Cadastro.this, "Proibido espaços na senha", Toast.LENGTH_SHORT).show();
                        return ""; // Retorna vazio para impedir a entrada
                    }
                }
                return null; // Permite a entrada
            }
        } });

        cadastrarButton.setOnClickListener(v -> {
            user = editTextNome.getText().toString().trim();
            password = editTextSenha.getText().toString();

            user = editTextNome.getText().toString().trim();
            verificarUser(user, new UserCallback() {
                @Override
                public void onUserChecked(boolean exists) {
                    runOnUiThread(() -> { // Executa na thread principal para atualizar UI
                        if (exists) {
                            Toast.makeText(getApplicationContext(), "Usuário já existe! Digite outro nome.", Toast.LENGTH_SHORT).show();
                            editTextNome.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E25D5D")));
                        } else{
                            editTextNome.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        }
                        if (Objects.equals(user, "")){
                            Toast.makeText(Cadastro.this, "Usuário vazio, por favor digite algo", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            if (Objects.equals(password, "")){
                Toast.makeText(Cadastro.this, "Digite uma senha", Toast.LENGTH_SHORT).show();
            }
            if (profileSelected == 0){
                Toast.makeText(Cadastro.this, "Selecione uma imagem", Toast.LENGTH_SHORT).show();
            }

            dadosValidos = !userExists && !Objects.equals(user, "") && !Objects.equals(password, "") && profileSelected != 0;

            if (dadosValidos){
                enviarCadastro(user, password, profileUrl);
            }
        });

        // Login


    }


    private void verificarUser(String nome, UserCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            userExists = FindUser.findUser(nome);
            callback.onUserChecked(userExists); // Chama o callback quando terminar
        });

        executor.shutdown(); // Fecha a thread após uso
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
                os.write(jsonParam.toString().getBytes(StandardCharsets.UTF_8));
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


