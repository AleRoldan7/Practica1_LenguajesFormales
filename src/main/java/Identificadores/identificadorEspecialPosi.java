/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Identificadores;

import Interfaz.cuadriculaInicio;
import java.awt.Color;

/**
 *
 * @author alejandro
 */
public class identificadorEspecialPosi {
    
    // Método que verifica si un token es especial y además pinta el token en la posición especificada
    public boolean esTokenEspecial(String token, int fila, int columna, cuadriculaInicio cuadricula) {
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
            if (esCodigoHexadecimalValido(hexCode)) {
                // Si el código es válido, pintar en la cuadrícula
                Color color = Color.decode(contenido); // Convertir el código hexadecimal a un objeto Color
                pintarEnCuadricula(fila, columna, color, cuadricula);
                return true;
            }
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
    
    // Método para pintar en la cuadrícula en la posición especificada
    private void pintarEnCuadricula(int fila, int columna, Color color, cuadriculaInicio cuadricula) {
        // Asumir que cuadriculaInicio tiene un método llamado pintar que toma fila, columna y un color
        
    }

}
