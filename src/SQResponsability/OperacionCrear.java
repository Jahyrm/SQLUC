/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQResponsability;

import com.csvreader.CsvWriter;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jahyr
 */
public class OperacionCrear extends OperacionAbstracta {
    String nombreTabla = "";
    String[] campos;
    String llave = "";
    String[] longitudes;
    String[] encriptados;
    File archivoTabla;
    File archivoMasterTablas;
    String[] vectorInfoTablaActual;
    int forma = 0;
    
    public OperacionCrear(int clave){
        this.clave = clave;
    }
    
    @Override
    protected void accion(String[] vectorQuery){
        if (tablaCorrecta(vectorQuery)){
            archivoTabla = new File(nombreTabla+".csv");
            archivoMasterTablas = new File("Master.csv");
            if (archivoTabla.exists()){
                try{
                    if(archivoTabla.getCanonicalPath().contains(nombreTabla)){
                        System.err.println("Error: la tabla: \""+nombreTabla
                        +"\" ya existe.");
                    } else {
                        String infoUnida = nombreTabla + "&" + llave + "&";
                        String longitudCampos = "";
                        for (int i = 0; i < campos.length; i++) {
                            if (i == (campos.length) - 1) {
                                longitudCampos = longitudCampos + campos[i]
                                        + "|" + longitudes[i];
                            } else {
                                longitudCampos = longitudCampos + campos[i]
                                        + "|" + longitudes[i] + ";";
                            }
                        }
                        infoUnida = infoUnida + longitudCampos;
                        if (forma == 1) {
                            infoUnida = infoUnida + "&";
                            String camposEncriptados = "";
                            for (int i = 0; i < encriptados.length; i++) {
                                for (int j = 0; j < campos.length; j++) {
                                    if (encriptados[i].equals(campos[j])) {
                                        if (i == (encriptados.length) - 1) {
                                            camposEncriptados = camposEncriptados
                                                    + encriptados[i] + "|" + j;
                                        } else {
                                            camposEncriptados = camposEncriptados
                                                    + encriptados[i] + "|"
                                                    + j + ";";
                                        }
                                    }
                                }
                            }
                            infoUnida = infoUnida + camposEncriptados;
                        }
                        vectorInfoTablaActual = infoUnida.split("&");
                        CsvWriter salidaParaTabla
                                = new CsvWriter(new FileWriter(archivoTabla), ',');
                        salidaParaTabla.writeRecord(campos);
                        salidaParaTabla.close();
                        CsvWriter salidaParaMaster
                                = new CsvWriter(new FileWriter(archivoMasterTablas,
                                        true), ',');
                        salidaParaMaster.writeRecord(vectorInfoTablaActual);
                        salidaParaMaster.close();

                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                }
                
            } else {
                String infoUnida=nombreTabla+"&"+llave+"&";
                String longitudCampos = "";
                for(int i=0;i<campos.length;i++){
                    if (i==(campos.length)-1){
                        longitudCampos = longitudCampos+campos[i]
                                +"|"+longitudes[i];
                    } else {
                        longitudCampos = longitudCampos+campos[i]
                                +"|"+longitudes[i]+";";
                    }
                }
                infoUnida = infoUnida + longitudCampos;
                if (forma == 1) {
                    infoUnida = infoUnida + "&";
                    String camposEncriptados = "";
                    for (int i = 0; i < encriptados.length; i++) {
                        for (int j = 0; j < campos.length; j++) {
                            if (encriptados[i].equals(campos[j])) {
                                if (i == (encriptados.length) - 1) {
                                    camposEncriptados = camposEncriptados
                                            + encriptados[i] + "|" + j;
                                } else {
                                    camposEncriptados = camposEncriptados
                                            + encriptados[i] + "|"
                                            + j + ";";
                                }
                            }
                        }
                    }
                    infoUnida = infoUnida + camposEncriptados;
                }
                vectorInfoTablaActual = infoUnida.split("&");
                try {
                    CsvWriter salidaParaTabla 
                            = new CsvWriter(new FileWriter(archivoTabla),',');
                    salidaParaTabla.writeRecord(campos);
                    salidaParaTabla.close();
                    CsvWriter salidaParaMaster 
                            = new CsvWriter(new FileWriter(archivoMasterTablas, 
                                    true), ',');
                    salidaParaMaster.writeRecord(vectorInfoTablaActual);
                    salidaParaMaster.close();
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                }
            }
        }
    }
    
    public boolean tablaCorrecta(String[] vectorQuery){
        vaciar();
        if (vectorQuery.length==9 || vectorQuery.length==10){
            nombreTabla = vectorQuery[2].replace("-"," ");
            campos = vectorQuery[4].split(",");
            for(int i=0; i<campos.length; i++){
                if (campos[i].contains("-")){
                    campos[i] = campos[i].replace("-", " ");
                }
            }
            llave = vectorQuery[6];
            longitudes = vectorQuery[8].split(",");
            if(Arrays.asList(campos).contains(llave)){
                if(longitudes.length == campos.length){
                    return true;
                } else {
                    System.err.println("Error: Los campos y las longitudes"
                            +" no coinciden.");
                }
            } else {
                System.err.println("Error: El campo clave no existe.");
            }
        } else {
            forma = 1;
            nombreTabla = vectorQuery[2].replace("-"," ");
            campos = vectorQuery[4].split(",");
            for(int i=0; i<campos.length; i++){
                if (campos[i].contains("-")){
                    campos[i] = campos[i].replace("-", " ");
                }
            }
            llave = vectorQuery[6];
            longitudes = vectorQuery[8].split(",");
            if (vectorQuery[10].contains(",")){
                encriptados = vectorQuery[10].split(",");
                for (int i = 0; i < encriptados.length; i++) {
                    if (encriptados[i].contains("-")) {
                        encriptados[i] = encriptados[i].replace("-", " ");
                    }
                }
            } else {
                encriptados = new String[1];
                encriptados[0] = vectorQuery[10].replace("-", " ");
            }
            
            if(Arrays.asList(campos).contains(llave) 
                    && longitudes.length == campos.length
                    && encriptados.length <= campos.length){
                for (int i=0; i<encriptados.length;i++){
                    if(!Arrays.asList(campos).contains(encriptados[i])){
                       System.err.println("Error: Un campo designado para "+
                               "encriptar no existe.");
                        return false; 
                    }
                }
                return true;
            } else {
                System.err.println("Error: El campo clave no existe.");
            }
        }
        return false;
    }
    public void vaciar(){
        nombreTabla = "";
        campos = new String[0];
        llave = "";
        longitudes = new String[0];
        encriptados = new String[0];
        archivoTabla = null;
        archivoMasterTablas = null;
        vectorInfoTablaActual = new String[0];
        forma = 0;
    }
}
