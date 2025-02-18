package com.example.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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

public class Plantas extends AppCompatActivity {

    private int dpToPx(int dp) { // Converte dp to pixel
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plantas);

        TextView voltar = findViewById(R.id.setaVoltar);
        ConstraintLayout constraintLayout1 = findViewById(R.id.constraintLayout1);
        ConstraintLayout constraintLayout2 = findViewById(R.id.constraintLayout2);

        List<AnimaisExtintosModel> animaisList = DataStorage.getInstance().getAnimaisList();

        voltar.setOnClickListener(v -> { // Configura o botao de voltar
            onBackPressed();
        });


        int plantas = 0;
        if (animaisList != null) { // Cria botoes
            for (int i = 0; i < animaisList.size(); i++) {
                AnimaisExtintosModel animal = animaisList.get(i);

                if (animal.getAnimalType() == 5) { // Filtra Plantas
                    plantas++; // Contador de plantas
                    Log.d("TAG", "onCreate: "+ plantas);
                    // Cria e configura cardView
                    CardView cardView = new CardView(Plantas.this);
                    cardView.setId(View.generateViewId());
                    cardView.setCardBackgroundColor(Color.parseColor("#1B485F"));
                    cardView.setRadius(dpToPx(10));

                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            0
                    );
                    layoutParams.setMarginEnd(dpToPx(10));
                    cardView.setLayoutParams(layoutParams);

                    ConstraintLayout targetLayout = (plantas % 2 == 0) ? constraintLayout2 : constraintLayout1; // Define qual constraint o botao sera adicionado
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

                    // Cria, configura e adiciona constraintLayout o cardView
                    ConstraintLayout constraintLayout = new ConstraintLayout(Plantas.this);
                    CardView.LayoutParams constraintLayoutParams = new CardView.LayoutParams(
                            CardView.LayoutParams.MATCH_PARENT,
                            CardView.LayoutParams.MATCH_PARENT
                    );
                    constraintLayout.setLayoutParams(constraintLayoutParams);
                    cardView.addView(constraintLayout);

                    // Cria, configura e adiciona TextView ao constraintLayout
                    TextView textView = new TextView(Plantas.this);
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

                    ImageView imageView = new ImageView(Plantas.this);
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

                    Glide.with(Plantas.this) // Adiciona imagem atraves do glide, pegando o URL
                            .load(animal.getAnimalPhoto())
                            .into(imageView);

                }
            }
        }
    }
}
