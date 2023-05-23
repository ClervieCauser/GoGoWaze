package com.example.gogowaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsDisplay;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
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

        //mise en place des fidget de la map
        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem voiture = new OverlayItem("voiture", "faible", new GeoPoint(43.65020, 7.00517));
        Drawable m = voiture.getMarker(0);
        items.add(voiture);
        items.add(new OverlayItem("moto", "moderer", new GeoPoint(43.64950, 7.00517)));

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(),
                items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });
        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);

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
        createAndShowNotification();

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
        createAndShowNotification();

    }

    public void createAndShowNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ApplicationDemo.CHANNEL_ID)
                .setSmallIcon(R.drawable.bell_icon)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);
        ;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.bell_icon, "Action", pendingIntent);


        // Obtenez une instance de NotificationManager à partir d'ApplicationDemo
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        int notificationId = 1;  // Ceci est juste un exemple, vous pouvez définir n'importe quel nombre unique

        notificationManager.notify(notificationId, builder.build());
    }

}