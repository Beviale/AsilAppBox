package uniba.roadhouse.asilappbox.fragments.thermometertest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uniba.roadhouse.asilappbox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TempFooterRunTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TempFooterRunTest extends Fragment implements SensorEventListener {

    private Button RUN_TEST_BUTTON;
    private SensorManager sensorManager;

    private Sensor temperatureSensor;



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

        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (temperatureSensor == null) {
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
        return inflater.inflate(R.layout.fragment_temp_footer_run_test, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        RUN_TEST_BUTTON = getView().findViewById(R.id.run_test_button);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float temperature = event.values[0];
        Log.d("TemperatureFragment", "Temperatura: " + temperature + " °C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}