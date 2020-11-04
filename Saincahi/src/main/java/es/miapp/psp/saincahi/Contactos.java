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

import java.util.Objects;

public class Contactos implements Comparable<Contactos> {

    private final int year;
    private final int mes;
    private final int dia;
    private final int hora;
    private final int min;
    private final int seg;
    private final int numTele;
    private final String nombre;

    public Contactos(int year, int mes, int dia, int hora, int min, int seg, int numTele, String nombre) {
        this.year = year;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.min = min;
        this.seg = seg;
        this.numTele = numTele;
        this.nombre = nombre;
    }

    public Contactos() {
        this(0, 0, 0, 0, 0, 0, 0, null);
    }

    public static Contactos fromCSVString(String csv, String separator) {
        Contactos contacto = null;
        String[] partes = csv.split(separator);

        if (partes.length == 8) {
            contacto = new Contactos(Integer.parseInt(partes[0].trim()),
                    Integer.parseInt(partes[1].trim()),
                    Integer.parseInt(partes[2].trim()),
                    Integer.parseInt(partes[3].trim()),
                    Integer.parseInt(partes[4].trim()),
                    Integer.parseInt(partes[5].trim()),
                    Integer.parseInt(partes[6].trim()),
                    partes[7].trim());
        }
        return contacto;
    }

    public String toCSV() {
        return year + "; " + mes + "; " + dia + "; " + hora + "; " + min + "; " + seg + "; " + numTele + "; " + nombre;
    }

    public String toCSVV2() {
        return nombre + "; " + year + "; " + mes + "; " + dia + "; " + hora + "; " + min + "; " + seg + "; " + numTele;
    }

    public int getYear() {
        return year;
    }

    public int getMes() {
        return mes;
    }

    public int getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public int getMin() {
        return min;
    }

    public int getSeg() {
        return seg;
    }

    public int getNumTele() {
        return numTele;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Contactos{" +
                "year=" + year +
                ", mes=" + mes +
                ", dia=" + dia +
                ", hora=" + hora +
                ", min=" + min +
                ", seg=" + seg +
                ", numTele=" + numTele +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contactos contactos = (Contactos) o;
        return year == contactos.year &&
                mes == contactos.mes &&
                dia == contactos.dia &&
                hora == contactos.hora &&
                min == contactos.min &&
                seg == contactos.seg &&
                numTele == contactos.numTele &&
                nombre.equals(contactos.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numTele);
    }

    @Override
    public int compareTo(Contactos o) {
        return 0;
    }

    //    @Override
//    public int compareTo(Coche o) {
//        //-1, 0, 1 -x <, 0 =, +x >
////        int sortMarca = this.marca.compareTo(o.marca);
////        if (sortMarca != 0)
////            return sortMarca;
////        int sortModelo = modelo.compareTo(o.modelo);
////        if (sortModelo != 0)
////            return sortModelo;
////        return year - o.year;
//        int sort = this.marca.compareTo(o.marca);
//        if (sort == 0) {
//            sort = modelo.compareTo(o.modelo);
//            if (sort == 0)
//                sort = year - o.year;
//        }
//        return sort;
//    }
}