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
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import es.miapp.psp.saincahi.R;

import static es.miapp.psp.saincahi.MainActivity.TAG;

public class VerFichero extends AppCompatActivity {

    private TextView eTResultado;
    private Button bTObtenerDatos, bTEliminar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_fichero);

        init();
    }

    private void init() {
        eTResultado = findViewById(R.id.eTResultado);
        bTObtenerDatos = findViewById(R.id.bTObtenerDatos);
        bTEliminar = findViewById(R.id.bTEliminar);

        eTResultado.isInEditMode();

        bTObtenerDatos.setOnClickListener(e -> {
            eTResultado.setText("");
            getContactos();
        });

        bTEliminar.setOnClickListener(e -> {
            this.getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);
            eTResultado.setText("");
        });
    }

    private void getContactos() {
//        List<String> datos = new ArrayList<>();
//        String[] datos = new String[]{CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME};

        Cursor curIncomingCall = getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                null);

        int date = curIncomingCall.getColumnIndex(CallLog.Calls.DATE);
        int number = curIncomingCall.getColumnIndex(CallLog.Calls.NUMBER);
        int name = curIncomingCall.getColumnIndex(CallLog.Calls.CACHED_NAME);

        while (curIncomingCall.moveToNext()) {
            String callDate = curIncomingCall.getString(date);
            Date callDayTime = new Date(Long.parseLong(callDate));
            String phNumber = curIncomingCall.getString(number);
            String namePh = curIncomingCall.getString(name);

            String fechaFinal = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(callDayTime);

            String fechaFinal2 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(callDayTime);

            int pos = curIncomingCall.getPosition() + 1;

            namePh = namePh == null ? "Desconocido" : namePh;
            eTResultado.append("Llamada -> " + pos
                    + "\nFecha: " + fechaFinal
                    + "\nNúmero: " + phNumber
                    + "\nNombre: " + namePh + "\n\n");


            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.FRANCE);

            String fechaFinal3;

            try {
                Date date1 = format.parse(fechaFinal2);
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date1);

                int agno = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH) + 1;
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);

                fechaFinal3 = agno + "; " + mes + "; " + dia + "; " + hora + "; " + minuto + "; " + second + "; " + phNumber + "; " + namePh + ";\n";

                Log.v(TAG, fechaFinal3);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        curIncomingCall.close();
    }
}
