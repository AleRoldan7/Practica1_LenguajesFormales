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
public class identificadorEspecial {
    
    private String tEspecial [] = {"Square.Color"};
    private char numeral [] = {'#'};
            
            
    public String validarCadena(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }


        return "Cadena válida: " + cadena;
    }
    
    
    public void agregarColor(String color){
        
        
        
    }
    
    
}
