package uniba.roadhouse.asilappbox.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.utils.TipoMisurazioneEnum;

public abstract class BaseFooterFragment extends Fragment {

    /**
     * Metodo usato per il cambio di schermata (Fragment). In quanto FooterFragment imposto di default un Bundle da comunicare
     * al Fragment dei risultati così da poter eventualmente inviare i dati al client.
     * @param tipoMisurazione
     * @param resultDataInString
     */
    protected void changeScreen(TipoMisurazioneEnum tipoMisurazione, String resultDataInString){
        if(MainActivity.Instance != null){
            // Creo un bundle per il passaggio dati
            Bundle bundle = new Bundle();
            bundle.putString("TIPO_MISURAZIONE", tipoMisurazione.name());
            bundle.putString("RESULT_DATA", resultDataInString);

            Fragment footerTestResultFragment = new FooterTestResult();
            footerTestResultFragment.setArguments(bundle);

            MainActivity.Instance.changeScreen(R.id.test_footer_fragment, footerTestResultFragment, false);
        }
    }

    protected void resetRunTestButton(Button button){
        button.setVisibility(View.VISIBLE);
        button.setText(R.string.runTestButtonLabel);
        button.setEnabled(true);
    }

    protected abstract void calculateTestResult();

}
