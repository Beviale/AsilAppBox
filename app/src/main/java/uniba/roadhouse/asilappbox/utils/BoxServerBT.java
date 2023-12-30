package uniba.roadhouse.asilappbox.utils;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import uniba.roadhouse.asilappbox.MainActivity;
import uniba.roadhouse.asilappbox.R;
import uniba.roadhouse.asilappbox.fragments.MainPageFragment;

public class BoxServerBT extends Thread {
    public static final String DEVICE_NAME = "AsilApp Box";
    private final BluetoothServerSocket SERVER_SOCKET;
    private final BluetoothAdapter BLUETOOTH_ADAPTER = BluetoothAdapter.getDefaultAdapter();
    private OutputStream outputStream;
    private InputStream inputStream;

    public BoxServerBT() {

        // Use a temporary object that is later assigned to serverSocket
        // because serverSocket is final.
         BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            if (ActivityCompat.checkSelfPermission(MainActivity.Instance, Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("BLUETOOTH", UUID.nameUUIDFromBytes(DEVICE_NAME.getBytes()).toString());
                ParcelUuid pUUID = new ParcelUuid(UUID.nameUUIDFromBytes(DEVICE_NAME.getBytes()));
                tmp = BLUETOOTH_ADAPTER.listenUsingRfcommWithServiceRecord(DEVICE_NAME, pUUID.getUuid());
            }
        } catch (IOException e) {
            Log.d("BLUETOOTH", "Socket's listen() method failed", e);
        }
        SERVER_SOCKET = tmp;
        Log.d("BLUETOOTH", "Socket's listen() method SUCCESS");
    }

    public void run() {
        BluetoothSocket socket = null;
        Log.d("BLUETOOTH", "In attesa di connessione...");
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = SERVER_SOCKET.accept();
                Log.d("BLUETOOTH", "Connessione stabilita!");
            } catch (IOException e) {
                Log.d("BLUETOOTH", "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                try {
                    manageMyConnectedSocket(socket);
                    SERVER_SOCKET.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }

        // Ascolta per messaggi da client
        while (true){
            //prendo i dati inviati dal server sottoforma di mappa
            try {
                byte[] messageByte = new byte[0];
                messageByte = new byte[inputStream.available()];
                DataInputStream in = new DataInputStream(inputStream);
                int numBytes = in.read(messageByte);
                if(numBytes>0) {
                    String messageFromServer = new String(messageByte, StandardCharsets.UTF_8);
                    if (messageFromServer.equals("CLOSE")){
                        MainActivity.Instance.terminateActivityOnClientDisconnect();
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void manageMyConnectedSocket(BluetoothSocket socket) throws IOException {
        MainActivity.Instance.changeScreen(R.id.main_fragment_container, MainPageFragment.class, false);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
    }

    /**
     * Chiude il Socket di connessione e termina il Thread.
     */
    public void cancel() {
        try {
            SERVER_SOCKET.close();
            Log.d("BLUETOOTH","Socket chiusa e thread terminato");
        } catch (IOException e) {
            Log.d("BLUETOOTH", "Could not close the connect socket", e);
        }
    }

    /**
     * Metodo che permette di inviare i dati della misurazione (avvenuta con successo) al client.
     * @param testName
     * @param resultData
     * @throws IOException
     */
    public void sendData(String testName, Double resultData) throws IOException {
        String result = testName + "@" + resultData;
        outputStream.write(result.getBytes());
        Log.d("BLUETOOTH","Dati inviati al client");
    }

    /**
     * Metodo che permette di inviare un messaggio al client (es. chiusura connessione, errore, ...)
     * @param msg
     * @throws IOException
     */
    public void sendData(String msg) throws IOException {
        outputStream.write(msg.getBytes());
        Log.d("BLUETOOTH","Inviato messaggio al client");
    }

}
