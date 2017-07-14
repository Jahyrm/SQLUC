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
import java.util.Arrays;
/**
 *
 * @author Jahyr
 */
public class OperacionModificar extends OperacionAbstracta {
    String nombreTabla = "";
    String campoOriginal = "";
    String nuevoCampo = "";
    File archivoTabla;
    File archivoMasterTablas;
    File archivoAuxiliar;
    
    public OperacionModificar(int clave){
        this.clave = clave;
    }
    
    @Override
    protected void accion(String[] vectorQuery){
        if(verificarTabla(vectorQuery)){
            archivoTabla = new File(nombreTabla+".csv");
            archivoMasterTablas = new File("Master.csv");
            archivoAuxiliar = new File("Auxiliar.csv");
            CsvReader lectorArchivoTabla;
            CsvWriter salidaParaTabla;
            CsvReader lectorArchivoMasterTabla;
            CsvWriter salidaParaMasterTablas;
            try {
                lectorArchivoTabla = new CsvReader(nombreTabla+".csv");
                lectorArchivoMasterTabla = new CsvReader("Master.csv", ',');
                lectorArchivoTabla.readHeaders();
                String[] campos = lectorArchivoTabla.getHeaders();
                for(int i=0; i<campos.length; i++){
                    if (campos[i].equals(campoOriginal)){
                        campos[i] = nuevoCampo;
                    }
                }
                salidaParaTabla = new CsvWriter(
                        new FileWriter(archivoAuxiliar),',');
                salidaParaTabla.writeRecord(campos);
                while (lectorArchivoTabla.readRecord()){
                    String[] recordActual = lectorArchivoTabla.getValues();
                    salidaParaTabla.writeRecord(recordActual);
		}
                lectorArchivoTabla.close();
                salidaParaTabla.close();
                
                escribirDeAuxiliarAArchivo(archivoTabla, 0);
                
                salidaParaMasterTablas = new CsvWriter(
                        new FileWriter(archivoAuxiliar),',');
                while (lectorArchivoMasterTabla.readRecord()){
                    String[] recordActual = lectorArchivoMasterTabla.getValues();
                    if (recordActual[0].equals(nombreTabla)){
                        if (recordActual[1].equals(campoOriginal)){
                            recordActual[1] = nuevoCampo;
                        }
                        recordActual[2] = recordActual[2].replace(campoOriginal,
                                nuevoCampo);
                        if (recordActual.length==4){
                            recordActual[3] = recordActual[3].replace(campoOriginal,
                                nuevoCampo);
                        }
                    }
                    salidaParaMasterTablas.writeRecord(recordActual);
		}
                lectorArchivoMasterTabla.close();
                salidaParaMasterTablas.close();
                
                escribirDeAuxiliarAArchivo(archivoMasterTablas, 1);
                
            } catch (FileNotFoundException ex) {
                System.err.println("Error: Archivo no encontrado.");
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
            }
        }
    }
    
    public void escribirDeAuxiliarAArchivo(File archivo, int num) {
        CsvReader lectorArchivoAuxiliar;
        CsvWriter salidaParaArchivo;
        try {
            lectorArchivoAuxiliar = new CsvReader("Auxiliar.csv", ',');
            salidaParaArchivo = new CsvWriter(new FileWriter(archivo), ',');
            while (lectorArchivoAuxiliar.readRecord()) {
                String[] recordActual = lectorArchivoAuxiliar.getValues();
                salidaParaArchivo.writeRecord(recordActual);
            }
            lectorArchivoAuxiliar.close();
            salidaParaArchivo.close();
            (new File("Auxiliar.csv")).delete();
        } catch (FileNotFoundException ex) {
            System.err.println("Error: Archivo no encontrado.");
        } catch (IOException ex) {
            System.err.println("Error: No se tienen los permisos.");
        }
    }
    
    public boolean verificarTabla(String[] vectorQuery){
        vaciar();
        nombreTabla = vectorQuery[2].replace("-"," ");
        campoOriginal = vectorQuery[4].replace("-"," ");
        nuevoCampo = vectorQuery[6].replace("-"," ");
        archivoTabla = new File(nombreTabla + ".csv");
        
        if (!archivoTabla.exists()) {
            System.err.println("Error: La tabla:" + nombreTabla + " no existe.");
        } else {
            CsvReader lectorArchivoTabla;
            try {
                if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                    lectorArchivoTabla = new CsvReader(nombreTabla + ".csv");
                    if (lectorArchivoTabla.readHeaders()) {
                        String[] campos = lectorArchivoTabla.getHeaders();
                        if (Arrays.asList(campos).contains(campoOriginal)) {
                            if (!Arrays.asList(campos).contains(nuevoCampo)){
                                lectorArchivoTabla.close();
                                return true;
                            } else {
                                System.err.println("Error: No se puede "
                                        +"modificar por el campo: \""
                                        +nuevoCampo+"\" por que ya existe un"
                                        +" campo con ese nombre en la tabla.");
                            }

                        } else {
                            System.err.println("Error: La tabla: "+nombreTabla
                                    +", no contiene el campo: "+campoOriginal
                                    +".");
                        }
                    } else {
                        System.err.println("Error: Este archivo no es una tabla.");
                    }
                    lectorArchivoTabla.close();
                } else {
                    System.err.println("Error: La tabla:" + nombreTabla 
                            +" no existe.");
                }
            } catch (FileNotFoundException ex) {
                System.err.println("Error: Archivo no encontrado.");
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
            }
        }
        return false;
    }
    
    public void vaciar() {
        nombreTabla = "";
        campoOriginal = "";
        nuevoCampo = "";
        archivoTabla = null;
        archivoMasterTablas = null;
        archivoAuxiliar = null;
    }
}
