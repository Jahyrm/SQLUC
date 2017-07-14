/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqluc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import SQResponsability.*;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.*; //Para CPU time

/**
 *
 * @author Jahyr
 */
public class Main {

    public static String query = "";
    public static Scanner in = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Date fecha = new Date();
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        String[] vectorQuery;
        OperacionAbstracta cadenaDeOperaciones = getCadenaDeOperaciones();
        System.out.println("SQLUC version 1.00 " + formatoFecha.format(fecha)
                + " " + formatoHora.format(fecha));
        System.out.println("Ingrese .ayuda para sugerencias de uso.");
        while (!query.equals("SALIR")) {
            System.out.print("sqluc>");
            query = in.nextLine().replaceAll(" +", " ").trim();
            if (query.contains(" ")) {
                vectorQuery = query.split(" ");
                // Comentado por que es de prueba: System.out.println(Arrays.toString(vectorQuery)); 
                switch(vectorQuery[0]){
                    case "CREAR":
                        if (validarQuery(vectorQuery, 1)){

                            //<MEDIDOR DE TIEMPO START>
                            long startTimeMs = System.currentTimeMillis( );
                            long startSystemTimeNano = getSystemTime( );
                            long startUserTimeNano   = getUserTime( );
                            
                            
                            cadenaDeOperaciones.realizarOperacion(
                                    OperacionAbstracta.CREAR, vectorQuery);
                            
                            
                            //</MEDIDOR DE TIEMPO END>
                            long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
                            long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
                            long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
                            System.out.println("\nWall clock time: "+taskTimeMs
                                    +" miliseg.");
                            System.out.println("CPU time: "+(taskUserTimeNano+taskSystemTimeNano)+" nanosegs.");
                            System.out.println("  User: "+taskUserTimeNano+" nanosegs.");
                            System.out.println("  System:"+taskSystemTimeNano+" nanosegs.\n");
                            
                        } else {
                            System.err.println("Error: "+query+". Error de síntaxis.");
                        }
                        break;
                    case "MODIFICAR":
                        if (validarQuery(vectorQuery, 2)){
                            
                            //<MEDIDOR DE TIEMPO START>
                            long startTimeMs = System.currentTimeMillis( );
                            long startSystemTimeNano = getSystemTime( );
                            long startUserTimeNano   = getUserTime( );
                            
                            
                            cadenaDeOperaciones.realizarOperacion(
                                    OperacionAbstracta.MODIFICAR, vectorQuery);
                            
                            
                            //</MEDIDOR DE TIEMPO END>
                            long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
                            long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
                            long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
                            System.out.println("\nWall clock time: "+taskTimeMs
                                    +" miliseg.");
                            System.out.println("CPU time: "+(taskUserTimeNano+taskSystemTimeNano)+" nanosegs.");
                            System.out.println("  User: "+taskUserTimeNano+" nanosegs.");
                            System.out.println("  System:"+taskSystemTimeNano+" nanosegs.\n");
                            
                        } else {
                            System.err.println("Error: "+query+". Error de síntaxis.");
                        }
                        break;
                    case "ELIMINAR":
                        if (validarQuery(vectorQuery, 3)){
                            
                            //<MEDIDOR DE TIEMPO START>
                            long startTimeMs = System.currentTimeMillis( );
                            long startSystemTimeNano = getSystemTime( );
                            long startUserTimeNano   = getUserTime( );
                            
                            
                            cadenaDeOperaciones.realizarOperacion(
                                    OperacionAbstracta.ELIMINAR, vectorQuery);
                            
                            
                            //</MEDIDOR DE TIEMPO END>
                            long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
                            long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
                            long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
                            System.out.println("\nWall clock time: "+taskTimeMs
                                    +" miliseg.");
                            System.out.println("CPU time: "+(taskUserTimeNano+taskSystemTimeNano)+" nanosegs.");
                            System.out.println("  User: "+taskUserTimeNano+" nanosegs.");
                            System.out.println("  System:"+taskSystemTimeNano+" nanosegs.\n");
                            
                        } else {
                            System.err.println("Error: "+query+". Error de síntaxis.");
                        }
                        break;
                    case "INSERTAR":
                        if (validarQuery(vectorQuery, 4)){
                            
                            //<MEDIDOR DE TIEMPO START>
                            long startTimeMs = System.currentTimeMillis( );
                            long startSystemTimeNano = getSystemTime( );
                            long startUserTimeNano   = getUserTime( );
                            
                            
                            cadenaDeOperaciones.realizarOperacion(
                                    OperacionAbstracta.INSERTAR, vectorQuery);
                            
                            
                            //</MEDIDOR DE TIEMPO END>
                            long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
                            long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
                            long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
                            System.out.println("\nWall clock time: "+taskTimeMs
                                    +" miliseg.");
                            System.out.println("CPU time: "+(taskUserTimeNano+taskSystemTimeNano)+" nanosegs.");
                            System.out.println("  User: "+taskUserTimeNano+" nanosegs.");
                            System.out.println("  System:"+taskSystemTimeNano+" nanosegs.\n");
                            
                        } else {
                            System.err.println("Error: "+query+". Error de síntaxis.");
                        }
                        break;
                    case "ACTUALIZAR":
                        if (validarQuery(vectorQuery, 5)){
                            
                            //<MEDIDOR DE TIEMPO START>
                            long startTimeMs = System.currentTimeMillis( );
                            long startSystemTimeNano = getSystemTime( );
                            long startUserTimeNano   = getUserTime( );
                            
                            
                            cadenaDeOperaciones.realizarOperacion(
                                    OperacionAbstracta.ACTUALIZAR, vectorQuery);
                            
                            
                            //</MEDIDOR DE TIEMPO END>
                            long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
                            long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
                            long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
                            System.out.println("\nWall clock time: "+taskTimeMs
                                    +" miliseg.");
                            System.out.println("CPU time: "+(taskUserTimeNano+taskSystemTimeNano)+" nanosegs.");
                            System.out.println("  User: "+taskUserTimeNano+" nanosegs.");
                            System.out.println("  System:"+taskSystemTimeNano+" nanosegs.\n");
                            
                        } else {
                            System.err.println("Error: "+query+". Error de síntaxis.");
                        }
                        break;
                    case "BORRAR":
                        if (validarQuery(vectorQuery, 6)){
                            
                            //<MEDIDOR DE TIEMPO START>
                            long startTimeMs = System.currentTimeMillis( );
                            long startSystemTimeNano = getSystemTime( );
                            long startUserTimeNano   = getUserTime( );
                            
                            
                            cadenaDeOperaciones.realizarOperacion(
                                    OperacionAbstracta.BORRAR, vectorQuery);
                            
                            
                            //</MEDIDOR DE TIEMPO END>
                            long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
                            long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
                            long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
                            System.out.println("\nWall clock time: "+taskTimeMs
                                    +" miliseg.");
                            System.out.println("CPU time: "+(taskUserTimeNano+taskSystemTimeNano)+" nanosegs.");
                            System.out.println("  User: "+taskUserTimeNano+" nanosegs.");
                            System.out.println("  System:"+taskSystemTimeNano+" nanosegs.\n");
                            
                        } else {
                            System.err.println("Error: "+query+". Error de síntaxis.");
                        }
                        break;
                    case "SELECCIONAR":
                        if (validarQuery(vectorQuery, 7)){
                            
                            //<MEDIDOR DE TIEMPO START>
                            long startTimeMs = System.currentTimeMillis( );
                            long startSystemTimeNano = getSystemTime( );
                            long startUserTimeNano   = getUserTime( );
                            
                            
                            cadenaDeOperaciones.realizarOperacion(
                                    OperacionAbstracta.SELECCIONAR, vectorQuery);
                            
                            
                            //</MEDIDOR DE TIEMPO END>
                            long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
                            long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
                            long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
                            System.out.println("\nWall clock time: "+taskTimeMs
                                    +" miliseg.");
                            System.out.println("CPU time: "+(taskUserTimeNano+taskSystemTimeNano)+" nanosegs.");
                            System.out.println("  User: "+taskUserTimeNano+" nanosegs.");
                            System.out.println("  System:"+taskSystemTimeNano+" nanosegs.\n");
                            
                            
                        } else {
                            System.err.println("Error: "+query+". Error de síntaxis.");
                        }
                        break;
                    case "UNIR":
                        if (validarQuery(vectorQuery, 8)){
                            
                            //<MEDIDOR DE TIEMPO START>
                            long startTimeMs = System.currentTimeMillis( );
                            long startSystemTimeNano = getSystemTime( );
                            long startUserTimeNano   = getUserTime( );
                            
                            
                            cadenaDeOperaciones.realizarOperacion(
                                    OperacionAbstracta.UNIR, vectorQuery);
                            
                            
                            //</MEDIDOR DE TIEMPO END>
                            long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
                            long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
                            long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
                            System.out.println("\nWall clock time: "+taskTimeMs
                                    +" miliseg.");
                            System.out.println("CPU time: "+(taskUserTimeNano+taskSystemTimeNano)+" nanosegs.");
                            System.out.println("  User: "+taskUserTimeNano+" nanosegs.");
                            System.out.println("  System:"+taskSystemTimeNano+" nanosegs.\n");
                            
                        } else {
                            System.err.println("Error: "+query+". Error de síntaxis.");
                        }
                        break;
                    case "LEER":
                        if(comprobarLeer(vectorQuery)){
                            scriptLeerTabla(vectorQuery);
                        }
                        break;
                    default:
                        System.err.println("Error: "+query+". Error de síntaxis.");
                }
            } else {
                switch (query) {
                    case ".ayuda":
                        mensajeDeAyuda();
                        break;
                    case "SALIR":
                        break;
                    default:
                        System.err.println("Error: "+query+". Error de sintaxis.");
                }
            }
        }
    }
    
    public static boolean comprobarLeer(String[] vquery){
        if (vquery.length==2){
            if(vquery[1].split("\\.")[1].equalsIgnoreCase("txt")||vquery[1].split("\\.")[1].equalsIgnoreCase("csv")){
                return true;
            } else {
                System.err.println("Error: Solo se permiten archivos "
                        +"\".csv\" o \".txt\"");
                return false;
            }
        } else {
            System.err.println("Error: "+query+". Error de síntaxis.");
            return false;
        }
    }
    
    public static void scriptLeerTabla(String[] vectorQuery){
        String nombreTabla = vectorQuery[1].split("\\.")[0];
        String llave = "";
        String camposLongitudes = "";
        File archivoTabla = new File(nombreTabla);
        File archivoMasterTablas = new File("Master.csv");
        CsvReader lectorArchivoTabla;
        CsvWriter salidaParaMasterTablas;
        try{
            lectorArchivoTabla = new CsvReader(vectorQuery[1]);
            String[] campos;
            String[] primeraFila;
            if (lectorArchivoTabla.readHeaders()) {
                campos = lectorArchivoTabla.getHeaders();
                llave = campos[0];
                if (lectorArchivoTabla.readRecord()) {
                    primeraFila = lectorArchivoTabla.getValues();
                    if (campos.length == primeraFila.length) {
                        for (int i = 0; i < campos.length; i++) {
                            if (i == 0) {
                                camposLongitudes = camposLongitudes + campos[i] + "|" + primeraFila[i].length();
                            } else {
                                camposLongitudes = camposLongitudes + ";" + campos[i] + "|" + primeraFila[i].length();
                            }
                        }
                        lectorArchivoTabla.close();
                        
                        String opcion = "";
                        boolean conti = true;
                        while ((!opcion.equals("N") && !opcion.equals("S"))) {
                            System.out.println("La CLAVE de la Tabla es el 1er "
                                +"campo: \""+llave+"\"? S/N ");
                            opcion = in.nextLine().replaceAll(" +", "").trim();
                            if (opcion.equals("S")) {} else if (opcion.equals("N")) {
                                System.out.println("");
                                for (int i=0; i<campos.length;i++){
                                    System.out.println((i)+": "+campos[i]);
                                }
                                String opcionDosString;
                                int opcionDos = -1;
                                while (!(opcionDos>=0 
                                        && opcionDos < campos.length)) {
                                    System.out.println("Seleccione el campo "
                                            +"clave de la lista:");
                                    opcionDosString = in.nextLine();
                                    try {
                                        opcionDos = Integer.parseInt(opcionDosString);
                                    } catch(NumberFormatException ex) {
                                        System.err.println("Error: Ingrese un "
                                                +"NÚMERO de la lista.");
                                        conti = false;
                                    }
                                    if (opcionDos >= 0 && opcionDos < campos.length) {
                                        llave = campos[opcionDos];
                                    } else {
                                        if (conti){
                                            System.err.println("Error: El campo elegido"
                                                + " no es correcto.");
                                        }
                                        conti = true;
                                    }
                                }
                            } else {
                                System.err.println("Error: Ingrese \"S\" para"
                                        +" si, \"N\" para no.");
                            }
                        }
                        String[] vectorInfoTabla = {nombreTabla, llave, camposLongitudes};
                        
                        CsvWriter salidaParaMaster
                                = new CsvWriter(new FileWriter(archivoMasterTablas,
                                        true), ',');
                        salidaParaMaster.writeRecord(vectorInfoTabla);
                        salidaParaMaster.close();
                    } else {
                        System.err.println("Error: El archivo es incorrecto.");
                    }
                }
            } else {
                System.err.println("Error: el archivo está vacío o es incorrecto.");
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error: No se encuentra el Archivo.");
        } catch (IOException ex) {
            System.err.println("Error: No se tienen los permisos.");
        }
    }
    
    public static void mensajeDeAyuda() {
        System.out.println("");
        System.out.println("[OPCIONAL]\n");
        System.out.println("(ALTERNATIVA1/ALTERNATIVA2)\n");
        System.out.println(".ayuda\t Muestra este mensaje.\n\n");
        System.out.println("TABLAS:");
        System.out.println("Crear una tabla en la base de datos:");
        System.out.println("CREAR TABLA nombre_tabla CAMPOS campo1,campo2,...,"
                +"campoN CLAVE campoN LONGITUD longitud1,longitud2,...,"
                +"longitudN [ENCRIPTADO campo1,campo2,...,campoN]\n");
        System.out.println("Si el nombre de un campo contiene 2 o más palabras"
                +" se debe utilizar un guión. Ej:");
        System.out.println("CREAR TABLA nombre_tabla CAMPOS c1-c,c2-d-d,...,"
                +"c-N CLAVE c1-c LONGITUD l1,l2,...,lN\n");
        System.out.println("Si desea encriptar debe mandar el nombre del campo"
                +" o los campo que se desea encriptar\n");
        System.out.println("Modificar una tabla:");
        System.out.println("MODIFICAR TABLA nombre_tabla CAMPO nombre_campo "
                +"POR nombre_campo\n");
        System.out.println("Eliminar una tabla:");
        System.out.println("ELIMINAR TABLA nombre_tabla\n\n");
        System.out.println("REGISTROS:");
        System.out.println("Crear un registro en una tabla:");
        System.out.println("INSERTAR EN nombre_tabla VALORES vCampo1,v-Campo2,"
                +"...,v-Campo-N\n");
        System.out.println("Modificar un registro en una tabla:");
        System.out.println("ACTUALIZAR REGISTRO nombre_tabla CLAVE "
                +"valorCampoClave CAMPO campo POR valor_campo_nuevo\n");
        System.out.println("Eliminar registro de una tabla:");
        System.out.println("BORRAR REGISTRO nombre_tabla CLAVE "
                +"valorCampoClave\n");
        System.out.println("Seleccionar registros de una tabla:");
        System.out.println("SELECCIONAR DE nombre_tabla [DONDE nombre_campo"
                +"=\"Algo\"] [ORDENADO asc/desc] [VER numero_registros]\n");
        System.out.println("Unir tablas:");
        System.out.println("UNIR nombre_tabla1,nombre_tabla2 POR (campoAmbasTablas"
                +"=\"Algo\"/campoTabla1=campoTabla2) [ORDENADO asc/desc] [VER numero_registros]\n");
        System.out.println("Leer tabla externa ingresada manualmente:");
        System.out.println("LEER nombreTabla.csv");
    }
    
    public static OperacionAbstracta getCadenaDeOperaciones(){
        OperacionAbstracta operacionCrear = 
                new OperacionCrear(OperacionAbstracta.CREAR);
        OperacionAbstracta operacionModificar = 
                new OperacionModificar(OperacionAbstracta.MODIFICAR);
        OperacionAbstracta operacionEliminar = 
                new OperacionEliminar(OperacionAbstracta.ELIMINAR);
        OperacionAbstracta operacionInsertar = 
                new OperacionInsertar(OperacionAbstracta.INSERTAR);
        OperacionAbstracta operacionActualizar = 
                new OperacionActualizar(OperacionAbstracta.ACTUALIZAR);
        OperacionAbstracta operacionBorrar = 
                new OperacionBorrar(OperacionAbstracta.BORRAR);
        OperacionAbstracta operacionSeleccionar = 
                new OperacionSeleccionar(OperacionAbstracta.SELECCIONAR);
        OperacionAbstracta operacionUnir = 
                new OperacionUnir(OperacionAbstracta.UNIR);
        
        operacionCrear.setSiguienteOperacion(operacionModificar);
        operacionModificar.setSiguienteOperacion(operacionEliminar);
        operacionEliminar.setSiguienteOperacion(operacionInsertar);
        operacionInsertar.setSiguienteOperacion(operacionActualizar);
        operacionActualizar.setSiguienteOperacion(operacionBorrar);
        operacionBorrar.setSiguienteOperacion(operacionSeleccionar);
        operacionSeleccionar.setSiguienteOperacion(operacionUnir);
        
        return operacionCrear;
    }
    
    public static boolean validarQuery(String[] vectorQuery, int clave){

        switch (clave) {
            case 1:
                if (vectorQuery.length == 9) {
                    if (vectorQuery[1].equals("TABLA")
                            && vectorQuery[3].equals("CAMPOS")
                            && vectorQuery[5].equals("CLAVE")
                            && vectorQuery[7].equals("LONGITUD")) {
                        return true;
                    }
                } else if (vectorQuery.length == 10){
                    if (vectorQuery[1].equals("TABLA")
                            && vectorQuery[3].equals("CAMPOS")
                            && vectorQuery[5].equals("CLAVE")
                            && vectorQuery[7].equals("LONGITUD")
                            && vectorQuery[9].equals("ENCRIPTADO")) {
                        return true;
                    }
                } else if (vectorQuery.length == 11) {
                    if (vectorQuery[1].equals("TABLA")
                            && vectorQuery[3].equals("CAMPOS")
                            && vectorQuery[5].equals("CLAVE")
                            && vectorQuery[7].equals("LONGITUD")
                            && vectorQuery[9].equals("ENCRIPTADO")) {
                        return true;
                    }
                }
                break;
            case 2:
                if (vectorQuery.length == 7) {
                    if (vectorQuery[1].equals("TABLA")
                            && vectorQuery[3].equals("CAMPO")
                            && vectorQuery[5].equals("POR")) {
                        return true;
                    }
                }
                break;
            case 3:
                if (vectorQuery.length == 3){
                    if (vectorQuery[1].equals("TABLA")) {
                        return true;
                    }
                }
                break;
            case 4:
                if (vectorQuery.length == 5){
                    if (vectorQuery[1].equals("EN")
                            && vectorQuery[3].equals("VALORES")) {
                        return true;
                    }
                }
                break;
            case 5:
                if (vectorQuery.length == 9){
                    if (vectorQuery[1].equals("REGISTRO")
                            && vectorQuery[3].equals("CLAVE")
                            && vectorQuery[5].equals("CAMPO")
                            && vectorQuery[7].equals("POR")) {
                        return true;
                    }
                }
                break;
            case 6:
                if (vectorQuery.length == 5){
                    if (vectorQuery[1].equals("REGISTRO")
                            && vectorQuery[3].equals("CLAVE")) {
                        return true;
                    }
                }
                break;
            case 7:
                if (vectorQuery.length == 3){
                    if (vectorQuery[1].equals("DE")) {
                        return true;
                    }
                } else if (vectorQuery.length == 5) {
                    if (vectorQuery[1].equals("DE")
                            && (vectorQuery[3].equals("DONDE") 
                            || vectorQuery[3].equals("ORDENADO") 
                            || vectorQuery[3].equals("VER"))) {
                        return true;
                    }
                } else if (vectorQuery.length == 7){
                    if (vectorQuery[1].equals("DE")){
                        if (vectorQuery[3].equals("DONDE")) {
                            if (vectorQuery[5].equals("ORDENADO")) {
                                return true;
                            } else if (vectorQuery[5].equals("VER")) {
                                return true;
                            }
                        } else {
                            if (vectorQuery[5].equals("VER")){
                                return true;
                            }
                        }
                    }
                } else if (vectorQuery.length == 9) {
                    if (vectorQuery[1].equals("DE")
                            && vectorQuery[3].equals("DONDE")
                            && vectorQuery[5].equals("ORDENADO")
                            && vectorQuery[7].equals("VER")) {
                        return true;
                    }
                }
                break;
            case 8:
                if (vectorQuery.length == 4){
                    if (vectorQuery[2].equals("POR")) {
                        return true;
                    }
                } else if (vectorQuery.length == 6){
                    if (vectorQuery[2].equals("POR")
                            && (vectorQuery[4].equals("ORDENADO")
                            || vectorQuery[4].equals("VER"))){
                        return true;
                    }
                } else if (vectorQuery.length == 8){
                    if (vectorQuery[2].equals("POR")
                            && vectorQuery[4].equals("ORDENADO")
                            && vectorQuery[6].equals("VER")){
                        return true;
                    }
                }
                break;
            default:
                System.err.println("Este error no debería ocurrir.");
        }
        return false;
    }
    
    /**
     * Get CPU time in nanoseconds.
     */
    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime() : 0L;
    }

    /**
     * Get user time in nanoseconds.
     */
    public static long getUserTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadUserTime() : 0L;
    }

    /**
     * Get system time in nanoseconds.
     */
    public static long getSystemTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported()
                ? (bean.getCurrentThreadCpuTime() - bean.getCurrentThreadUserTime()) : 0L;
    }
    
}
