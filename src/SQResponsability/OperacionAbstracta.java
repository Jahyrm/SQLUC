/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQResponsability;

/**
 *
 * @author Jahyr
 */
public abstract class OperacionAbstracta {
    public static int CREAR = 1;
    public static int MODIFICAR = 2;
    public static int ELIMINAR = 3;
    public static int INSERTAR = 4;
    public static int ACTUALIZAR = 5;
    public static int BORRAR = 6;
    public static int SELECCIONAR = 7;
    public static int UNIR = 8;
    
    protected int clave;
    
    //siguiente elemento en la cadena de responsabilidad
    protected OperacionAbstracta siguienteOperacion;
    
    public void setSiguienteOperacion(OperacionAbstracta siguienteOperacion){
        this.siguienteOperacion = siguienteOperacion;
    }
    
    public void realizarOperacion(int clave, String[] query){
        if(this.clave==clave){
            accion(query);
        }
        if(siguienteOperacion!=null){
            siguienteOperacion.realizarOperacion(clave, query);
        }
    }
    
    abstract protected void accion(String[] query);
}

