package uniba.roadhouse.asilappbox.fragments.thermometertest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

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
import java.util.Random;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.stresstest.StressFooterResultTest;
import uniba.roadhouse.asilappbox.fragments.stresstest.StressFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TempFooterRunTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TempFooterRunTest extends Fragment implements SensorEventListener {

    private Button RUN_TEST_BUTTON;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private final int TEST_DURATION_IN_MS = 5000;
    private final ArrayList<Float> tempEvent = new ArrayList<>();


    public TempFooterRunTest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TempFooterRunTest.
     */
    // TODO: Rename and change types and number of parameters
    public static TempFooterRunTest newInstance() {
        TempFooterRunTest fragment = new TempFooterRunTest();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            // Il sensore di temperatura non è disponibile sul dispositivo
            Log.d("sensor","Sensore di Prossimità non disponibile");
        }else{
            Log.d("sensor", "Sensore disponibile!!!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_footer_run, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        RUN_TEST_BUTTON = getView().findViewById(R.id.run_test_button);
        resetRunTestButton();

        RUN_TEST_BUTTON.setOnClickListener(v -> {

            // Rendo visibili gli elementi ed imposto il listener per il sensore
            TempFragment.TEST_PROGRESS_BAR.setVisibility(View.VISIBLE);
            TempFragment.TEST_PROGRESS_BAR.setProgress(0);

            tempEvent.clear();
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

            // Creo un Thread che mi arresta automaticamente dopo 5 secondi il listener (raccolgo dati per soli 5 secondi)
            new Handler().postDelayed(() ->{

                sensorManager.unregisterListener(this);
                TempFragment.TEST_PROGRESS_BAR.setVisibility(View.INVISIBLE);

               if(calculateTestResult()){
                   Log.d("Misurazione","misura corretta");
                   Toast.makeText(getActivity(),String.format("%.2f C°", TempFragment.tempResult), Toast.LENGTH_LONG).show();
               }else{
                   Log.d("Misurazione","Errore misura");
                   Toast.makeText(getActivity(),"ERRORE MISURAZIONE: mantieni il sensore di prossimità vicino al corpo", Toast.LENGTH_LONG).show();
               }

                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));

                changeScreen();
                    }, TEST_DURATION_IN_MS);

            // Imposto l'animazione per la progress bar
            ValueAnimator animator = ValueAnimator.ofInt(0, TempFragment.TEST_PROGRESS_BAR.getMax());
            animator.setDuration(TEST_DURATION_IN_MS);
            animator.addUpdateListener(animation -> TempFragment.TEST_PROGRESS_BAR.setProgress((Integer)animation.getAnimatedValue()));
            animator.start();

            RUN_TEST_BUTTON.setEnabled(false);
            RUN_TEST_BUTTON.setText(R.string.testRunningButtonLable);
        });

    }

    private void resetRunTestButton(){
        RUN_TEST_BUTTON.setVisibility(View.VISIBLE);
        RUN_TEST_BUTTON.setText(R.string.runTestButtonLabel);
        RUN_TEST_BUTTON.setEnabled(true);
    }

    private Boolean calculateTestResult() {

        //verifico se è stato effettuato il test in maniera corretta col sensore vicino al corpo
        for(float f : tempEvent){
            Log.d("Sensore", "prossimita': " + f);
            if(f != 0.0){
                Log.d("Sensor","ErrorDistance");
                return false;
            }
        }

        /*Se il test è stato eseguito correttamente, allora simulo il termometro prendendo
          un valore random dove c'è solo 1/20 di possibilità che il paziente abbia la febbre.
          */
        Random random = new Random();
        int occurrence = random.nextInt(20);

        if (occurrence == 0) {
            // Restituisci un valore maggiore di 37 ma minore o uguale a 41
           TempFragment.tempResult = 37.0f + random.nextFloat() * 4.0f;
        } else {
            // Restituisci un valore tra 35 e 37
            TempFragment.tempResult = 35.0f + random.nextFloat() * 2.0f;
        }
        return true;
    }
    private void changeScreen(){
        if(MainActivity.Instance != null){
            MainActivity.Instance.changeScreen(R.id.test_footer_fragment, TempFooterResultTest.class);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
        tempEvent.add(event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}