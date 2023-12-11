package uniba.roadhouse.asilappbox.fragments.stresstest;

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
 * Use the {@link StressFooterResultTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StressFooterResultTest extends Fragment {

    private Button CONFIRM_BUTTON;
    private Button CANCEL_BUTTON;

    public StressFooterResultTest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StressFooterResultTest.
     */
    public static StressFooterResultTest newInstance() {
        StressFooterResultTest fragment = new StressFooterResultTest();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        CONFIRM_BUTTON.setOnClickListener(v -> sendResult(StressFragment.stressLevel));
        CANCEL_BUTTON.setOnClickListener(v -> MainActivity.Instance.popFragmentFromBackStack());

    }

    private StressFragment.StressLevel sendResult(StressFragment.StressLevel stressLevel) {
        return stressLevel;
    }

}