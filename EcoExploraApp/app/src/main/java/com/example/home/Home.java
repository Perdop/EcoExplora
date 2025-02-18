package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.home.model.AnimaisExtintosModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Home extends AppCompatActivity {

    private List<AnimaisExtintosModel> animaisList;
    private String userUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        SharedPreferences userState = getSharedPreferences("userState", MODE_PRIVATE);
        boolean logado = userState.getBoolean("logado", false);
        String userName = userState.getString("user", "");

        getUserPhoto(userName);

        SharedPreferences.Editor editor = userState.edit();
        editor.putString("userPhoto", userUrl);
        editor.apply();
        String userPhoto = userState.getString("userPhoto", "");

        TextView loginBtn = findViewById(R.id.loginBtn);
        TextView userNameText = findViewById(R.id.userNameText);
        ImageButton profileButton = findViewById(R.id.profileButton);

        if(logado){
            loginBtn.setVisibility(View.GONE);
            userNameText.setText(userName + "!");
            Glide.with(this)
                    .load(userPhoto)
                    .error(R.drawable.profilebutton) // Imagem padrão em caso de erro
                    .into(profileButton);

        }
        animaisList = DataStorage.getInstance().getAnimaisList();

        // Verificando se a lista foi recebida corretamente
        if (animaisList != null) {
            // Aqui você pode tratar a lista de animais, como filtrar ou realizar outras operações
            // Exemplo de log para verificar o número de animais carregados
            System.out.println("Animais recebidos: " + animaisList.size());
        }

        // Configuração dos botões da Home

        ImageButton especiesButton = findViewById(R.id.fundoTop2);
        ImageButton plantasButton = findViewById(R.id.imagePlantas);
        ImageButton anfibiosButton = findViewById(R.id.imageAnfibios);
        ImageButton mamiferosButton = findViewById(R.id.imageMamiferos);
        ImageButton avesButton = findViewById(R.id.imageAves);
        ImageButton repteisButton = findViewById(R.id.imageRepteis);
        ImageButton peixesButton = findViewById(R.id.imagePeixes);
        ImageButton devButton = findViewById(R.id.fundoBottom2);

        // Navegação para a tela de login
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Cadastro.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Cadastro.class);
                startActivity(intent);
            }
        });

        // Navegação para a página de informações
        especiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Infopage.class);
                startActivity(intent);
            }
        });

        // Navegação para a MamiferosActivity
        mamiferosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui passamos a lista de animais para a próxima Activity
                Intent intent = new Intent(Home.this, Mamiferos.class);
                startActivity(intent);
            }
        });

        // Navegação para a AnfibiosActivity
        anfibiosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Anfibios.class);
                startActivity(intent);
            }
        });

        // Navegação para a PlantasActivity
        plantasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Plantas.class);
                startActivity(intent);
            }
        });

        // Navegação para a AvesActivity
        avesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Aves.class);
                startActivity(intent);
            }
        });

        // Navegação para a RepteisActivity
        repteisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Repteis.class);
                startActivity(intent);
            }
        });

        // Navegação para a PeixesActivity
        peixesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Peixes.class);
                startActivity(intent);
            }
        });

        // Navegação para a tela de desenvolvedores
        devButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Desenvolvedores.class);
                startActivity(intent);
            }
        });
    }


    private void getUserPhoto(String userName) {
        new Thread(() -> {
            try {
                // Sua lógica para obter a userUrl
                URL url = new URL("https://ecoexplora.onrender.com/getUserPhoto/" + userName);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("X-API-KEY", BuildConfig.API_KEY);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    userUrl = response.toString(); // Assume que a resposta é a URL da imagem
                    runOnUiThread(() -> loadImage(userUrl)); // Carregar a imagem na UI thread
                } else {
                    Log.d("UserPhotoResponse", "Erro na requisição: " + responseCode);
                }
                conn.disconnect();
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(Home.this, "Erro ao conectar à API.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void loadImage(String userUrl) {
        ImageButton profileButton = findViewById(R.id.profileButton);
        Glide.with(this)
                .load(userUrl)
                .error(R.drawable.profilebutton) // Imagem padrão em caso de erro
                .into(profileButton);
    }

}
