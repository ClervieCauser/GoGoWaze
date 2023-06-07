package com.example.gogowaze;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

public class SignalFragment extends Fragment {

    private Button btnFermer;
    private Button btnAnnuler;
    private Button btnValider;
    private Button btnCapture;
    private Bitmap capturedImage = null;

    private RadioGroup radioGroupGravity;
    private RadioGroup radioGroupType;
    private TextInputEditText textTitle;
    private TextInputEditText textDescription;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signal, container, false);

        btnFermer = view.findViewById(R.id.buttonExit);
        btnAnnuler = view.findViewById(R.id.annulerSignalement);
        btnValider = view.findViewById(R.id.validateSignalement);
        btnCapture = view.findViewById(R.id.buttonCapture);
        radioGroupGravity = view.findViewById(R.id.radioButtonGroupeGravity);
        radioGroupType = view.findViewById(R.id.radioButtonGroupeType);
        textTitle = view.findViewById(R.id.textTitre);
        textDescription = view.findViewById(R.id.textDescription);

        btnFermer.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().remove(SignalFragment.this).commit());
        btnAnnuler.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().remove(SignalFragment.this).commit());
        btnValider.setOnClickListener(v -> {
            createAndShowNotification();
            getActivity().getSupportFragmentManager().beginTransaction().remove(SignalFragment.this).commit();
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'appareil photo
                // Vérifier si l'application a l'autorisation d'utiliser l'appareil photo
                if (ContextCompat.checkSelfPermission(SignalFragment.this.getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SignalFragment.this.getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    openCamera();
                }
            }
        });
        return view;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Override this method to get the result of the camera intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            capturedImage = (Bitmap) extras.get("data");
        }
    }


    public void createAndShowNotification() {
        int selectedGravityId = radioGroupGravity.getCheckedRadioButtonId();
        RadioButton selectedGravity = selectedGravityId != -1 ? getView().findViewById(selectedGravityId) : null;
        String selectedGravityText = selectedGravity != null ? selectedGravity.getText().toString() : "";

        int selectedTypeId = radioGroupType.getCheckedRadioButtonId();
        RadioButton selectedType = selectedTypeId != -1 ? getView().findViewById(selectedTypeId) : null;
        String selectedTypeText = selectedType != null ? selectedType.getText().toString() : "";

        String titleText = textTitle.getText().toString();
        String descriptionText = textDescription.getText().toString();

        // vérifiez si les champs sont remplis
        if (titleText.isEmpty() || descriptionText.isEmpty() || selectedGravity == null || selectedType == null) {
            // Vous pouvez afficher un message à l'utilisateur ici pour le notifier que les champs sont vides.
            return;
        }

        Bitmap largeIcon = capturedImage != null ? capturedImage : BitmapFactory.decodeResource(getResources(), R.drawable.bell_icon);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getContext(), Notification.CHANNEL_ID)
                .setSmallIcon(R.drawable.bell_icon)
                .setContentTitle(titleText)
                .setContentText(descriptionText)
                .setSubText("Gravity: " + selectedGravityText + ", Type: " + selectedTypeText)
                .setLargeIcon(largeIcon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);

        PendingIntent pendingIntent = PendingIntent.getActivity(this.getContext(), 0, new Intent(this.getContext(), MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.bell_icon, "Action", pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getContext());

        int notificationId = 1;

        notificationManager.notify(notificationId, builder.build());
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
