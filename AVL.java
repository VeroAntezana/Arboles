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
public class AVL< K extends Comparable<K>, V> extends ArbolBinarioBusqueda <K,V> {
    
    private static final byte DIFERENCIA_PERMITIDA = 1;

   @Override
   
       public void insertar (K claveAInsertar, V valorAInsertar)throws ExcepcionClaveNoExiste{
        if (valorAInsertar == null) {
            throw new ExcepcionClaveNoExiste();
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
   public V eliminar(K claveAEliminar)  throws ExcepcionClaveNoExiste {
      V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
// return super.eliminar(claveAEliminar); //To change body of generated methods, choose Tools | Templates.
    }
    
    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> nuevoSupuestoHijo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }

        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> nuevoSupuestoHijo = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        // si llego hasta aca, quiere decir que encontre en el arbol la clave que queria insertar
        // insertar actualizo el valor
        // caso 1
        if(nodoActual.esHoja()){
           return null;
        }
        //caso 2
        if(nodoActual.esVacioHijoIzquierdo()&& !nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoDerecho();
        }
         if(!nodoActual.esVacioHijoIzquierdo()&& nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoIzquierdo();
        }
        // caso 3
         NodoBinario<K, V> nodoDelSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
         
       
    }
    
    /*private NodoBinario<K,V> balancear(NodoBinario<K, V> nodoActual){
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
   
     */
   
    
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
    
   
}
