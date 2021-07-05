/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.arboled2.excepciones;

/**
 *
 * @author Veronica
 */
public class ExcepcionNoSeInsertaValoresNulos extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionNoSeInsertaValoresNulos</code>
     * without detail message.
     */
    public ExcepcionNoSeInsertaValoresNulos() {
        super ("No se permite insertar valores nulos");
    }

    /**
     * Constructs an instance of <code>ExcepcionNoSeInsertaValoresNulos</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionNoSeInsertaValoresNulos(String msg) {
        super(msg);
    }
}
