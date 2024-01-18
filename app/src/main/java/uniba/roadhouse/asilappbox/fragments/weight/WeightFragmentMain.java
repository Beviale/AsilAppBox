package uniba.roadhouse.asilappbox.fragments.weight;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import uniba.roadhouse.asilappbox.fragments.bpm.BpmTestFooterRun;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeightFragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightFragmentMain extends BaseFragment {
    protected static ProgressBar TEST_PROGRESS_BAR;

    public WeightFragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WeightFragmentMain.
     */
    public static WeightFragmentMain newInstance() {
        WeightFragmentMain fragment = new WeightFragmentMain();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFragmentContent();
    }

    @Override
    protected void setFragmentContent() {
        ((TextView)getActivity().findViewById(R.id.testTitle)).setText(R.string.weight);
        ((ImageView)getView().findViewById(R.id.iconHolder)).setImageResource(R.mipmap.weight_icon_black);
        ((EditText)getActivity().findViewById(R.id.testDescription)).setText(R.string.weightDescription);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get References
        TEST_PROGRESS_BAR = getView().findViewById(R.id.test_progress_bar);

        if(MainActivity.Instance != null){
            MainActivity.Instance.changeScreen(R.id.test_footer_fragment, WeightTestFooterRun.class, false);
        }
    }
}