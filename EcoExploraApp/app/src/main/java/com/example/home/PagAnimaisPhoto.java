package com.example.home;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.home.databinding.ActivityMainBinding;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PagAnimaisPhoto extends AppCompatActivity {

    private String uploadPreset = "EcoExplora"; // NEW - Name of unsigned upload preset";

    private ActivityMainBinding binding; // New - Activity binding

    private String userName;
    private String photoUrl = "";
    private String nomeAnimal;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private FusedLocationProviderClient fusedLocationClient;
    private EditText locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pag_animais_photo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences userState = getSharedPreferences("userState", MODE_PRIVATE);
        userName = userState.getString("user", "");

        binding = ActivityMainBinding.inflate(getLayoutInflater()); // New - inflate view binding

        // Pede permissao da camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }

        CardView pickImg = findViewById(R.id.pickImg);
        CardView takePic = findViewById(R.id.takePic);
        TextView txtPickImg = findViewById(R.id.textView3);
        TextView txtTakePic = findViewById(R.id.textView4);
        EditText editLoc = findViewById(R.id.editTextText);
        TextView editData = findViewById(R.id.editTextText3);
        CardView locBtn = findViewById(R.id.cardView3);
        CardView dateBtn = findViewById(R.id.cardView4);
        Button sendDataBtn = findViewById(R.id.sendDataBtn);
        CardView cardViewEditData = findViewById(R.id.cardView5);


        // Pega o nome do animal que é passado pelo intent
        String getNomeAnimal = getIntent().getStringExtra("NOME_ANIMAL");
        nomeAnimal = getNomeAnimal;

        // Selecao de imagem
        pickImg.setOnClickListener(v -> {
            if( userName == ""){
                Toast.makeText(PagAnimaisPhoto.this, "Faça login antes de enviar", Toast.LENGTH_SHORT).show();
                return;
            }
            selecionarImagemGaleria();
        });
        txtPickImg.setOnClickListener(v -> {
            if( userName == ""){
                Toast.makeText(PagAnimaisPhoto.this, "Faça login antes de enviar", Toast.LENGTH_SHORT).show();
                return;
            }
            selecionarImagemGaleria();
        });

        // Tira foto
        takePic.setOnClickListener(v -> {
            if( userName == ""){
                Toast.makeText(PagAnimaisPhoto.this, "Faça login antes de enviar", Toast.LENGTH_SHORT).show();
                return;
            }
            verificarPermissaoCamera();
        });
        txtTakePic.setOnClickListener(v -> {
            if( userName == ""){
                Toast.makeText(PagAnimaisPhoto.this, "Faça login antes de enviar", Toast.LENGTH_SHORT).show();
                return;
            }
            verificarPermissaoCamera();
        });

        // Pega localizacao
        locationText = editLoc;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locBtn.setOnClickListener(v -> {
            getLastLocation();
        });

        // Pega data
        dateBtn.setOnClickListener(v -> {
            LocalDate hoje = LocalDate.now();
            String dataFormatada = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            editData.setText(dataFormatada);
        });

        editData.setOnClickListener(v -> {
            mostrarDataPicker();
        });


        // Envia os dados
        sendDataBtn.setOnClickListener(v -> {
            String local = "";
            String date = "";

            if(editLoc.getText().length() > 0){
                local = editLoc.getText().toString();
            }
            if(editData.getText().length() > 0){
                date = editData.getText().toString();
            }
            if(editLoc.getText().length() == 0){
                Toast.makeText(PagAnimaisPhoto.this, "Digite um endereço!", Toast.LENGTH_SHORT).show();
            } else if (editData.getText().length() == 0){
                Toast.makeText(PagAnimaisPhoto.this, "Digite uma data!", Toast.LENGTH_SHORT).show();
            } else if(photoUrl.isEmpty()){
                Toast.makeText(PagAnimaisPhoto.this, "Escolha um imagem", Toast.LENGTH_SHORT).show();
            }
            if(editLoc.getText().length() > 0 && editData.getText().length() > 0 && !photoUrl.isEmpty()){
                enviarDados(userName, local, date, photoUrl, nomeAnimal);
            }
        });

    }

    private void verificarPermissaoCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            abrirCamera();
        }
    }

    private Uri photoURI;
    private File photoFile;

    private void abrirCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Criar um arquivo para a imagem
            try {
                photoFile = createImageFile(); // Método que cria o arquivo
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(this,
                            "com.example.home.fileprovider", // Nome do seu provider
                            photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    abrirCameraLauncher.launch(cameraIntent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void selecionarImagemGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        abrirGaleriaLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> abrirCameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Aqui, a imagem já foi salva no arquivo photoFile
                    Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    // Chame o método para fazer o upload, se necessário
                    uploadImageBit(bitmap);
                }
            }
    );



    private final ActivityResultLauncher<Intent> abrirGaleriaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Processar resultado da galeria aqui
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri imageUri = data.getData(); // URI da imagem selecionada
                        uploadImageUri(imageUri); // New - call upload image method
                    }
                }
            }
    );

    private void uploadImageUri(Uri uri) {
        MediaManager.get().upload(uri).unsigned(uploadPreset).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d("Cloudinary Quickstart", "Upload start");
                Toast.makeText(PagAnimaisPhoto.this, "Espere a imagem carregar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d("Cloudinary Quickstart", "Upload progress");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d("Cloudinary Quickstart", "Upload success");
                String url = (String) resultData.get("secure_url");
                Log.d("requestId", "onSuccess: " + url);
                Toast.makeText(PagAnimaisPhoto.this, "Imagem enviada com sucesso", Toast.LENGTH_SHORT).show();
                photoUrl = url;
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d("Cloudinary Quickstart", "Upload failed");
                Toast.makeText(PagAnimaisPhoto.this, "Erro ao escolher imagem! Tente Denovo", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

    private void uploadImageBit(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Envia o ByteArray para o Cloudinary
        MediaManager.get().upload(byteArray).unsigned(uploadPreset).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d("Cloudinary Quickstart", "Upload start");
                Toast.makeText(PagAnimaisPhoto.this, "Espere a imagem carregar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d("Cloudinary Quickstart", "Upload progress");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                String url = (String) resultData.get("url"); // Obtém a URL da imagem
                Log.d("Cloudinary", "Imagem enviada com sucesso: " + url);
                Toast.makeText(PagAnimaisPhoto.this, "Imagem enviada com sucesso", Toast.LENGTH_SHORT).show();
                photoUrl = url;

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d("Cloudinary Quickstart", "Upload failed");
                Toast.makeText(PagAnimaisPhoto.this, "Erro ao escolher imagem! Tente Denovo", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

    // Metodo de pegar localizacao
    private void getLastLocation() {
        // Verifica permissões
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location = task.getResult();
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            getAddressFromLocation(latitude, longitude, locationText);
                        } else {
                            Toast.makeText(PagAnimaisPhoto.this, "Erro, Digite a localização", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Permissão para localizacao
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        }
    }

    // Transforma coordenadas em um localizacao
    private void getAddressFromLocation(double lat, double lon, EditText txt) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);  // Endereço completo

                txt.setText(fullAddress);
            } else {
                Toast.makeText(PagAnimaisPhoto.this, "Erro, Digite a localização", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(PagAnimaisPhoto.this, "Erro, Digite a localização", Toast.LENGTH_SHORT).show();
        }
    }

    // Selecao de data
    private void mostrarDataPicker() {
        // Obtém a data atual
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        // Cria o DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                // Mes selecionado é baseado em 0 (janeiro = 0, fevereiro = 1, etc.), então somamos 1
                String dataSelecionada = diaSelecionado + "/" + (mesSelecionado + 1) + "/" + anoSelecionado;
                // Aqui você pode usar a data selecionada, por exemplo, exibi-la em um EditText
                TextView editData = findViewById(R.id.editTextText3);
                Log.d("dataSelecionada", "onDateSet: "+ dataSelecionada);
                editData.setText(dataSelecionada);
            }
        }, ano, mes, dia);

        // Mostra o DatePickerDialog
        datePickerDialog.show();
    }


    // Metodo para enviar os dados
    private void enviarDados(String user, String location, String date, String photo, String animal) {
        new Thread(() -> {
            try {
                // URL da API
                URL url = new URL("https://ecoexplora.onrender.com/addSightings");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("X-API-KEY", BuildConfig.API_KEY);
                conn.setDoOutput(true);

                // Criar o objeto JSON
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("user", user);
                jsonParam.put("location", location);
                jsonParam.put("date", date);
                jsonParam.put("photo", photo);
                jsonParam.put("animal", animal);
                Log.d("addTest", "user: " + user + " Loc: " + location + " Date: " + date + " photo: " + photo + "animal" + nomeAnimal);

                // Enviar o JSON
                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes(StandardCharsets.UTF_8));
                os.close();

                // Verificar a resposta
                int responseCode = conn.getResponseCode();
                runOnUiThread(() -> {
                    if (responseCode == 200) {
                        Toast.makeText(PagAnimaisPhoto.this, "Informação enviada!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PagAnimaisPhoto.this, Home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PagAnimaisPhoto.this, "Erro ao enviar! Tente Denovo", Toast.LENGTH_SHORT).show();
                    }
                });

                conn.disconnect();
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(PagAnimaisPhoto.this, "Erro no envio!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

}