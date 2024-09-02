/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author alejandro
 */
public class reportePanel {
    
    public static void showReport(List<reporteTokens> reporteToken, JFrame frameToken) {
        String[] columnNames = {"Token", "Lexema", "Línea", "Columna", "Cuadro"};
        Object[][] data = new Object[reporteToken.size()][5];

        for (int i = 0; i < reporteToken.size(); i++) {
            reporteTokens reporte = reporteToken.get(i);
            data[i][0] = reporte.getTipoToken();
            data[i][1] = reporte.getLexeme();
            data[i][2] = reporte.getFila();
            data[i][3] = reporte.getColumna();
            data[i][4] = "Fila:" + reporte.getFilaGRid() + " Col:" + reporte.getColumnaGrid() + " Color: " + reporte.getColor();
        }

        JTable table = new JTable(data, columnNames);

        
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Token
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Lexema
        table.getColumnModel().getColumn(2).setPreferredWidth(50);  // Línea
        table.getColumnModel().getColumn(3).setPreferredWidth(50);  // Columna
        table.getColumnModel().getColumn(4).setPreferredWidth(400); // Cuadro

        
        table.setRowHeight(30); 

        JScrollPane scrollPane = new JScrollPane(table);

        
        scrollPane.setPreferredSize(new Dimension(800, 400)); 

        JOptionPane.showMessageDialog(frameToken, scrollPane, "Reporte de Tokens", JOptionPane.INFORMATION_MESSAGE);
    }
}
