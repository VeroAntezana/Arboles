/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.grafos.nopesados;

import ed2.uagrm.arboled2.excepciones.ExcepcionAristaNoExiste;
import ed2.uagrm.arboled2.excepciones.ExcepcionAristaYaExiste;
import ed2.uagrm.arboled2.excepciones.ExcepcionNroVerticesInvalidos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Veronica
 */
public class Grafos {
    private UtilsRecorrido controlMarcados; //para utilizar el utilitario de marcados
    private DFS grafo; // LLamar a los metodos del recorrido
    protected List<List<Integer>> ListaDeAdyacencias;

    public Grafos() {
        this.ListaDeAdyacencias = new ArrayList<>();
    }

    public Grafos(int nroInicialDelVertice) throws ExcepcionNroVerticesInvalidos {
        if (nroInicialDelVertice <= 0) {
            throw new ExcepcionNroVerticesInvalidos();
        }
        this.ListaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroInicialDelVertice; i++) {
            this.insertarVertice();

        }
    }

    public void insertarVertice() {
        List<Integer> adyacentesDeNuevoVertice = new ArrayList<>();
        this.ListaDeAdyacencias.add(adyacentesDeNuevoVertice);
    }

    public int cantidadDeVertice() {
        return ListaDeAdyacencias.size();
    }

    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.ListaDeAdyacencias.get(posDeVertice);
        return adyacentesDelVertice.size();
    }

    public void validarVertice(int posDeVertice) {
        if ((posDeVertice < 0 )||( posDeVertice >= this.cantidadDeVertice()))
        {
            throw new IllegalArgumentException(" No existe vertice en la "
                    + "posicion " + posDeVertice + "en este grafo");
        }

    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.ListaDeAdyacencias.get(posVerticeDestino);
            adyacentesDelDestino.add(posVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDelOrigen.contains(posVerticeDestino);
    }

    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.ListaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> iterableAdyacentes = adyacentesDelVertice;
        return iterableAdyacentes;
    }

    public Iterable<Integer> adyacenteDeVerticeAmbosSentidos(int posVertice){
        validarVertice(posVertice);
        List<Integer> adyacentesDelVertice = this.ListaDeAdyacencias.get(posVertice);
        for(int i=1; i<this.ListaDeAdyacencias.size();i++){         //i=0 Preguntar
        if(i!= posVertice){
            List<Integer> aux = this.ListaDeAdyacencias.get(i);
            for(Integer a : aux){
                if(a== posVertice){
                    adyacentesDelVertice.add(i);
                }
            }
        }
        }                                                  
        Iterable<Integer> iterableAdyacentes = adyacentesDelVertice;
        return iterableAdyacentes;
    
    }
    public int cantidadDeArista() {
        int cantArist = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDeUnVertice = this.ListaDeAdyacencias.get(i);
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (i == posDeAdyacente) {
                    cantLazos++;
                } else {
                    cantArist++;
                }
            }// fin foreach
        }// fin for
        return cantLazos + (cantArist / 2);
    }

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.ListaDeAdyacencias.get(posVerticeDestino);
            int posicionDelOrigen = adyacentesDelOrigen.indexOf(posVerticeOrigen);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }

    public void eliminarVertice(int posVerticeAEliminar) {
        validarVertice(posVerticeAEliminar);
          this.ListaDeAdyacencias.remove(posVerticeAEliminar);
          for (List<Integer> adyacentesDeVertice : this.ListaDeAdyacencias){
              int posicionDeVerticeEnAdy= adyacentesDeVertice.indexOf(posVerticeAEliminar);
              if( posicionDeVerticeEnAdy >=0){
                  adyacentesDeVertice.remove(posicionDeVerticeEnAdy);
              }
              for (int i =0; i < adyacentesDeVertice.size(); i++){
                  int posicionDeAdyacente = adyacentesDeVertice.get(i);
                  if(posicionDeAdyacente > posVerticeAEliminar){
                      adyacentesDeVertice.set(i, posicionDeAdyacente - 1);
                  }
              }  
          }
          
          
    }
    
    
    public String toString(){
          if (this.ListaDeAdyacencias.size() == 0) {
           return "(Grafo vacio)";
        }
          String s="";
          return s;
    }
    
    
    // cantidad de islas.
    /*   public int cantIslas( int posVerticeEnTurno){
     DFS vertice ;
        controlMarcados.desmarcarTodos();
        int islas=0;
        
       for (int i=0 ; i <this.ListaDeAdyacencias.size();i++){
           vertice = new DFS(this.ListaDeAdyacencias, i);
           if(grafo.hayCaminosATodos()){
               islas++;
           }
           procesarDFS(posVerticeEnTurno);
       }
 
    }
   */
}
