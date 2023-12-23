package uniba.roadhouse.asilappbox.fragments.glucose;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.Random;

import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.BaseFooterFragment;
import uniba.roadhouse.asilappbox.utils.TipoMisurazioneEnum;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GlucoseTestFooterRun#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GlucoseTestFooterRun extends BaseFooterFragment {
    private Button RUN_TEST_BUTTON;
    private final int TEST_DURATION_IN_MS = 5000;
    private Integer resultData;

    public GlucoseTestFooterRun() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StressFooterRunTest.
     */
    public static GlucoseTestFooterRun newInstance() {
        GlucoseTestFooterRun fragment = new GlucoseTestFooterRun();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
            GlucoseFragmentMain.TEST_PROGRESS_BAR.setVisibility(View.VISIBLE);
            GlucoseFragmentMain.TEST_PROGRESS_BAR.setProgress(0);

            // Creo un Thread che mi arresta automaticamente dopo 5 secondi il listener (raccolgo dati per soli 5 secondi)
            new Handler().postDelayed(() -> {
                GlucoseFragmentMain.TEST_PROGRESS_BAR.setVisibility(View.INVISIBLE);
                calculateTestResult();

                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));

                changeScreen(TipoMisurazioneEnum.GLUCOSIO, String.valueOf(this.resultData));
            }, TEST_DURATION_IN_MS);

            // Imposto l'animazione per la progress bar
            ValueAnimator animator = ValueAnimator.ofInt(0, GlucoseFragmentMain.TEST_PROGRESS_BAR.getMax());
            animator.setDuration(TEST_DURATION_IN_MS);
            animator.addUpdateListener(animation -> GlucoseFragmentMain.TEST_PROGRESS_BAR.setProgress((Integer)animation.getAnimatedValue()));
            animator.start();

            RUN_TEST_BUTTON.setEnabled(false);
            RUN_TEST_BUTTON.setText(R.string.testRunningButtonLable);

        });

    }

    @Override
    protected void calculateTestResult() {
        resultData = getRandomNumberInRange(50, 381);
    }

    /**
     * Ottiene un numero random nel range [min, max)
     * @param min
     * @param max
     * @return
     */
    private int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}