package Identificadores;

import java.awt.Color;

public class generadorAsignacion {
    
    // Colores para los operadores de asignación
    Color simpleColor = new Color(0x41D9D4);
    Color compuestaColor = new Color(0xFFFFFF);
    
    char asSimple[] = {'='};
    String asCompuesta[] = {"+=", "-=", "*=", "/="};
    
    public String validarAsignacion(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }

        // Validar si la cadena corresponde a un operador de asignación válido
        if (esCompuesta(cadena)) {
            return "Cadena válida: " + cadena;
        } else if (cadena.length() == 1 && esSimple(cadena.charAt(0))) {
            return "Cadena válida: " + cadena;
        } else {
            return "Cadena inválida: La cadena no es un operador de asignación válido.";
        }
    }
    
    public Color colorAsignacion(String cadena) {
        String resultado = validarAsignacion(cadena);
        
        // Comparar la cadena de resultado para determinar el color
        if (resultado.contains("Cadena válida")) {
            if (cadena.length() == 1 && esSimple(cadena.charAt(0))) {
                return simpleColor;
            } else if (esCompuesta(cadena)) {
                return compuestaColor;
            }
        }
        
        return Color.BLACK;
    }
    
    public boolean esSimple(char c) {
        for (char sigSimple : asSimple) {
            if (c == sigSimple) {
                return true;
            }
        }
        return false;
    }
    
    public boolean esCompuesta(String c) {
        for (String sigCompuesta : asCompuesta) {
            if (c.equals(sigCompuesta)) {
                return true;
            }
        }
        return false;
    }
}
