package uniba.roadhouse.asilappbox;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import uniba.roadhouse.asilappbox.fragments.MainPageFragment;

public class MainActivity extends AppCompatActivity {

    public static MainActivity Instance;
    private FragmentManager fragmentManager;
    private final int REQUEST_BT_PERMISSION = 1;

    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    // PERMISSION GRANTED
                    Log.d("PERMISSION","Siamo nel listener buono");
                    changeScreen(R.id.main_fragment_container, MainPageFragment.class);
                } else {
                    // PERMISSION NOT GRANTED
                    finish();
                    Log.d("PERMISSION","Siamo nel listener BRUTTO");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        Instance = this;
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrRequestBTPermission();
    }

    public void changeScreen(int fragmentContainer, Class<? extends Fragment> newFragment, boolean addToBackStack) {
        //apro il fragment indicato
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, newFragment, null);
        if(addToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void changeScreen(int fragmentContainer, Class<? extends Fragment> newFragment) {
        //apro il fragment indicato
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, newFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void popFragmentFromBackStack(){
        this.fragmentManager.popBackStack();
    }

    public void checkOrRequestBTPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            this.changeScreen(R.id.main_fragment_container, MainPageFragment.class);
            Log.d("PERMISSION", "MA ALLORA SEI STRONZO!");
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.BLUETOOTH_CONNECT)) {
            showExplanation(R.string.permission_needed, R.string.bt_permission_rationale, Manifest.permission.BLUETOOTH_CONNECT, REQUEST_BT_PERMISSION);
            Log.d("PERMISSION", "MA ALLORA SEI STRONZ 2!");
        } else {
            // You can directly ask for the permission.
            //requestPermissions(new String[] { Manifest.permission.BLUETOOTH_CONNECT }, REQUEST_BT_PERMISSION);
            requestPermission(Manifest.permission.BLUETOOTH_CONNECT, REQUEST_BT_PERMISSION);
            Log.d("PERMISSION", "MA ALLORA SEI STRONZO 3!");
        }
    }

    private void showExplanation(int title,
                                 int message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> requestPermission(permission, permissionRequestCode))
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> finish());
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT);
    }
}
