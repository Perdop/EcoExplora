package com.example.home;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.Manifest;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;
public class PagAnimais extends AppCompatActivity {
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pag_animais);

        // Pede permissao da camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }

        // Botao que chama a camera
        ImageButton camBtn = findViewById(R.id.camBtn);
        camBtn.setOnClickListener(v -> {
            abrirCamera();
        });

        // Recebe os dados passados pela Intent
        String nomeAnimal = getIntent().getStringExtra("NOME_ANIMAL");
        String descricaoAnimal = getIntent().getStringExtra("DESCRICAO_ANIMAL");
        String estadoAnimal = getIntent().getStringExtra("ESTADO_ANIMAL");
        String existentesAnimal = String.valueOf(getIntent().getIntExtra("EXISTENTES_ANIMAL", 0));  // Converte o inteiro para string
        String imgAnimal = getIntent().getStringExtra("IMG_ANIMAL");

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
        TextView animaisVoltar = findViewById(R.id.setaVoltar);


        // Alterando o comportamento do botão de voltar para considerar o tipo do animal
        animaisVoltar.setOnClickListener(v -> {
            // Chama o método onBackPressed(), que simula o comportamento do botão "voltar" do celular
            onBackPressed();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private void abrirCamera() {
        // Verifica se a permissão da câmera foi concedida
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Solicita a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    
}
