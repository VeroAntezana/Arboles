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
 */
public class ArbolMViasBusqueda< K extends Comparable<K>, V>
        implements IArbolBusqueda<K, V> {

    protected NodoMVias<K, V> raiz;
    protected int orden;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V eliminar(K claveEliminar) throws ExcepcionClaveNoExiste {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V buscar(K claveABuscar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contiene(K claveABuscar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int altura() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
