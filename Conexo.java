/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.grafos.nopesados;

/**
 *
 * @author Veronica
 */
public class Conexo {
    private DFS dfsGrafo;
    public Conexo (Grafos unGrafo){
        dfsGrafo = new DFS(unGrafo, 0);
        
    }
    
    public boolean esConexo(){
    return dfsGrafo.hayCaminosATodos(); 
    }
    
    
    
}
