/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Identificadores;

import Interfaz.cuadriculaInicio;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class identificadorEspecial {
    
    public  boolean esTokenEspecial(String token) {
        
        String prefijo = "Square.Color(";
        
       
        if (token.length() <= prefijo.length()) {
            return false;
        }

        
        for (int i = 0; i < prefijo.length(); i++) {
            if (token.charAt(i) != prefijo.charAt(i)) {
                return false;
            }
        }
        
       
        if (token.charAt(token.length() - 1) != ')') {
            return false;
        }
        
       
        String contenido = token.substring(prefijo.length(), token.length() - 1);

        
        if (contenido.length() == 7 && contenido.charAt(0) == '#') {
            String hexCode = contenido.substring(1);
            return esCodigoHexadecimalValido(hexCode);
        }

        return false;
    }

    private static boolean esCodigoHexadecimalValido(String hexCode) {
        if (hexCode.length() != 6) {
            return false;
        }
        
        for (char c : hexCode.toCharArray()) {
            if (!Character.isDigit(c) && !(c >= 'A' && c <= 'F') && !(c >= 'a' && c <= 'f')) {
                return false;
            }
        }
        
        return true;
    }
    
}
