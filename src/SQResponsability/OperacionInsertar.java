/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQResponsability;

import sqluc.StringEncrypt;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jahyr
 */
public class OperacionInsertar extends OperacionAbstracta{
    String nombreTabla = "";
    String llave;
    int indiceLlave;
    String[] valores;
    String[] longCampos;
    File archivoTabla;
    String[] camposQueDebenEncriptarse;
    boolean camposEncriptados = false;
    boolean llaveEncriptada = false;
    String key = "92AE31A79FEEB2A3"; //llave
    String iv = "0123456789ABCDEF"; // vector de inicialización
    
    public OperacionInsertar(int clave){
        this.clave = clave;
    }
    
    @Override
    protected void accion(String[] vectorQuery){
        if (todoCorrecto(vectorQuery)){
            boolean longValores = true;
            boolean repetido = false;
            CsvReader lectorArchivoTabla;
            CsvWriter salidaParaArchivo;
            
            try{
                for(int i=0; i<valores.length; i++){
                    if(valores[i].length()!=Integer.parseInt(longCampos[i]
                            .substring(longCampos[i].indexOf("|")+1))){
                        longValores = false;
                    }
                }
                
                if (longValores) {
                    String[] recordActual;
                    boolean primeraFila = true;

                    lectorArchivoTabla = new CsvReader(nombreTabla + ".csv");
                    while (lectorArchivoTabla.readRecord()) {
                        if (primeraFila) {
                            primeraFila = false;
                        } else {
                            recordActual = lectorArchivoTabla.getValues();
                            if (llaveEncriptada) {
                                if (StringEncrypt.decrypt(key, iv, recordActual[indiceLlave])
                                        .equals(valores[indiceLlave])) {
                                    repetido = true;
                                    primeraFila = true;
                                    break;
                                }
                            } else {
                                if (recordActual[indiceLlave]
                                        .equals(valores[indiceLlave])) {
                                    repetido = true;
                                    primeraFila = true;
                                    break;
                                }
                            }
                        }
                    }
                    lectorArchivoTabla.close();
                    
                    if (!repetido) {
                        salidaParaArchivo = new CsvWriter(
                                new FileWriter(archivoTabla, true), ',');
                        
                        for (int i = 0; i < valores.length; i++) {
                            if (valores[i].contains("-")) {
                                valores[i] = valores[i].replace("-", " ");
                            }
                        }
                        
                        if (!camposEncriptados){
                            salidaParaArchivo.writeRecord(valores);
                        } else {
                            for(int i =0; i<camposQueDebenEncriptarse.length; i++){
                                int indice = Integer.parseInt(
                                        camposQueDebenEncriptarse[i]
                                                .substring(
                                                        camposQueDebenEncriptarse[i]
                                                                .indexOf("|")+1));
                                valores[indice] = StringEncrypt.encrypt(key, iv,
                                        valores[indice]);
                            }
                            salidaParaArchivo.writeRecord(valores);
                        }
                        salidaParaArchivo.close();
                        
                    } else {
                        System.err.println("Error: Ya existe un registro con"
                                + " esa clave.");
                    }
                } else {
                    System.err.println("Error: Un valor tiene mas o menos "
                            +"caracteres de los permitidos.");
                }
            } catch (FileNotFoundException ex) {
                System.err.println("Error: Archivo no encontrado.");
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
            } catch (Exception ex) {
                System.err.println("Error: No se puedo encriptar/desencriptar "
                        +"los datos.");
            } 
        }
    }
    
    public boolean todoCorrecto(String[] vectorQuery){
        vaciar();
        nombreTabla = vectorQuery[2].replace("-"," ");
        valores = (vectorQuery[4]).split(",");
        archivoTabla = new File(nombreTabla.toLowerCase()+".csv");
        
        if (archivoTabla.exists()) {
            CsvReader lectorArchivoTabla;
            CsvReader lectorArchivoMasterTablas;
            try{
                if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                    lectorArchivoTabla = new CsvReader(nombreTabla + ".csv");
                    lectorArchivoMasterTablas = new CsvReader("Master.csv", ',');

                    lectorArchivoTabla.readHeaders();
                    String[] campos = lectorArchivoTabla.getHeaders();
                    if (valores.length == campos.length) {
                        lectorArchivoTabla.close();
                        String[] recordActual;
                        while (lectorArchivoMasterTablas.readRecord()) {
                            recordActual = lectorArchivoMasterTablas.getValues();
                            if (recordActual[0].equals(nombreTabla)) {
                                llave = recordActual[1];
                                longCampos = (recordActual[2]).split(";");
                                for (int i = 0; i < longCampos.length; i++) {
                                    if ((longCampos[i]).contains(llave)) {
                                        indiceLlave = i;
                                    }
                                }
                                if (recordActual.length == 4) {
                                    camposEncriptados = true;
                                    camposQueDebenEncriptarse = recordActual[3]
                                            .split(";");
                                    for (int i=0; i<camposQueDebenEncriptarse
                                            .length;i++){
                                        if(camposQueDebenEncriptarse[i].contains(llave)){
                                            llaveEncriptada = true;
                                        }
                                    }
                                }
                                lectorArchivoTabla.close();
                                lectorArchivoMasterTablas.close();
                                return true;
                            }
                        }
                    }
                    lectorArchivoTabla.close();
                    lectorArchivoMasterTablas.close();
                    System.err.println("Error: Hay pocos o demasiados valores.");
                } else {
                    System.err.println("Error: La tabla: " + nombreTabla
                            + " no existe.");
                }
            } catch (FileNotFoundException ex) {
                System.err.println("Error: Archivo no encontrado.");
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
            }
        } else {
            System.err.println("Error: La tabla: " + nombreTabla + " no existe.");
        }
        return false;
    }
    
    public void vaciar(){
        nombreTabla = "";
        llave = "";
        indiceLlave = -1;
        valores = new String[0];
        longCampos = new String[0];
        archivoTabla = null;
        camposQueDebenEncriptarse = new String[0];
        camposEncriptados = false;
        llaveEncriptada = false;
    }
}
