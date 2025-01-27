package com.example.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.home.model.AnimaisExtintosModel;

import java.util.List;

public class Mamiferos extends AppCompatActivity {

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mamiferos);

        // Configuração das barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Encontrando os elementos do layout
        TextView voltar = findViewById(R.id.setaAnimais);
        ImageButton perfilAnimais = findViewById(R.id.profileButtonMamiferos);
        ConstraintLayout constraintLayout1 = findViewById(R.id.constraintLayout1); // ConstraintLayout onde os botões serão adicionados
        ConstraintLayout constraintLayout2 = findViewById(R.id.constraintLayout2); // Segundo ConstraintLayout

        // Obtendo a lista de animais da HomeActivity
        Intent intent = getIntent();
        List<AnimaisExtintosModel> animaisList = intent.getParcelableArrayListExtra("ANIMAIS_LIST");

        // Verificando se a lista foi recebida corretamente
        if (animaisList != null && !animaisList.isEmpty()) {
            Log.d("Mamiferos", "Número de animais recebidos: " + animaisList.size());
        } else {
            Log.e("Mamiferos", "A lista de animais está vazia ou a requisição falhou.");
        }

        // Configuração do botão voltar
        voltar.setOnClickListener(v -> {
            onBackPressed();
        });

        // Configuração do botão de perfil (Login)
        perfilAnimais.setOnClickListener(v -> {
            Intent intentPerfil = new Intent(Mamiferos.this, Cadastro.class);
            startActivity(intentPerfil);
        });

        // Se a lista de animais não estiver vazia, começa a criar os botões
        if (animaisList != null) {
            int topMargin = 20; // Inicializa a margem superior para o primeiro botão

            // Filtra e cria botões para os mamíferos (classe 1)
            for (int i = 0; i < animaisList.size(); i++) {
                AnimaisExtintosModel animal = animaisList.get(i);

                // Verifica se o animal é um mamífero (classe 1)
                if (animal.getAnimalType() == 1) {
                    Log.d("Mamiferos", "Adicionando mamífero: " + animal.getName());



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

                    ConstraintLayout targetLayout = (i % 2 == 0) ? constraintLayout1 : constraintLayout2;
                    targetLayout.addView(cardView);

                    // Define as constraints do botão
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(targetLayout);

                    constraintSet.setDimensionRatio(cardView.getId(), "1:1");
                    constraintSet.connect(cardView.getId(), ConstraintSet.START, targetLayout.getId(), ConstraintSet.START, 0);
                    constraintSet.connect(cardView.getId(), ConstraintSet.END, targetLayout.getId(), ConstraintSet.END, 0);

                    // Configura as constraints do botão de acordo com a posição

                    if (i == 0) {
                        constraintSet.connect(cardView.getId(), ConstraintSet.TOP, targetLayout.getId(), ConstraintSet.TOP, 0);
                    }else if (i == 1) {
                        constraintSet.connect(cardView.getId(), ConstraintSet.TOP, targetLayout.getId(), ConstraintSet.TOP, 0);
                    } else {
                        CardView previousButton = (CardView) targetLayout.getChildAt(targetLayout.getChildCount() - 2);
                        constraintSet.connect(cardView.getId(), ConstraintSet.TOP, previousButton.getId(), ConstraintSet.BOTTOM, dpToPx(16));
                    }

                    constraintSet.applyTo(targetLayout);

                    cardView.setOnClickListener(v -> {
                        Log.d("Mamiferos", "Botão clicado para o animal: " + animal.getName());

                        // Passa os dados para a PagAnimaisActivity (detalhes do animal)
                        Intent detailIntent = new Intent(Mamiferos.this, PagAnimais.class);
                        detailIntent.putExtra("NOME_ANIMAL", animal.getName());
                        detailIntent.putExtra("DESCRICAO_ANIMAL", animal.getAbout());
                        detailIntent.putExtra("ESTADO_ANIMAL", animal.getState());
                        detailIntent.putExtra("EXISTENTES_ANIMAL", animal.getLiving());
                        detailIntent.putExtra("IMG_ANIMAL", animal.getAnimalPhoto());
                        startActivity(detailIntent);
                    });

                    ConstraintLayout constraintLayout = new ConstraintLayout(Mamiferos.this);
                    CardView.LayoutParams constraintLayoutParams = new CardView.LayoutParams(
                            CardView.LayoutParams.MATCH_PARENT,
                            CardView.LayoutParams.MATCH_PARENT
                    );
                    constraintLayout.setLayoutParams(constraintLayoutParams);
                    cardView.addView(constraintLayout);

                    // Criação do TextView
                    TextView textView = new TextView(Mamiferos.this);
                    textView.setId(View.generateViewId());
                    textView.setText(animal.getName());
                    textView.setTextSize(dpToPx(6));
                    textView.setTextColor(Color.WHITE); // Cor do texto

                    ConstraintLayout.LayoutParams textLayoutParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    textView.setLayoutParams(textLayoutParams);
                    constraintLayout.addView(textView);

                    // Cria um ConstraintSet para definir as constraints
                    ConstraintSet textViewConstraintSet = new ConstraintSet();
                    textViewConstraintSet.clone(constraintLayout); // Clona o ConstraintLayout para usar as constraints existentes
                    textViewConstraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
                    textViewConstraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
                    textViewConstraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dpToPx(3));
                    textViewConstraintSet.applyTo(constraintLayout);

                    //

                    ImageView imageView = new ImageView(Mamiferos.this);
                    imageView.setId(View.generateViewId()); // Define um ID único para o ImageView
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    ConstraintLayout.LayoutParams imageViewLayoutParams = new ConstraintLayout.LayoutParams(
                            0, // largura
                            0  // altura
                    );
                    imageView.setLayoutParams(imageViewLayoutParams);
                    constraintLayout.addView(imageView);

                    ConstraintSet imageViewConstraintSet = new ConstraintSet();
                    imageViewConstraintSet.clone(constraintLayout);

                    imageViewConstraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
                    imageViewConstraintSet.connect(imageView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
                    imageViewConstraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                    imageViewConstraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, textView.getId(), ConstraintSet.TOP, dpToPx(3)); // Margem inferior

                    imageViewConstraintSet.applyTo(constraintLayout);


                    Glide.with(Mamiferos.this)
                            .load(animal.getAnimalPhoto()) // URL ou recurso da imagem
                            .into(imageView);





//                    // Cria um novo botão programaticamente
//                    Button button = new Button(Mamiferos.this);
//                    button.setId(View.generateViewId());
//                    button.setText(animal.getName());
//                    button.setTextColor(getResources().getColor(android.R.color.white));
//                    button.setTextSize(14); // Ajusta o tamanho do texto
//                    button.setAllCaps(false); // Texto normal, sem letras maiúsculas
//                    button.setBackground(ContextCompat.getDrawable(Mamiferos.this, R.drawable.btn_animaltype_extinctanimals));
//                    button.setPadding(0,0,0,16);
//
//
//
//                    // Define o layout do botão
//                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
//                            ConstraintLayout.LayoutParams.MATCH_PARENT,
//                            ConstraintLayout.LayoutParams.WRAP_CONTENT
//                    );
//                    layoutParams.setMargins(20, topMargin, 20, 20);
//                    button.setLayoutParams(layoutParams);
//
//                    // Carrega a imagem no botão usando Glide
//                    Glide.with(Mamiferos.this)
//                            .load(animal.getAnimalPhoto()) // URL ou recurso da imagem
//                            .into(new CustomViewTarget<Button, Drawable>(button) {
//                                @Override
//                                protected void onResourceCleared(Drawable placeholder) {
//                                    button.setCompoundDrawablesWithIntrinsicBounds(null, placeholder, null, null);
//                                }
//
//                                @Override
//                                public void onLoadFailed(Drawable errorDrawable) {
//                                    button.setCompoundDrawablesWithIntrinsicBounds(null, errorDrawable, null, null);
//                                }
//
//                                @Override
//                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                                    button.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null);
//                                }
//                            });
//
//                    // Seleciona qual ConstraintLayout vai adicionar o botão (alternando entre constraintLayout1 e constraintLayout2)
//                    ConstraintLayout targetLayout = (i % 2 == 0) ? constraintLayout1 : constraintLayout2;
//                    targetLayout.addView(button);
//
//                    // Define as constraints do botão
//                    ConstraintSet constraintSet = new ConstraintSet();
//                    constraintSet.clone(targetLayout);
//
//                    // Configura as constraints do botão de acordo com a posição
//                    if (targetLayout.getChildCount() == 1) {
//                        constraintSet.connect(button.getId(), ConstraintSet.TOP, targetLayout.getId(), ConstraintSet.TOP, 100);
//                    } else {
//                        Button previousButton = (Button) targetLayout.getChildAt(targetLayout.getChildCount() - 2);
//                        constraintSet.connect(button.getId(), ConstraintSet.TOP, previousButton.getId(), ConstraintSet.BOTTOM, 20);
//                    }
//
//                    // Alinha os botões ao centro (horizontalmente)
//                    constraintSet.connect(button.getId(), ConstraintSet.START, targetLayout.getId(), ConstraintSet.START, 20);
//                    constraintSet.connect(button.getId(), ConstraintSet.END, targetLayout.getId(), ConstraintSet.END, 20);
//
//                    // Aplica as constraints ao layout
//                    constraintSet.applyTo(targetLayout);
//
//                    // Configura o OnClickListener para abrir os detalhes do animal
//                    button.setOnClickListener(v -> {
//                        Log.d("Mamiferos", "Botão clicado para o animal: " + animal.getName());
//
//                        // Passa os dados para a PagAnimaisActivity (detalhes do animal)
//                        Intent detailIntent = new Intent(Mamiferos.this, PagAnimais.class);
//                        detailIntent.putExtra("NOME_ANIMAL", animal.getName());
//                        detailIntent.putExtra("DESCRICAO_ANIMAL", animal.getAbout());
//                        detailIntent.putExtra("ESTADO_ANIMAL", animal.getState());
//                        detailIntent.putExtra("EXISTENTES_ANIMAL", animal.getLiving());
//                        detailIntent.putExtra("IMG_ANIMAL", animal.getAnimalPhoto());
//                        startActivity(detailIntent);
//                    });
                }
            }
        }
    }
}
