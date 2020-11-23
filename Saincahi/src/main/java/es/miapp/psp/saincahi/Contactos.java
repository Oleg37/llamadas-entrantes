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

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Contactos implements Comparable<Contactos> {

    private final int numTele;
    private final String nombre;
    private final Date fecha;

    public Contactos(Date fecha, int numTele, String nombre) {
        this.fecha = fecha;
        this.numTele = numTele;
        this.nombre = nombre;
    }

    public Contactos() {
        this(new Date(), 0, "");
    }

    public static Contactos fromCSVString(String csv, String separator) throws ParseException {
        Contactos contacto = null;
        String[] partes = csv.split(separator);

//        Log.v("contactoFromCSVString-1", " " + contacto + " Largo de array: " + partes.length);

        if (partes.length == 8) {
            String fechaCorrecta = partes[0] + "; " + partes[1] + "; " + partes[2] + "; " + partes[3] + "; " + partes[4] + "; " + partes[5];
            Date fechaFromCSVString = new SimpleDateFormat("yyyy; MM; dd; HH; mm; ss", Locale.getDefault()).parse(fechaCorrecta);
//            Log.v("contactoFromFecha", String.valueOf(fechaFromCSVString));
            contacto = new Contactos(fechaFromCSVString,
                    Integer.parseInt(partes[6].trim()),
                    partes[7].trim());
        }
//        Log.v("contactoFromCSVString-2", " " + contacto);
        return contacto;
    }

    public String toCSV() {
        Log.v("contactoCSV", new SimpleDateFormat("yyyy; MM; dd; HH; mm; ss", Locale.getDefault()).format(fecha) + "; " + numTele + "; " + nombre);
        return new SimpleDateFormat("yyyy; MM; dd; HH; mm; ss", Locale.getDefault()).format(fecha) + "; " + numTele + "; " + nombre;
    }

    public String toCSVV2() {
        Log.v("contactoCSV", nombre + "; " + new SimpleDateFormat("yyyy; MM; dd; HH; mm; ss", Locale.getDefault()).format(fecha) + "; " + numTele);
        return nombre + "; " + new SimpleDateFormat("yyyy; MM; dd; HH; mm; ss", Locale.getDefault()).format(fecha) + "; " + numTele;
    }

    public int getNumTele() {
        return numTele;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contactos contactos = (Contactos) o;
        return numTele == contactos.numTele &&
                Objects.equals(nombre, contactos.nombre) &&
                Objects.equals(fecha, contactos.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numTele, nombre, fecha);
    }

    @Override
    public int compareTo(Contactos o) {
        int sort = this.getFecha().compareTo(o.getFecha());
        if (sort == 0) {
            sort = this.getNombre().compareTo(o.getNombre());
        }
        return sort;
    }
}