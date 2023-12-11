package uniba.roadhouse.asilappbox.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.stresstest.StressFragment;
import uniba.roadhouse.asilappbox.fragments.thermometertest.TempFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment {

    private Map<String, Class> toolScreenFragments;

    private final String TEMPERATURE_SCREEN = "Temperature";
    private final String CARDIO_SCREEN = "Cardio";
    private final String STRESS_SCREEN = "Stress";
    private final String EYE_SCREEN = "Eye";
    private final String SCALE_SCREEN = "Scale";
    private final String GLUCOMETER_SCREEN = "Glucometer";

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
            put(TEMPERATURE_SCREEN, TempFragment.class);
            put(CARDIO_SCREEN, CardioFragment.class);
            put(STRESS_SCREEN, StressFragment.class);
            put(SCALE_SCREEN, ScaleFragment.class);
            put(GLUCOMETER_SCREEN, GlucometerFragment.class);
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
        getView().findViewById(R.id.cardio_btn).setOnClickListener(v -> changeScreen(this.CARDIO_SCREEN));
        getView().findViewById(R.id.stress_btn).setOnClickListener(v -> changeScreen(this.STRESS_SCREEN));
        //getView().findViewById(R.id.eye_btn).setOnClickListener(v -> changeScreen(this.EYE_SCREEN));
        getView().findViewById(R.id.scale_btn).setOnClickListener(v -> changeScreen(this.SCALE_SCREEN));
        getView().findViewById(R.id.glucometer_btn).setOnClickListener(v -> changeScreen(this.GLUCOMETER_SCREEN));
    }

    private void changeScreen(String screen) {
        if(MainActivity.Instance != null){
            MainActivity.Instance.changeScreen(R.id.main_fragment_container, toolScreenFragments.get(screen));
        }
    }

}