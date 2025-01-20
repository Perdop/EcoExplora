package com.example.home;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;
public class PagAnimais extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pag_animais);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recebe os dados passados pela Intent
        String nomeAnimal = getIntent().getStringExtra("NOME_ANIMAL");
        String descricaoAnimal = getIntent().getStringExtra("DESCRICAO_ANIMAL");
        String estadoAnimal = getIntent().getStringExtra("ESTADO_ANIMAL");
        String existentesAnimal = String.valueOf(getIntent().getIntExtra("EXISTENTES_ANIMAL", 0));  // Converte o inteiro para string
        String imgAnimal = getIntent().getStringExtra("IMG_ANIMAL");
        String tipoAnimal = getIntent().getStringExtra("TIPO_ANIMAL");  // Novo parâmetro para determinar se é ave ou mamífero

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
        ImageButton profileAnimal = findViewById(R.id.profileButtonAnimais);
        TextView animaisVoltar = findViewById(R.id.setaAnimais);

        profileAnimal.setOnClickListener(v -> {
            Intent intent = new Intent(PagAnimais.this, Cadastro.class);
            startActivity(intent);
        });

        // Alterando o comportamento do botão de voltar para considerar o tipo do animal
        animaisVoltar.setOnClickListener(v -> {
            // Chama o método onBackPressed(), que simula o comportamento do botão "voltar" do celular
            onBackPressed();
        });




    }
}
