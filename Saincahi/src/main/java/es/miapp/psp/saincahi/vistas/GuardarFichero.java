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
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.miapp.psp.saincahi.Contactos;
import es.miapp.psp.saincahi.R;
import es.miapp.psp.saincahi.util.ContactoComparator;

public class GuardarFichero extends AppCompatActivity {

    String nameCall = null;
    String incomingNumber;
    SwitchMaterial sWModo;
    private Button bTGuardarMemInterna, bTLeerMemInterna, bTBorrarFicheroInterno,
            bTGuardarMemExterna, bTLeerMemExterna, bTBorrarFicheroExterno;
    private TextView eTGuardar;
    private boolean isCheckedBool = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardar_en_fichero);

        init();
    }

    private void init() {
        eTGuardar = findViewById(R.id.eTGuardar);

        bTGuardarMemInterna = findViewById(R.id.bTMemInterna);
        bTLeerMemInterna = findViewById(R.id.bTLeerMemInterna);
        bTBorrarFicheroInterno = findViewById(R.id.bTBorrarFicheroInterno);
        bTGuardarMemExterna = findViewById(R.id.bTMemExterna);
        bTLeerMemExterna = findViewById(R.id.bTLeerMemExterna);
        bTBorrarFicheroExterno = findViewById(R.id.bTBorrarFicheroExterno);

        bTGuardarMemInterna.setEnabled(false);
        bTGuardarMemInterna.setTextColor(Color.GRAY);
        bTLeerMemInterna.setEnabled(false);
        bTLeerMemInterna.setTextColor(Color.GRAY);
        bTBorrarFicheroInterno.setEnabled(false);
        bTBorrarFicheroInterno.setTextColor(Color.GRAY);


        eTGuardar.setText("");

        leerContactos();

        bTGuardarMemInterna.setOnClickListener(v -> getContactosLlamada());
        bTLeerMemInterna.setOnClickListener(v -> leerContactos());
        bTBorrarFicheroInterno.setOnClickListener(v -> resetContenido());
        bTGuardarMemExterna.setOnClickListener(v -> getContactosLlamada());
        bTLeerMemExterna.setOnClickListener(v -> leerContactos());
        bTBorrarFicheroExterno.setOnClickListener(v -> resetContenido());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu_bar, menu);

        MenuItem itemSwitch = menu.findItem(R.id.sWGuardar);
        itemSwitch.setActionView(R.layout.use_switch);

        sWModo = menu.findItem(R.id.sWGuardar).getActionView().findViewById(R.id.SwitchModo);

        sWModo.setChecked(true);

        sWModo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCheckedBool = false;
                    bTGuardarMemExterna.setEnabled(true);
                    bTGuardarMemExterna.setTextColor(Color.rgb(146, 248, 83));
                    bTGuardarMemInterna.setEnabled(false);
                    bTGuardarMemInterna.setTextColor(Color.GRAY);

                    bTLeerMemExterna.setEnabled(true);
                    bTLeerMemExterna.setTextColor(Color.rgb(107, 243, 243));
                    bTLeerMemInterna.setEnabled(false);
                    bTLeerMemInterna.setTextColor(Color.GRAY);

                    bTBorrarFicheroExterno.setEnabled(true);
                    bTBorrarFicheroExterno.setTextColor(Color.rgb(255, 50, 50));
                    bTBorrarFicheroInterno.setEnabled(false);
                    bTBorrarFicheroInterno.setTextColor(Color.GRAY);

                    Toast.makeText(GuardarFichero.this, "Modo Externo", Toast.LENGTH_SHORT).show();
                } else {
                    isCheckedBool = true;
                    bTGuardarMemInterna.setEnabled(true);
                    bTGuardarMemInterna.setTextColor(Color.rgb(146, 248, 83));
                    bTGuardarMemExterna.setEnabled(false);
                    bTGuardarMemExterna.setTextColor(Color.GRAY);

                    bTLeerMemInterna.setEnabled(true);
                    bTLeerMemInterna.setTextColor(Color.rgb(107, 243, 243));
                    bTLeerMemExterna.setEnabled(false);
                    bTLeerMemExterna.setTextColor(Color.GRAY);

                    bTBorrarFicheroInterno.setEnabled(true);
                    bTBorrarFicheroInterno.setTextColor(Color.rgb(255, 50, 50));
                    bTBorrarFicheroExterno.setEnabled(false);
                    bTBorrarFicheroExterno.setTextColor(Color.GRAY);
                    Toast.makeText(GuardarFichero.this, "Modo Interno", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getContactosLlamada() {
        eTGuardar.setText("");
        resetContenido();
        Cursor curIncomingCall = getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                null);

        curIncomingCall.moveToFirst();
        do {
            if (!(curIncomingCall.getCount() == 0)) {
                if (!eTGuardar.toString().contains("historial")) {
                    incomingNumber = curIncomingCall.getString(curIncomingCall.getColumnIndex(CallLog.Calls.NUMBER));
                    String incomingFecha = curIncomingCall.getString(curIncomingCall.getColumnIndex(CallLog.Calls.DATE));

                    // Obtener nombre del contacto teniendo de referencia la agenda de contactos (contactos guardados)

                    hiloNombre hiloNombre = new hiloNombre();
                    hiloNombre.run();

                    // Transformaciones Y Resolución
                    Date callDayTime = new Date(Long.parseLong(incomingFecha));

                    String nombreAgenda;
                    nombreAgenda = hiloNombre.getName == null ? "Desconocido" : hiloNombre.getName;

                    Contactos contacto = new Contactos(callDayTime, Integer.parseInt(incomingNumber), nombreAgenda);

                    Log.v("contacto", new SimpleDateFormat("yyyy; MM; dd; HH; mm; ss", Locale.getDefault()).format(callDayTime) + "; " + incomingNumber + "; " + nombreAgenda);

                    guardarContactos(contacto);
                }
                eTGuardar.setText("");
            } else
                eTGuardar.setText("No ha un historial reciente.\n\nHaz que te llamen para registrar la primera llamada.");
        } while (curIncomingCall.moveToNext());
        curIncomingCall.close();
        Toast.makeText(this, "¡Nueva lista de contactos generada!", Toast.LENGTH_SHORT).show();
    }

    private List<Contactos> getContactos() {
        List<Contactos> listaDeContactos = new ArrayList<>();
        File f;
        if (isCheckedBool) {
            f = new File(getExternalFilesDir(null), "coches.csv");
        } else f = new File(getFilesDir(), "coches.csv");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                listaDeContactos.add(Contactos.fromCSVString(linea, ";"));
            }
            br.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
//        Collections.sort(listaDeContactos); // compareTo, Ordeno
        return listaDeContactos;
    }

    private void guardarContactos(Contactos contacto) {
        List<Contactos> contactosList = getContactos();
        contactosList.add(contacto);
        // Collestion.sort(coches); //ordena usando el compareTo() de coche
//        Collections.sort(coches);
        Collections.sort(contactosList, new ContactoComparator()); // Ordenamos al revés, usando el compare() de la clase CocheComparator

//            boolean resultado = true;
        File f;

        if (isCheckedBool) {
            f = new File(getExternalFilesDir(null), "coches.csv");
        } else f = new File(getFilesDir(), "coches.csv");

        FileWriter fw;
        try {
            fw = new FileWriter(f);
            if (isCheckedBool) {
                for (Contactos c : contactosList) {
                    fw.write(c.toCSV() + "\n");
                }
            } else {
                for (Contactos c : contactosList) {
                    fw.write(c.toCSV() + "\n");
                }
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
//                resultado = false;
        }
//            return resultado;
    }

    private void leerContactos() {
        File f;
        if (isCheckedBool) {
            f = new File(getExternalFilesDir(null), "coches.csv");
        } else f = new File(getFilesDir(), "coches.csv");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            StringBuilder texto = new StringBuilder();

            if (br.readLine() == null)
                Toast.makeText(this, "No hay nada que leer", Toast.LENGTH_SHORT).show();

            while ((linea = br.readLine()) != null) {
                Contactos.fromCSVString(linea, ";");
                texto.append(linea);
                texto.append("\n");
            }
            eTGuardar.setText(texto);
            br.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void resetContenido() {
        File f;
        if (isCheckedBool) {
            f = new File(getExternalFilesDir(null), "coches.csv");
            Toast.makeText(this, "Contenido memoria externa borrado", Toast.LENGTH_SHORT).show();
            Log.v("RUTA-1", f.getAbsolutePath());
        } else {
            f = new File(getFilesDir(), "coches.csv");
            Toast.makeText(this, "Contenido memoria interna borrado", Toast.LENGTH_SHORT).show();
            Log.v("RUTA-2", f.getAbsolutePath());
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class hiloNombre implements Runnable {
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

    private class hiloGuardar implements Runnable {

        Contactos contacto;

        public hiloGuardar(Contactos contacto) {
            this.contacto = contacto;
        }

        @Override
        public void run() {
        }
    }
}