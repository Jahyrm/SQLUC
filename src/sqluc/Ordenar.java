/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqluc;

import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Jahyr
 */
public class Ordenar {

    private static File archivoAux;
    private static int indice=-1;

    public static File getArchivoOrdenado(String[] titulos,
            List<String[]> registros, boolean descendiente, int indiceCampoAOrdenar) 
            throws IOException {
        vaciar();
        indice = indiceCampoAOrdenar;
        archivoAux = new File("Auxiliar.csv");
        CsvWriter flujoSalida = new CsvWriter(new FileWriter(archivoAux), ',');
        flujoSalida.writeRecord(titulos);
        
        registros = ordenar(registros, descendiente, indice);
        
        for (String[] c : registros) {
            flujoSalida.writeRecord(c);
        }
        flujoSalida.close();
        return archivoAux;
    }

    public static List<String[]> ordenar(List<String[]> registros,
            boolean descending, int indice2) {
        vaciar();
        final int d = descending ? -1 : 1;
        Comparator<String[]> comparator = new Comparator<String[]>() {
            public int compare(String[] c1, String[] c2) {
                if (indice==-1){
                    try {
                        int uno = Integer.parseInt(c1[indice2]);
                        int dos = Integer.parseInt(c2[indice2]);
                        return (uno > dos ? 1
                        : uno == dos ? 0 : -1) * d;
                    } catch (NumberFormatException ex) {
                        return (c1[indice2].compareTo(c2[indice2])) * d;
                    }
                } else {
                    try {
                        int uno = Integer.parseInt(c1[indice]);
                        int dos = Integer.parseInt(c2[indice]);
                        return (uno > dos ? 1
                        : uno == dos ? 0 : -1) * d;
                    } catch (NumberFormatException ex) {
                        return (c1[indice].compareTo(c2[indice])) * d;
                    }
                }
                
            }
        };
        Collections.sort(registros, comparator);
        return registros;
    }
    
    public static void vaciar(){
        archivoAux = null;
        indice=-1;
    }

}
