/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author alejandro
 */

public class Automata {
    private celdaToken token;

    public Automata(celdaToken token) {
        this.token = token;
    }

    
    public void graficarAutomata() {
        try {
            System.out.println("Iniciando la generación del autómata para el token: " + token.getToken());

           
            FileWriter fileWriter = new FileWriter("automata.dot");

            
            fileWriter.write("digraph G {\n");
            fileWriter.write("rankdir=LR;\n");
            fileWriter.write("node [shape=circle];\n");

            
            String lexema = token.getToken();
            for (int i = 0; i < lexema.length(); i++) {
                String nodoActual = "q" + i;
                String nodoSiguiente = "q" + (i + 1);

               
                fileWriter.write(nodoActual + " -> " + nodoSiguiente + " [label=\"" + lexema.charAt(i) + "\"];\n");
                if (i == lexema.length() - 1) {
                    fileWriter.write(nodoSiguiente + " [shape=doublecircle];\n");
                }
            }

            fileWriter.write("}\n");
            fileWriter.close();
            System.out.println("Archivo .dot creado correctamente.");

            
            Process proceso = Runtime.getRuntime().exec("dot -Tpng automata.dot -o automata.png");

            
            int exitCode = proceso.waitFor();
            if (exitCode == 0) {
                System.out.println("Imagen del autómata generada como automata.png");
            } else {
                System.out.println("Error al generar la imagen del autómata. Código de salida: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    
    public void mostrarInformacion() {
        JFrame ventana = new JFrame("Información del Token");
        ventana.setSize(400, 300);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        JTextArea infoToken = new JTextArea();
        
        infoToken.setEditable(false);

        
        JButton botonGraficar = new JButton("Mostrar Autómata");
        botonGraficar.addActionListener(e -> {
            graficarAutomata();
            try {
                mostrarImagenAutomata();
            } catch (IOException ex) {
                Logger.getLogger(Automata.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ventana.add(new JScrollPane(infoToken), BorderLayout.CENTER);
        ventana.add(botonGraficar, BorderLayout.SOUTH);
        ventana.setVisible(true);
    }

   
    
    private void mostrarImagenAutomata() throws IOException {
        File archivoImagen = new File("automata.png");

        if (archivoImagen.exists()) {
           
            BufferedImage imagen = ImageIO.read(archivoImagen);
            ImageIcon iconoImagen = new ImageIcon(imagen);
            JFrame ventanaAutomata = new JFrame("Autómata");
            ventanaAutomata.setSize(500, 500);
            ventanaAutomata.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JLabel etiquetaImagen = new JLabel(iconoImagen);
            ventanaAutomata.add(etiquetaImagen);
            ventanaAutomata.setVisible(true);
                } else {
                    
                    JOptionPane.showMessageDialog(null, "Error: no se pudo generar el autómata.");
                }
    }
}
