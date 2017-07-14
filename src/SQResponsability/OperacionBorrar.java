/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQResponsability;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sqluc.StringEncrypt;

/**
 *
 * @author Jahyr
 */
public class OperacionBorrar extends OperacionAbstracta{
    String nombreTabla = "";
    String valorDeLlave = "";
    int indiceLlave;
    File archivoTabla;
    File archivoMasterTablas;
    File archivoAuxiliar;
    boolean llaveEncriptada;
    String key = "92AE31A79FEEB2A3"; //llave
    String iv = "0123456789ABCDEF"; // vector de inicialización
    
    public OperacionBorrar(int clave){
        this.clave = clave;
    }
    
    @Override
    protected void accion(String[] vectorQuery){
        if (todoCorrecto(vectorQuery)){
            CsvReader lectorArchivoTabla;
            CsvWriter salidaParaArchivo;
            CsvReader lectorArchivoAuxiliar;
            CsvWriter salidaParaArchivoAuxiliar;
            archivoAuxiliar = new File("Auxiliar.csv");
            boolean existeLlave = false;
            boolean primeraVez = true;
            try {
                lectorArchivoTabla = new CsvReader(nombreTabla + ".csv", ',');
                salidaParaArchivoAuxiliar = new CsvWriter(
                        new FileWriter(archivoAuxiliar), ',');
                while (lectorArchivoTabla.readRecord()) {
                    String[] recordActual = lectorArchivoTabla.getValues();
                    
                    if(llaveEncriptada){
                        if (primeraVez){
                            salidaParaArchivoAuxiliar.writeRecord(recordActual);
                            primeraVez = false;
                        } else {
                            if (!StringEncrypt.decrypt(key, iv, recordActual[indiceLlave])
                                    .equals(valorDeLlave)){
                                salidaParaArchivoAuxiliar.writeRecord(recordActual);
                                existeLlave = true;
                            }
                        }
                    } else {
                        if (!recordActual[indiceLlave].equals(valorDeLlave)) {
                            salidaParaArchivoAuxiliar.writeRecord(recordActual);
                            existeLlave = true;
                        }
                    }

                }
                lectorArchivoTabla.close();
                salidaParaArchivoAuxiliar.close();
                
                if (existeLlave) {
                    lectorArchivoAuxiliar = new CsvReader("Auxiliar.csv", ',');
                    salidaParaArchivo = new CsvWriter(
                                new FileWriter(archivoTabla), ',');
                    while (lectorArchivoAuxiliar.readRecord()) {
                        String[] recordActual = lectorArchivoAuxiliar.getValues();
                        salidaParaArchivo.writeRecord(recordActual);
                    }
                    lectorArchivoAuxiliar.close();
                    salidaParaArchivo.close();
                    (new File("Auxiliar.csv")).delete();
                } else {
                    System.err.println("Error: No existe un registro con esa"
                            + " clave.");
                    (new File("Auxiliar.csv")).delete();
                }
            } catch (FileNotFoundException ex) {
                System.err.println("Error: Archivo no encontrado.");
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
            } catch (Exception ex) {
                System.err.println("Error: Error desencriptando.");
            }
        }
    }
    
    public boolean todoCorrecto(String[] vectorQuery){
        vaciar();
        nombreTabla = vectorQuery[2].replace("-"," ");
        valorDeLlave = vectorQuery[4];
        archivoTabla = new File(nombreTabla+".csv");
                
        if (archivoTabla.exists()){
            CsvReader lectorArchivoMasterTabla;
            try {
                if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                    lectorArchivoMasterTabla = new CsvReader("Master.csv", ',');
                    while (lectorArchivoMasterTabla.readRecord()) {
                        String[] recordActual = lectorArchivoMasterTabla.getValues();
                        if (recordActual[0].equals(nombreTabla)) {
                            String[] vectorDeCampos = recordActual[2].split(";");
                            for (int i = 0; i < vectorDeCampos.length; i++) {
                                if ((vectorDeCampos[i].substring(0, vectorDeCampos[i]
                                        .indexOf('|'))).equals(recordActual[1])) {
                                    indiceLlave = i;
                                }
                            }
                            if (recordActual.length == 4) {
                                String[] camposEncriptados;
                                camposEncriptados = recordActual[3].split(";");
                                for (int i = 0; i < camposEncriptados.length; i++) {
                                    if ((camposEncriptados[i].substring(0, camposEncriptados[i]
                                            .indexOf('|'))).equals(recordActual[1])) {
                                        llaveEncriptada = true;
                                        lectorArchivoMasterTabla.close();
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    lectorArchivoMasterTabla.close();
                } else {
                    System.err.println("Error: La tabla:" + nombreTabla
                            + " no existe.");
                }
            } catch (FileNotFoundException ex) {
                System.err.println("Error: Archivo no encontrado.");
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
            }
        } else {
            System.err.println("Error: La tabla:" + nombreTabla + " no existe.");
        }
        return false;
    }
    
    public void vaciar(){
        nombreTabla = "";
        valorDeLlave = "";
        indiceLlave = -1;
        archivoTabla = null;
        archivoMasterTablas = null;
        archivoAuxiliar = null;
        llaveEncriptada = false;
    }
}
