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

package es.miapp.psp.saincahi.receptores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import static es.miapp.psp.saincahi.MainActivity.TAG;

/**
 * Clase para controlar las llamadas entrantes
 *
 * @author Oleg Fdez-Llebrez Rdgz
 * @version 22/09/2016 - v0.1-alpha
 * @see <a href="https://github.com/Oleg37/llamadas-entrantes/tree/main"/> master branch </a>
 * @since v0.1-alpha
 */

public class IncomingCallReceiver extends BroadcastReceiver {

    String numTlf;

    @Override
    public void onReceive(Context context, Intent i) {
        Log.v(TAG, "Receiver de Incoming Calls Receiver");

        if (i.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Toast.makeText(context, "Llamada cogida", Toast.LENGTH_SHORT).show();
        } else if (i.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Toast.makeText(context, "Llamada finalizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Llamada entrante", Toast.LENGTH_SHORT).show();
        }

        numTel(i, context);
    }

    private void numTel(Intent i, Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                numTlf = i.getStringExtra(incomingNumber);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}