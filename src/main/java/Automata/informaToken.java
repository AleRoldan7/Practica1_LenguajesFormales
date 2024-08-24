/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

/**
 *
 * @author alejandro
 */
public class informaToken {
    private String token;
    private int row;
    private int col;
    private int gridRow;
    private int gridCol;
    private String tipoToken;

    public informaToken(String token, int row, int col, int gridRow, int gridCol, String tipoToken) {
        this.token = token;
        this.row = row;
        this.col = col;
        this.gridRow = gridRow;
        this.gridCol = gridCol;
        this.tipoToken = tipoToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getGridRow() {
        return gridRow;
    }

    public void setGridRow(int gridRow) {
        this.gridRow = gridRow;
    }

    public int getGridCol() {
        return gridCol;
    }

    public void setGridCol(int gridCol) {
        this.gridCol = gridCol;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }

    
}
