package uniba.roadhouse.asilappbox.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitingForClient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingForClient extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WaitingForClient.
     */
    public static WaitingForClient newInstance() {
        WaitingForClient fragment = new WaitingForClient();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_waiting_for_client, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.Instance.openServerSocket();
    }
}