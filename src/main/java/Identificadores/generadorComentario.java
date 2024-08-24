/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Identificadores;

import java.awt.Color;

/**
 *
 * @author alejandro
 */
public class generadorComentario {
    
    private Color coloComentario = new Color(0xB3B3B3);
    
    public String validarComentario(String cadena){
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }

        if (!esComentario(cadena)) {
            return "Cadena inválida: La cadena no corresponde a ningún tipo de dato válido.";
        }

        return "Cadena válida: " + cadena;
        
    }
    
    public Color comentarioColor(String cadena){
        String resultado = validarComentario(cadena);
        
        if (resultado.equals("Cadena válida: " + cadena)) {
            if (esComentario(cadena)) {
                return coloComentario;
            }
        }
        return Color.WHITE;
        
    }
    
    
    
    public boolean esComentario(String cadena) {
        
        return cadena.length() > 1 && cadena.charAt(0) == '\'';
    }
}
