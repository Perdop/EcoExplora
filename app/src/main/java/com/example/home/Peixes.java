package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.home.model.AnimaisExtintosModel;

import java.util.List;

public class Peixes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_peixes);

        // Configuração das barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Encontrando os elementos do layout
        TextView voltar = findViewById(R.id.setaAnimais);
        ImageButton perfilAnimais = findViewById(R.id.profileButtonPeixes);
        ConstraintLayout constraintLayout1 = findViewById(R.id.constraintLayout1); // ConstraintLayout onde os botões serão adicionados
        ConstraintLayout constraintLayout2 = findViewById(R.id.constraintLayout2); // Segundo ConstraintLayout

        // Obtendo a lista de animais da HomeActivity
        Intent intent = getIntent();
        List<AnimaisExtintosModel> animaisList = intent.getParcelableArrayListExtra("ANIMAIS_LIST");

        // Verificando se a lista foi recebida corretamente
        if (animaisList != null && !animaisList.isEmpty()) {
            Log.d("Peixes", "Número de animais recebidos: " + animaisList.size());
        } else {
            Log.e("Peixes", "A lista de animais está vazia ou a requisição falhou.");
        }

        // Configuração do botão voltar
        voltar.setOnClickListener(v -> {
            onBackPressed();
        });

        // Configuração do botão de perfil (Login)
        perfilAnimais.setOnClickListener(v -> {
            Intent intentPerfil = new Intent(Peixes.this, Cadastro.class);
            startActivity(intentPerfil);
        });

        // Se a lista de animais não estiver vazia, começa a criar os botões para peixes
        if (animaisList != null) {
            int topMargin = 20; // Inicializa a margem superior para o primeiro botão

            // Filtra e cria botões para os peixes (classe 6)
            for (int i = 0; i < animaisList.size(); i++) {
                AnimaisExtintosModel animal = animaisList.get(i);

                // Verifica se o animal é um peixe (classe 6)
                if (animal.getClasse() == 6) {
                    Log.d("Peixes", "Adicionando peixe: " + animal.getNome());

                    // Cria um novo botão programaticamente
                    Button button = new Button(Peixes.this);
                    button.setId(View.generateViewId());
                    button.setText(animal.getNome());
                    button.setBackgroundColor(getResources().getColor(android.R.color.white));
                    button.setTextColor(getResources().getColor(android.R.color.black));

                    // Define o layout do botão
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(20, topMargin, 20, 20);
                    button.setLayoutParams(layoutParams);

                    // Seleciona qual ConstraintLayout vai adicionar o botão (alternando entre constraintLayout1 e constraintLayout2)
                    ConstraintLayout targetLayout = (i % 2 == 0) ? constraintLayout1 : constraintLayout2;
                    targetLayout.addView(button);

                    // Define as constraints do botão
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(targetLayout);

                    // Configura as constraints do botão de acordo com a posição
                    if (targetLayout.getChildCount() == 1) {
                        constraintSet.connect(button.getId(), ConstraintSet.TOP, targetLayout.getId(), ConstraintSet.TOP, 100);
                    } else {
                        Button previousButton = (Button) targetLayout.getChildAt(targetLayout.getChildCount() - 2);
                        constraintSet.connect(button.getId(), ConstraintSet.TOP, previousButton.getId(), ConstraintSet.BOTTOM, 20);
                    }

                    // Alinha os botões ao centro (horizontalmente)
                    constraintSet.connect(button.getId(), ConstraintSet.START, targetLayout.getId(), ConstraintSet.START, 20);
                    constraintSet.connect(button.getId(), ConstraintSet.END, targetLayout.getId(), ConstraintSet.END, 20);

                    // Aplica as constraints ao layout
                    constraintSet.applyTo(targetLayout);

                    // Configura o OnClickListener para abrir os detalhes do animal
                    button.setOnClickListener(v -> {
                        Log.d("Peixes", "Botão clicado para o peixe: " + animal.getNome());

                        // Passa os dados para a PagAnimaisActivity (detalhes do animal)
                        Intent detailIntent = new Intent(Peixes.this, PagAnimais.class);
                        detailIntent.putExtra("NOME_ANIMAL", animal.getNome());
                        detailIntent.putExtra("DESCRICAO_ANIMAL", animal.getSobre());
                        detailIntent.putExtra("ESTADO_ANIMAL", animal.getEstado());
                        detailIntent.putExtra("EXISTENTES_ANIMAL", animal.getExistentes());
                        detailIntent.putExtra("IMG_ANIMAL", animal.getImg());
                        startActivity(detailIntent);
                    });

                    // Aumenta a margem superior para o próximo botão
                    topMargin += 100; // Ajuste para evitar sobreposição
                }
            }
        }
    }
}
