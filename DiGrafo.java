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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Veronica
 */
public class DiGrafo extends Grafos {
    private DFS dfs;
    private DFSContraSentido dfsCS;
    private UtilsRecorrido controlMarcados;
    public DiGrafo(){
        
        super();
    }
    
    public DiGrafo(int nroInicialDelVertice) throws ExcepcionNroVerticesInvalidos {
        super(nroInicialDelVertice);
    }

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
             if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino); 
    }

    @Override
    public int cantidadDeArista() {
        //return super.cantidadDeArista(); //To change body of generated methods, choose Tools | Templates.
        //Sumar las listas de adyacencias
        int cantArista=0;
       for( List<Integer> adyacentesDeUnVertice : super.ListaDeAdyacencias){
           for (Integer posDeAdyacente : adyacentesDeUnVertice){
               cantArista++;
           }
       }
        return cantArista;
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
          if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("Metodo no soportado en grafos dirigidos");
    }
    
    public int gradoDeEntradaDeVertice(int posDeVertice){
        super.validarVertice(posDeVertice);
        int entradaDeVertice = 0 ;
        for(List<Integer> adyacentesDeUnVertice : super.ListaDeAdyacencias){
            for (Integer posDeAdyacente : adyacentesDeUnVertice){
                if(posDeAdyacente == posDeVertice){
                    entradaDeVertice++;
                }
            }
        }
        return entradaDeVertice;
    }
    
    public int gradoDeSalidaDeVertice(int posDeVertice){
        return super.gradoDeVertice(posDeVertice);
        
    }
    //Ejercicio 6
    public boolean existeCiclo(){
        controlMarcados.desmarcarTodos();
        while(!controlMarcados.estanTodosMarcados()){
            int vertice = obtenerPrimerVerticeNoMarcado();
            Queue <Integer> cola = new LinkedList<>();
            cola.offer(vertice);
            List<Integer> caminos = new ArrayList<>();
            while(!cola.isEmpty()){
                vertice = cola.poll();
                if(caminos.contains(vertice)){
                    return true;
                }
            caminos.add(vertice);
            controlMarcados.marcarVertice(vertice);
            for (int i= 0; i < super.ListaDeAdyacencias.get(vertice).size(); i++){
                cola.add(super.ListaDeAdyacencias.get(vertice).get(i)); 
            }
            }
        }
        return false;
    }

    private int obtenerPrimerVerticeNoMarcado() {
       for(int i = 0 ; i < super.ListaDeAdyacencias.size(); i++){
           if(!controlMarcados.estaVerticeMarcado(i)){
               return i;
           }
       }
       return super.ListaDeAdyacencias.size();
    }
    
    //ejercicio 3
    // Retorne componentes de islas que existen en un digrafo
    
    public List<Integer> componentesIsla(){
        
          controlMarcados.desmarcarTodos();
        int islas=0;
        while(controlMarcados.estanTodosMarcados()==false){
         for (int i=0 ; i <super.ListaDeAdyacencias.size();i++){
          
          List<Integer> adyacentesDelVertice = super.ListaDeAdyacencias.get(i); 
            dfsCS.procesarDFSContraSentido(i);
            adyacentesDelVertice.add(i);
           islas++;
            if(dfs.hayCaminosATodos()){
            
               return adyacentesDelVertice;
           }
           i= buscarVerticeSinMarcar()-1;
       }
        }
   
    
        
        
    }
        
        
            
       
      /*  List<Integer> adyacentesDelVertice = this.ListaDeAdyacencias.get(posVertice);
        for(int i=1; i<this.ListaDeAdyacencias.size();i++){         //i=0 Preguntar
        if(i!= posVertice){
            List<Integer> aux = this.ListaDeAdyacencias.get(i);
            for(Integer a : aux){
                if(a== posVertice){
                    adyacentesDelVertice.add(i);
                }
            }
        }
        }                                                  Iterable<Integer> iterableAdyacentes = adyacentesDelVertice;
        return iterableAdyacentes;
       */ 
    

       private int buscarVerticeSinMarcar() {
       for(int c=0; c < super.ListaDeAdyacencias.size();c++){
           if(!controlMarcados.estaVerticeMarcado(c)){
               return c;
           }
       }
       return super.ListaDeAdyacencias.size();
    }  
        
    
    //Ejercicio 7 
    public boolean debilmenteConexo(){
       controlMarcados.desmarcarTodos();
       dfs.procesarDFS(0);
       if(!dfs.hayCaminosATodos()){
           return false;
       }
        return true;
    }
    //Cantidad De Islas
    
    public int cantIslas(){
        int cant = 0;
        while(controlMarcados.estanTodosMarcados()==false){
            int i = this.obtenerPrimerVerticeNoMarcado();
            dfsCS.procesarDFSContraSentido(i);
            cant++;
        }
        return cant;
    }
}
