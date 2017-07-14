/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQResponsability;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jahyr
 */
public class OperacionEliminar extends OperacionAbstracta{
    String nombreTabla = "";
    File archivoTabla;
    File archivoMaster;
    File archivoAuxiliar;
    
    public OperacionEliminar(int clave){
        this.clave = clave;
    }
    
    @Override
    protected void accion(String[] vectorQuery){
        vaciar();
        nombreTabla = vectorQuery[2].replace("-"," ");
        archivoTabla = new File(nombreTabla+".csv");
        archivoMaster = new File("Master.csv");
        archivoAuxiliar = new File("Auxiliar.csv");
        if (archivoTabla.exists()){
            (new File(nombreTabla+".csv")).delete();
            CsvReader lectorArchivoMasterTabla;
            CsvWriter salidaParaMasterTablas;
            CsvReader lectorArchivoAuxiliar;
            CsvWriter salidaParaArchivo;
            try {
                if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                    lectorArchivoMasterTabla = new CsvReader("Master.csv", ',');
                    salidaParaMasterTablas = new CsvWriter(
                            new FileWriter(archivoAuxiliar), ',');
                    while (lectorArchivoMasterTabla.readRecord()) {
                        String[] recordActual = lectorArchivoMasterTabla.getValues();
                        if (!recordActual[0].equals(nombreTabla)) {
                            salidaParaMasterTablas.writeRecord(recordActual);
                        }
                    }
                    lectorArchivoMasterTabla.close();
                    salidaParaMasterTablas.close();

                    lectorArchivoAuxiliar = new CsvReader("Auxiliar.csv", ',');
                    salidaParaArchivo = new CsvWriter(new FileWriter(archivoMaster), ',');
                    while (lectorArchivoAuxiliar.readRecord()) {
                        String[] recordActual = lectorArchivoAuxiliar.getValues();
                        salidaParaArchivo.writeRecord(recordActual);
                    }
                    lectorArchivoAuxiliar.close();
                    salidaParaArchivo.close();
                    (new File("Auxiliar.csv")).delete();
                } else {
                    System.err.println("Error: La tabla: " + nombreTabla
                            + " no existe.");
                }
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
            }
            
        } else {
            System.err.println("Error: La tabla: " + nombreTabla + " no existe.");
        }
    }
    
    public void vaciar(){
        nombreTabla = "";
        archivoTabla = null;
        archivoMaster = null;
        archivoAuxiliar = null;
    }
}
