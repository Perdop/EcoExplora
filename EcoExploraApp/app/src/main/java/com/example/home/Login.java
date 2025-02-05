package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import com.example.home.controller.FindUser;
import com.example.home.controller.GetUser;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {
    private String userInfo;
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

        String username = editTextUser.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        editTextUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!username.isEmpty() && !password.isEmpty()) {
                        // Executa a busca do usuário em uma nova thread
                        new Thread(() -> {
                            userInfo = GetUser.getUser(username);
                            runOnUiThread(() -> {
                                // Exibe a resposta na TextView ou em um Toast

                            });
                        }).start();
                    }
                }
            }
        });

        loginBtn.setOnClickListener(v -> {
            if (userInfo == ""){
                Toast.makeText(this, "Usuário não existe", Toast.LENGTH_SHORT).show();
            }
            if (username.isEmpty()){
                Toast.makeText(this, "Por favor, insira um nome de usuário.", Toast.LENGTH_SHORT).show();
            }
            if(password.isEmpty()){
                Toast.makeText(this, "Por favor, insira uma senha", Toast.LENGTH_SHORT).show();
            }
            if(user)
            Log.d("UserTest", "onCreate: " + userInfo);
        });



//        // Salvando usuario
//        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
//        SharedPreferences.Editor editor = user.edit();
//        editor.putBoolean("logado", false); // Salva um número inteiro
//        editor.apply();

    }

}