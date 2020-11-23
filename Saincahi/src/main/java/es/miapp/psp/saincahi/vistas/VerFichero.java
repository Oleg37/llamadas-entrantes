/*
 * Copyright (c) 2020. Saincahi Project.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete
 * source code of licensed works and modifications, which include larger works using a licensed
 * work, under the same license. Copyright and license notices must be preserved. Contributors
 * provide an express grant of patent rights.
 */

package es.miapp.psp.saincahi.vistas;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

import es.miapp.psp.saincahi.R;

public class VerFichero extends AppCompatActivity {

    private TextView eTResultado;
    String nameCall = null;
    String incomingNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_fichero);

        init();
    }

    private void init() {
        eTResultado = findViewById(R.id.eTResultado);
        Button bTObtenerDatos = findViewById(R.id.bTObtenerDatos);
        Button bTEliminar = findViewById(R.id.bTEliminar);

        eTResultado.isInEditMode();

        bTObtenerDatos.setOnClickListener(v -> {
            eTResultado.setText("");
            getContactos();

            if (eTResultado.getText().toString().isEmpty()) {
                Toast.makeText(this, "No hay llamadas recientes", Toast.LENGTH_SHORT).show();
            }
        });

        bTEliminar.setOnClickListener(v -> {
            this.getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);
            eTResultado.setText("");
        });
    }

    private void getContactos() {
        Cursor curIncomingCall = getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                null);
        curIncomingCall.moveToFirst();

        do {
            if (!(curIncomingCall.getCount() == 0)) {
                if (!eTResultado.toString().contains("historial")) {

                    incomingNumber = curIncomingCall.getString(curIncomingCall.getColumnIndex(CallLog.Calls.NUMBER));
                    String incomingFecha = curIncomingCall.getString(curIncomingCall.getColumnIndex(CallLog.Calls.DATE));

                    // Obtener nombre del contacto teniendo de referencia la agenda de contactos (contactos guardados)

                    hiloNombre hiloNombre = new hiloNombre();
                    hiloNombre.run();

                    // Transformaciones Y Resolución
                    Date callDayTime = new Date(Long.parseLong(incomingFecha));
                    String fechaModificada = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(callDayTime);

                    int pos = curIncomingCall.getPosition() + 1;

                    String nombreAgenda;
                    nombreAgenda = hiloNombre.getName == null ? "Desconocido" : hiloNombre.getName;

                    eTResultado.append(toString2(pos, fechaModificada, incomingNumber, nombreAgenda));

                } else eTResultado.setText("");
            } else
                eTResultado.setText("No ha un historial reciente.\n\nHaz que te llamen para registrar la primera llamada.");
        } while (curIncomingCall.moveToNext());
        curIncomingCall.close();
    }

    private String toString2(int pos, String fecha, String numero, String nombre) {
        return "Llamada -> " + pos
                + "\nFecha: " + fecha
                + "\nNúmero: " + numero
                + "\nNombre: " + nombre + "\n\n";
    }

    public class hiloNombre implements Runnable {
        String getName = nameCall;

        @Override
        public void run() {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(incomingNumber));
            Cursor cursor = getContentResolver().query(uri, new String[]{
                            ContactsContract.Contacts.DISPLAY_NAME},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst())
                getName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            cursor.close();
        }
    }
}