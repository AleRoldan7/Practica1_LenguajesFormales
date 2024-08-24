/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

import Identificadores.generadorAritmeticos;
import Identificadores.generadorAsignacion;
import Identificadores.generadorComentario;
import Identificadores.generadorComparacion;
import Identificadores.generadorIdentificadores;
import Identificadores.generadorLogico;
import Identificadores.generadorPalabrasReservadas;
import Identificadores.generadorSignos;
import Identificadores.generadorTiposDato;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author alejandro
 */
public class celdaToken extends JPanel{
    
    private String token;
    private Color color;
    private int filaTexto;
    private int columnaTexto;
    private int filaCuadricula;
    private int columnaCuadricula;
    private generadorIdentificadores geneIdentificadores = new generadorIdentificadores();
    private generadorAritmeticos geneAritmeticos = new generadorAritmeticos();
    private generadorComparacion geneComparacion = new generadorComparacion();
    private generadorLogico geneLogico = new generadorLogico();
    private generadorAsignacion geneAsignacion = new generadorAsignacion();
    private generadorPalabrasReservadas geneReservadas = new generadorPalabrasReservadas();
    private generadorTiposDato geneDato = new generadorTiposDato();
    private generadorSignos geneSignos = new generadorSignos();
    private generadorComentario geneComentario = new generadorComentario();
   
    public celdaToken(String token, Color color, int filaTexto, int columnaTexto, int filaCuadricula, int columnaCuadricula) {
        this.token = token;
        this.color = color;
        this.filaTexto = filaTexto;
        this.columnaTexto = columnaTexto;
        this.filaCuadricula = filaCuadricula;
        this.columnaCuadricula = columnaCuadricula;
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mostrarInformacion();
                automataIdentificador(token);
            }
        });
    }

    private void mostrarInformacion() {
        String tipo = identificarTipoToken(token);
        String mensaje = String.format(
            "Token: %s\nTipo: %s\nUbicación en el editor: Línea %d, Columna %d\nUbicación en la cuadrícula: Fila %d, Columna %d",
            token, tipo, filaTexto, columnaTexto, filaCuadricula, columnaCuadricula
        );

        JOptionPane.showMessageDialog(this, mensaje, "Información del Token", JOptionPane.INFORMATION_MESSAGE);

        
    }

    private String identificarTipoToken(String token) {
        // Identificar el tipo del token
        if (esComparacion(token)) return "Comparación";
        if (esAsignacion(token)) return "Asignación";
        if (esLogico(token)) return "Lógico";
        if (esTipoDato(token)) return "Tipo de Dato";
        if (esAritmetico(token)) return "Aritmético";
        if (esReservada(token)) return "Palabra Reservada";
        if (esSigno(token)) return "Signo";
        if (esComentario(token)) return "Comentario";
        return "Identificador";
    }

    private void automataIdentificador(String token){
        // Generar la imagen del autómata
        automataIdentificador generator = new automataIdentificador();
        generator.generarAuto(token);

        // Mostrar la imagen en una ventana emergente
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Automata para " + token);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            try {
                File imgFile = new File("automata_" + token + ".png");
                Image img = ImageIO.read(imgFile);
                JLabel imgLabel = new JLabel(new ImageIcon(img));
                frame.add(imgLabel, BorderLayout.CENTER);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            frame.setVisible(true);
        });
    }
    
    
    private boolean esComparacion(String token) {
        return geneComparacion.esIgual(token) || 
               geneComparacion.esDife(token) ||
               geneComparacion.esMayor(token.charAt(0)) ||
               geneComparacion.esMenor(token.charAt(0)) ||
               geneComparacion.esMayIgua(token) ||
               geneComparacion.esMenIgua(token);
    }

    private boolean esAritmetico(String token) {
        return geneAritmeticos.esSuma(token.charAt(0)) || 
               geneAritmeticos.esResta(token.charAt(0)) ||
               geneAritmeticos.esExpo(token.charAt(0)) ||
               geneAritmeticos.esDivision(token.charAt(0)) ||
               geneAritmeticos.esMod(token) ||
               geneAritmeticos.esMulti(token.charAt(0));
    }

    private boolean esAsignacion(String token) {
        return geneAsignacion.esSimple(token.charAt(0)) ||
               geneAsignacion.esCompuesta(token);
    }
    
    private boolean esLogico(String token) {
        return geneLogico.esAnd(token) || 
               geneLogico.esOr(token) ||
               geneLogico.esNega(token);
    }

    private boolean esReservada(String token) {
        return geneReservadas.esReservada(token);
    }

    private boolean esTipoDato(String token) {
        return geneDato.esEntero(token.charAt(0)) ||
               geneDato.esDecimal(token) ||
               geneDato.esCadena(token) ||
               geneDato.esBooleano(token) ||
               geneDato.esCaracter(token);
    }

    private boolean esSigno(String token) {
        return geneSignos.esParentesis(token) ||
               geneSignos.esLlave(token) ||
               geneSignos.esCorchete(token) ||
               geneSignos.esComa(token.charAt(0)) ||
               geneSignos.esPunto(token.charAt(0));
    }

    private boolean esComentario(String token) {
        return geneComentario.esComentario(token);
    }



}
