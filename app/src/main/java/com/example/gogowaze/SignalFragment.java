package com.example.gogowaze;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

public class SignalFragment extends Fragment {

    private Button btnFermer;
    private Button btnAnnuler;
    private Button btnValider;
    private RadioGroup radioGroupGravity;
    private RadioGroup radioGroupType;
    private TextInputEditText textTitle;
    private TextInputEditText textDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signal, container, false);

        btnFermer = view.findViewById(R.id.buttonExit);
        btnAnnuler = view.findViewById(R.id.annulerSignalement);
        btnValider = view.findViewById(R.id.validateSignalement);
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

        return view;
    }

    public void createAndShowNotification() {
        int selectedGravityId = radioGroupGravity.getCheckedRadioButtonId();
        RadioButton selectedGravity = getView().findViewById(selectedGravityId);
        String selectedGravityText = selectedGravity.getText().toString();

        int selectedTypeId = radioGroupType.getCheckedRadioButtonId();
        RadioButton selectedType = getView().findViewById(selectedTypeId);
        String selectedTypeText = selectedType.getText().toString();

        String titleText = textTitle.getText().toString();
        String descriptionText = textDescription.getText().toString();

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bell_icon);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getContext(), ApplicationDemo.CHANNEL_ID)
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

        int notificationId = 1;  // This is just an example, you can set any unique number

        notificationManager.notify(notificationId, builder.build());
    }
}
