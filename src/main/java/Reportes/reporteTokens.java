/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import java.awt.Color;

/**
 *
 * @author alejandro
 */
public class reporteTokens {
    
    private String tipoToken;
    private String lexeme;
    private int fila;
    private int columna;
    private int filaGRid;
    private int columnaGrid;
    private Color color;

    public reporteTokens(String tipoToken, String lexeme, int fila, int columna, int filaGRid, int columnaGrid, Color color) {
        this.tipoToken = tipoToken;
        this.lexeme = lexeme;      
        this.fila = fila;
        this.columna = columna;
        this.filaGRid = filaGRid;
        this.columnaGrid = columnaGrid;
        this.color = color;
    }

    // Getters y setters (omitiendo para brevedad)

    public String getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getFilaGRid() {
        return filaGRid;
    }

    public void setFilaGRid(int filaGRid) {
        this.filaGRid = filaGRid;
    }

    public int getColumnaGrid() {
        return columnaGrid;
    }

    public void setColumnaGrid(int columnaGrid) {
        this.columnaGrid = columnaGrid;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        return "Tipo de Token: " + tipoToken +
               ", Lexema: " + lexeme +
               ", Posición en texto: (Fila: " + fila + ", Columna: " + columna + ")" +
               ", Posición en cuadrícula: (Fila: " + filaGRid + ", Columna: " + columnaGrid + ")" +
               ", Color: " + hexColor;
    }

    
    
    

}
