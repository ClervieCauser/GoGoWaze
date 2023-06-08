package com.example.gogowaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsDisplay;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import com.example.gogowaze.databinding.ActivityMainBinding;
import com.example.gogowaze.signalisation.SignalFragment;
import com.example.gogowaze.statistics.StatisticsActivity;

public class MainActivity extends AppCompatActivity {

    private MapView map;
    ActivityMainBinding binding;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //mise en place de la map
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK); // render
        map.getZoomController().activate();
        CustomZoomButtonsDisplay customZoomButtonsDisplay = map.getZoomController().getDisplay();
        customZoomButtonsDisplay.setPositions(false,
                CustomZoomButtonsDisplay.HorizontalPosition.RIGHT,
                CustomZoomButtonsDisplay.VerticalPosition.CENTER);

        GeoPoint startPoint = new GeoPoint(43.65020, 07.00517);
        IMapController mapControler = map.getController();
        mapControler.setZoom(18.0);
        mapControler.setCenter(startPoint);

        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(43.65020,7.00517));
        marker.setTitle("voiture");
        marker.setSnippet("faible");
        marker.setIcon(getResources().getDrawable(R.drawable.accidentfaible));

        Marker marker2 = new Marker(map);
        marker2.setPosition(new GeoPoint(43.64950, 7.00517));
        marker2.setTitle("moto");
        marker2.setSnippet("moderee");
        marker2.setIcon(getResources().getDrawable(R.drawable.accidentmoderee));

        map.getOverlays().add(marker);
        map.getOverlays().add(marker2);


        //Rajouter ici le fragment par défaut

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    Intent intent = new Intent(getApplicationContext(), ConfigurationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.stats:
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

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }


    private void changeDisplay(String type, String gravite, double latitude, double longitude) {
        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(latitude,longitude));
        marker.setTitle(type);
        marker.setSnippet(gravite);
        switch (gravite) {
            case "faible":
                marker.setIcon(getResources().getDrawable(R.drawable.accidentfaible));
            case "moderee":
                marker.setIcon(getResources().getDrawable(R.drawable.accidentmoderee));
            case "elevee":
                marker.setIcon(getResources().getDrawable(R.drawable.accidentelevee));
        }
    }
}