package uniba.roadhouse.asilappbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import uniba.roadhouse.asilappbox.fragments.MainPageFragment;

public class MainActivity extends AppCompatActivity {

    public static MainActivity Instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        Instance = this;
        this.changeScreen(R.id.main_fragment_container, MainPageFragment.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void changeScreen(int fragmentContainer, Class<? extends Fragment> newFragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        //apro il fragment indicato
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, newFragment, null);
        if(addToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void changeScreen(int fragmentContainer, Class<? extends Fragment> newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        //apro il fragment indicato
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, newFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}