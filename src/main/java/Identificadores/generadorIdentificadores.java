package Identificadores;

import java.awt.Color;

public class generadorIdentificadores {

    private char numero[] = {'0','1','2','3','4','5','6','7','8','9'};
    private char subra = '_';
    private char letras [] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    
    public String validarCadena(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }

        char primerCaracter = cadena.charAt(0);
        if (!esLetra(primerCaracter)) {
            return "Cadena inválida: La cadena debe comenzar con una letra.";
        }

        for (int i = 1; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (!esLetra(c) && !esNumero(c) && c != subra) {
                return "Cadena inválida: La cadena contiene caracteres inválidos.";
            }
        }

        return "Cadena válida: " + cadena;
    }

    public Color obtenerColorParaCadena(String cadena) {
        // Validar la cadena
        String resultado = validarCadena(cadena);
        
        // Si la cadena es válida, devolvemos el color amarillo, sino el color blanco
        if (resultado.equals("Cadena válida: " + cadena)) {
            return Color.YELLOW;
        } else {
            return Color.WHITE;
        }
    }

    public boolean esLetra(char c) {
        for (char letra : letras) {
            if (c == letra) {
                return true;
            }
        }
        return false;
    }

    private boolean esNumero(char c) {
        for (char num : numero) {
            if (c == num) {
                return true;
            }
        }
        return false;
    }
    
     // Nuevo método para verificar identificadores alfanuméricos válidos
    public boolean esIdentificadorValido(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return false;
        }

        char primerCaracter = cadena.charAt(0);
        if (!esLetra(primerCaracter)) {
            return false;
        }

        for (int i = 1; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (!esLetra(c) && !esNumero(c) && c != subra) {
                return false;
            }
        }

        return true;
    }

    
}
