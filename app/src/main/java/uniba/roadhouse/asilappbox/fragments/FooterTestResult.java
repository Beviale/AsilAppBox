package uniba.roadhouse.asilappbox.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.trembling.TremblingFragmentMain;
import uniba.roadhouse.asilappbox.utils.TipoMisurazioneEnum;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FooterTestResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FooterTestResult extends Fragment {

    private Button CONFIRM_BUTTON;
    private Button CANCEL_BUTTON;
    private String tipoMisurazione;
    private String resultData;

    public FooterTestResult() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StressFooterResultTest.
     */
    public static FooterTestResult newInstance() {
        FooterTestResult fragment = new FooterTestResult();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Recupero informazioni dal Bundle
        Bundle bundle = getArguments();
        if(bundle!=null){
            this.tipoMisurazione = bundle.getString("TIPO_MISURAZIONE");
            this.resultData = bundle.getString("RESULT_DATA");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_footer_result, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Get Referneces and Set Listeners
        CONFIRM_BUTTON = getView().findViewById(R.id.confirm_result_button);
        CANCEL_BUTTON = getView().findViewById(R.id.cancel_result_button);

        CONFIRM_BUTTON.setOnClickListener(v -> {
            try {
                sendResult();
                Toast.makeText(this.getContext(), getResources().getString(R.string.resultSentMsg),Toast.LENGTH_LONG).show();
                // Torno alla Home
                MainActivity.Instance.popFragmentFromBackStack();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        CANCEL_BUTTON.setOnClickListener(v -> MainActivity.Instance.popFragmentFromBackStack());

    }

    private void sendResult() throws IOException {
        Log.d("RESULT", String.valueOf(this.resultData) + " sent");

        MainActivity.Instance.getBoxServerBT().sendData(this.tipoMisurazione, this.resultData);
    }

}