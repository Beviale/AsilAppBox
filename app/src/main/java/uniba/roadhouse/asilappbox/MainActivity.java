package uniba.roadhouse.asilappbox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.io.IOException;

import uniba.roadhouse.asilappbox.fragments.MainPageFragment;
import uniba.roadhouse.asilappbox.fragments.WaitingForClient;
import uniba.roadhouse.asilappbox.utils.BoxServerBT;

public class MainActivity extends AppCompatActivity {
    public static MainActivity Instance;
    private FragmentManager fragmentManager;
    private BoxServerBT boxServerBT;
    private BluetoothAdapter bluetoothAdapter;
    private final int REQUEST_BT_PERMISSION = 1;
    private boolean permissionChecked = false;

    private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                if (result.get(Manifest.permission.BLUETOOTH_CONNECT) &&
                        result.get(Manifest.permission.BLUETOOTH_ADVERTISE)) {
                    // PERMISSION GRANTED
                    Log.d("BLUETOOTH", "PERMISION_GRANTED");
                    checkBTEnabled();
                } else {
                    // PERMISSION NOT GRANTED
                    Log.d("BLUETOOTH", "PERMISSION_DENIED");
                    finish();
                }
            }
    );

    private ActivityResultLauncher<Intent> bluetoothEnableResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResultCallback<ActivityResult>) result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    //se l'utente ha attivato il bluetooth avvio a ricerca dei dispositivi
                    Log.d("BLUETOOTH", "ENABLED");
                    checkVisibility();
                } else {
                    //se l'utente non ha attivato il bluetooth vado alla home
                    Log.d("BLUETOOTH", "DISABLED");
                    finish();
                }
            });

    private final BroadcastReceiver bluetoothStateChangedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch(state) {
                    case BluetoothAdapter.STATE_OFF:
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        // chiudo la socket e riavvio l'applicazione
                        try {
                            closeServerSocket();
                            restartApplication();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case BluetoothAdapter.STATE_ON:
                    case BluetoothAdapter.STATE_TURNING_ON:
                        // do nothing
                        break;
                }

            }
        }
    };

    private ActivityResultLauncher<Intent> bluetoothVisibilityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResultCallback<ActivityResult>) result -> {
                if (result.getResultCode() != Activity.RESULT_CANCELED) {
                    //se l'utente ha attivato il bluetooth avvio a ricerca dei dispositivi
                    Log.d("BLUETOOTH", "VISIBLE");
                    this.changeScreen(R.id.main_fragment_container, WaitingForClient.class);
                } else {
                    //se l'utente non ha attivato il bluetooth vado alla home
                    Log.d("BLUETOOTH", "INVISIBLE");
                    finish();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disattivo la modalità scusa
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        // Imposto il colore della status bar
        Window window = this.getWindow();
        window.setStatusBarColor(getColor(R.color.appBarColor));

        Instance = this;
        fragmentManager = getSupportFragmentManager();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(bluetoothStateChangedReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        if (!permissionChecked)
            checkOrRequestBTPermission();
    }

    /***
     * Metodo che permette, mediante l'istanza dell'Activity, il cambio di schermata
     * @param fragmentContainer container target
     * @param newFragment nuovo fragment da visualizzare
     * @param addToBackStack booleano per l'aggiunta al backstack (se omesso di default viene aggiunto)
     */
    public void changeScreen(int fragmentContainer, Class<? extends Fragment> newFragment, boolean addToBackStack) {
        //apro il fragment indicato
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, newFragment, null);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /***
     * Metodo che permette, mediante l'istanza dell'Activity, il cambio di schermata
     * (aggiunge di default il fragment precedente al backstack)
     * @param fragmentContainer container target
     * @param newFragment nuovo fragment da visualizzare
     */
    public void changeScreen(int fragmentContainer, Class<? extends Fragment> newFragment) {
        //apro il fragment indicato
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, newFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Metodo che permette, mediante l'istanza dell'Activity, il cambio di schermata
     * @param fragmentContainer container target
     * @param newFragment nuovo fragment da visualizzare
     * @param addToBackStack booleano per l'aggiunta al backstack
     */
    public void changeScreen(int fragmentContainer, Fragment newFragment, boolean addToBackStack){
        //apro il fragment indicato
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, newFragment);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void popFragmentFromBackStack() {
        this.fragmentManager.popBackStack();
    }

    /***
     * Metodo per la verifica, e gestione, del possesso del permesso BLUETOOTH_ADVERTISE
     */
    public void checkOrRequestBTPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            checkBTEnabled();
            bluetoothAdapter.setName(BoxServerBT.DEVICE_NAME);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.BLUETOOTH_ADVERTISE)) {
            showExplanation(R.string.permission_needed, R.string.bt_permission_rationale, new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_ADVERTISE});
        } else {
            // You can directly ask for the permission.
            permissionChecked = true;
            requestPermission(new String[]{Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT});
        }
    }

    /***
     * Metodo per poter stampare a schermo un Dialog informativo per l'utente.
     * @param title titolo del permesso
     * @param message messaggio informativo
     * @param permissions array di nomi di permessi
     */
    private void showExplanation(int title, int message, final String[] permissions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> requestPermission(permissions))
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> finish());
        builder.create().show();
    }

    /***
     * Chiamata alla funzione di callback per la richiesta del permesso
     * @param permissionName nome del permesso
     */
    private void requestPermission(String[] permissionName) {
        requestPermissionLauncher.launch(permissionName);
    }

    /***
     * Metodo per verificare se il BluetoothAdapter è abilitato e può essere utilizzato
     */
    private void checkBTEnabled() {
        if (bluetoothAdapter.isEnabled()) {
            // BT attivo
            checkVisibility();
        } else {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            bluetoothEnableResult.launch(intent);
        }
    }

    /**
     * Metodo per verificare la visibilità dell'applicazione ed impostare un periodo di extra discoverability.
     */
    private void checkVisibility() {
        permissionChecked = true;
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        bluetoothVisibilityResult.launch(discoverableIntent);
    }

    /**
     * Metodo per la creazione di una nuova istanza del BoxServerBT e run del thread
     */
    public void openServerSocket(){
        boxServerBT = new BoxServerBT();
        boxServerBT.start();
    }

    /**
     * Metodo per la chiusura del BoxServerBT (socket) ed invio di messaggio chiusura al client
     */
    public void closeServerSocket() throws IOException {
        boxServerBT.sendData("CLOSE@CLOSE");
        boxServerBT.cancel();
    }

    public BoxServerBT getBoxServerBT(){
        return boxServerBT;
    }

    public void terminateActivityOnClientDisconnect(){
        boxServerBT.cancel();
        finish();
    }

    /**
     * Metodo per il restart dell'applicazione in caso di perdita di connessione con il client.
     * In questo modo evitiamo di avere connessioni aperte o dover forza l'arresto dell'App.
     */
    private void restartApplication() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Termina il processo corrente
        finish();
        System.exit(0);
    }

}
