package Interfaz;

import Automata.automataIdentificador;
import Automata.celdaToken;
import Identificadores.generadorAritmeticos;
import Identificadores.generadorAsignacion;
import Identificadores.generadorComentario;
import Identificadores.generadorComparacion;
import Identificadores.generadorIdentificadores;
import Identificadores.generadorLogico;
import Identificadores.generadorPalabrasReservadas;
import Identificadores.generadorSignos;
import Identificadores.generadorTiposDato;
import Identificadores.identificadorEspecial;
import ListaTokens.ListaTokens;
import Reportes.reportePanel;
import Reportes.reporteTokens;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class cuadriculaInicio extends JFrame {

    private generadorIdentificadores geneIdentificadores = new generadorIdentificadores();
    private generadorAritmeticos geneAritmeticos = new generadorAritmeticos();
    private generadorComparacion geneComparacion = new generadorComparacion();
    private generadorLogico geneLogico = new generadorLogico();
    private generadorAsignacion geneAsignacion = new generadorAsignacion();
    private generadorPalabrasReservadas geneReservadas = new generadorPalabrasReservadas();
    private generadorTiposDato geneDato = new generadorTiposDato();
    private generadorSignos geneSignos = new generadorSignos();
    private generadorComentario geneComentario = new generadorComentario();
    private ListaTokens listaTokens;
    private JTextArea texto;
    private JPanel panel;
    private int tamaGrid;
    private JLabel estadoCursor;
    
    
    
    
    public cuadriculaInicio() {
        setTitle("Analizador Lexico");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
       
        texto = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(texto);
        add(scrollPane, BorderLayout.WEST);
        
        JPanel topPanel = new JPanel();
        JButton tamaButton = new JButton("Establecer Tamaño");
        tamaButton.addActionListener(new tamaPanel());
        topPanel.add(tamaButton);

        JButton cargarButton = new JButton("Cargar Archivo");
        cargarButton.addActionListener(new CargarArchivoPanel());
        topPanel.add(cargarButton);

        JButton mostrarTokensButton = new JButton("Mostrar Tokens");
        mostrarTokensButton.addActionListener(new MostrarTokensPanel());
        topPanel.add(mostrarTokensButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        panel = new JPanel();
        add(panel, BorderLayout.CENTER);
       
        texto.addCaretListener(e -> actualizarCuadricula());
        
        estadoCursor = new JLabel("Línea: 1, Columna: 1");
        estadoCursor.setBorder(BorderFactory.createEtchedBorder());
        add(estadoCursor, BorderLayout.SOUTH);

        texto.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                try {
                    int pos = texto.getCaretPosition();
                    int row = texto.getLineOfOffset(pos);
                    int col = getColumnInText(texto.getText(), pos);
                    estadoCursor.setText(String.format("Línea: %d, Columna: %d", row +1 , col ));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private int getColumnInText(String text, int pos) {
        int col = 1;
        for (int i = 0; i < pos; i++) {
            if (text.charAt(i) == '\n') {
                col = 1;
            } else {
                col++;
            }
        }
        return col;
    }

    private class tamaPanel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String tamaCuadricula = JOptionPane.showInputDialog("Ingrese el tamaño de la cuadrícula:");
            try {
                tamaGrid = Integer.parseInt(tamaCuadricula);
                panel.setLayout(new GridLayout(tamaGrid, tamaGrid));
                panel.revalidate();
                actualizarCuadricula(); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
            }
        }
    }
    
    private class CargarArchivoPanel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(cuadriculaInicio.this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                    StringBuilder sb = new StringBuilder();
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        sb.append(linea).append("\n");
                    }
                    texto.setText(sb.toString());
                    actualizarCuadricula();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(cuadriculaInicio.this, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    
    private class MostrarTokensPanel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String textoEscrito = texto.getText();
            String[] tokens = separarTokens(textoEscrito);
            List<reporteTokens> tokenReports = new ArrayList<>();

            int line = 1; // Supongamos que tienes un método para obtener la línea actual
            int column = 1; // Similar para la columna

            for (String token : tokens) {
                String tipo = identificarTipoToken(token);
                // Asumiendo que tienes métodos para obtener la posición en la cuadrícula y el color
                int gridRow = obtenerFilaTexto(token);
                int gridColumn = obtenerColumnaTexto(token);
                Color color = obtenerColorParaToken(tipo);

                // Crear un TokenReport y añadirlo a la lista
                reporteTokens report = new reporteTokens(tipo, token, line, column, gridRow, gridColumn, color);
                tokenReports.add(report);

                // Actualizar columna/línea para el siguiente token (esto depende de tu lógica)
                line++;
                column++;
            }

            // Mostrar el reporte usando la clase TokenReportViewer
            reportePanel.showReport(tokenReports, ((JFrame) SwingUtilities.getWindowAncestor(texto)));
        }

        private String identificarTipoToken(String token) {
            if (esComparacion(token)) {
                return "Comparación";
            } else if (esAsignacion(token)) {
                return "Asignación";
            } else if (esLogico(token)) {
                return "Lógico";
            } else if (esAritmetico(token)) {
                return "Aritmético";
            } else if (esReservada(token)) {
                return "Palabra Reservada";
            } else if (esTipoDato(token)) {
                return "Tipo de Dato";
            } else if (esSigno(token)) {
                return "Signo";
            } else if (esComentario(token)) {
                return "Comentario";
            } else {
                return "Identificador";
            }   
        }
        
        
        
    }

    private void actualizarCuadricula() {
        panel.removeAll();

        String textoEscrito = texto.getText();
        String[] tokens = separarTokens(textoEscrito);

        int cuadrosTotales = tamaGrid * tamaGrid;
        int tokenIndex = 0;
        List<String> tokensPintados = new ArrayList<>(); // Lista para almacenar tokens ya pintados

        for (int i = 0; i < cuadrosTotales; i++) {
            String token = tokenIndex < tokens.length ? tokens[tokenIndex] : null;
            Color color = (token != null) ? obtenerColorParaToken(token) : Color.WHITE;

            celdaToken celda = new celdaToken(token, color, obtenerFilaTexto(token), obtenerColumnaTexto(token), i / tamaGrid, i % tamaGrid);
            
            panel.add(celda);
            tokenIndex++;
        }

        panel.revalidate();
        panel.repaint();
    }

    // Implementar el método obtenerColorParaToken() para determinar el color del token
    private Color obtenerColorParaToken(String token) {
        if (esComparacion(token)) return geneComparacion.colorComparacion(token);
        if (esAsignacion(token)) return geneAsignacion.colorAsignacion(token);
        if (esLogico(token)) return geneLogico.colorLogico(token);
        if (esAritmetico(token)) return geneAritmeticos.colorAritmetico(token);
        if (esReservada(token)) return geneReservadas.colorReservada(token);
        if (esTipoDato(token)) return geneDato.obtenerColorTipo(token);
        if (esSigno(token)) return geneSignos.colorSigno(token);
        if (esComentario(token)) return geneComentario.comentarioColor(token);
        else{
            return geneIdentificadores.obtenerColorParaCadena(token);
        }
        /*
        else{
            JOptionPane.showMessageDialog(cuadriculaInicio.this, "Token no valido", "Error", JOptionPane.ERROR_MESSAGE);
 
        }
        */
       
    }

   
    private int obtenerFilaTexto(String token) {
        
        return 1;
    }

    private int obtenerColumnaTexto(String token) {
        
        return 1;
    }



    private boolean esAritmetico(String token) {
        return geneAritmeticos.esSuma(token.charAt(0)) || 
               geneAritmeticos.esResta(token.charAt(0)) ||
               geneAritmeticos.esExpo(token.charAt(0)) ||
               geneAritmeticos.esDivision(token.charAt(0)) ||
               geneAritmeticos.esMod(token) ||
               geneAritmeticos.esMulti(token.charAt(0));
    }
    
    private boolean esComparacion(String token) {
        return geneComparacion.esIgual(token) || 
               geneComparacion.esDife(token) ||
               geneComparacion.esMayor(token.charAt(0)) ||
               geneComparacion.esMenor(token.charAt(0)) ||
               geneComparacion.esMayIgua(token) ||
               geneComparacion.esMenIgua(token);
    }

    private boolean esLogico(String token) {
        return geneLogico.esAnd(token) || 
               geneLogico.esOr(token) ||
               geneLogico.esNega(token);
    }

    private boolean esAsignacion(String token){
        return geneAsignacion.esSimple(token.charAt(0)) ||
               geneAsignacion.esCompuesta(token);
    }
    
    public boolean esReservada(String token){
        return geneReservadas.esReservada(token);
    }
    
    private boolean esTipoDato(String token){
        return geneDato.esEntero(token.charAt(0)) ||
               geneDato.esDecimal(token) ||
               geneDato.esCadena(token) ||
               geneDato.esBooleano(token) ||
               geneDato.esCaracter(token);
    }
    
    private boolean esSigno(String token){
        return geneSignos.esParentesis(token) ||
               geneSignos.esLlave(token) ||
               geneSignos.esCorchete(token) ||
               geneSignos.esComa(token.charAt(0)) ||
               geneSignos.esPunto(token.charAt(0));
    }
    
    private boolean esComentario(String token){
        return geneComentario.esComentario(token);
    }
    
    
    
    
    
    /*
    public String[] separarTokens(String texto) {
        int tamaCadena = texto.length();
        List<String> tokens = new ArrayList<>();
        StringBuilder tokenBuilder = new StringBuilder();
        boolean dentroDeNumero = false;  // Bandera para controlar cuando estamos dentro de un número

        for (int i = 0; i < tamaCadena; i++) {
            char c = texto.charAt(i);

            if (Character.isWhitespace(c)) {
                // Cuando se encuentra un espacio, agregar el token si existe
                if (tokenBuilder.length() > 0) {
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                    dentroDeNumero = false;
                }
            } else if (esDelimitador(c)) {
                // Agregar el token acumulado antes del delimitador
                if (tokenBuilder.length() > 0) {
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                    dentroDeNumero = false;
                }
                // Verificar si es un punto y si estamos en un número
                if (c == '.' && dentroDeNumero && i + 1 < tamaCadena && Character.isDigit(texto.charAt(i + 1))) {
                    // Si es un punto decimal y hay un dígito después, continuar acumulando el número
                    tokenBuilder.append(c);
                } else {
                    // Agregar el delimitador como token
                    tokens.add(String.valueOf(c));
                }
            } else {
                // Acumular caracteres para un token, puede ser parte de un identificador o número
                tokenBuilder.append(c);

                // Verificar si estamos construyendo un número
                if (Character.isDigit(c) || (c == '.' && dentroDeNumero)) {
                    dentroDeNumero = true; // Estamos dentro de un número
                }

                // Verificar si el siguiente carácter no es parte de un número o identificador
                if (i + 1 >= tamaCadena || esDelimitador(texto.charAt(i + 1)) || Character.isWhitespace(texto.charAt(i + 1))) {
                    // Agregar el token acumulado hasta ahora
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                    dentroDeNumero = false;  // Reiniciar bandera después de completar un token
                }
            }
        }

        // Añadir el último token si existe
        if (tokenBuilder.length() > 0) {
            tokens.add(tokenBuilder.toString());
        }

        return tokens.toArray(new String[0]);
    }

    private boolean esDelimitador(char c) {
        // Ajustar esta lógica para que los delimitadores sean los que definas como tales
        return !(Character.isLetter(c) || Character.isDigit(c) || c == '_') 
               && (esSigno(String.valueOf(c)) || esAritmetico(String.valueOf(c))
               || esComparacion(String.valueOf(c)) || esLogico(String.valueOf(c)) || esAsignacion(String.valueOf(c)))
               && c != '.';  // Excluir el punto como delimitador, lo manejaremos manualmente
    }
    */

    
    
    
    
    
    public String[] separarTokens(String texto) {
        int tamaCadena = texto.length();
        List<String> tokens = new ArrayList<>();
        StringBuilder tokenBuilder = new StringBuilder();

        boolean enToken = false;

        for (int i = 0; i < tamaCadena; i++) {
            char c = texto.charAt(i);

            if (Character.isWhitespace(c)) {
                if (enToken) {
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                    enToken = false;
                }
            } else {
                tokenBuilder.append(c);
                enToken = true;
            }
        }

        if (enToken) {
            tokens.add(tokenBuilder.toString());
        }

        return tokens.toArray(new String[0]);
    }
    

}
