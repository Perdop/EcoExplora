package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.home.model.AnimaisExtintosModel;

import java.util.List;

public class Home extends AppCompatActivity {

    private List<AnimaisExtintosModel> animaisList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Configuração das barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recuperando a lista de animais passada pela MainActivity
        Intent intent = getIntent();
        animaisList = intent.getParcelableArrayListExtra("ANIMAIS_LIST");

        // Verificando se a lista foi recebida corretamente
        if (animaisList != null) {
            // Aqui você pode tratar a lista de animais, como filtrar ou realizar outras operações
            // Exemplo de log para verificar o número de animais carregados
            System.out.println("Animais recebidos: " + animaisList.size());
        }

        // Configuração dos botões da Home
        ImageButton profileButton = findViewById(R.id.profileButton);
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
                intent.putParcelableArrayListExtra("ANIMAIS_LIST", new java.util.ArrayList<>(animaisList));
                startActivity(intent);
            }
        });

        // Navegação para a AnfibiosActivity
        anfibiosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Anfibios.class);
                intent.putParcelableArrayListExtra("ANIMAIS_LIST", new java.util.ArrayList<>(animaisList));
                startActivity(intent);
            }
        });

        // Navegação para a PlantasActivity
        plantasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Plantas.class);
                intent.putParcelableArrayListExtra("ANIMAIS_LIST", new java.util.ArrayList<>(animaisList));
                startActivity(intent);
            }
        });

        // Navegação para a AvesActivity
        avesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Aves.class);
                intent.putParcelableArrayListExtra("ANIMAIS_LIST", new java.util.ArrayList<>(animaisList));
                startActivity(intent);
            }
        });

        // Navegação para a RepteisActivity
        repteisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Repteis.class);
                intent.putParcelableArrayListExtra("ANIMAIS_LIST", new java.util.ArrayList<>(animaisList));
                startActivity(intent);
            }
        });

        // Navegação para a PeixesActivity
        peixesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Peixes.class);
                intent.putParcelableArrayListExtra("ANIMAIS_LIST", new java.util.ArrayList<>(animaisList));
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
}
