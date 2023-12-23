package uniba.roadhouse.asilappbox.fragments.glucose;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GlucoseFragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GlucoseFragmentMain extends BaseFragment {
    protected static ProgressBar TEST_PROGRESS_BAR;

    public GlucoseFragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GlucometerFragment.
     */
    public static GlucoseFragmentMain newInstance() {
        GlucoseFragmentMain fragment = new GlucoseFragmentMain();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    protected void setFragmentContent() {
        ((TextView)getActivity().findViewById(R.id.testTitle)).setText(R.string.glucose);
        ((ImageView)getView().findViewById(R.id.iconHolder)).setImageResource(R.mipmap.glucose_icon_white);
        ((EditText)getActivity().findViewById(R.id.testDescription)).setText(R.string.glucoseDescription);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get References
        TEST_PROGRESS_BAR = getView().findViewById(R.id.test_progress_bar);

        if(MainActivity.Instance != null){
            MainActivity.Instance.changeScreen(R.id.test_footer_fragment, GlucoseTestFooterRun.class, false);
        }
    }
}