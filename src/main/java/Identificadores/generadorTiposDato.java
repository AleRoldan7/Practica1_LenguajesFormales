package Identificadores;

import java.awt.Color;

public class generadorTiposDato {

    private Color enteroColor = new Color(0x1BA1E2);
    private Color deciColor = new Color(0xFFFF88);
    private Color cadeColor = new Color(0xE51400);
    private Color booColor = new Color(0xFA6800);
    private Color caraColor = new Color(0x0050EF);

    private char[] entero = {'0','1','2','3','4','5','6','7','8','9'};
    private String [] decimal = {"."};
    private String[] booleano = {"True", "False"};

    public String validarTipos(String cadena){
        if (cadena == null || cadena.isEmpty()) {
            return "Cadena inválida: La cadena está vacía.";
        }

        if (!esEntero(cadena.charAt(0)) && !esDecimal(cadena) && !esCadena(cadena) && !esBooleano(cadena) && !esCaracter(cadena)) {
            return "Cadena inválida: La cadena no corresponde a ningún tipo de dato válido.";
        }

        return "Cadena válida: " + cadena;
    }

    public Color obtenerColorTipo(String cadena) {
        String resultado = validarTipos(cadena);
        
        if (resultado.equals("Cadena válida: " + cadena)) {
            if (esDecimal(cadena)) {
                return deciColor;
            } else if (esEntero(cadena.charAt(0))) {
                return enteroColor;
            } else if (esCadena(cadena)) {
                return cadeColor;
            } else if (esBooleano(cadena)) {
                return booColor;
            } else if (esCaracter(cadena)) {
                return caraColor;
            }
        }
        return Color.WHITE;
    }

    public boolean esEntero(char c){
        for (char sigEntero : entero) {
            if (c == sigEntero) {
                return true;
            }
        }
        return false;
    }
    
    

    
    public boolean esDecimal(String cadena) {
        // Verificar que la cadena contiene exactamente un punto
        int puntoIndex = cadena.indexOf('.');
        if (puntoIndex == -1 || cadena.indexOf('.', puntoIndex + 1) != -1) {
            return false; // No tiene un punto o tiene más de un punto
        }

        // Verificar que haya al menos un dígito antes y después del punto
        if (puntoIndex == 0 || puntoIndex == cadena.length() - 1) {
            return false; // No hay dígitos antes o después del punto
        }

        // Verificar que los caracteres antes y después del punto sean dígitos
        for (int i = 0; i < puntoIndex; i++) {
            if (!Character.isDigit(cadena.charAt(i))) {
                return false; // Caracter antes del punto no es dígito
            }
        }
        for (int i = puntoIndex + 1; i < cadena.length(); i++) {
            if (!Character.isDigit(cadena.charAt(i))) {
                return false; // Caracter después del punto no es dígito
            }
        }

        return true; // La cadena cumple con todos los requisitos para ser un decimal
    }

    
    public boolean esCadena(String cadena) {
        return cadena.length() >= 2 && cadena.charAt(0) == '"' && cadena.charAt(cadena.length() - 1) == '"';
    }

    public boolean esBooleano(String cadena) {
        for (String sigBool : booleano) {
            if (cadena.equalsIgnoreCase(sigBool)) {
                return true;
            }
        }
        return false;
    }

    public boolean esCaracter(String cadena) {
        
        return cadena.length() == 3 && cadena.charAt(0) == '\'' && cadena.charAt(2) == '\'';
    }
}
