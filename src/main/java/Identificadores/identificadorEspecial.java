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
        // Definir el prefijo a comparar
        String prefijo = "Square.Color(";
        
        // Verificar que la longitud del token sea suficiente
        if (token.length() <= prefijo.length()) {
            return false;
        }

        // Verificar manualmente el prefijo comparando carácter por carácter
        for (int i = 0; i < prefijo.length(); i++) {
            if (token.charAt(i) != prefijo.charAt(i)) {
                return false;
            }
        }
        
        // Verificar manualmente el sufijo
        if (token.charAt(token.length() - 1) != ')') {
            return false;
        }
        
        // Extraer la parte dentro de los paréntesis
        String contenido = token.substring(prefijo.length(), token.length() - 1);

        // Verificar que el contenido tenga el formato correcto
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
