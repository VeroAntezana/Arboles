/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.arboled2.arboles;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Veronica
 */
public class NodoMVias<K, V> {

    private List<K> ListaDeClaves;
    private List<V> ListaDeValores;
    private List<NodoMVias<K, V>> ListaDeHijos;

    public NodoMVias(int orden) {
        ListaDeClaves = new LinkedList<>();
        ListaDeValores = new LinkedList<>();
        ListaDeHijos = new LinkedList<>();
        for (int i = 0; i < orden - 1; i++) {
            ListaDeClaves.add((K) NodoMVias.datoVacio());
            ListaDeValores.add((V) NodoMVias.datoVacio());
            ListaDeHijos.add(NodoMVias.nodoVacio());
        }
        ListaDeHijos.add(NodoMVias.nodoVacio());
    }

    public NodoMVias(int orden, K primerClave, V primerValor) {
        this(orden);
        this.ListaDeClaves.set(0, primerClave);
        this.ListaDeValores.set(0, primerValor);

    }

    public static NodoMVias nodoVacio() {
        return null;
    }

    public static Object datoVacio() {
        return null;
    }

    public K getClave(int posicion) {
        return this.ListaDeClaves.get(posicion);
    }

    public void setClave(int posicion, K clave) {
        this.ListaDeClaves.set(posicion, clave);
    }

    public V getValor(int posicion) {
        return this.ListaDeValores.get(posicion);
    }

    public void setValor(int posicion, V valor) {
        this.ListaDeValores.set(posicion, valor);
    }

    public NodoMVias<K, V> getHijo(int posicion) {
        return this.ListaDeHijos.get(posicion);
    }

    public void setHijo(int posicion, NodoMVias<K, V> nodoHijo) {
        this.ListaDeHijos.set(posicion, nodoHijo);
    }

    public static boolean esNodoVacio(NodoMVias nodo) {
        return NodoMVias.nodoVacio() == nodo;
    }

    public boolean esClaveVacia(int posicion) {
        return this.ListaDeClaves.get(posicion) == NodoMVias.datoVacio();
    }

    public boolean esHijoVacio(int posicion) {
        return NodoMVias.esNodoVacio(this.ListaDeHijos.get(posicion));
    }

    public boolean esHoja() {
        for (int i = 0; i < this.ListaDeHijos.size(); i++) {
            if (!this.esHijoVacio(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean estanClavesLlenas() {
        for (int i = 0; i < this.ListaDeClaves.size(); i++) {
            if (this.esClaveVacia(i)) {
                return false;
            }
        }
        return true;
    }
    
    public int cantidadDeClavesNoVacias(){
        int cantidad=0;
          for (int i = 0; i < this.ListaDeClaves.size(); i++) {
            if (!this.esClaveVacia(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }
    
      public int cantidadDeHijosVacios(){
        int cantidad=0;
          for (int i = 0; i < this.ListaDeHijos.size(); i++) {
            if (this.esHijoVacio(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }
          public int cantidadDeHijosNoVacios(){
        int cantidad=0;
          for (int i = 0; i < this.ListaDeHijos.size(); i++) {
            if (!this.esHijoVacio(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }

}
