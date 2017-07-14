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
public class OperacionActualizar extends OperacionAbstracta{
    String nombreTabla = "";
    String valorDeLlave = "";
    int indiceLlave;
    String campoACambiar = "";
    int indiceDeCampoACambiar;
    String nuevoValor = "";
    File archivoTabla;
    File archivoMasterTablas;
    File archivoAuxiliar;
    String key = "92AE31A79FEEB2A3"; //llave
    String iv = "0123456789ABCDEF"; // vector de inicialización
    String[] camposQueDebenEncriptarse;
    String[] vectorDeCampos;
    boolean campoEncriptado = false;
    
    public OperacionActualizar(int clave){
        this.clave = clave;
    }
    
    @Override
    protected void accion(String[] vectorQuery){
        if (todoCorrecto(vectorQuery)){
            CsvReader lectorArchivoTabla;
            CsvReader lectorArchivoMasterTabla;
            CsvWriter salidaParaArchivo;
            CsvReader lectorArchivoAuxiliar;
            CsvWriter salidaParaArchivoAuxiliar;
            archivoAuxiliar = new File("Auxiliar.csv");
            boolean existeLlave = false;
            boolean llaveEncriptada = false;
            boolean primeraVez = true;
            boolean yaDioError = false;
            
            try {
                lectorArchivoMasterTabla = new CsvReader("Master.csv", ',');
                while (lectorArchivoMasterTabla.readRecord()) {
                    String[] recordActual = lectorArchivoMasterTabla.getValues();
                    if (recordActual[0].equals(nombreTabla)) {
                        vectorDeCampos = recordActual[2].split(";");
                        for (int i = 0; i < vectorDeCampos.length; i++) {
                            if ((vectorDeCampos[i].substring(0, vectorDeCampos[i]
                                    .indexOf('|'))).equals(recordActual[1])) {
                                indiceLlave = i;
                            }
                            if (recordActual.length == 4) {
                                camposQueDebenEncriptarse = recordActual[3]
                                        .split(";");
                                for (int j = 0; j < camposQueDebenEncriptarse.length; j++) {
                                    if (camposQueDebenEncriptarse[j].contains(recordActual[1])) {
                                        llaveEncriptada = true;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                lectorArchivoMasterTabla.close();

                lectorArchivoTabla = new CsvReader(nombreTabla + ".csv", ',');
                salidaParaArchivoAuxiliar = new CsvWriter(
                        new FileWriter(archivoAuxiliar), ',');
                while (lectorArchivoTabla.readRecord()) {
                    String[] recordActual = lectorArchivoTabla.getValues();
                    
                    if (llaveEncriptada) {
                        if (primeraVez){
                            primeraVez = false;
                        } else {
                            if (StringEncrypt.decrypt(key, iv, recordActual[indiceLlave])
                                    .equals(valorDeLlave)) {
                                
                                if(nuevoValor.length()==Integer.parseInt(
                                        vectorDeCampos[indiceDeCampoACambiar]
                                                .substring(vectorDeCampos[indiceDeCampoACambiar]
                                                        .indexOf("|")+1))){
                                    for(int i=0; i<camposQueDebenEncriptarse
                                            .length; i++){
                                        if (camposQueDebenEncriptarse[i]
                                                .contains(campoACambiar)){
                                            campoEncriptado = true;
                                            break;
                                        }
                                    }
                                    if (campoEncriptado){
                                        recordActual[indiceDeCampoACambiar]
                                        = StringEncrypt.encrypt(key, iv, nuevoValor);
                                    } else {
                                        recordActual[indiceDeCampoACambiar] =
                                                nuevoValor;
                                    }
                                    existeLlave = true;
                                } else {
                                    System.err.println("Error: El nuevo valor"
                                            +" tiene mas o menos caracteres "
                                            +"de los permitidos.");
                                    yaDioError = true;
                                }
                            }
                        }
                    } else {
                        if (recordActual[indiceLlave].equals(valorDeLlave)) {
                            
                            if(nuevoValor.length()==Integer.parseInt(
                                        vectorDeCampos[indiceDeCampoACambiar]
                                                .substring(vectorDeCampos[indiceDeCampoACambiar]
                                                        .indexOf("|")+1))){
                                    for(int i=0; i<camposQueDebenEncriptarse
                                            .length; i++){
                                        if (camposQueDebenEncriptarse[i]
                                                .contains(campoACambiar)){
                                            campoEncriptado = true;
                                            break;
                                        }
                                    }
                                    if (campoEncriptado){
                                        recordActual[indiceDeCampoACambiar]
                                        = StringEncrypt.encrypt(key, iv, nuevoValor);
                                    } else {
                                        recordActual[indiceDeCampoACambiar] =
                                                nuevoValor;
                                    }
                                    existeLlave = true;
                                } else {
                                    System.err.println("Error: El nuevo valor"
                                            +" tiene mas o menos caracteres "
                                            +"de los permitidos.");
                                    yaDioError = true;
                                }

                            recordActual[indiceDeCampoACambiar] = nuevoValor;
                        }
                    }
                    salidaParaArchivoAuxiliar.writeRecord(recordActual);
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
                    if(!yaDioError){
                        System.err.println("Error: No existe un registro con esa"
                            + " clave.");
                    }
                    (new File("Auxiliar.csv")).delete();
                }

            } catch (FileNotFoundException ex) {
                System.err.println("Error: Archivo no encontrado.");
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
            } catch (Exception ex) {
                Logger.getLogger(OperacionActualizar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean todoCorrecto(String[] vectorQuery) {
        vaciar();
        nombreTabla = vectorQuery[2].replace("-"," ");
        valorDeLlave = vectorQuery[4];
        campoACambiar = vectorQuery[6].replace("-"," ");
        nuevoValor = vectorQuery[8].replace("-"," ");
        archivoTabla = new File(nombreTabla+".csv");
        
        if (archivoTabla.exists()){
            CsvReader lectorArchivoTabla;
            try {
                if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                    lectorArchivoTabla = new CsvReader(nombreTabla + ".csv");
                    lectorArchivoTabla.readHeaders();
                    String[] campos = lectorArchivoTabla.getHeaders();
                    for (int i = 0; i < campos.length; i++) {
                        if (campos[i].equals(campoACambiar)) {
                            lectorArchivoTabla.close();
                            indiceDeCampoACambiar = i;
                            return true;
                        }
                    }
                    System.err.println("Error: La tabla: "+nombreTabla
                                +", no contiene el campo: "+campoACambiar+".");
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
    
    public void vaciar() {
        nombreTabla = "";
        valorDeLlave = "";
        indiceLlave = -1;
        campoACambiar = "";
        indiceDeCampoACambiar = -1;
        nuevoValor = "";
        archivoTabla = null;
        archivoMasterTablas = null;
        archivoAuxiliar = null;
        camposQueDebenEncriptarse = new String[0];
        vectorDeCampos = new String[0];
        campoEncriptado = false;
    }
}
