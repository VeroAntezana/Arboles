/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.arboled2.arboles;

import java.util.Scanner;

/**
 *
 * @author Veronica
 */
public class TestArbol {
    public static void main(String argumentos[]){
    IArbolBusqueda<Integer,String>arbolBusqueda;
    ArbolBinarioBusqueda< Integer,String>arbol= new ArbolBinarioBusqueda<>() ;
    Scanner entrada=new Scanner(System.in);
    System.out.print("Elija un tipo de arbol(ABB,AVl)");
    String tipoArbol= entrada.next();
    tipoArbol=tipoArbol.toUpperCase();
    switch(tipoArbol){
        case "ABB":
            arbolBusqueda=new ArbolBinarioBusqueda<>();
            break;
        case "AVL":
            arbolBusqueda=new AVL<>();
            break;
        default :
            return;
    }
   // arbol=new ArbolBinarioBusqueda<>();
   /* arbol.insertar(50,"Franco");
    arbol.insertar(75,"Abner");
    arbol.insertar(25,"Yuliana");
    arbol.insertar(35,"Jorge");
    arbol.insertar(40,"Marco");
    arbol.insertar(15,"Ruth");
    arbol.insertar(10,"Ricky");
    arbol.insertar(23,"Harold");
    arbol.insertar(22,"Laura");
    arbol.insertar(24,"Jaquelin");
    arbol.insertar(30,"Nataniel");
    */
   arbol.insertar(3,"3");
   arbol.insertar(2,"2");
   arbol.insertar(1,"1");
   arbol.insertar(4,"4");
    System.out.println(arbol);
    //arbol.cantidadDeHojas();
    //arbol.cantidadDeHijosVacios();
    //arbol.altura();
    System.out.println("Altura: "+arbol.altura());
    System.out.println("Nivel: "+arbol.altura());
    System.out.println("Cantidad de Hojas: "+arbol.cantidadDeHojas());
    System.out.println("Cantidad De Hijos: "+arbol.cantidadDeHijosVaciosRec());
    System.out.println("Cantidad De Hijos Ite: "+arbol.cantidadDeHijosVaciosRec());
    System.out.println("Arbol InOrden: "+arbol.recorridoEnInOrdenIter());
     System.out.println("Size: "+arbol.size());
      System.out.println("PostOrden: "+arbol.recorridoEnPostOrden());
      System.out.println("Size PostOrden: "+arbol.sizeEnPostOrden());
    }
}
