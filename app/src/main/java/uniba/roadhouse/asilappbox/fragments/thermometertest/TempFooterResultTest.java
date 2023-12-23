package uniba.roadhouse.asilappbox.fragments.thermometertest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TempFooterResultTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TempFooterResultTest extends Fragment {

    private Button CONFIRM_BUTTON;
    private Button CANCEL_BUTTON;

    public TempFooterResultTest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TempFooterResultTest.
     */
    // TODO: Rename and change types and number of parameters
    public static TempFooterResultTest newInstance() {
        TempFooterResultTest fragment = new TempFooterResultTest();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_footer_result, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Get Referneces and Set Listeners
        CONFIRM_BUTTON = getView().findViewById(R.id.confirm_result_button);
        CANCEL_BUTTON = getView().findViewById(R.id.cancel_result_button);

        CONFIRM_BUTTON.setOnClickListener(v -> sendResult(TempFragment.tempResult));
        CANCEL_BUTTON.setOnClickListener(v -> MainActivity.Instance.popFragmentFromBackStack());
    }

    private Float sendResult(float tempResult){
        return tempResult;
    }
}