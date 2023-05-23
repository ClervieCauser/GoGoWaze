package com.example.gogowaze;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfilPageActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_page);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ProfilPageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProfilPageActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    openCamera();
                }
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null && extras.containsKey("data")) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageBitmap(imageBitmap);
                    saveImageToExternalStorage(imageBitmap);
                }
            }
        }
    }

    private void saveImageToInternalStorage(Bitmap imageBitmap) {
        String imageFileName = "captured_image.jpg";
        File storageDir = getFilesDir();
        Log.d("ProfilPageActivity", storageDir.getAbsolutePath());
        File imageFile = new File(storageDir, imageFileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Vérifier si le fichier existe
            if (imageFile.exists()) {
                Log.d("ProfilPageActivity", "L'image a été enregistrée avec succès.");
            } else {
                Log.d("ProfilPageActivity", "Erreur lors de l'enregistrement de l'image.");
            }

            // Vérifier la taille du fichier
            if (imageFile.length() > 0) {
                Log.d("ProfilPageActivity", "La taille de l'image est correcte.");
            } else {
                Log.d("ProfilPageActivity", "Erreur, la taille de l'image est 0.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveImageToExternalStorage(Bitmap imageBitmap) {
        String imageFileName = "captured_image.jpg";

        // Vérifiez si le stockage externe est disponible
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MonApplicationImages");
            Log.d("ProfilPageActivity EXTERNE", storageDir.getAbsolutePath());
            // Créez le répertoire s'il n'existe pas
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    Log.d("ProfilPageActivity", "Erreur lors de la création du répertoire MonApplicationImages.");
                    return;
                }
            }

            File imageFile = new File(storageDir, imageFileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();

                // Vérifiez si le fichier existe
                if (imageFile.exists()) {
                    Log.d("ProfilPageActivity", "L'image a été enregistrée avec succès.");
                } else {
                    Log.d("ProfilPageActivity", "Erreur lors de l'enregistrement de l'image.");
                }

                // Vérifiez la taille du fichier
                if (imageFile.length() > 0) {
                    Log.d("ProfilPageActivity", "La taille de l'image est correcte.");
                } else {
                    Log.d("ProfilPageActivity", "Erreur, la taille de l'image est 0.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ProfilPageActivity", "Le stockage externe n'est pas disponible.");
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }
}
