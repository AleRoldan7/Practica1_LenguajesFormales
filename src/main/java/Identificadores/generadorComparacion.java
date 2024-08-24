package Identificadores;

import java.awt.Color;

public class generadorComparacion {
    
    // Colores para cada tipo de operador de comparación
    Color iguaColor = new Color(0x6A00FF);
    Color difeColor = new Color(0x3F2212);
    Color mayColor = new Color(0xD9D441);
    Color menColor = new Color(0xD94A41);
    Color mIgualColor = new Color(0xE3C800);
    Color meIguaColor = new Color(0xF0A30A);

    String igual[] = {"=="};
    String diferencia[] = {"<>"};
    char mayor[] = {'>'};
    char menor[] = {'<'};
    String mayoIgual[] = {">="};
    String menoIgual[] = {"<="};
    
    public String validarComparacion(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }
        
        // Verificar que la cadena corresponde a un operador válido
        if (esIgual(cadena) || esDife(cadena) || esMayIgua(cadena) || esMenIgua(cadena)) {
            return "Cadena válida: " + cadena;
        } else if (cadena.length() == 1 && (esMayor(cadena.charAt(0)) || esMenor(cadena.charAt(0)))) {
            return "Cadena válida: " + cadena;
        } else {
            return "Cadena inválida: La cadena no es un operador de comparación válido.";
        }
    }
    
    public Color colorComparacion(String cadena) {
        String resultado = validarComparacion(cadena);
        
        // Determinar el color basado en el resultado de validación
        if (resultado.contains("Cadena válida")) {
            if (esIgual(cadena)) {
                return iguaColor;
            } else if (esDife(cadena)) {
                return difeColor;
            } else if (esMayIgua(cadena)) {
                return mIgualColor;
            } else if (esMenIgua(cadena)) {
                return meIguaColor;
            } else if (cadena.length() == 1) {
                char primerCaracter = cadena.charAt(0);
                if (esMayor(primerCaracter)) {
                    return mayColor;
                } else if (esMenor(primerCaracter)) {
                    return menColor;
                }
            }
        }
        
        return Color.WHITE; // Color por defecto para cadenas inválidas
    }

    public boolean esIgual(String c) {
        for (String sigIgua : igual) {
            if (c.equals(sigIgua)) {
                return true;
            }
        }
        return false;
    }

    public boolean esDife(String c) {
        for (String sigDife : diferencia) {
            if (c.equals(sigDife)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean esMayor(char c) {
        for (char sigMay : mayor) {
            if (c == sigMay) {
                return true;
            }
        }
        return false;
    }

    public boolean esMenor(char c) {
        for (char sigMen : menor) {
            if (c == sigMen) {
                return true;
            }
        }
        return false;
    }
    
    public boolean esMayIgua(String c) {
        for (String sigMaI : mayoIgual) {
            if (c.equals(sigMaI)) {
                return true;
            }
        }
        return false;
    }

    public boolean esMenIgua(String c) {
        for (String sigMeI : menoIgual) {
            if (c.equals(sigMeI)) {
                return true;
            }
        }
        return false;
    }
}
