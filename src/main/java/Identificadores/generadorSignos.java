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
public class generadorSignos {
    
    private Color coloParen = new Color(0x9AD8D8);
    private Color coloLlaves = new Color(0xDBD29A);
    private Color coloCorche = new Color(0xDBA49A);
    private Color coloComa = new Color(0xB79ADB);
    private Color coloPunto = new Color(0x9ADBA6);
    
    private String paren [] = {"(", ")"};
    private String llaves [] = {"{","}"};
    private String corche [] = {"[","]"};
    private char coma [] = {','};
    private char punto [] = {'.'};
    
    public String validarSignos (String cadena){
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }
        
        cadena = cadena.trim();
        
        if (esParentesis(cadena) || esLlave(cadena) || esCorchete(cadena) || esComa(cadena.charAt(0)) || esPunto(cadena.charAt(0))) {
            return "Cadena válida: " + cadena;
        }

        return "Cadena inválida: La cadena no es un operador de comparación válido.";
        
    }
    
    
    public Color colorSigno (String cadena){
        String resultado = validarSignos(cadena);
        
        if (resultado.equals("Cadena válida: " + cadena)) {
            cadena = cadena.trim();
            if (esParentesis(cadena)) {
                return coloParen;
            }else if (esLlave(cadena)) {
                return coloLlaves;
            }else if (esCorchete(cadena)) {
                return coloCorche;
            }else if (esComa(cadena.charAt(0))) {
                return coloComa;
            }else if (esPunto(cadena.charAt(0))) {
                return coloPunto;
            }
        }
        return Color.WHITE;
    }
    
    
    public boolean esParentesis (String c){
        c = c.trim();
        for (String sigParen : paren) {
            if (c.equals(sigParen)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean esLlave (String c){
        c = c.trim();
        for (String sigLlave : llaves) {
            if (c.equals(sigLlave)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean esCorchete (String c){
        c = c.trim();
        for (String sigCorche : corche) {
            if (c.equals(sigCorche)) {
                return true;
            }
        }
        return false;
    }
    
    
    public boolean esComa (char c){
        for (char sigComa : coma) {
            if (c == sigComa) {
                return true;
            }
        }
        return false;
    }
    
    public boolean esPunto (char c){
        for (char sigPunto : punto) {
            if (c == sigPunto) {
                return true;
            }
        }
        return false;
    }
    
    
}
