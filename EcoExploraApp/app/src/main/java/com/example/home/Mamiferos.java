package com.example.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.example.home.model.AnimaisExtintosModel;

import java.util.List;

public class Mamiferos extends AppCompatActivity {

    private int dpToPx(int dp) { // Converte dp to pixel
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mamiferos);

        TextView voltar = findViewById(R.id.setaVoltar);
        ConstraintLayout constraintLayout1 = findViewById(R.id.constraintLayout1);
        ConstraintLayout constraintLayout2 = findViewById(R.id.constraintLayout2);

        Intent intent = getIntent(); // Obtem lista de animais extintos
        List<AnimaisExtintosModel> animaisList = intent.getParcelableArrayListExtra("ANIMAIS_LIST");

        voltar.setOnClickListener(v -> { // Configura o botao de voltar
            onBackPressed();
        });

        int mamiferos = 0;
        if (animaisList != null) { // Cria botoes
            for (int i = 0; i < animaisList.size(); i++) {
                AnimaisExtintosModel animal = animaisList.get(i);

                if (animal.getAnimalType() == 1) { // Filtra mamíferos
                    mamiferos++; // Contador de mamiferos
                    // Cria e configura cardView
                    CardView cardView = new CardView(Mamiferos.this);
                    cardView.setId(View.generateViewId());
                    cardView.setCardBackgroundColor(Color.parseColor("#1B485F"));
                    cardView.setRadius(dpToPx(10));

                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            0
                    );
                    layoutParams.setMarginEnd(dpToPx(10));
                    cardView.setLayoutParams(layoutParams);

                    ConstraintLayout targetLayout = (mamiferos % 2 == 0) ? constraintLayout2 : constraintLayout1; // Define qual constraint o botao sera adicionado
                    targetLayout.addView(cardView);

                    // Configura cardView ao constraintLayout
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(targetLayout);

                    constraintSet.setDimensionRatio(cardView.getId(), "1:1");
                    constraintSet.connect(cardView.getId(), ConstraintSet.START, targetLayout.getId(), ConstraintSet.START, 0);
                    constraintSet.connect(cardView.getId(), ConstraintSet.END, targetLayout.getId(), ConstraintSet.END, 0);

                    // Configura as constraints do botão de acordo com a posição
                    if (targetLayout.getChildCount() == 1) {
                        constraintSet.connect(cardView.getId(), ConstraintSet.TOP, targetLayout.getId(), ConstraintSet.TOP, 0);
                    } else {
                        CardView previousButton = (CardView) targetLayout.getChildAt(targetLayout.getChildCount() - 2);
                        constraintSet.connect(cardView.getId(), ConstraintSet.TOP, previousButton.getId(), ConstraintSet.BOTTOM, dpToPx(16));
                    }

                    constraintSet.applyTo(targetLayout);

                    cardView.setOnClickListener(v -> {
                        // Passa os dados para a PagAnimaisActivity
                        Intent detailIntent = new Intent(Mamiferos.this, PagAnimais.class);
                        detailIntent.putExtra("NOME_ANIMAL", animal.getName());
                        detailIntent.putExtra("DESCRICAO_ANIMAL", animal.getAbout());
                        detailIntent.putExtra("ESTADO_ANIMAL", animal.getState());
                        detailIntent.putExtra("EXISTENTES_ANIMAL", animal.getLiving());
                        detailIntent.putExtra("IMG_ANIMAL", animal.getAnimalPhoto());
                        startActivity(detailIntent);
                    });

                    // Cria, configura e adiciona constraintLayout o cardView
                    ConstraintLayout constraintLayout = new ConstraintLayout(Mamiferos.this);
                    CardView.LayoutParams constraintLayoutParams = new CardView.LayoutParams(
                            CardView.LayoutParams.MATCH_PARENT,
                            CardView.LayoutParams.MATCH_PARENT
                    );
                    constraintLayout.setLayoutParams(constraintLayoutParams);
                    cardView.addView(constraintLayout);

                    // Cria, configura e adiciona TextView ao constraintLayout
                    TextView textView = new TextView(Mamiferos.this);
                    textView.setId(View.generateViewId());
                    textView.setText(animal.getName());
                    textView.setTextSize(dpToPx(6));
                    Typeface typeface = ResourcesCompat.getFont(this, R.font.amaranth);
                    textView.setTypeface(typeface);
                    textView.setTextColor(Color.WHITE);

                    ConstraintLayout.LayoutParams textLayoutParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    textView.setLayoutParams(textLayoutParams);
                    constraintLayout.addView(textView);

                    ConstraintSet textViewConstraintSet = new ConstraintSet();
                    textViewConstraintSet.clone(constraintLayout);
                    textViewConstraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
                    textViewConstraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
                    textViewConstraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dpToPx(3));
                    textViewConstraintSet.applyTo(constraintLayout);

                    // Cria, configura e adiciona imageView ao constraintLayout

                    ImageView imageView = new ImageView(Mamiferos.this);
                    imageView.setId(View.generateViewId()); // Define um ID único para o ImageView
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    ConstraintLayout.LayoutParams imageViewLayoutParams = new ConstraintLayout.LayoutParams(
                            0,
                            0
                    );
                    imageView.setLayoutParams(imageViewLayoutParams);
                    constraintLayout.addView(imageView);

                    ConstraintSet imageViewConstraintSet = new ConstraintSet();
                    imageViewConstraintSet.clone(constraintLayout);

                    imageViewConstraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
                    imageViewConstraintSet.connect(imageView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
                    imageViewConstraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                    imageViewConstraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, textView.getId(), ConstraintSet.TOP, dpToPx(3));
                    imageViewConstraintSet.applyTo(constraintLayout);

                    Glide.with(Mamiferos.this) // Adiciona imagem atraves do glide, pegando o URL
                            .load(animal.getAnimalPhoto())
                            .into(imageView);

                }
            }
        }
    }
}
