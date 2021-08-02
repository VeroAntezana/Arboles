/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Veronica
 */
public class DFS {
    private UtilsRecorrido controlMarcados;
    private Grafos grafo;
    private List<Integer> recorrido;
    
    public DFS(Grafos unGrafo, int posVerticePartida){
         this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorrido(this.grafo.cantidadDeVertice()); // ya esta todo desmarcado
        procesarDFS(posVerticePartida);
        
    }
    
    public void procesarDFS(int posVertice){
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
         Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVertice);
            for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno){
                if(!controlMarcados.estaVerticeMarcado(posVerticeAdyacente )){
                    procesarDFS(posVerticeAdyacente);
                    //podria hacerce con pila 
                }
            }
                
    }
    
    public boolean hayCaminoVertice(int posVertice){
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);
        
        
    }
    public Iterable<Integer> elRecorrido(){
        return this.recorrido;
    }
    
    public boolean hayCaminosATodos(){
        return controlMarcados.estanTodosMarcados();
    }
    
    public int cantIslas(){ //grafo
          
        controlMarcados.desmarcarTodos();
        int islas=0;
        
       for (int i=0 ; i <grafo.ListaDeAdyacencias.size();i++){
            procesarDFS(i);
           islas++;
            if(hayCaminosATodos()){
               return islas;
           }
           i= buscarVerticeSinMarcar()-1;
       }
       return islas;
    }

    private int buscarVerticeSinMarcar() {
       for(int c=0; c < grafo.ListaDeAdyacencias.size();c++){
           if(!controlMarcados.estaVerticeMarcado(c)){
               return c;
           }
       }
       return grafo.ListaDeAdyacencias.size();
    }
    
}
