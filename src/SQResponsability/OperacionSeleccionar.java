/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQResponsability;

import sqluc.Ordenar;
import sqluc.StringEncrypt;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jahyr
 */
public class OperacionSeleccionar extends OperacionAbstracta {
    
    String nombreTabla = "";
    String condicion = "";
    boolean ascDsc = true;
    int cantidadDeRegistros = -1;
    File archivoTabla;
    File archivoAuxiliar;
    boolean camposEncriptados = false;
    String campoCondicion;
    String valorCondicion;
    boolean existeCampo = false;
    int indiceCampoAComparar;
    boolean condicionCorrecta = true;
    List<String[]> listaDeArreglos;
    String[] vectorDeTitulos;
    String llave = "";
    int indiceLlave;
    boolean continuar = false;
    
    String key = "92AE31A79FEEB2A3"; //llave
    String iv = "0123456789ABCDEF"; // vector de inicialización
    
    String[] indicesCamposEncriptados;
    
    public OperacionSeleccionar(int clave){
        this.clave = clave;
    }
    
    @Override
    protected void accion(String[] vectorQuery){
        int accion = conocerAccion(vectorQuery);
        archivoAuxiliar = new File("Auxiliar2.csv");
        archivoTabla = new File(nombreTabla+".csv");
        CsvReader lectorArchivoTabla;
        CsvWriter salidaParaAuxiliar;
        boolean primeraVez = true;
        switch(accion){
            case 1:
                try {
                    if (archivoTabla.exists()) {
                        if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                            String[] recordActual;
                            lectorArchivoTabla = new CsvReader(nombreTabla+".csv");
                            comprobarEncriptacion();
                            if (!camposEncriptados){
                                System.out.println("");
                                while (lectorArchivoTabla.readRecord()) {
                                    recordActual = lectorArchivoTabla.getValues();
                                    System.out.println(Arrays.toString(recordActual));
                                }
                            } else {
                                System.out.println("");
                                while (lectorArchivoTabla.readRecord()) {
                                    if (primeraVez){
                                        primeraVez = false;
                                        recordActual = lectorArchivoTabla.getValues();
                                        System.out.println(Arrays.toString(recordActual));
                                    } else {
                                        recordActual = lectorArchivoTabla.getValues();
                                        for (int i=0;i<indicesCamposEncriptados.length;i++){
                                            int indice = Integer.parseInt(indicesCamposEncriptados[i].substring(indicesCamposEncriptados[i].indexOf("|")+1));
                                            recordActual[indice] = StringEncrypt.decrypt(key, iv, recordActual[indice]);
                                        }
                                        System.out.println(Arrays.toString(recordActual));
                                    }
                                }
                            }
                        } else {
                            System.err.println("Error: La tabla:" + nombreTabla
                                    + " no existe.");
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTabla
                                + " no existe.");
                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                } catch (Exception ex) {
                    System.err.println("Error: No se pudo desencriptar la tabla.");
                }
                break;
            case 2:
                try {
                    if (archivoTabla.exists()) {
                        if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                            String[] recordActual;
                            lectorArchivoTabla = new CsvReader(nombreTabla+".csv");
                            comprobarEncriptacion();
                            prepararCondicion();
                            if (!condicionCorrecta){
                                System.err.println("Error: La condicion no "
                                        +"es correcta.");
                                break;
                            }
                            if (!camposEncriptados){
                                if (existeCampo) {
                                    System.out.println("");
                                    while (lectorArchivoTabla.readRecord()) {
                                        recordActual = lectorArchivoTabla.getValues();
                                        if(recordActual[indiceCampoAComparar]
                                                .equals(valorCondicion) || recordActual[indiceCampoAComparar]
                                                .equals(campoCondicion)){
                                            System.out.println(Arrays
                                                    .toString(recordActual));
                                        }
                                    }
                                } else {
                                    System.err.println("Error: El campo buscado"
                                            +" no existe.");
                                }
                            } else {
                                
                                if (existeCampo) {
                                    System.out.println("");
                                    while (lectorArchivoTabla.readRecord()) {
                                        if (primeraVez) {
                                            primeraVez = false;
                                            recordActual = lectorArchivoTabla.getValues();
                                            System.out.println(Arrays.toString(recordActual));
                                        } else {
                                            recordActual = lectorArchivoTabla.getValues();
                                            for (int i = 0; i < indicesCamposEncriptados.length; i++) {
                                                int indice = Integer.parseInt(indicesCamposEncriptados[i].substring(indicesCamposEncriptados[i].indexOf("|") + 1));
                                                recordActual[indice] = StringEncrypt.decrypt(key, iv, recordActual[indice]);
                                            }
                                            if (recordActual[indiceCampoAComparar]
                                                    .equals(valorCondicion) || recordActual[indiceCampoAComparar]
                                                    .equals(campoCondicion)) {
                                                System.out.println(Arrays
                                                        .toString(recordActual));
                                            }
                                        }
                                    }
                                } else {
                                    System.err.println("Error: La condición es "
                                            +"incorrecta revise si el campo existe.");
                                }
                            }
                        } else {
                            System.err.println("Error: La tabla:" + nombreTabla
                                    + " no existe.");
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTabla
                                + " no existe.");
                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                } catch (Exception ex) {
                    System.err.println("Error: No se pudo desencriptar la tabla.");
                }
                break;
            case 3:
                try{
                    if (archivoTabla.exists()) {
                        if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                            String[] recordActual;
                            comprobarEncriptacion();
                            listaDeArreglos = getRegistros(false);
                            
                            //COMENTAR DE AQUÍ 
                            archivoAuxiliar = Ordenar.getArchivoOrdenado(
                                    vectorDeTitulos, listaDeArreglos, !ascDsc, indiceLlave);
                            lectorArchivoTabla = new CsvReader("Auxiliar.csv");
                            System.out.println("");
                            while (lectorArchivoTabla.readRecord()) {
                                recordActual = lectorArchivoTabla.getValues();
                                System.out.println(Arrays
                                                        .toString(recordActual));
                            }
                            lectorArchivoTabla.close();
                            (new File("Auxiliar.csv")).delete();
                            //HASTA AQUÍ
                            

                            /*Otra forma de ordenar:
                            listaDeArreglos = Ordenar.ordenar(listaDeArreglos, !ascDsc, indiceLlave);
                            for (String[] c : listaDeArreglos) {
                                System.out.println(Arrays.toString(c));
                            }
                            */
                            
                        } else {
                            System.err.println("Error: La tabla:" + nombreTabla
                                    + " no existe.");
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTabla
                                + " no existe.");
                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                }
                break;
            case 4:
                try {
                    if (archivoTabla.exists()) {
                        if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                            String[] recordActual;
                            lectorArchivoTabla = new CsvReader(nombreTabla+".csv");
                            comprobarEncriptacion();
                            if (!camposEncriptados){
                                System.out.println("");
                                for (int i = 0; i <= cantidadDeRegistros; i++) {
                                    if (lectorArchivoTabla.readRecord()) {
                                        recordActual = lectorArchivoTabla.getValues();
                                        System.out.println(Arrays.toString(recordActual));
                                    }
                                }
                            } else {
                                System.out.println("");
                                for (int i=0; i<=cantidadDeRegistros; i++){
                                    if (lectorArchivoTabla.readRecord()) {
                                        if (primeraVez) {
                                            primeraVez = false;
                                            recordActual = lectorArchivoTabla.getValues();
                                            System.out.println(Arrays.toString(recordActual));
                                        } else {
                                            recordActual = lectorArchivoTabla.getValues();
                                            for (int j = 0; j < indicesCamposEncriptados.length; j++) {
                                                int indice = Integer.parseInt(indicesCamposEncriptados[j].substring(indicesCamposEncriptados[j].indexOf("|") + 1));
                                                recordActual[indice] = StringEncrypt.decrypt(key, iv, recordActual[indice]);
                                            }
                                            System.out.println(Arrays.toString(recordActual));
                                        }
                                    }
                                }
                            }
                        } else {
                            System.err.println("Error: La tabla:" + nombreTabla
                                    + " no existe.");
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTabla
                                + " no existe.");
                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                } catch (Exception ex) {
                    System.err.println("Error: No se pudo desencriptar la tabla.");
                }
                break;
            case 5:
                try {
                    if (archivoTabla.exists()) {
                        if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                            String[] recordActual;
                            lectorArchivoTabla = new CsvReader(nombreTabla+".csv");
                            salidaParaAuxiliar = new CsvWriter(new FileWriter(archivoAuxiliar),',');
                            comprobarEncriptacion();
                            prepararCondicion();
                            if (!condicionCorrecta){
                                System.err.println("Error: La condicion no "
                                        +"es correcta.");
                                break;
                            }
                            if (!camposEncriptados){
                                if (existeCampo) {
                                    System.out.println("");
                                    while (lectorArchivoTabla.readRecord()) {
                                        recordActual = lectorArchivoTabla.getValues();
                                        if(recordActual[indiceCampoAComparar]
                                                .equals(valorCondicion) || recordActual[indiceCampoAComparar]
                                                .equals(campoCondicion)){
                                            salidaParaAuxiliar.writeRecord(recordActual);
                                        }
                                    }
                                    lectorArchivoTabla.close();
                                    salidaParaAuxiliar.close();
                                    continuar = true;
                                } else {
                                    System.err.println("Error: El campo buscado"
                                            +" no existe.");
                                }
                            } else {
                                
                                if (existeCampo) {
                                    System.out.println("");
                                    while (lectorArchivoTabla.readRecord()) {
                                        if (primeraVez) {
                                            primeraVez = false;
                                            recordActual = lectorArchivoTabla.getValues();
                                            System.out.println(Arrays.toString(recordActual));
                                        } else {
                                            recordActual = lectorArchivoTabla.getValues();
                                            for (int i = 0; i < indicesCamposEncriptados.length; i++) {
                                                int indice = Integer.parseInt(indicesCamposEncriptados[i].substring(indicesCamposEncriptados[i].indexOf("|") + 1));
                                                recordActual[indice] = StringEncrypt.decrypt(key, iv, recordActual[indice]);
                                            }
                                            if (recordActual[indiceCampoAComparar]
                                                    .equals(valorCondicion) || recordActual[indiceCampoAComparar]
                                                    .equals(campoCondicion)) {
                                                salidaParaAuxiliar.writeRecord(recordActual);
                                            }
                                        }
                                    }
                                    lectorArchivoTabla.close();
                                    salidaParaAuxiliar.close();
                                    continuar = true;
                                } else {
                                    System.err.println("Error: La condición es "
                                            +"incorrecta revise si el campo existe.");
                                }
                            }
                            salidaParaAuxiliar.close();
                            
                            if (continuar) {
                                listaDeArreglos = getRegistros(true);

                                //COMENTAR DE AQUÍ 
                                archivoAuxiliar = Ordenar.getArchivoOrdenado(
                                        vectorDeTitulos, listaDeArreglos, !ascDsc, indiceLlave);
                                lectorArchivoTabla = new CsvReader("Auxiliar.csv");
                                System.out.println("");
                                while (lectorArchivoTabla.readRecord()) {
                                    recordActual = lectorArchivoTabla.getValues();
                                    System.out.println(Arrays
                                            .toString(recordActual));
                                }
                                lectorArchivoTabla.close();
                                archivoAuxiliar.delete();
                                //HASTA AQUÍ
                            }
                            
                            
                        } else {
                            System.err.println("Error: La tabla:" + nombreTabla
                                    + " no existe.");
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTabla
                                + " no existe.");
                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                } catch (Exception ex) {
                    System.err.println("Error: No se pudo desencriptar la tabla.");
                }
                break;
            case 6:
                try {
                    if (archivoTabla.exists()) {
                        if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                            String[] recordActual;
                            lectorArchivoTabla = new CsvReader(nombreTabla+".csv");
                            comprobarEncriptacion();
                            prepararCondicion();
                            if (!condicionCorrecta){
                                System.err.println("Error: La condicion no "
                                        +"es correcta.");
                                break;
                            }
                            if (!camposEncriptados){
                                if (existeCampo) {
                                    System.out.println("");
                                    for (int i=0; i<=cantidadDeRegistros; i++ ) {
                                        if(lectorArchivoTabla.readRecord()) {
                                            recordActual = lectorArchivoTabla.getValues();
                                            if (recordActual[indiceCampoAComparar]
                                                    .equals(valorCondicion) || recordActual[indiceCampoAComparar]
                                                    .equals(campoCondicion)) {
                                                System.out.println(Arrays
                                                        .toString(recordActual));
                                            }
                                        }
                                    }
                                } else {
                                    System.err.println("Error: El campo buscado"
                                            +" no existe.");
                                }
                            } else {
                                
                                if (existeCampo) {
                                    System.out.println("");
                                    for (int j = 0; j <= cantidadDeRegistros; j++) {
                                        if (lectorArchivoTabla.readRecord()) {
                                            if (primeraVez) {
                                                primeraVez = false;
                                                recordActual = lectorArchivoTabla.getValues();
                                                System.out.println(Arrays.toString(recordActual));
                                            } else {
                                                recordActual = lectorArchivoTabla.getValues();
                                                for (int i = 0; i < indicesCamposEncriptados.length; i++) {
                                                    int indice = Integer.parseInt(indicesCamposEncriptados[i].substring(indicesCamposEncriptados[i].indexOf("|") + 1));
                                                    recordActual[indice] = StringEncrypt.decrypt(key, iv, recordActual[indice]);
                                                }
                                                if (recordActual[indiceCampoAComparar]
                                                        .equals(valorCondicion) || recordActual[indiceCampoAComparar]
                                                        .equals(campoCondicion)) {
                                                    System.out.println(Arrays
                                                            .toString(recordActual));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.err.println("Error: La condición es "
                                            +"incorrecta revise si el campo existe.");
                                }
                            }
                        } else {
                            System.err.println("Error: La tabla:" + nombreTabla
                                    + " no existe.");
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTabla
                                + " no existe.");
                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                } catch (Exception ex) {
                    System.err.println("Error: No se pudo desencriptar la tabla.");
                }
                break;
            case 7:
                try{
                    if (archivoTabla.exists()) {
                        if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                            String[] recordActual;
                            comprobarEncriptacion();
                            listaDeArreglos = getRegistros(false);
                            
                            //COMENTAR DE AQUÍ 
                            archivoAuxiliar = Ordenar.getArchivoOrdenado(
                                    vectorDeTitulos, listaDeArreglos, !ascDsc, indiceLlave);
                            lectorArchivoTabla = new CsvReader("Auxiliar.csv");
                            System.out.println("");
                            for (int i=0; i<=cantidadDeRegistros; i++) {
                                if (lectorArchivoTabla.readRecord()) {
                                    recordActual = lectorArchivoTabla.getValues();
                                    System.out.println(Arrays
                                            .toString(recordActual));
                                }
                            }
                            lectorArchivoTabla.close();
                            archivoAuxiliar.delete();
                            //HASTA AQUÍ
                            

                            /*Otra forma de ordenar:
                            listaDeArreglos = Ordenar.ordenar(listaDeArreglos, !ascDsc, indiceLlave);
                            for (String[] c : registros) {
                                System.out.println(Arrays
                                                        .toString(recordActual));
                            }
                            */
                            
                        } else {
                            System.err.println("Error: La tabla:" + nombreTabla
                                    + " no existe.");
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTabla
                                + " no existe.");
                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                }
                break;
            case 8:
                try {
                    if (archivoTabla.exists()) {
                        if (archivoTabla.getCanonicalPath().contains(nombreTabla)) {
                            String[] recordActual;
                            lectorArchivoTabla = new CsvReader(nombreTabla+".csv");
                            salidaParaAuxiliar = new CsvWriter(new FileWriter(archivoAuxiliar),',');
                            comprobarEncriptacion();
                            prepararCondicion();
                            if (!condicionCorrecta){
                                System.err.println("Error: La condicion no "
                                        +"es correcta.");
                                break;
                            }
                            if (!camposEncriptados){
                                if (existeCampo) {
                                    while (lectorArchivoTabla.readRecord()) {
                                        recordActual = lectorArchivoTabla.getValues();
                                        if(recordActual[indiceCampoAComparar]
                                                .equals(valorCondicion) || recordActual[indiceCampoAComparar]
                                                .equals(campoCondicion)){
                                            salidaParaAuxiliar.writeRecord(recordActual);
                                        }
                                    }
                                    lectorArchivoTabla.close();
                                    salidaParaAuxiliar.close();
                                    continuar = true;
                                } else {
                                    System.err.println("Error: El campo buscado"
                                            +" no existe.");
                                }
                            } else {
                                
                                if (existeCampo) {
                                    while (lectorArchivoTabla.readRecord()) {
                                        if (primeraVez) {
                                            primeraVez = false;
                                            recordActual = lectorArchivoTabla.getValues();
                                            //System.out.println(Arrays.toString(recordActual));
                                        } else {
                                            recordActual = lectorArchivoTabla.getValues();
                                            for (int i = 0; i < indicesCamposEncriptados.length; i++) {
                                                int indice = Integer.parseInt(indicesCamposEncriptados[i].substring(indicesCamposEncriptados[i].indexOf("|") + 1));
                                                recordActual[indice] = StringEncrypt.decrypt(key, iv, recordActual[indice]);
                                            }
                                            if (recordActual[indiceCampoAComparar]
                                                    .equals(valorCondicion) || recordActual[indiceCampoAComparar]
                                                    .equals(campoCondicion)) {
                                                salidaParaAuxiliar.writeRecord(recordActual);
                                            }
                                        }
                                    }
                                    lectorArchivoTabla.close();
                                    salidaParaAuxiliar.close();
                                    continuar = true;
                                } else {
                                    System.err.println("Error: La condición es "
                                            +"incorrecta revise si el campo existe.");
                                }
                            }
                            
                            if (continuar) {
                                listaDeArreglos = getRegistros(true);

                                //COMENTAR DE AQUÍ 
                                archivoAuxiliar = Ordenar.getArchivoOrdenado(
                                        vectorDeTitulos, listaDeArreglos, !ascDsc, indiceLlave);
                                lectorArchivoTabla = new CsvReader("Auxiliar.csv");
                                System.out.println("");
                                for (int i = 0; i <= cantidadDeRegistros; i++) {
                                    if (lectorArchivoTabla.readRecord()) {
                                        recordActual = lectorArchivoTabla.getValues();
                                        System.out.println(Arrays
                                                .toString(recordActual));
                                    }
                                }
                                lectorArchivoTabla.close();
                                (new File("Auxiliar.csv")).delete();
                                //HASTA AQUÍ
                            }
                            
                            
                        } else {
                            System.err.println("Error: La tabla:" + nombreTabla
                                    + " no existe.");
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTabla
                                + " no existe.");
                    }
                } catch (IOException ex) {
                    System.err.println("Error: No se tienen los permisos.");
                } catch (Exception ex) {
                    System.err.println("Error: No se pudo desencriptar la tabla.");
                }
                break;
            default:
                System.err.println("Error: Error de sintaxis.");
        }
        
    }
    
    
    public int conocerAccion(String[] vectorQuery){
        vaciar();
        nombreTabla = vectorQuery[2].replace("-", " ");
        switch(vectorQuery.length){
            case 3:
                //SELECCIONAR DE
                return 1;
            case 5:
                //SELECCIONAR DE (DONDE/ORDENADO/VER)
                if (vectorQuery[3].equals("DONDE")){
                    condicion = vectorQuery[4];
                    return 2;
                } else if (vectorQuery[3].equals("ORDENADO")){
                    if (vectorQuery[4].equals("asc")){
                        ascDsc = true;
                        return 3;
                    } else if (vectorQuery[4].equals("dsc")) {
                        ascDsc = false;
                        return 3;
                    }
                } else {
                    try{
                        cantidadDeRegistros = Integer.parseInt(vectorQuery[4]);
                        return 4;
                    } catch (NumberFormatException e) {}
                }
                break;
            case 7:
                //SELECCIONAR DE (DONDE/ORDENADO) (ORDENADO/VER)
                if (vectorQuery[3].equals("DONDE")){
                    condicion = vectorQuery[4];
                    if (vectorQuery[5].equals("ORDENADO")) {
                        if (vectorQuery[6].equals("asc")) {
                            ascDsc = true;
                            return 5;
                        } else if (vectorQuery[6].equals("dsc")) {
                            ascDsc = false;
                            return 5;
                        }
                    } else {
                        try {
                            cantidadDeRegistros = Integer.parseInt(vectorQuery[6]);
                            return 6;
                        } catch (NumberFormatException e) {}
                    }
                } else {
                    if (vectorQuery[4].equals("asc")) {
                        ascDsc = true;
                        try {
                            cantidadDeRegistros = Integer.parseInt(vectorQuery[6]);
                            return 7;
                        } catch (NumberFormatException e) {}
                    } else if (vectorQuery[4].equals("dsc")) {
                        ascDsc = false;
                        try {
                            cantidadDeRegistros = Integer.parseInt(vectorQuery[6]);
                            return 7;
                        } catch (NumberFormatException e) {}
                    }
                }
                break;
            case 9:
                condicion = vectorQuery[4];
                if (vectorQuery[6].equals("asc")) {
                    ascDsc = true;
                    try {
                        cantidadDeRegistros = Integer.parseInt(vectorQuery[8]);
                        return 8;
                    } catch (NumberFormatException e) {}
                } else if (vectorQuery[6].equals("dsc")) {
                    ascDsc = false;
                    try {
                        cantidadDeRegistros = Integer.parseInt(vectorQuery[8]);
                        return 8;
                    } catch (NumberFormatException e) {}
                }
                break;
        }
        return -1;
    }
    
    public void vaciar(){
        nombreTabla = "";
        condicion = "";
        ascDsc = true;
        cantidadDeRegistros = -1;
        archivoTabla = null;
        camposEncriptados = false;
        indicesCamposEncriptados = new String[0];
        campoCondicion= "";
        valorCondicion= "";
        existeCampo = false;
        indiceCampoAComparar = -1;
        condicionCorrecta = true;
        listaDeArreglos = null;
        vectorDeTitulos = new String[0];
        llave = "";
        indiceLlave = -1;
        continuar = false;
    }
    
    public void comprobarEncriptacion() {
        CsvReader lectorArchivoMasterTabla;
        String[] recordActual;
        try {
            lectorArchivoMasterTabla = new CsvReader("Master.csv", ',');
            while (lectorArchivoMasterTabla.readRecord()) {
                recordActual = lectorArchivoMasterTabla.getValues();
                if (recordActual[0].equals(nombreTabla)) {
                    if (recordActual.length == 4) {
                        camposEncriptados = true;
                        indicesCamposEncriptados
                                = recordActual[3].split(";");
                        break;
                    }
                }
            }
            lectorArchivoMasterTabla.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Error: Archivo no encontrado.");
        } catch (IOException ex) {
            System.err.println("Error: No se tienen los permisos.");
        }
    }
    
    public void prepararCondicion() {
        CsvReader lectorArchivoMasterTabla;
        String[] recordActual;
        try{
            String[] vectorCondicion = condicion.split("=");
            campoCondicion = vectorCondicion[0];
            valorCondicion = vectorCondicion[1].replace("\"", "");
            valorCondicion = valorCondicion.replace("-", " ");
        } catch(Exception ex){
            condicionCorrecta = false;
            return;
        }
        try {
            lectorArchivoMasterTabla = new CsvReader("Master.csv", ',');
            while (lectorArchivoMasterTabla.readRecord()) {
                recordActual = lectorArchivoMasterTabla.getValues();
                if (recordActual[0].equals(nombreTabla)) {
                    String[] campos = recordActual[2].split(";");
                    for(int i=0; i<campos.length; i++){
                        if(campos[i].contains(campoCondicion)){
                            existeCampo = true;
                            indiceCampoAComparar = i;
                            break;
                        }
                    }
                    break;
                }
            }
            lectorArchivoMasterTabla.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Error: Archivo no encontrado.");
        } catch (IOException ex) {
            System.err.println("Error: No se tienen los permisos.");
        }
    }
    
    public List<String[]> getRegistros(boolean aux) {
        List<String[]> registros = new ArrayList<String[]>();
        CsvReader lectorArchivoTabla;
        CsvReader lectorArchivoMasterTablas;
        String[] recordActual = null;
        boolean primeraVez = true;
        try {
            lectorArchivoMasterTablas = new CsvReader("Master.csv", ',');
            while (lectorArchivoMasterTablas.readRecord()) {
                recordActual = lectorArchivoMasterTablas.getValues();
                if (recordActual[0].equals(nombreTabla)) {
                    llave = recordActual[1];
                    String[] vectorDeCampos = recordActual[2].split(";");
                    for (int i = 0; i < vectorDeCampos.length; i++) {
                        if ((vectorDeCampos[i].substring(0, vectorDeCampos[i]
                                .indexOf('|'))).equals(llave)) {
                            indiceLlave = i;
                            break;
                        }
                    }
                    break;
                }
            }
            lectorArchivoMasterTablas.close();

            if (!aux){
                lectorArchivoTabla = new CsvReader(nombreTabla + ".csv");
            } else {
                lectorArchivoTabla = new CsvReader("Auxiliar2.csv");
            }
            if (!camposEncriptados) {
                while (lectorArchivoTabla.readRecord()) {
                    if (primeraVez) {
                        vectorDeTitulos = lectorArchivoTabla.getValues();
                        primeraVez = false;
                    } else {
                        recordActual = lectorArchivoTabla.getValues();
                        registros.add(recordActual);
                    }
                }
                lectorArchivoTabla.close();
            } else {
                while (lectorArchivoTabla.readRecord()) {
                    if (primeraVez) {
                        vectorDeTitulos = lectorArchivoTabla.getValues();
                        primeraVez = false;
                    } else {
                        recordActual = lectorArchivoTabla.getValues();
                        if (aux){
                            registros.add(recordActual);
                        } else {
                            for (int i = 0; i < indicesCamposEncriptados.length; i++) {
                                int indice = Integer.parseInt(indicesCamposEncriptados[i].substring(indicesCamposEncriptados[i].indexOf("|") + 1));
                                recordActual[indice] = StringEncrypt.decrypt(key, iv, recordActual[indice]);
                            }
                            registros.add(recordActual);
                        }
                    }
                }
                lectorArchivoTabla.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado.");
        } catch (IOException ex) {
            System.err.println("Error: No se tienen los permisos.");
        } catch (Exception ex) {
            System.err.println("Error: Problema desencriptando.");
        }
        if (aux){
            (new File("Auxiliar2.csv")).delete();
        }
        return registros;
    }
}
