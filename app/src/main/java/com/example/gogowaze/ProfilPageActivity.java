package com.example.gogowaze;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ProfilPageActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_page);

        // Obtenez une référence à l'ImageView
        ImageView imageView = findViewById(R.id.imageView);

        // Ajouter un OnClickListener à l'ImageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'appareil photo
                // Vérifier si l'application a l'autorisation d'utiliser l'appareil photo
                if (ContextCompat.checkSelfPermission(ProfilPageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Demander la permission d'utiliser l'appareil photo
                    ActivityCompat.requestPermissions(ProfilPageActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    // Ouvrir l'appareil photo
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // startActivityForResult(intent, 1);
            }
        });




        // Obtenez une référence au TabLayout et au ViewPager dans votre activité ou votre fragment
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Créer un adaptateur de fragments pour le ViewPager
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                // Retourner le fragment approprié pour chaque onglet
                switch (position) {
                    case 0:
                        return new HistoriqueNotificationsFragment();
                    case 1:
                        return new HistoriqueSignalementsFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                // Retourner le nombre total d'onglets
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                // Retourner le titre de chaque onglet
                switch (position) {
                    case 0:
                        return "Notifications";
                    case 1:
                        return "Signalements";
                    default:
                        return null;
                }
            }
        };

        // Attacher l'adaptateur de fragments au ViewPager
        viewPager.setAdapter(adapter);

        // Attacher le ViewPager au TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }

    // Gérer le résultat de la prise de photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Récupérer l'image capturée
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = findViewById(R.id.imageView);

            // Mettre l'image capturée dans l'ImageView
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
