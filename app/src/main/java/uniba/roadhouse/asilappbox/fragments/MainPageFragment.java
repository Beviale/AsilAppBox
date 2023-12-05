package uniba.roadhouse.asilappbox.fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import uniba.roadhouse.asilappbox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment {

    // VARIABILI BOTTONI
    private ImageButton TEMPERATURE_BTN;
    private ImageButton CARDIO_BTN;
    private ImageButton STRESS_BTN;
    private ImageButton EYE_BTN;
    private ImageButton SCALE_BTN;
    private ImageButton GLUCOMETER_BTN;

    public MainPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageFragment newInstance(String param1, String param2) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // do-stuff
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Riferimento agli ImageButton
        getImageButtonReferences();
        /*
        // Aggiornamento icone a seconda delle impostazioni utente
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                setNightModeButtonIcons();
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                setBaseButtonIcons();
                break;
        }
        */
    }

    @Override
    public void onStart() {
        super.onStart();

        // Aggiunta dei Listener
        addImageButtonListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
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
        TEMPERATURE_BTN = getView().findViewById(R.id.temperature_btn);
        CARDIO_BTN = getView().findViewById(R.id.cardio_btn);
        STRESS_BTN = getView().findViewById(R.id.stress_btn);
        EYE_BTN = getView().findViewById(R.id.eye_btn);
        SCALE_BTN = getView().findViewById(R.id.scale_btn);
        GLUCOMETER_BTN = getView().findViewById(R.id.glucometer_btn);
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