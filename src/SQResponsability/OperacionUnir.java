/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQResponsability;

import com.csvreader.CsvReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sqluc.Ordenar;
import sqluc.StringEncrypt;

/**
 *
 * @author Jahyr
 */
public class OperacionUnir extends OperacionAbstracta{
    String nombreTablaUno = "";
    String nombreTablaDos = "";
    String campoUno = "";
    String campoDos = "";
    int indiceCampoBuscadoUno;
    int indiceCampoBuscadoDos;
    boolean ascDsc = true;
    int cantidadDeRegistros;
    File archivoTablaUno;
    File archivoTablaDos;
    File archivoAuxiliar;
    boolean camposEncriptadosUno = false;
    boolean camposEncriptadosDos = false;
    String[] camposTablaUno;
    String[] camposTablaDos;
    String[] indicesCamposEncriptadosUno;
    String[] indicesCamposEncriptadosDos;
    String key = "92AE31A79FEEB2A3"; //llave
    String iv = "0123456789ABCDEF"; // vector de inicialización
    List<String[]> listaDeArreglos;
    String valorCondicion="";
    boolean condicion = false;
    
    
    public OperacionUnir(int clave){
        this.clave = clave;
    }
    
    @Override
    protected void accion(String[] vectorQuery){
        String[] headers;
        
        switch(verificarAccion(vectorQuery)){
            case 1:
                headers = camposTablaUno;
                if (campoUno.equals(campoDos)){
                    for (int i=0; i<camposTablaDos.length; i++){
                        if (i!=indiceCampoBuscadoDos){
                            headers = appendArray(headers, camposTablaDos[i]);
                        }
                    }
                } else {
                    for (int i=0; i<camposTablaDos.length; i++){
                        headers = appendArray(headers, camposTablaDos[i]);
                    }
                }
                listaDeArreglos = getRegistros();
                System.out.println(Arrays.toString(headers));
                for (String[] c : listaDeArreglos) {
                    System.out.println(Arrays.toString(c));
                }
                break;
            case 2:
                headers = camposTablaUno;
                if (campoUno.equals(campoDos)){
                    for (int i=0; i<camposTablaDos.length; i++){
                        if (i!=indiceCampoBuscadoDos){
                            headers = appendArray(headers, camposTablaDos[i]);
                        }
                    }
                } else {
                    for (int i=0; i<camposTablaDos.length; i++){
                        headers = appendArray(headers, camposTablaDos[i]);
                    }
                }
                listaDeArreglos = getRegistros();
                listaDeArreglos = Ordenar.ordenar(listaDeArreglos, !ascDsc, indiceCampoBuscadoUno);
                System.out.println(Arrays.toString(headers));
                for (String[] c : listaDeArreglos) {
                    System.out.println(Arrays.toString(c));
                }
                break;
            case 3:
                headers = camposTablaUno;
                if (campoUno.equals(campoDos)){
                    for (int i=0; i<camposTablaDos.length; i++){
                        if (i!=indiceCampoBuscadoDos){
                            headers = appendArray(headers, camposTablaDos[i]);
                        }
                    }
                } else {
                    for (int i=0; i<camposTablaDos.length; i++){
                        headers = appendArray(headers, camposTablaDos[i]);
                    }
                }
                listaDeArreglos = getRegistros();
                System.out.println(Arrays.toString(headers));
                for (int i=0; i<cantidadDeRegistros; i++) {
                    try{
                        System.out.println(Arrays.toString(listaDeArreglos.get(i)));
                    } catch (IndexOutOfBoundsException ex){}
                }
                break;
            case 4:
                headers = camposTablaUno;
                if (campoUno.equals(campoDos)){
                    for (int i=0; i<camposTablaDos.length; i++){
                        if (i!=indiceCampoBuscadoDos){
                            headers = appendArray(headers, camposTablaDos[i]);
                        }
                    }
                } else {
                    for (int i=0; i<camposTablaDos.length; i++){
                        headers = appendArray(headers, camposTablaDos[i]);
                    }
                }
                listaDeArreglos = getRegistros();
                listaDeArreglos = Ordenar.ordenar(listaDeArreglos, !ascDsc, indiceCampoBuscadoUno);
                System.out.println(Arrays.toString(headers));
                for (int i=0; i<cantidadDeRegistros; i++) {
                    try{
                        System.out.println(Arrays.toString(listaDeArreglos.get(i)));
                    } catch (IndexOutOfBoundsException ex){}
                }
                break;
            default:
                System.out.print("");
        }
    }
    
    public int verificarAccion(String[] vectorQuery){
        vaciar();
        try {
            nombreTablaUno = vectorQuery[1].split(",")[0].replace("-", " ");
            nombreTablaDos = vectorQuery[1].split(",")[1].replace("-", " ");
            campoUno = vectorQuery[3].split("=")[0].replace("-", " ");
            campoDos = vectorQuery[3].split("=")[1].replace("-", " ");
            if (campoDos.contains("\"")){
                valorCondicion=campoDos.replace("\"", "");
                campoDos = campoUno;
                condicion = true;
            }
        } catch (Exception e){
            System.err.println("Error: Error de sintaxis, revise las tablas "
                    +"(tabla1,tabla2) o la comparación de campos: "
                    +"(campo_tabla_1=campo_tabla_2 / campo_tablas=\"valor\")");
            return -1;
        }
        camposTablaUno = null;
        camposTablaDos = null;
        boolean campoUnoExiste = false;
        boolean campoDosExiste = false;
        boolean continuar = false;

        CsvReader lectorArchivoMasterTabla;
        String[] recordActual;

        
        if (!"".equals(nombreTablaUno) && !"".equals(nombreTablaDos)
                && !"".equals(campoUno) && !"".equals(campoDos)) {
            archivoTablaUno = new File(nombreTablaUno+".csv");
            archivoTablaDos = new File(nombreTablaDos+".csv");
            
            try {
                if (archivoTablaUno.exists() && archivoTablaUno
                        .getCanonicalPath().contains(nombreTablaUno)){
                    if (archivoTablaDos.exists() && archivoTablaDos
                        .getCanonicalPath().contains(nombreTablaDos)) {
                        
                        lectorArchivoMasterTabla = new CsvReader("Master.csv", ',');
                        while (lectorArchivoMasterTabla.readRecord()) {
                            recordActual = lectorArchivoMasterTabla.getValues();
                            if (recordActual[0].equals(nombreTablaUno)) {
                                camposTablaUno = recordActual[2].split(";");
                                if (recordActual.length == 4) {
                                    camposEncriptadosUno = true;
                                    indicesCamposEncriptadosUno
                                            = recordActual[3].split(";");
                                }
                            } else if (recordActual[0].equals(nombreTablaDos)) {
                                camposTablaDos = recordActual[2].split(";");
                                if (recordActual.length == 4) {
                                    camposEncriptadosDos = true;
                                    indicesCamposEncriptadosDos
                                            = recordActual[3].split(";");
                                }
                            }
                        }
                        lectorArchivoMasterTabla.close();
                        
                        for (int i=0; i<camposTablaUno.length; i++){
                            camposTablaUno[i] = camposTablaUno[i].substring(0, camposTablaUno[i].indexOf('|'));
                            if (camposTablaUno[i].equals(campoUno)){
                                campoUnoExiste = true;
                                indiceCampoBuscadoUno = i;
                            }    
                        }
                        for (int i=0; i<camposTablaDos.length; i++){
                            camposTablaDos[i] = camposTablaDos[i].substring(0, camposTablaDos[i].indexOf('|'));
                            if(camposTablaDos[i].equals(campoDos)){
                                campoDosExiste = true;
                                indiceCampoBuscadoDos = i;
                            }
                        }
                        if (campoUnoExiste){
                            if (campoDosExiste){
                                continuar = true;
                            } else {
                                System.err.println("Error: El campo: \""+campoDos
                                    +"\" no existe en la tabla: \""+nombreTablaDos
                                    +"\".");
                                return -1;
                            }    
                        } else {
                            System.err.println("Error: El campo: \""+campoUno
                                    +"\" no existe en la tabla: \""+nombreTablaUno
                                    +"\".");
                            return -1;
                        }
                    } else {
                        System.err.println("Error: La tabla:" + nombreTablaDos
                                + " no existe.");
                        return -1;
                    }
                } else {
                    System.err.println("Error: La tabla:" + nombreTablaUno
                                + " no existe.");
                    return -1;
                }
            } catch (IOException ex) {
                System.err.println("Error: No se tienen los permisos.");
                return -1;
            }
        }
        
        if (continuar){
            switch (vectorQuery.length) {
                case 4:
                    return 1;
                case 6:
                    try {
                        if (vectorQuery[4].equals("ORDENADO")) {
                            if (vectorQuery[5].equals("asc")) {
                                ascDsc = true;
                                return 2;
                            } else if (vectorQuery[5].equals("dsc")) {
                                ascDsc = false;
                                return 2;
                            } else {
                                System.err.println("Error: El valor de orden "
                                        +"solo puede ser: \"asc\" o \"dsc\"");
                                return -1;
                            }
                        } else {
                            cantidadDeRegistros = Integer.parseInt(vectorQuery[5]);
                            return 3;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Eror: Se esperaba un número después"
                                +" de VER.");
                        return -1;
                    }
                case 8:
                    if (vectorQuery[5].equals("asc") || vectorQuery[5].equals("dsc")) {
                        if (vectorQuery[5].equals("asc")){
                            ascDsc = true;
                        } else {
                            ascDsc = false;
                        }
                        try {
                            cantidadDeRegistros = Integer.parseInt(vectorQuery[7]);
                            return 4;
                        } catch (NumberFormatException e) {
                            System.err.println("Eror: Se esperaba un número "
                                    +"después de VER.");
                            return -1;
                        }
                    } else{
                        System.err.println("Error: El valor de orden "
                                        +"solo puede ser: \"asc\" o \"dsc\"");
                        return -1;
                    }
            }
        } else {
            System.err.println("Error: Error de Síntaxis, revise los nombres de"
                    +" las tablas o el nombre del campo o campos a comparar.");
        }
        return -1;
    }
    
    public void vaciar() {
        nombreTablaUno = "";
        nombreTablaDos = "";
        campoUno = "";
        campoDos = "";
        indiceCampoBuscadoUno = -1;
        indiceCampoBuscadoDos = -1;
        ascDsc = true;
        cantidadDeRegistros = -1;
        archivoTablaUno = null;
        archivoTablaDos = null;
        archivoAuxiliar = null;
        camposEncriptadosUno = false;
        camposEncriptadosDos = false;
        camposTablaUno = null;
        camposTablaDos = null;
        indicesCamposEncriptadosUno = null;
        indicesCamposEncriptadosDos = null;
        listaDeArreglos = null;
        valorCondicion="";
        condicion = false;
    }
    
    private String[] appendArray(String[] array, String x) {
        String[] result = new String[array.length + 1];

        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }

        result[result.length - 1] = x;

        return result;
    }
    
    public List<String[]> getRegistros() {
        List<String[]> registros = new ArrayList<String[]>();
        CsvReader lectorArchivoTablaUno;
        CsvReader lectorArchivoTablaDos;
        String[] recordActual = null;
        String[] recordActualDos = null;
        boolean primeraVez = true;
        boolean primeraVezDos = true;
        String[] nuevoArray = new String[0];

        boolean condicionInside;
        
        try {
            lectorArchivoTablaUno = new CsvReader(nombreTablaUno + ".csv");
            lectorArchivoTablaDos = new CsvReader(nombreTablaDos + ".csv");
            if (!camposEncriptadosUno) {
                while (lectorArchivoTablaUno.readRecord()) {
                    if (primeraVez) {
                        recordActual = lectorArchivoTablaUno.getValues();
                        primeraVez = false;
                    } else {
                        recordActual = lectorArchivoTablaUno.getValues();
                        lectorArchivoTablaDos = new CsvReader(nombreTablaDos + ".csv");
                        if (!camposEncriptadosDos) {
                            while (lectorArchivoTablaDos.readRecord()) {
                                if (primeraVezDos) {
                                    recordActualDos = lectorArchivoTablaDos.getValues();
                                    primeraVezDos = false;
                                } else {
                                    recordActualDos = lectorArchivoTablaDos.getValues();
                                    
                                    if (condicion){
                                        condicionInside = (recordActual[indiceCampoBuscadoUno].equals(valorCondicion) && recordActualDos[indiceCampoBuscadoDos].equals(valorCondicion));
                                    } else {
                                        condicionInside = recordActual[indiceCampoBuscadoUno].equals(recordActualDos[indiceCampoBuscadoDos]);
                                    }
                                    
                                    if (condicionInside) {
                                        if (campoUno.equals(campoDos)) {
                                            nuevoArray = recordActual;
                                            for (int i = 0; i < recordActualDos.length; i++) {
                                                if (i != indiceCampoBuscadoDos) {
                                                    recordActual= appendArray(recordActual, recordActualDos[i]);
                                                }
                                            }
                                            registros.add(recordActual);
                                            recordActual = nuevoArray;
                                        } else {
                                            nuevoArray = recordActual;
                                            for (int i = 0; i < recordActualDos.length; i++) {
                                                recordActual = appendArray(recordActual, recordActualDos[i]);
                                            }
                                            registros.add(recordActual);
                                            recordActual = nuevoArray;
                                        }
                                    }
                                }
                            }
                        } else {
                            while (lectorArchivoTablaDos.readRecord()) {
                                if (primeraVezDos) {
                                    recordActualDos = lectorArchivoTablaDos.getValues();
                                    primeraVezDos = false;
                                } else {
                                    recordActualDos = lectorArchivoTablaDos.getValues();
                                    for (int i = 0; i < indicesCamposEncriptadosDos.length; i++) {
                                        int indice = Integer.parseInt(indicesCamposEncriptadosDos[i].substring(indicesCamposEncriptadosDos[i].indexOf("|") + 1));
                                        recordActualDos[indice] = StringEncrypt.decrypt(key, iv, recordActualDos[indice]);
                                    }
                                    
                                    if (condicion){
                                        condicionInside = (recordActual[indiceCampoBuscadoUno].equals(valorCondicion) && recordActualDos[indiceCampoBuscadoDos].equals(valorCondicion));
                                    } else {
                                        condicionInside = recordActual[indiceCampoBuscadoUno].equals(recordActualDos[indiceCampoBuscadoDos]);
                                    }
                                    
                                    if (condicionInside) {
                                        if (campoUno.equals(campoDos)) {
                                            nuevoArray = recordActual;
                                            for (int i = 0; i < recordActualDos.length; i++) {
                                                if (i != indiceCampoBuscadoDos) {
                                                    recordActual = appendArray(recordActual, recordActualDos[i]);
                                                }
                                            }
                                            registros.add(recordActual);
                                            recordActual = nuevoArray;
                                        } else {
                                            nuevoArray = recordActual;
                                            for (int i = 0; i < recordActualDos.length; i++) {
                                                recordActual = appendArray(recordActual, recordActualDos[i]);
                                            }
                                            registros.add(recordActual);
                                            recordActual = nuevoArray;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    lectorArchivoTablaDos.close();
                }
                lectorArchivoTablaUno.close();
            } else {
                while (lectorArchivoTablaUno.readRecord()) {
                    if (primeraVez) {
                        recordActual = lectorArchivoTablaUno.getValues();
                        primeraVez = false;
                    } else {
                        recordActual = lectorArchivoTablaUno.getValues();
                        for (int i = 0; i < indicesCamposEncriptadosUno.length; i++) {
                            int indice = Integer.parseInt(indicesCamposEncriptadosUno[i].substring(indicesCamposEncriptadosUno[i].indexOf("|") + 1));
                            recordActual[indice] = StringEncrypt.decrypt(key, iv, recordActual[indice]);
                        }
                        
                        lectorArchivoTablaDos = new CsvReader(nombreTablaDos + ".csv");
                        if (!camposEncriptadosDos) {
                            while (lectorArchivoTablaDos.readRecord()) {
                                if (primeraVezDos) {
                                    recordActualDos = lectorArchivoTablaDos.getValues();
                                    primeraVezDos = false;
                                } else {
                                    recordActualDos = lectorArchivoTablaDos.getValues();
                                    
                                    if (condicion){
                                        condicionInside = (recordActual[indiceCampoBuscadoUno].equals(valorCondicion) && recordActualDos[indiceCampoBuscadoDos].equals(valorCondicion));
                                    } else {
                                        condicionInside = recordActual[indiceCampoBuscadoUno].equals(recordActualDos[indiceCampoBuscadoDos]);
                                    }
                                    
                                    if (condicionInside) {
                                        if (campoUno.equals(campoDos)) {
                                            nuevoArray = recordActual;
                                            for (int i = 0; i < recordActualDos.length; i++) {
                                                if (i != indiceCampoBuscadoDos) {
                                                    recordActual = appendArray(recordActual, recordActualDos[i]);
                                                }
                                            }
                                            registros.add(recordActual);
                                            recordActual=nuevoArray;
                                        } else {
                                            nuevoArray = recordActual;
                                            for (int i = 0; i < recordActualDos.length; i++) {
                                                recordActual = appendArray(recordActual, recordActualDos[i]);
                                            }
                                            registros.add(recordActual);
                                            recordActual = nuevoArray;
                                        }
                                    }
                                }
                            }
                        } else {
                            while (lectorArchivoTablaDos.readRecord()) {
                                if (primeraVezDos) {
                                    recordActualDos = lectorArchivoTablaDos.getValues();
                                    primeraVezDos = false;
                                } else {
                                    recordActualDos = lectorArchivoTablaDos.getValues();
                                    for (int i = 0; i < indicesCamposEncriptadosDos.length; i++) {
                                        int indice = Integer.parseInt(indicesCamposEncriptadosDos[i].substring(indicesCamposEncriptadosDos[i].indexOf("|") + 1));
                                        recordActualDos[indice] = StringEncrypt.decrypt(key, iv, recordActualDos[indice]);
                                    }
                                    if (condicion){
                                        condicionInside = (recordActual[indiceCampoBuscadoUno].equals(valorCondicion) && recordActualDos[indiceCampoBuscadoDos].equals(valorCondicion));
                                    } else {
                                        condicionInside = recordActual[indiceCampoBuscadoUno].equals(recordActualDos[indiceCampoBuscadoDos]);
                                    }
                                    
                                    if (condicionInside) {
                                        if (campoUno.equals(campoDos)) {
                                            nuevoArray = recordActual;
                                            for (int i = 0; i < recordActualDos.length; i++) {
                                                if (i != indiceCampoBuscadoDos) {
                                                    recordActual = appendArray(recordActual, recordActualDos[i]);
                                                }
                                            }
                                            registros.add(recordActual);
                                            recordActual = nuevoArray;
                                        } else {
                                            nuevoArray = recordActual;
                                            for (int i = 0; i < recordActualDos.length; i++) {
                                                recordActual = appendArray(recordActual, recordActualDos[i]);
                                            }
                                            registros.add(recordActual);
                                            recordActual = nuevoArray;
                                        }
                                    }
                                }
                            }
                        }
                        
                        
                    }
                    lectorArchivoTablaDos.close();
                }
                lectorArchivoTablaUno.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado.");
        } catch (IOException ex) {
            System.err.println("Error: No se tienen los permisos.");
        } catch (Exception ex) {
            System.err.println("Error: Problema desencriptando.");
        }
        return registros;
    }
}
