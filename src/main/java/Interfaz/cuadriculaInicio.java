package Interfaz;

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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
    private identificadorEspecial ideEspecial = new identificadorEspecial();
    
    
    
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
        
        JButton guardarImagen = new JButton("Guardar Imagen");
        guardarImagen.addActionListener(new GuardarImagenPanel());
        topPanel.add(guardarImagen);
        
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

            int line = obtenerFilaTexto(textoEscrito); 
            int column = obtenerColumnaTexto(textoEscrito); 

            for (String token : tokens) {
                String tipo = identificarTipoToken(token);
                
                int gridFila = obtenerFilaTexto(token);
                int gridColumn = obtenerColumnaTexto(token);
                Color color = obtenerColorParaToken(tipo);

                
                reporteTokens report = new reporteTokens(tipo, token, line, column, gridFila, gridColumn, color);
                tokenReports.add(report);

                
                line++;
                column++;
            }

            
            reportePanel.showReport(tokenReports, ((JFrame) SwingUtilities.getWindowAncestor(texto)));
        }

        private String identificarTipoToken(String token) {
            if(especialToken(token)){
                return "Token_Especial";
            } else if (esComparacion(token)) {
                return "Comparación";
            } else if (esAsignacion(token)) {
                return "Asignación";
            } else if (esLogico(token)) {
                return "Lógico";
            } else if (esAritmetico(token)) {
                return "Aritmético";
            } else if (esReservada(token)) {
                return "Palabra_Reservada";
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

           
            int filaTexto = obtenerFilaTexto(token);
            int columnaTexto = obtenerColumnaTexto(token);

            
            int gridFila = (tokenIndex / tamaGrid);
            int gridColumna = (tokenIndex % tamaGrid);

            celdaToken celda = new celdaToken(token, color, filaTexto, columnaTexto, gridFila, gridColumna);

            panel.add(celda);
            tokenIndex++;
        }

        panel.revalidate();
        panel.repaint();
    }



   
    private Color obtenerColorParaToken(String token) {
        if (especialToken(token)) {
            return Color.decode(getColorHexFromToken(token)); 
        }
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
       
       
    }

   
    private int obtenerFilaTexto(String token) {
        if (token == null) return -1;

        String textoEscrito = texto.getText();
        int index = textoEscrito.indexOf(token);

        if (index == -1) {
            return -1; 
        }

       
        int fila = 1;
        for (int i = 0; i < index; i++) {
            if (textoEscrito.charAt(i) == ' ') {
                fila++;
            }
        }
        return fila;
    }

    private int obtenerColumnaTexto(String token) {
        if (token == null) return -1;

        String textoEscrito = texto.getText();
        int index = textoEscrito.indexOf(token);

        if (index == -1) {
            return -1; 
        }

        
        int lastNewLineIndex = textoEscrito.lastIndexOf(' ', index);
        int columna = index - lastNewLineIndex;

        return columna + 1; 
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
    
    private boolean especialToken(String token){
        return ideEspecial.esTokenEspecial(token);
    }
    
    private String getColorHexFromToken(String token) {
        String contenido = token.substring("Square.Color(".length(), token.length() - 1);
        return contenido; 
    }
    
    
    public String[] separarTokens(String texto) {
        int tamaCadena = texto.length();
        List<String> tokens = new ArrayList<>();
        StringBuilder tokenBuilder = new StringBuilder();
        boolean dentroDeNumero = false;  // Para saber que viene un numero

        for (int i = 0; i < tamaCadena; i++) {
            char c = texto.charAt(i);

            if (Character.isWhitespace(c)) {
                if (tokenBuilder.length() > 0) {
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                    dentroDeNumero = false;
                }
            } else if (limitaToken(c)) {
                if (tokenBuilder.length() > 0) {
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                    dentroDeNumero = false;
                }

               
                if (i + 1 < tamaCadena && tokenDoble(c, texto.charAt(i + 1))) {
                    tokens.add("" + c + texto.charAt(i + 1));
                    i++;  
                } else if (c == '.' && dentroDeNumero && i + 1 < tamaCadena && Character.isDigit(texto.charAt(i + 1))) {
                    tokenBuilder.append(c);
                } else {
                    tokens.add(String.valueOf(c));
                }
            } else {
                
                if (esInicioTokenEspecial(texto, i)) {
                    int finTokenEspecial = encontrarFinTokenEspecial(texto, i);
                    tokens.add(texto.substring(i, finTokenEspecial + 1));
                    i = finTokenEspecial;
                } else {
                    tokenBuilder.append(c);

                    if (Character.isDigit(c) || (c == '.' && dentroDeNumero)) {
                        dentroDeNumero = true;
                    }

                    if (i + 1 >= tamaCadena || limitaToken(texto.charAt(i + 1)) || Character.isWhitespace(texto.charAt(i + 1))) {
                        tokens.add(tokenBuilder.toString());
                        tokenBuilder.setLength(0);
                        dentroDeNumero = false;
                    }
                }
            }
        }

        if (tokenBuilder.length() > 0) {
            tokens.add(tokenBuilder.toString());
        }

        return tokens.toArray(new String[0]);
    }

    
    private boolean tokenDoble(char c1, char c2) {
        String op = "" + c1 + c2;
        return op.equals("<>") || op.equals("==") || op.equals("*=") || op.equals("-=") || 
               op.equals("+=") || op.equals("/=") || op.equals("<=") || op.equals(">=");
    }

    private boolean esInicioTokenEspecial(String texto, int index) {
        String tokenEspecial = "Square.Color(";
        int longitud = tokenEspecial.length();
        if (index + longitud <= texto.length()) {
            return texto.substring(index, index + longitud).equals(tokenEspecial);
        }
        return false;
    }

    private int encontrarFinTokenEspecial(String texto, int index) {
        
        for (int i = index; i < texto.length(); i++) {
            if (texto.charAt(i) == ')') {
                return i;
            }
        }
        return texto.length() - 1;  
    }

    private boolean limitaToken(char c) {
        return !(Character.isLetter(c) || Character.isDigit(c) || c == '_') 
               && (esSigno(String.valueOf(c)) || esAritmetico(String.valueOf(c))
               || esComparacion(String.valueOf(c)) || esLogico(String.valueOf(c)) || esAsignacion(String.valueOf(c)))
               && c != '.';
    }

    
    
    
    
    /*
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
    */
    private class GuardarImagenPanel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            panel.paint(g2d);
            g2d.dispose();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Imagen");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagenes JPG y PNG", "jpg", "png"));

            int sleccion = fileChooser.showSaveDialog(cuadriculaInicio.this);
            if (sleccion == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getPath();

                
                String extension = extensionImagen(filePath);
                String formatName = "jpg"; 
                if (extension.equals("png")) {
                    formatName = "png";
                }

                try {
                    ImageIO.write(image, formatName, fileToSave);
                    JOptionPane.showMessageDialog(cuadriculaInicio.this, "Imagen guardada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(cuadriculaInicio.this, "Error al guardar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private String extensionImagen(String filePath) {
            String fileName = new File(filePath).getName();
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                return fileName.substring(dotIndex + 1).toLowerCase();
            }
            return "";
        }
    }

}
