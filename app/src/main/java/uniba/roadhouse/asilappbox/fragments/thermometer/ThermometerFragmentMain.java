package uniba.roadhouse.asilappbox.fragments.thermometer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.BaseFragment;

public class ThermometerFragmentMain extends BaseFragment {
    protected static ProgressBar TEST_PROGRESS_BAR;

    public ThermometerFragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TempFragment.
     */
    public static ThermometerFragmentMain newInstance() {
        ThermometerFragmentMain fragment = new ThermometerFragmentMain();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFragmentContent();
    }

    @Override
    protected void setFragmentContent(){
        ((TextView)getActivity().findViewById(R.id.testTitle)).setText(R.string.temperature);
        ((ImageView)getView().findViewById(R.id.iconHolder)).setImageResource(R.mipmap.temperature_icon_black);
        ((EditText)getActivity().findViewById(R.id.testDescription)).setText(R.string.temperatureDescription);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get references
        TEST_PROGRESS_BAR = getView().findViewById(R.id.test_progress_bar);

        if(MainActivity.Instance != null){
            MainActivity.Instance.changeScreen(R.id.test_footer_fragment, ThermometerTestFooterRun.class, false);
        }
    }
}
