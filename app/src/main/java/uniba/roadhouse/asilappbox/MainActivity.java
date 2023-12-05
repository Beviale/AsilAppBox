package uniba.roadhouse.asilappbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton TEMPERATURE_BTN;
    private ImageButton CARDIO_BTN;
    private ImageButton STRESS_BTN;
    private ImageButton EYE_BTN;
    private ImageButton SCALE_BTN;
    private ImageButton GLUCOMETER_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Riferimento agli ImageButton
        getImageButtonReferences();

        // Aggiornamento icone a seconda delle impostazioni utente
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                setNightModeButtonIcons();
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                setBaseButtonIcons();
                break;
        }

        // Aggiunta dei Listener
        addImageButtonListeners();

    }

    private void setNightModeButtonIcons() {
        TEMPERATURE_BTN.setImageResource(R.drawable.temp_icon_night);
        CARDIO_BTN.setImageResource(R.drawable.heart_icon_night);
        STRESS_BTN.setImageResource(R.drawable.parkinson_icon_night);
        EYE_BTN.setImageResource(R.drawable.eye_icon_night);
        SCALE_BTN.setImageResource(R.drawable.scale_icon_night);
        GLUCOMETER_BTN.setImageResource(R.drawable.glucometer_icon_night);
    }

    private void setBaseButtonIcons() {
        TEMPERATURE_BTN.setImageResource(R.drawable.temp_icon);
        CARDIO_BTN.setImageResource(R.drawable.heart_icon);
        STRESS_BTN.setImageResource(R.drawable.parkinson_icon);
        EYE_BTN.setImageResource(R.drawable.eye_icon);
        SCALE_BTN.setImageResource(R.drawable.scale_icon);
        GLUCOMETER_BTN.setImageResource(R.drawable.glucometer_icon);
    }

    private void getImageButtonReferences(){
        TEMPERATURE_BTN = findViewById(R.id.temperature_btn);
        CARDIO_BTN = findViewById(R.id.cardio_btn);
        STRESS_BTN = findViewById(R.id.stress_btn);
        EYE_BTN = findViewById(R.id.eye_btn);
        SCALE_BTN = findViewById(R.id.scale_btn);
        GLUCOMETER_BTN = findViewById(R.id.glucometer_btn);
    }

    private void addImageButtonListeners() {
        TEMPERATURE_BTN.setOnClickListener(v -> {
            Log.d("TEMP_BTN", "temp pressed");
        });

        CARDIO_BTN.setOnClickListener(v -> {
            Log.d("CARDIO_BTN", "cardio pressed");
        });

        STRESS_BTN.setOnClickListener(v -> {
            Log.d("STRESS_BTN", "stress pressed");
        });

        EYE_BTN.setOnClickListener(v -> {
            Log.d("EYE_BTN", "eye pressed");
        });

        SCALE_BTN.setOnClickListener(v -> {
            Log.d("SCALE_BTN", "scale pressed");
        });

        GLUCOMETER_BTN.setOnClickListener(v -> {
            Log.d("GLUCOMETER_BTN", "glucometer pressed");
        });
    }

}