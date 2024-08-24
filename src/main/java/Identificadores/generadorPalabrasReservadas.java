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
public class generadorPalabrasReservadas {
    
    Color reservadaColor = new Color(0x60A917);
    
    String palaReservada [] = {"Module", "End", "Sub", "Main", "Dim", "As",
                              "Integer", "String", "Boolean", "Double", "Char",
                              "Console.WriteLine", "Console.ReadLine", "If", "ElseIf",
                              "Else", "Then", "While", "Do", "Loop", "For", "To", "Next",
                              "Function", "Return", "Const"};
    
    
    public String validarReservada(String cadena){
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }

        if (!esReservada(cadena)) {
            return "Cadena inválida: La cadena no es un operador de comparación válido.";
        }

        return "Cadena válida: " + cadena;
    }
    
    public Color colorReservada(String cadena){
        String resultado = validarReservada(cadena);
        
        if (resultado.equals("Cadena válida: " + cadena)) {
            if (esReservada(cadena)) {
                return reservadaColor;
            }
        }
        
        return Color.WHITE; 
    }
    
    public boolean esReservada(String c){
        for (String resePalabra : palaReservada) {
            if (c.equals(resePalabra)) {
                return true;
            }
        }
        return false;
    }
}
