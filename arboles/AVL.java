/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.arboled2.arboles;

import ed2.uagrm.arboled2.excepciones.ExcepcionClaveNoExiste;
import ed2.uagrm.arboled2.excepciones.ExcepcionNoSeInsertaValoresNulos;
import java.util.List;

/**
 *
 * @author Veronica
 */
public class AVL< K extends Comparable<K>, V> extends ArbolBinarioBusqueda {
    
    private static final byte DIFERENCIA_PERMITIDA = 1;

 
 
    @Override
    public boolean vaciosAntesDeUnNivelRec(NodoBinario nodoActual, int nivelObjetivo, int nivelActual) {
        return super.vaciosAntesDeUnNivelRec(nodoActual, nivelObjetivo, nivelActual); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean vacioAntesDeUnNivel(int nivel) {
        return super.vacioAntesDeUnNivel(nivel); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadDeHijosVaciosRec() {
        return super.cantidadDeHijosVaciosRec(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadDeHijosVacios() {
        return super.cantidadDeHijosVacios(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List recorridoEnPostOrden() {
        return super.recorridoEnPostOrden(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List recorridoEnInOrdenIter() {
        return super.recorridoEnInOrdenIter(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List recorridoEnPreOrdenRec() {
        return super.recorridoEnPreOrdenRec(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List recorridoEnPreOrden() {
        return super.recorridoEnPreOrden(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List recorridoPorNiveles() {
        return super.recorridoPorNiveles(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean esArbolVacio() {
        return super.esArbolVacio(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void vaciar() {
        super.vaciar(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int nivel() {
        return super.nivel(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int alturaIt() {
        return super.alturaIt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int altura() {
        return super.altura(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int sizeRec() {
        return super.sizeRec(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        return super.size(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contiene(Comparable claveABuscar) {
        return super.contiene(claveABuscar); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object buscar(Comparable claveABuscar) {
        return super.buscar(claveABuscar); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected NodoBinario buscarSucesor(NodoBinario nodoActual) {
        return super.buscarSucesor(nodoActual); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object eliminar(Comparable claveAEliminar) throws ExcepcionClaveNoExiste {
        return super.eliminar(claveAEliminar); //To change body of generated methods, choose Tools | Templates.
    }
   
    
       public void insertar1(K claveAInsertar, V valorAInsertar)throws ExcepcionNoSeInsertaValoresNulos {
        if (valorAInsertar == null) {
            throw new ExcepcionNoSeInsertaValoresNulos();
        }
        this.raiz = insertar(this.raiz, claveAInsertar, valorAInsertar);

    }

    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        if (claveAInsertar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> nuevoSupuestoHijo = insertar(nodoActual.getHijoIzquierdo(), claveAInsertar, valorAInsertar);
            nodoActual.setHijoIzquierdo(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }

        if (claveAInsertar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> nuevoSupuestoHijo = insertar(nodoActual.getHijoDerecho(), claveAInsertar, valorAInsertar);
            nodoActual.setHijoDerecho(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        // si llego hasta aca, quiere decir que encontre en el arbol la clave que queria insertar
        // insertar actualizo el valor
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }
    
    private NodoBinario<K,V> balancear(NodoBinario<K, V> nodoActual){
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura (nodoActual.getHijoDerecho());
        int diferenciaDeAltura= alturaPorIzquierda - alturaPorDerecha;
        if(diferenciaDeAltura> DIFERENCIA_PERMITIDA){
            //rotacion a derecha
             NodoBinario<K,V> hijoIzquierdoDelActual= nodoActual.getHijoIzquierdo();
             alturaPorIzquierda = altura(hijoIzquierdoDelActual.getHijoIzquierdo());
             alturaPorDerecha = altura (hijoIzquierdoDelActual.getHijoDerecho());
             if(alturaPorDerecha > alturaPorIzquierda){
                return rotacionDobleADerecha(nodoActual);
             }
                return rotacionSimpleADerecha(nodoActual);
                
        } else if(diferenciaDeAltura< - DIFERENCIA_PERMITIDA){
            //Rotacion a izquierda 
             NodoBinario<K,V> hijoDerechoDelActual= nodoActual.getHijoDerecho();
             alturaPorIzquierda = altura(hijoDerechoDelActual.getHijoIzquierdo());
             alturaPorDerecha = altura (hijoDerechoDelActual.getHijoDerecho());
             if(alturaPorIzquierda>alturaPorDerecha){
             return rotacionDobleAIzquierda(nodoActual);
             }
             return rotacionSimpleAIzquierda(nodoActual);
        }
        //quiere decir que no hay que hacer rotaciones
        return nodoActual;
    }
// HACER EL ELIMINAR 
   /* public void insertar(Comparable claveAInsertar, Object valorAInsertar) {
        super.insertar(claveAInsertar, valorAInsertar); //To change body of generated methods, choose Tools | Templates.
    }

    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        int diferenciaDeAltura=alturaPorIzquierda - alturaPorDerecha;
        if(diferenciaDeAltura>DIFERENCIA_PERMITIDA){
            // Rotacion a derecha
            NodoBinario<K, V> hijoIzquierdoDelActual= nodoActual.getHijoIzquierdo();
            alturaPorIzquierda=altura(hijoIzquierdoDelActual.getHijoIzquierdo());
            alturaPorDerecha=altura(hijoIzquierdoDelActual.getHijoDerecho());
            if(alturaPorDerecha>alturaPorIzquierda){
                return rotacionDobleADerecha(nodoActual);
            }else
                return rotacionSimpleADerecha(nodoActual);
            
        }else if(diferenciaDeAltura< -DIFERENCIA_PERMITIDA){
            
                //Rotacion a izquierda
            }
        // si viene por aca quiere decir que no hay que hacer rotaciones.
        return nodoActual;
    }*/
    
    private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K,V>nodoActual){
        NodoBinario<K,V>nodoQueRota= nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    } 
    
    private NodoBinario<K,V>rotacionDobleADerecha(NodoBinario<K,V>nodoActual){
        NodoBinario<K,V>nodoPrimerRotacion=rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoPrimerRotacion);
        return rotacionSimpleADerecha(nodoActual);
                }
    private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K,V>nodoActual){
           NodoBinario<K,V>nodoQueRota= nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }
    private NodoBinario<K,V>rotacionDobleAIzquierda(NodoBinario<K,V>nodoActual){
             NodoBinario<K,V>nodoPrimerRotacion=rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoPrimerRotacion);
        return rotacionSimpleAIzquierda(nodoActual);
    }
}
