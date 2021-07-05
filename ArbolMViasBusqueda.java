/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.arboled2.arboles;

import ed2.uagrm.arboled2.excepciones.ExcepcionClaveNoExiste;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Veronica
 * @param <K>
 * @param <V>
 */

public class ArbolMViasBusqueda< K extends Comparable<K>, V>
        implements IArbolBusqueda<K, V> {

    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected int POSICION_INVALIDA = -1;

    public ArbolMViasBusqueda() {
        this.orden = 3;
    }

    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido {
        if (orden < 3) {
            throw new ExcepcionOrdenInvalido();
        }
        this.orden = orden;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        // aqui tambien tengo que poner la excepcion de valores nulos y claves
        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
            return;
        }
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionDeClave = this.obtenerPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClave != POSICION_INVALIDA) {
                nodoActual.setValor(posicionDeClave, valorAInsertar);
                nodoActual = NodoMVias.nodoVacio();

            } else {
                if (nodoActual.esHoja()) {
                    if (nodoActual.estanClavesLlenas()) {
                        int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                        NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                    } else {
                        this.insertarClaveOrdenadaEnNodo(nodoActual, claveAInsertar, valorAInsertar);

                    }
                    nodoActual = NodoMVias.nodoVacio();

                }else {
                     int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                     if(nodoActual.esHijoVacio(posicionPorDondeBajar)){
                          NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                        nodoActual = NodoMVias.nodoVacio();
                     }else{
                         nodoActual= nodoActual.getHijo(posicionPorDondeBajar);
                     }
                }
            }
        }

    }
     private void insertarClaveOrdenadaEnNodo(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        int i = orden-1;
        while(i>0){
            if(!nodoActual.esClaveVacia(orden-1)){
                if(claveAInsertar.compareTo(nodoActual.getClave(i)) > 0){
                    nodoActual.setClave(i, claveAInsertar);
                }else{
                    nodoActual.setClave(i, nodoActual.getClave(i-1));
                }
            
            }
         i--;   
        }
        nodoActual.setClave(0, claveAInsertar);
    }

    private int obtenerPosicionDeClave(NodoMVias<K, V> nodoActual, K claveAInsertar) {
         for(int i =0; i< nodoActual.cantidadDeClavesNoVacias(); i++){
            K claveActual = nodoActual.getClave(i);
            if(claveActual.compareTo(claveAInsertar)==0){
                return i;
            }
        }
        return POSICION_INVALIDA;
    }

    private int obtenerPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveAInsertar) {
     
        return POSICION_INVALIDA;
        
       
    }

    @Override
    public V eliminar(K claveEliminar) throws ExcepcionClaveNoExiste {
         V valorAEliminar = this.buscar(claveEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        this.raiz = eliminar(this.raiz, claveEliminar);
        return valorAEliminar;
    }
    private NodoMVias<K,V> eliminar (NodoMVias<K,V>nodoActual, K claveAEliminar){
        for(int i=0; i< nodoActual.cantidadDeClavesNoVacias();i++){
            K claveActual= nodoActual.getClave(i);
            if(claveAEliminar.compareTo(claveActual)==0){
                if(nodoActual.esHoja()){
                    this.eliminarClaveYValorDelNodo(nodoActual,i);
                    if(nodoActual.cantidadDeClavesNoVacias()==0){
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                //No es hoja
                K claveDeReemplazo;
                if(this.hayHijosMasAdelante(nodoActual,i)){
                    claveDeReemplazo = this.buscarClaveSucesoraInOrden(nodoActual, claveAEliminar);
                    
                }else{
                claveDeReemplazo =  this.buscarClavePredecesoraInOrden(nodoActual, claveAEliminar);
                }
                V valorDeReemplazo = buscar(claveDeReemplazo);
                nodoActual = eliminar(nodoActual,claveDeReemplazo);
                nodoActual.setClave(i, claveDeReemplazo);
                nodoActual.setValor(i, valorDeReemplazo);
                return nodoActual;
            }
            // No esta en la posicion i del nodo Actual
            if(claveAEliminar.compareTo(claveActual)<0){
                NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(i),claveAEliminar);
                nodoActual.setHijo(i,supuestoNuevoHijo );
                return nodoActual;
            }
        }
        // quiere decir que nunca baje por ningun lado ni lo encontre
                NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),claveAEliminar);
                nodoActual.setHijo(nodoActual.cantidadDeClavesNoVacias(),supuestoNuevoHijo );
                return nodoActual;
    }
      
    private void eliminarClaveYValorDelNodo(NodoMVias<K, V> nodoActual, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V buscar(K claveABuscar) {
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean huboCambioDeNodoActual = false;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias() && !huboCambioDeNodoActual; i++) {
                K claveActual = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveActual) == 0) {
                    return nodoActual.getValor(i);
                }
                if (claveABuscar.compareTo(claveActual) < 0) {
                    nodoActual = nodoActual.getHijo(i);
                    huboCambioDeNodoActual = true;
                }
            }// fin del for

            if (!huboCambioDeNodoActual) {            // sin esto seria un bucle infinito
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
            }
        }
        return (V) NodoMVias.datoVacio();   // return NULLO
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != NodoMVias.datoVacio();

    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoMVias<K, V> nodoActual) {
        if (nodoActual.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaMay = 0;
        for (int i = 0; i < orden; i++) {
            int alturaActual = altura(nodoActual.getHijo(i));
            if (alturaActual > alturaMay) {
                alturaMay = alturaActual;
            }
        }
        return alturaMay + 1;
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();

            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                recorrido.add(nodoActual.getClave(i));
                if (!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }

            }

            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        // si n=0
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnInOrdenIter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;

    }

    private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorridoEnPostOrden(nodoActual.getHijo(i + 1), recorrido);
            recorrido.add(nodoActual.getClave(i));

        }

    }
@Override
public V minimo(){
    if(this.esArbolVacio()){
        return null;
    }
    NodoMVias<K,V> nodoActual = this.raiz;
    NodoMVias<K,V> nodoAnterior = (NodoMVias<K,V>) NodoMVias.nodoVacio();
        while(nodoActual!=null){            
        nodoAnterior= nodoActual;
        nodoActual = nodoActual.getHijo(0);
    
    }
    return nodoAnterior.getValor(0);
}

    private boolean hayHijosMasAdelante(NodoMVias<K, V> nodoActual, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private K buscarClaveSucesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private K buscarClavePredecesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
public boolean esSimilar(ArbolMViasBusqueda arbol) {                //Ejercicio 20
        return esSimilar1(raiz, arbol.raiz);
    }
    
    public boolean esSimilar1(NodoMVias<K, V> arbol1, NodoMVias<K, V> arbol2) {
        if(NodoMVias.esNodoVacio(arbol1) && NodoMVias.esNodoVacio(arbol2))
            return true;
        if(NodoMVias.esNodoVacio(arbol1) || NodoMVias.esNodoVacio(arbol2))
            return false;
        
        if(arbol1.esHoja() && arbol2.esHoja()) {
            if(arbol1.cantidadDeClavesNoVacias() == arbol2.cantidadDeClavesNoVacias())
                return true;
            return false;
        }
        if(arbol1.esHoja() || arbol2.esHoja())
            return false;        
        
        if(arbol1.cantidadDeHijosNoVacios() != arbol2.cantidadDeHijosNoVacios()) {
            return false;
        }
                
        for(int i = 0; i < orden-1; i++) {
            if (arbol1.getClave(i).compareTo(arbol2.getClave(i)) != 0)
                return false;
        }
                
        for(int i = 0; i < orden; i++) {
            if (esSimilar1(arbol1.getHijo(i), arbol2.getHijo(i)) == false)
                return false;
        }
        return true;
    }

 

}
