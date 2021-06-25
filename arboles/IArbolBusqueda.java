/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.arboled2.arboles;

import ed2.uagrm.arboled2.excepciones.ExcepcionClaveNoExiste;
import java.util.List;

/**
 *
 * @author Veronica
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda< K extends Comparable <K>, V>  {
    void insertar (K claveAInsertar, V valorAInsertar ) ;
    V eliminar (K claveEliminar) throws ExcepcionClaveNoExiste;
    V buscar (K claveABuscar);
    boolean contiene(K claveABuscar);
    int size();
    int altura();
    int nivel();
    void vaciar();
    boolean esArbolVacio();
    List< K> recorridoPorNiveles();
    List <K> recorridoEnPreOrden();
    List<K> recorridoEnInOrdenIter();
    List <K> recorridoEnPostOrden();
   
    
}
