package Automata;

import Interfaz.cuadriculaInicio;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author alejandro
 */
public class automataIdentificador {

    private cuadriculaInicio cuInicio;

    public void generarAuto(String token, String tipoToken) {
        // Crear la representación DOT en función del token
        StringBuilder dotFileContent = new StringBuilder();
        dotFileContent.append("digraph DFA {\n");
        dotFileContent.append("    label=\"Tipo de Token: ").append(tipoToken).append("\";\n");
        dotFileContent.append("    labelloc=\"t\";\n"); // Posición del título en la parte superior
        dotFileContent.append("    start [shape=point];\n");

        String nodoAnterior = "start";
        for (int i = 0; i < token.length(); i++) {
            String nodoActual = "s" + i;
            char c = token.charAt(i);
            dotFileContent.append("    ").append(nodoAnterior).append(" -> ").append(nodoActual).append(" [label=\"").append(c).append("\"];\n");
            dotFileContent.append("    ").append(nodoActual).append(" [shape=circle];\n");
            nodoAnterior = nodoActual;
        }
        dotFileContent.append("    ").append(nodoAnterior).append(" [shape=doublecircle];\n"); // Nodo final como doble círculo
        dotFileContent.append("}\n");

        try {
            // Escribir el archivo DOT
            File dotFile = new File("diagram.dot");
            FileWriter writer = new FileWriter(dotFile);
            writer.write(dotFileContent.toString());
            writer.close();

            // Ejecutar Graphviz para generar una imagen PNG
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", "diagram.dot", "-o", "diagram.png");
            Process process = pb.start();
            process.waitFor();

            System.out.println("Diagrama generado como diagram.png");

            // Mostrar la imagen en una ventana emergente
            mostrarImagen("diagram.png");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarImagen(String rutaImagen) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Diagrama del Autómata");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            try {
                BufferedImage img = ImageIO.read(new File(rutaImagen));
                JLabel imgLabel = new JLabel(new ImageIcon(img));
                frame.add(new JScrollPane(imgLabel), BorderLayout.CENTER);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "No se pudo cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            frame.setVisible(true);
        });
    }
}

