/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Identificadores;

import java.awt.Color;

/**
 *
 * @author crisa
 */
public class generadorLogico {
    
    Color yColor = new Color(0x414ED9);
    Color oColor = new Color(0x41D95D);
    Color negaColor = new Color(0xA741D9);
    
    
    String Y [] = {"And"};
    String O [] = {"Or"};
    String negacion [] = {"Not"};

    public String validarLogico(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }

        // Validar si la cadena corresponde a un operador de comparación válido
        if (!esAnd(cadena) && !esOr(cadena) && !esNega(cadena)) {
            return "Cadena inválida: La cadena no es un operador de comparación válido.";
        }

        return "Cadena válida: " + cadena;
    }

    public Color colorLogico(String cadena) {
        // Validar la cadena
        String resultado = validarLogico(cadena);
        
        // Si la cadena es válida, devolver el color correspondiente
        if (resultado.equals("Cadena válida: " + cadena)) {
            if (esAnd(cadena)) {
                return yColor;
            } else if (esOr(cadena)) {
                return oColor;
            } else if (esNega(cadena)) {
                return negaColor;
            } 
        }
        
        return Color.WHITE; // Color por defecto para cadenas inválidas
    }
    
    public boolean esAnd (String c){
        for (String sigY : Y) {
            if (c.equals(sigY)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean esOr (String c){
        for (String sigO : O) {
            if (c.equals(sigO)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean esNega (String c){
        for (String sigNega : negacion) {
            if (c.equals(sigNega)) {
                return true;
            }
        }
        
        return false;
    }
}
