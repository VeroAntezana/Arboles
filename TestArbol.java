/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.arboled2.arboles;

import ed2.uagrm.arboled2.excepciones.ExcepcionClaveNoExiste;
import java.util.Scanner;

/**
 *
 * @author Veronica
 */
public class TestArbol {
    public static void main(String argumentos[]) throws ExcepcionClaveNoExiste{
    IArbolBusqueda<Integer,String>arbolBusqueda;
   ArbolBinarioBusqueda< Integer,String>arbol= new ArbolBinarioBusqueda<>() ;
     
    Scanner entrada=new Scanner(System.in);
    System.out.print("Elija un tipo de arbol(ABB,AVl,AMV)");
    String tipoArbol= entrada.next();
    tipoArbol=tipoArbol.toUpperCase();
    switch(tipoArbol){
        case "ABB":
            arbolBusqueda=new ArbolBinarioBusqueda<>();
            break;
        case "AVL":
            arbolBusqueda=new AVL<>();
            break;
        case "AMV":
            arbolBusqueda=new ArbolMViasBusqueda<>();
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
   
   
   arbol.insertar(5,"5");
   arbol.insertar(3,"3");
   arbol.insertar(2,"2");
   arbol.insertar(4,"4");
   arbol.insertar(7,"7");
   arbol.insertar(6,"6");
   arbol.insertar(9,"9");
  
    System.out.println(arbol);
  // arbol.eliminar(1);
  // arbol.eliminar(2);
  // arbol.eliminar(3);
  // arbol.eliminar(4);
  // arbol.eliminar(5);
  // arbol.eliminar(6);
   // System.out.println(arbol);
    //arbol.cantidadDeHojas();
    //arbol.cantidadDeHijosVacios();
    //arbol.altura();
    System.out.println("Altura: "+arbol.altura());
    System.out.println("Nivel: "+arbol.nivel());
    System.out.println("Cantidad de Hojas: "+arbol.cantidadDeHojas());
    System.out.println("Cantidad De Hijos Vacios: "+arbol.cantidadDeHijosVaciosRec());
    System.out.println("Ejercicio N°3: "+arbol.cantidadDeHijosNoVacios());
    System.out.println("Cantidad De Hijos Vacios Ite: "+arbol.cantidadDeHijosVacios());
     System.out.println("Ejercicio N°4: "+arbol.cantidadDeHijosNoVaciosRec());
     System.out.println("Ejercicio N°5: "+arbol.distintoDeVacioEnUnNivel(1));
    System.out.println("Arbol InOrden: "+arbol.recorridoEnInOrdenIter());
     System.out.println("Size: "+arbol.size());
      System.out.println("PostOrden: "+arbol.recorridoEnPostOrden());
      System.out.println("Size PostOrden: "+arbol.sizeEnPostOrden());
    }
}
