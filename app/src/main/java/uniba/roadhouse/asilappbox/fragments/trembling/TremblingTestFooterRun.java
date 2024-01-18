package uniba.roadhouse.asilappbox.fragments.trembling;

import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.BaseFooterFragment;
import uniba.roadhouse.asilappbox.fragments.FooterTestResult;
import uniba.roadhouse.asilappbox.utils.TipoMisurazioneEnum;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TremblingTestFooterRun#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TremblingTestFooterRun extends BaseFooterFragment implements SensorEventListener{
    private Button RUN_TEST_BUTTON;
    private final SensorEventListener listener = this;
    private final ArrayList<Float> yEvent = new ArrayList<>();
    private final int TEST_DURATION_IN_MS = 5000;
    private SensorManager sensorManager;
    private Double resultData;

    public TremblingTestFooterRun() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StressFooterRunTest.
     */
    public static TremblingTestFooterRun newInstance() {
        TremblingTestFooterRun fragment = new TremblingTestFooterRun();
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
        return inflater.inflate(R.layout.fragment_test_footer_run, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        RUN_TEST_BUTTON = getView().findViewById(R.id.run_test_button);
        resetRunTestButton(RUN_TEST_BUTTON);

        // set button listener
        RUN_TEST_BUTTON.setOnClickListener(v -> {

            // Rendo visibili gli elementi ed imposto il listener per il sensore
            TremblingFragmentMain.TEST_PROGRESS_BAR.setVisibility(View.VISIBLE);
            TremblingFragmentMain.TEST_PROGRESS_BAR.setProgress(0);
            sensorManager.registerListener(this.listener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);

            // Creo un Thread che mi arresta automaticamente dopo 5 secondi il listener (raccolgo dati per soli 5 secondi)
            new Handler().postDelayed(() -> {
                sensorManager.unregisterListener(listener);
                TremblingFragmentMain.TEST_PROGRESS_BAR.setVisibility(View.INVISIBLE);
                calculateTestResult();

                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));

                Log.d("RESULT", String.valueOf(this.resultData));
                Toast.makeText(this.getContext(), String.format("%.2f", this.resultData), Toast.LENGTH_LONG).show();
                changeScreen(TipoMisurazioneEnum.TREMOLIO, String.valueOf(this.resultData));
            }, TEST_DURATION_IN_MS);

            // Imposto l'animazione per la progress bar
            ValueAnimator animator = ValueAnimator.ofInt(0, TremblingFragmentMain.TEST_PROGRESS_BAR.getMax());
            animator.setDuration(TEST_DURATION_IN_MS);
            animator.addUpdateListener(animation -> TremblingFragmentMain.TEST_PROGRESS_BAR.setProgress((Integer)animation.getAnimatedValue()));
            animator.start();

            RUN_TEST_BUTTON.setEnabled(false);
            RUN_TEST_BUTTON.setText(R.string.testRunningButtonLable);

        });

    }

    @Override
    protected void calculateTestResult() {
        float avg = 0;
        for(float f : yEvent){
            avg += Math.abs(f);
        }
        avg /= yEvent.size();

        resultData = convertToHz(avg);
    }

    private Double convertToHz(float rawInput){
        return 2 * Math.PI * rawInput;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Add all y-values to the list
        if(event.sensor.getType()== Sensor.TYPE_GYROSCOPE){
            yEvent.add(event.values[1]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}