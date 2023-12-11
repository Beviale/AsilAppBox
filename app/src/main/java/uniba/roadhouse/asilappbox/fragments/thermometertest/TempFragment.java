package uniba.roadhouse.asilappbox.fragments.thermometertest;

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
import uniba.roadhouse.asilappbox.fragments.stresstest.StressFooterRunTest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TempFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TempFragment extends Fragment {

    protected static ProgressBar TEST_PROGRESS_BAR;


    public TempFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TempFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TempFragment newInstance() {
        TempFragment fragment = new TempFragment();
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
        return inflater.inflate(R.layout.fragment_test, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFragmentContent();
    }

    private void setFragmentContent() {
        ((TextView)getActivity().findViewById(R.id.testTitle)).setText(R.string.tempToolTitle);
        ((ImageView)getActivity().findViewById(R.id.iconHolder)).setImageResource(R.mipmap.temp_icon);
        ((EditText)getActivity().findViewById(R.id.testDescription)).setText(R.string.tempToolDesc);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get references
        TEST_PROGRESS_BAR = getView().findViewById(R.id.test_progress_bar);

        if(MainActivity.Instance != null){
            MainActivity.Instance.changeScreen(R.id.test_footer_fragment, TempFooterRunTest.class, false);
        }
    }
}