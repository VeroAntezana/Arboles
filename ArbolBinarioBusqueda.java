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
import java.util.Stack;

/**
 *
 * @author Veronica
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda< K extends Comparable<K>, V>
        implements IArbolBusqueda<K, V> {

    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {

    }

    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<V> valoresInOrden, List<K> clavesNoInOrden, List<V> valoresNoInOrden, boolean esConPreOrden) {

    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) throws ExcepcionClaveNoExiste {
        if (valorAInsertar == null) {
            throw new ExcepcionClaveNoExiste();

        }
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;

        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveAInsertar.compareTo(claveActual) < 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        // si llego a este punto quiere decir que encontre donde insertar 
        // la clave y el valor 
        NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        K claveAnterior = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(claveAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
    }

    @Override
    public V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste {
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzq = eliminar(nodoActual.getHijoIzquierdo(),
                    claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDer = eliminar(nodoActual.getHijoIzquierdo(),
                    claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDer);
            return nodoActual;
        }
        //Si llego aca es que ya encontre el nodo con la clave a eliminar
        //caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        //caso 2
        //caso 2.1
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //caso2.2
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        //caso 3
        NodoBinario<K, V> nodoDelSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }

    protected NodoBinario<K, V> buscarSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }

    @Override
    public V minimo() {
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = (NodoBinario<K, V>) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();

        }
        return nodoAnterior.getValor();
    }

    @Override
    public V buscar(K claveABuscar) {
        //  if(this.esArbolVacio()){
        //    return null;
        //}
        NodoBinario<K, V> nodoActual = this.raiz;

        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveABuscar.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual.getValor();
            }
        }
        // si llego a este punto la clave a buscar no esta en el Arbol
        //entonces  retornar null
        return null;
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != null;
    }

    @Override
    public int size() {

        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidadDeNodos = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            cantidadDeNodos++;
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }

            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cantidadDeNodos;
    }

    public int sizeRec() {
        return sizeRec(this.raiz);
    }

    private int sizeRec(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadPorIzquierda = sizeRec(nodoActual.getHijoIzquierdo());
        int cantidadPorDerecha = sizeRec(nodoActual.getHijoDerecho());
        return cantidadPorIzquierda + cantidadPorDerecha + 1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());

        return alturaPorIzquierda > alturaPorDerecha ? alturaPorIzquierda + 1
                : alturaPorDerecha + 1;
    }

    public int alturaIt() {

        if (this.esArbolVacio()) {
            return 0;
        }
        int altura = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            altura++;
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }

            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return altura;
    }

    @Override
    public int nivel() {

        return nivel(this.raiz);
    }

    private int nivel(NodoBinario<K, V> nodoActual) {
        if (this.esArbolVacio()) {
            return 0;
        }
        int i = 0;
        int j = 0;
        if (!nodoActual.esVacioHijoIzquierdo()) {
            i = nivel(nodoActual.getHijoIzquierdo()) + 1;
        }
        if (!nodoActual.esVacioHijoDerecho()) {
            j = nivel(nodoActual.getHijoDerecho()) + 1;
        }
        if (i > j) {
            return i;
        }
        return j;
        /* if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int nivelPorIzquierda = nivel(nodoActual.getHijoIzquierdo()) + 1;
        int nivelPorDerecha = nivel(nodoActual.getHijoDerecho()) + 1;

        return nivelPorIzquierda > nivelPorDerecha ? nivelPorIzquierda
                : nivelPorDerecha;
         */
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }

            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return recorrido;
    }

    public List<K> recorridoEnPreOrdenRec() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPreOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrdenRec(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        // si n=0
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorrido.add(nodoActual.getClave());
        recorridoEnPreOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPreOrdenRec(nodoActual.getHijoDerecho(), recorrido);
    }

    @Override
    public List<K> recorridoEnInOrdenIter() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        rellenarPila(this.raiz, pilaDeNodos);       //RELLENA EN LA PILA POR TODO EL LADO IZQUIERDO
        while (!pilaDeNodos.empty()) {
            NodoBinario<K, V> nodo = pilaDeNodos.pop();
            recorrido.add(nodo.getClave());
            if (!nodo.esVacioHijoDerecho()) {
                rellenarPila(nodo.getHijoDerecho(), pilaDeNodos);
            }
        }

        return recorrido;
    }

    private void rellenarPila(NodoBinario<K, V> nodoAProcesar, Stack<NodoBinario<K, V>> pilaAProcesar) {
        while (!NodoBinario.esNodoVacio(nodoAProcesar)) {
            pilaAProcesar.push(nodoAProcesar);
            nodoAProcesar = nodoAProcesar.getHijoIzquierdo();
        }
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        insertarEnPilaParaPostOrden(nodoActual, pilaDeNodos);
        /*while(!NodoBinario.esNodoVacio(nodoActual)){
             pilaDeNodos.push(nodoActual);
             if(!nodoActual.esVacioHijoIzquierdo()){
             nodoActual=nodoActual.getHijoIzquierdo();
             }else{
             nodoActual=nodoActual.getHijoDerecho();
             }
             
         }*/
        //iniciando a sacar nodos de la Pila 
        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho()
                        && nodoDelTope.getHijoDerecho() != nodoActual) {
                    insertarEnPilaParaPostOrden(nodoDelTope.getHijoDerecho(),
                            pilaDeNodos);
                }
            }
        }
        return recorrido;
    }

    private void insertarEnPilaParaPostOrden(NodoBinario<K, V> nodoActual, Stack<NodoBinario<K, V>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }

    public int sizeEnPostOrden() {
        int cant = 0;
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return cant;

        }
        Stack<NodoBinario<K, V>> pila = new Stack<>();
        NodoBinario<K, V> nodo = this.raiz;
        insertarEnPila(nodo, pila);

        while (!pila.isEmpty()) {
            nodo = pila.pop();
            recorrido.add(nodo.getClave());
            cant++;
            if (!pila.isEmpty()) {
                NodoBinario<K, V> tope = pila.peek();
                if (!tope.esVacioHijoDerecho() && tope.getHijoDerecho() != nodo) {
                    insertarEnPila(tope.getHijoDerecho(), pila);
                }

            }
        }
        return cant;
    }

    private void insertarEnPila(NodoBinario<K, V> nodoAProcesar, Stack<NodoBinario<K, V>> pilaAProcesar) {

        while (!NodoBinario.esNodoVacio(nodoAProcesar)) {
            pilaAProcesar.push(nodoAProcesar);
            if (!nodoAProcesar.esVacioHijoIzquierdo()) {
                nodoAProcesar = nodoAProcesar.getHijoIzquierdo();

            } else {
                nodoAProcesar = nodoAProcesar.getHijoDerecho();

            }
        }
    }

    @Override
    public String toString() {
        return generarCadenaDeArbol(raiz, "", true);
    }

    private String generarCadenaDeArbol(NodoBinario<K, V> nodoActual, String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "└──(R)" : "├──(R)");
        } else {
            cadena.append(ponerCodo ? "└──(D)" : "├──(I)");
        }

        if (NodoBinario.esNodoVacio(nodoActual)) {
            cadena.append("╣\n");
            return cadena.toString();
        }

        cadena.append(nodoActual.getClave().toString());
        cadena.append("\n");

        NodoBinario<K, V> nodoIzquierdo = nodoActual.getHijoIzquierdo();
        String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");
        cadena.append(generarCadenaDeArbol(nodoIzquierdo, prefijoAux, false));

        NodoBinario<K, V> nodoDerecho = nodoActual.getHijoDerecho();
        cadena.append(generarCadenaDeArbol(nodoDerecho, prefijoAux, true));

        return cadena.toString();
    }

    public int cantidadDeHijosVacios() {

        if (NodoBinario.esNodoVacio(this.raiz)) {
            return 0;
        }
        int cantidad = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if (nodoActual.esHoja()) {
                cantidad = cantidad + 2;
            } else if ((nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo())
                    || (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho())) {
                cantidad = cantidad + 1;
            }
            /*if(nodoActual.esVacioHijoDerecho()){
                //  cantidad++;
              }
              if(nodoActual.esVacioHijoIzquierdo()){
                  cantidad++;
              }*/

            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cantidad;
    }

    public int cantidadDeHijosVaciosRec() {
        return cantidadDeHijosVaciosRec(this.raiz);

    }

    private int cantidadDeHijosVaciosRec(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int a = cantidadDeHijosVaciosRec(nodoActual.getHijoIzquierdo());
        int b = cantidadDeHijosVaciosRec(nodoActual.getHijoDerecho());
        if (nodoActual.esHoja()) {
            return a + b + 2;
        }
        int x = 0;
        if (nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
            x++;
        }
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            x++;
        }

        return a + b + x;
    }

    public boolean vacioAntesDeUnNivel(int nivel) {

        boolean b = true;
        if (this.esArbolVacio()) {
            return false;
        }
        int n = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty() && n < nivel) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();

            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }

            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return b;
    }

    public boolean vaciosAntesDeUnNivelRec(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return false;
        }
        if (nivelActual >= nivelObjetivo) {
            return false;
        }
        if (nodoActual.esVacioHijoIzquierdo() || nodoActual.esVacioHijoDerecho()) {
            return true;
        }
        boolean hayVaciosPorIzquierda = vaciosAntesDeUnNivelRec(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        boolean hayVaciosPorDerecha = vaciosAntesDeUnNivelRec(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);

        return hayVaciosPorIzquierda || hayVaciosPorDerecha;
    }

    public int cantidadDeHojas() {
        return cantidadDeHojas(this.raiz, 0);
    }

    private int cantidadDeHojas(NodoBinario<K, V> nodo, int cantidad) {
        int c = cantidad;
        if (NodoBinario.esNodoVacio(nodo)) {
            return 0;
        }
        if (nodo.esHoja()) {
            return c = c + 1;
        }
        if (!nodo.esVacioHijoIzquierdo()) {
            c = cantidadDeHojas(nodo.getHijoIzquierdo(), c);
        }
        if (!nodo.esVacioHijoDerecho()) {
            c = cantidadDeHojas(nodo.getHijoDerecho(), c);
        }
        return c;
    }

    public int cantidadDeHijosNoVacios() {         //Ejercicio Numero 3
        int cant = 0;
        if (this.esArbolVacio()) {
            return cant;
        }

        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
                cant++;
            }
        }
        return cant;
    }

    public int cantidadDeHijosNoVaciosRec() {
        return cantidadDeHijosNoVaciosRec(this.raiz);

    }

    private int cantidadDeHijosNoVaciosRec(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int a = cantidadDeHijosNoVaciosRec(nodoActual.getHijoIzquierdo());
        int b = cantidadDeHijosNoVaciosRec(nodoActual.getHijoDerecho());

        int x = 0;
        if (!nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
            x++;
        }

        return a + b + x;
    }

    public int distintoDeVacioEnUnNivel(int nivel) {

        int cant = 0;
        if (this.esArbolVacio()) {
            return cant;
        }
        int n = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();

            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }

            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
            if (n == nivel) {
                if (!nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
                    cant++;
                }
            }
          n=n+1;
        }
        return cant;

    }
    
    private NodoBinario<K,V> predecesorInOrden(NodoBinario<K,V> nodoActual){        //Ejercicio N°15
    NodoBinario<K,V> aux= nodoActual.getHijoIzquierdo();
    while(aux.getHijoDerecho()!=null){
    aux= aux.getHijoDerecho();
    }
    return aux;
    }
    
}
