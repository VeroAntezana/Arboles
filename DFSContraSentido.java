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
public class DFSContraSentido {
     private UtilsRecorrido controlMarcados;
    private Grafos grafo;
    private List<Integer> recorrido;
    
    public DFSContraSentido(Grafos unGrafo, int posVerticePartida){
         this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorrido(this.grafo.cantidadDeVertice()); // ya esta todo desmarcado
        procesarDFSContraSentido(posVerticePartida);
        
    }
    
    public void procesarDFSContraSentido(int posVertice){
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
         Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacenteDeVerticeAmbosSentidos(posVertice);
            for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno){
                if(!controlMarcados.estaVerticeMarcado(posVerticeAdyacente )){
                    procesarDFSContraSentido(posVerticeAdyacente);
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
    
}
