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

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import es.miapp.psp.saincahi.R;

import static es.miapp.psp.saincahi.MainActivity.TAG;

public class PermissionActivity extends AppCompatActivity {

    private static final int PERMISOS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_layout);

        clasePermisos();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void clasePermisos() {
        Button bTEstado = findViewById(R.id.bTEstado);
        Button bTContactos = findViewById(R.id.bTContactos);
        Button bTRegistro = findViewById(R.id.bTRegistro);

        bTEstado.setOnClickListener(e -> {
            int permiso1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

            if (permiso1 == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Tengo permisos de READ PHONE STATE");
                bTEstado.setBackgroundColor(Color.BLACK);
                bTEstado.setEnabled(false);
                bTEstado.setTextColor(Color.GREEN);
            } else {
                bTEstado.setTextColor(Color.RED);
                Log.v(TAG, "No tengo permisos de READ PHONE STATE");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISOS);
            }
        });

        bTContactos.setOnClickListener(e -> {
            int permiso2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
            int permiso3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG);

            if (permiso2 == PackageManager.PERMISSION_GRANTED && permiso3 == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Tengo permisos de READ/WRITE CALL LOG");
                bTContactos.setBackgroundColor(Color.BLACK);
                bTContactos.setEnabled(false);
                bTContactos.setTextColor(Color.GREEN);
            } else {
                bTContactos.setTextColor(Color.RED);
                Log.v(TAG, "No tengo permisos de READ/WRITE CALL LOG");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG}, PERMISOS);
                }
            }
        });

        bTRegistro.setOnClickListener(e -> {
            int permiso3 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
            if (permiso3 == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Tengo permisos de READ CONTACTS");
                bTRegistro.setBackgroundColor(Color.BLACK);
                bTRegistro.setEnabled(false);
                bTRegistro.setTextColor(Color.GREEN);
            } else {
                bTRegistro.setTextColor(Color.RED);
                Log.v(TAG, "No tengo permisos de READ CONTACTS");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISOS);
            }
        });
    }
}