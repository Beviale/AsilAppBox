package uniba.roadhouse.asilappbox.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.bloodpressure.BloodPressureFragmentMain;
import uniba.roadhouse.asilappbox.fragments.bpm.BpmFragmentMain;
import uniba.roadhouse.asilappbox.fragments.glucose.GlucoseFragmentMain;
import uniba.roadhouse.asilappbox.fragments.trembling.TremblingFragmentMain;
import uniba.roadhouse.asilappbox.fragments.thermometer.ThermometerFragmentMain;
import uniba.roadhouse.asilappbox.fragments.weight.WeightFragmentMain;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment {

    private Map<String, Class> toolScreenFragments;

    private final String TEMPERATURE_SCREEN = "Temperature";
    private final String BPM_SCREEN = "Bpm";
    private final String TREMBLING_SCREEN = "Trembling";
    private final String BLOOD_PRESSURE_SCREEN = "Blood_Pressure";
    private final String WEIGHT_SCREEN = "Weight";
    private final String GLUCOSE_SCREEN = "Glucose";

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

        toolScreenFragments = new HashMap<String, Class>(){{
            put(TEMPERATURE_SCREEN, ThermometerFragmentMain.class);
            put(BPM_SCREEN, BpmFragmentMain.class);
            put(TREMBLING_SCREEN, TremblingFragmentMain.class);
            put(BLOOD_PRESSURE_SCREEN, BloodPressureFragmentMain.class);
            put(WEIGHT_SCREEN, WeightFragmentMain.class);
            put(GLUCOSE_SCREEN, GlucoseFragmentMain.class);
        }};

    }

    @Override
    public void onStart() {
        super.onStart();

        // Aggiunta dei Listener
        setButtonListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    private void setButtonListeners(){
        getView().findViewById(R.id.temperature_btn).setOnClickListener(v -> changeScreen(this.TEMPERATURE_SCREEN));
        getView().findViewById(R.id.bpm_btn).setOnClickListener(v -> changeScreen(this.BPM_SCREEN));
        getView().findViewById(R.id.trembling_btn).setOnClickListener(v -> changeScreen(this.TREMBLING_SCREEN));
        getView().findViewById(R.id.blood_pressure_btn).setOnClickListener(v -> changeScreen(this.BLOOD_PRESSURE_SCREEN));
        getView().findViewById(R.id.weight_btn).setOnClickListener(v -> changeScreen(this.WEIGHT_SCREEN));
        getView().findViewById(R.id.glucose_btn).setOnClickListener(v -> changeScreen(this.GLUCOSE_SCREEN));

        // Bottone per chiudere la Box
        getView().findViewById(R.id.closeBoxBtn).setOnClickListener(v -> {
            try {
                MainActivity.Instance.closeServerSocket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MainActivity.Instance.finish();
        });
    }

    private void changeScreen(String screen) {
        if(MainActivity.Instance != null){
            MainActivity.Instance.changeScreen(R.id.main_fragment_container, toolScreenFragments.get(screen));
        }
    }

}