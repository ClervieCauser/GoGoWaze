package com.example.gogowaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import com.example.gogowaze.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView map;
    ActivityMainBinding binding;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load( getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK); // render
        map.setBuiltInZoomControls(true); // zoom
        GeoPoint startPoint = new GeoPoint(43.65020, 07.00517);
        IMapController mapControler = map.getController();
        mapControler.setZoom(18.0);
        mapControler.setCenter(startPoint);

        //Rajouter ici le fragment par défaut

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    Intent intent = new Intent(getApplicationContext(), ConfigurationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.stats:
                    replaceFragment(new StatsFragment());
                    Intent intent2 = new Intent(getApplicationContext(), StatisticsActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.warning:
                    replaceFragment(new SignalFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    Intent intent3 = new Intent(getApplicationContext(), ProfilPageActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.sos:
                    replaceFragment(new EmergencyFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}