package uniba.roadhouse.asilappbox.fragments.stresstest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StressFooterRunTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StressFooterRunTest extends Fragment implements SensorEventListener{
    private Button RUN_TEST_BUTTON;
    private final SensorEventListener listener = this;
    private final ArrayList<Float> xEvent = new ArrayList<>();
    private final int TEST_DURATION_IN_MS = 5000;
    private SensorManager sensorManager;

    public StressFooterRunTest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StressFooterRunTest.
     */
    public static StressFooterRunTest newInstance() {
        StressFooterRunTest fragment = new StressFooterRunTest();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stress_footer_run_test, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        RUN_TEST_BUTTON = getView().findViewById(R.id.run_test_button);
        resetRunTestButton();

        // set button listener
        RUN_TEST_BUTTON.setOnClickListener(v -> {

            // Rendo visibili gli elementi ed imposto il listener per il sensore
            StressFragment.TEST_PROGRESS_BAR.setVisibility(View.VISIBLE);
            StressFragment.TEST_PROGRESS_BAR.setProgress(0);
            sensorManager.registerListener(this.listener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);

            // Creo un Thread che mi arresta automaticamente dopo 5 secondi il listener (raccolgo dati per soli 5 secondi)
            new Handler().postDelayed(() -> {
                sensorManager.unregisterListener(listener);
                StressFragment.TEST_PROGRESS_BAR.setVisibility(View.INVISIBLE);
                calculateTestResult();
                changeScreen();
            }, TEST_DURATION_IN_MS);

            // Imposto l'animazione per la progress bar
            ValueAnimator animator = ValueAnimator.ofInt(0, StressFragment.TEST_PROGRESS_BAR.getMax());
            animator.setDuration(TEST_DURATION_IN_MS);
            animator.addUpdateListener(animation -> StressFragment.TEST_PROGRESS_BAR.setProgress((Integer)animation.getAnimatedValue()));
            animator.start();

            RUN_TEST_BUTTON.setEnabled(false);
            RUN_TEST_BUTTON.setText(R.string.stressTestRunningButtonLable);

        });

    }

    private void resetRunTestButton(){
        RUN_TEST_BUTTON.setVisibility(View.VISIBLE);
        RUN_TEST_BUTTON.setText(R.string.runStressTestButtonLabel);
        RUN_TEST_BUTTON.setEnabled(true);
    }

    private void calculateTestResult() {
        float avg = 0;
        for(float f : xEvent){
            avg += Math.abs(f);
        }
        avg /= xEvent.size();

        if(avg <= 0.09){
            // Normal Result
            Toast.makeText(getActivity(), "Normal Stress Level", Toast.LENGTH_LONG).show();
            StressFragment.stressLevel = StressFragment.StressLevel.NORMAL;
        } else if (avg > 0.09 && avg < 0.3) {
            // Highly stressed
            Toast.makeText(getActivity(), "High Stress Level", Toast.LENGTH_LONG).show();
            StressFragment.stressLevel = StressFragment.StressLevel.HIGH;
        }else{
            // Extremely stressed
            Toast.makeText(getActivity(), "Extreme Stress Level!", Toast.LENGTH_LONG).show();
            StressFragment.stressLevel = StressFragment.StressLevel.EXTREME;
        }
    }

    private void changeScreen(){
        if(MainActivity.Instance != null){
            MainActivity.Instance.changeScreen(R.id.stress_test_footer_fragment, StressFooterResultTest.class);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Add all y-values to the list
        if(event.sensor.getType()== Sensor.TYPE_GYROSCOPE){
            xEvent.add(event.values[1]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}