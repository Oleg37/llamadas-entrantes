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

package es.miapp.psp.saincahi.util;

import java.util.Comparator;

import es.miapp.psp.saincahi.Contactos;

public class ContactoComparator implements Comparator<Contactos> {
    @Override
    public int compare(Contactos con1, Contactos con2) {
//        int sort = o1.getMarca().compareTo(o2.getMarca());
//        if (sort == 0) {
//            sort = o1.getModelo().compareTo(o2.getModelo());
//            if (sort == 0)
//                sort = o1.getYear() - o2.getYear();
//        }
//        return -sort;
//        return -o1.compareTo(o2);
//        return o2.compareTo(o1);

        int sort = con1.getFecha().compareTo(con2.getFecha());
        if (sort == 0) {
            sort = con1.getNombre().compareTo(con2.getNombre());
        }
        return sort;
    }
}