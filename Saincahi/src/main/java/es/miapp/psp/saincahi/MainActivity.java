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

package es.miapp.psp.saincahi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;

import es.miapp.psp.saincahi.permisos.PermissionActivity;
import es.miapp.psp.saincahi.receptores.IncomingCallReceiver;
import es.miapp.psp.saincahi.vistas.GuardarFichero;
import es.miapp.psp.saincahi.vistas.VerFichero;

/**
 * Esta clase es la principal la que ejecutará al iniciarse nuestra aplicación
 *
 * @author Oleg Fdez-Llebrez Rdgz
 * @version 22/09/2016 - v0.1-alpha
 * @see <a href="https://github.com/Oleg37/llamadas-entrantes/tree/main"/> master branch </a>
 * @since v0.1-alpha
 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "xyzxy -> " + MainActivity.class.getName();

    private final IncomingCallReceiver incomingCallReceiver = new IncomingCallReceiver();
    private TextView tVPermisosInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tVPermisosInicio = findViewById(R.id.tVPermisosInicio);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.oPTPermisos || !permisos()) {
            Toast.makeText(this, "Redirigiendo a permisos", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, PermissionActivity.class));
            return true;
        } else if (permisos()) {
            switch (item.getItemId()) {
                case R.id.oPTTema:
                    // TODO, NO IMPLEMENTADO AÚN
                    Toast.makeText(this, "Has presionado cambiar de tema", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.oPTHistorial:
                    Toast.makeText(this, "Has seleccionado mostrar el historial", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, VerFichero.class));
                    return true;
                case R.id.oPGuardar:
                    Toast.makeText(this, "Has seleccionado guardar en fichero", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, GuardarFichero.class));
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(incomingCallReceiver, new IntentFilter());
        if (incomingCallReceiver.isOrderedBroadcast()) {
            Log.v(TAG, "Buenos días");
        }
    }

    private boolean permisos() {
        int permiso1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permiso2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
        int permiso3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG);
        int permiso4 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int general = PackageManager.PERMISSION_GRANTED;
        return permiso1 == general && permiso2 == general && permiso3 == general && permiso4 == general;
    }
}