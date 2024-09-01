package Identificadores;

import java.awt.Color;

public class generadorAritmeticos {
    
    // Colores para cada tipo de operador aritmético
    private Color sumaColor = new Color(0xFF33FF);  
    private Color restaColor = new Color(0xC19A6B); 
    private Color expoColor = new Color(0xFCD0B4);  
    private Color diviColor = new Color(0xB4D941);  
    private Color modColor = new Color(0xD9AB41);   
    private Color multiColor = new Color(0xD80073); 
    
    private char suma[] = {'+'};
    private char resta[] = {'-'};
    private char expo[] = {'^'};
    private char division[] = {'/'};
    private String modulo[] = {"Mod"};
    private char multi[] = {'*'};
    
    public String validarAritmeticos(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }

        // Validar si la cadena corresponde a un operador de comparación válido
        if (!esSuma(cadena.charAt(0)) && !esResta(cadena.charAt(0)) && !esExpo(cadena.charAt(0)) && !esDivision(cadena.charAt(0)) &&
            !esMod(cadena) && !esMulti(cadena.charAt(0))) {
            return "Cadena inválida: La cadena no es un operador de comparación válido.";
        }

        // Verificar que la cadena no tenga operadores duplicados consecutivos
        if (cadena.length() > 1) {
            char primerCaracter = cadena.charAt(0);
            char segundoCaracter = cadena.charAt(1);
            if (primerCaracter == segundoCaracter) {
                return "Cadena inválida: El operador no puede estar repetido.";
            }
        }

        // Verificar caracteres válidos en el resto de la cadena
        for (int i = 1; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (!esSuma(c) && !esResta(c) && !esExpo(c) && !esDivision(c) && !esMod(cadena) && !esMulti(c)) {
                return "Cadena inválida: La cadena contiene caracteres inválidos.";
            }
        }
        
        // Verificar que no haya operadores aritméticos diferentes consecutivos
        for (int i = 0; i < cadena.length() - 1; i++) {
            char actual = cadena.charAt(i);
            char siguiente = cadena.charAt(i + 1);

            // Si ambos son operadores aritméticos, pero distintos, es inválido
            if (esOperadorAritmetico(actual) && esOperadorAritmetico(siguiente) && actual != siguiente) {
                return "Cadena inválida: No se permiten operadores aritméticos diferentes juntos.";
            }
        }

        // Verificar caracteres válidos en el resto de la cadena
        for (int i = 1; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (!esOperadorAritmetico(c) && !esMod(cadena)) {
                return "Cadena inválida: La cadena contiene caracteres inválidos.";
            }
        }
        
        return "Cadena válida: " + cadena;
    }
    
    public Color colorAritmetico(String cadena) {
        String resultado = validarAritmeticos(cadena);

        // Si la cadena es válida, devolver el color correspondiente
        if (resultado.equals("Cadena válida: " + cadena)) {
            
            if (esSuma(cadena.charAt(0))) {
                return sumaColor;
            } else if (esResta(cadena.charAt(0))) {
                return restaColor;
            } else if (esExpo(cadena.charAt(0))) {
                return expoColor;
            } else if (esDivision(cadena.charAt(0))) {
                return diviColor;
            } else if (esMod(cadena)) {
                return modColor;
            } else if (esMulti(cadena.charAt(0))) {
                return multiColor;
            }
        }
        
        return Color.WHITE; 
    }

    public boolean esSuma(char c) {
        for (char sigSuma : suma) {
            if (c == sigSuma) {
                return true;
            }
        }
        return false;
    }

    public boolean esResta(char c) {
        for (char sigResta : resta) {
            if (c == sigResta) {
                return true;
            }
        }
        return false;
    }
    
    public boolean esExpo(char c) {
        for (char sigExpo : expo) {
            if (c == sigExpo) {
                return true;
            }
        }
        return false;
    }

    public boolean esDivision(char c) {
        for (char sigDivi : division) {
            if (c == sigDivi) {
                return true;
            }
        }
        return false;
    }
    
    public boolean esMod(String c) {
        for (String sigMod : modulo) {
            if (c.equals(sigMod)) {
                return true;
            }
        }
        return false;
    }

    public boolean esMulti(char c) {
        for (char sigMulti : multi) {
            if (c == sigMulti) {
                return true;
            }
        }
        return false;
    }
    
    private boolean esOperadorAritmetico(char c) {
        return esSuma(c) || esResta(c) || esExpo(c) || esDivision(c) || esMulti(c) || esExpo(c);
    }
}
