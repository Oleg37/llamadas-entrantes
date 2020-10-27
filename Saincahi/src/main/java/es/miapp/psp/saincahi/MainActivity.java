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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Esta clase es la principal la que ejecutará al iniciarse nuestra aplicación
 *
 * @author Oleg Fdez-Llebrez Rdgz
 * @version 22/09/2016 - v0.1-alpha
 * @see <a href="https://github.com/Oleg37/llamadas-entrantes/tree/main"/> master branch </a>
 * @since v0.1-alpha
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}