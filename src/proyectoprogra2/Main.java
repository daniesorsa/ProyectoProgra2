package proyectoprogra2;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
public class Main extends javax.swing.JFrame {    
    //uml
    private ArrayList<Variable> variables = new ArrayList<>();
    private ArrayList<Figura> figuras = new ArrayList<>();
    private ArrayList<Figura> plantillas = new ArrayList<>();
    private ArrayList<ClaseVisual> clases = new ArrayList<>();
    private JPanel canvasPanel;
    private Figura figuraClickDerecho = null;
    private Figura figuraCopiada = null;
    private Diamante padreTemporal = null;
    
    //clases
    private ArrayList<FiguraArbol> figurasClases = new ArrayList<>();
    private JPanel canvasClasesPanel;
    private FiguraArbol claseClickDerecho = null;
    private ArrayList<Variable> parametrosTemporales = new ArrayList<>();
    
    public Main() {
        initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        

//figuras        
        Ovalo inicio = new Ovalo(10, 10, 90, 35);
        inicio.setNombre("Inicio");
        inicio.setTexto("Inicio");

        Ovalo fin = new Ovalo(130, 10, 90, 35);
        fin.setNombre("Fin");
        fin.setTexto("Fin");
        
        Rectangulo proceso = new Rectangulo(10, 80, 90, 40);
        proceso.setNombre("Proceso");
        proceso.setTexto("Proceso");
        
        Paralelogramo declarar = new Paralelogramo(130, 80, 90, 40);
        declarar.setNombre("Operacion");
        declarar.setTexto("Operacion");
        
        Diamante ifFig = new Diamante(10, 150, 90, 50);
        ifFig.setNombre("If");
        ifFig.setTexto("if");
        
        RectanguloDoble sout = new RectanguloDoble(130, 150, 90, 40);
        sout.setNombre("sout");
        sout.setTexto("Sout");
        
        Diamante whileFig = new Diamante(10, 220, 90, 50);
        whileFig.setNombre("While");
        whileFig.setTexto("while");
        
        Diamante forFig = new Diamante(130, 220, 90, 50);
        forFig.setNombre("For");
        forFig.setTexto("for");
        
        plantillas.add(inicio);
        plantillas.add(fin);
        plantillas.add(proceso);
        plantillas.add(declarar);
        plantillas.add(ifFig);
        plantillas.add(sout);
        plantillas.add(whileFig);
        plantillas.add(forFig);
        
        JPanel opcionesPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                for (Figura f : plantillas) f.dibujar(g2);
            }
        };
        opcionesPanel.setBackground(Color.WHITE);
        opcionesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                for (Figura f : plantillas) {
                    if (f.contiene(e.getX(), e.getY())) {
                        
                        // solo 1 inicio y 1 final
                        String nombreNueva = f.getNombre();
                        if (nombreNueva.equals("Inicio") || nombreNueva.equals("Fin")) {
                            for (Figura figGuardada : figuras) {
                                if (figGuardada.getNombre().equals(nombreNueva)) {
                                    JOptionPane.showMessageDialog(null, "Error: Solo se puede agregar un nodo de '" + nombreNueva + "' al diagrama.");
                                    return;
                                }
                            }
                        }
                        //offset para poder mover
                        int x = Math.max(10, canvasPanel.getWidth()  / 2 - f.getAncho() / 2)+ (figuras.size() % 6) * 15;
                        int y = Math.max(10, canvasPanel.getHeight() / 2 - f.getAlto()  / 2) + (figuras.size() % 6) * 15;
                        figuras.add(f.copiar(x, y));
                        figuras.getLast().setTexto(nombreNueva);
                        figuras.getLast().setNombre(nombreNueva);
                        canvasPanel.repaint();
                        break;
                    }
                }
            }
        });
        jp_opciones.setLayout(new BorderLayout());
        jp_opciones.add(opcionesPanel, BorderLayout.CENTER);

        final Figura[] seleccionada = {null};
        final int[] offsetX = {0};
        final int[] offsetY = {0};

        canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                for (Figura f : figuras) f.dibujar(g2);
            }
        };
        canvasPanel.setBackground(Color.WHITE);
        canvasPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.getButton() == 1) {
                    seleccionada[0] = null;
                    for (int i = figuras.size() - 1; i >= 0; i--) {
                        if (figuras.get(i).contiene(e.getX(), e.getY())) {
                            seleccionada[0] = figuras.get(i);
                            offsetX[0] = e.getX() - seleccionada[0].getX();
                            offsetY[0] = e.getY() - seleccionada[0].getY();
                            break;
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                seleccionada[0] = null;
            }
             @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                figuraClickDerecho = null;
                if (evt.getButton() == 3) {
                for (int i = figuras.size() - 1; i >= 0; i--) {
                    if (figuras.get(i).contiene(evt.getX(), evt.getY())) {
                        figuraClickDerecho = figuras.get(i);
                        break;
                    }
                }
                if(figuraClickDerecho != null) {
                    String textoAccion = figuraClickDerecho.getNombreAccionEspecial();
                    if (textoAccion != null) {
                        jmi_accionEspecial.setText(textoAccion);
                        jmi_accionEspecial.setVisible(true);
                    } else jmi_accionEspecial.setVisible(false);
                    jpm_diagramas.show(canvasPanel, evt.getX(), evt.getY());
                    jpm_diagramas.show(canvasPanel, evt.getX(), evt.getY());
                }
            }
        }});
        canvasPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (seleccionada[0] != null && javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    int nuevoX = e.getX() - offsetX[0];
                    int nuevoY = e.getY() - offsetY[0];
                    
                    nuevoX = Math.max(0, Math.min(nuevoX,canvasPanel.getWidth()  - seleccionada[0].getAncho()));
                    nuevoY = Math.max(0, Math.min(nuevoY,canvasPanel.getHeight() - seleccionada[0].getAlto()));

                    seleccionada[0].setX(nuevoX);
                    seleccionada[0].setY(nuevoY);
                    canvasPanel.repaint();
                }
            }
        });
    
        jp_diagrama.removeAll();
        jp_diagrama.setLayout(new BorderLayout());
        jp_diagrama.add(canvasPanel, BorderLayout.CENTER);
        jtp_diagrama.setSelectedIndex(0);
        
// ****************************PANEL CLASES****************************
        final FiguraArbol[] seleccionadaClase = {null};
        final int[] offsetXClase = {0};
        final int[] offsetYClase = {0};

        canvasClasesPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                for (FiguraArbol f : figurasClases) f.dibujar(g2);
            }
        };
        canvasClasesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.getButton() == 1) {
                    seleccionadaClase[0] = null;
                    for (int i = figurasClases.size() - 1; i >= 0; i--) {
                        if (figurasClases.get(i).contiene(e.getX(), e.getY())) {
                            seleccionadaClase[0] = figurasClases.get(i);
                            offsetXClase[0] = e.getX() - seleccionadaClase[0].getX();
                            offsetYClase[0] = e.getY() - seleccionadaClase[0].getY();
                            break;
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                seleccionadaClase[0] = null;
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                claseClickDerecho = null;
                if (evt.getButton() == 3) {
                    for (int i = figurasClases.size() - 1; i >= 0; i--) {
                        if (figurasClases.get(i).contiene(evt.getX(), evt.getY())) {
                            claseClickDerecho = figurasClases.get(i);
                            break;
                        }
                    }
                    if(claseClickDerecho != null) jpm_clases.show(canvasClasesPanel, evt.getX(), evt.getY());
                }
            }
        });
        canvasClasesPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (seleccionadaClase[0] != null && javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    int nuevoX = e.getX() - offsetXClase[0];
                    int nuevoY = e.getY() - offsetYClase[0];
                    
                    nuevoX = Math.max(0, Math.min(nuevoX, canvasClasesPanel.getWidth()  - seleccionadaClase[0].getAncho()));
                    nuevoY = Math.max(0, Math.min(nuevoY, canvasClasesPanel.getHeight() - seleccionadaClase[0].getAlto()));

                    seleccionadaClase[0].setX(nuevoX);
                    seleccionadaClase[0].setY(nuevoY);
                    canvasClasesPanel.repaint();
                }
            }
        });
    
        jp_diagramaClases.removeAll();
        jp_diagramaClases.setLayout(new java.awt.BorderLayout());
        jp_diagramaClases.add(canvasClasesPanel, java.awt.BorderLayout.CENTER);
        
        this.pack();
        this.setLocationRelativeTo(null);
        
//eventos para nuevo, abrir o guardar
        // Ctrl + N para Nueva Ventana
        jmi_nuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        
        // Ctrl + G para Guardar Binario
        jmi_guardarBinario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        
        // Ctrl + A para Abrir Binario
        jmi_abrirTexto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        
        // Ctrl + Shift + G para Guardar Texto
        jmi_guardarTexto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | java.awt.event.InputEvent.SHIFT_DOWN_MASK));

        // Ctrl + Shift + A para Abrir Texto
        jmi_abrirBinario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        
//******Modelos******
//jcb
        String[] fuentes = (String[]) GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        DefaultComboBoxModel modeloFuentes = new DefaultComboBoxModel(fuentes);
        jcb_fuente.setModel(modeloFuentes);
        jcb_propiedadFuente.setModel(modeloFuentes);
        
        String[] tamanhos = {"8", "10", "12", "14", "16", "18", "24", "36", "48"};
        jcb_tamanhoFuente.setModel(new DefaultComboBoxModel<>(tamanhos));
        
        DefaultComboBoxModel modeloVariables1 = new DefaultComboBoxModel();
        DefaultComboBoxModel modeloVariables2 = new DefaultComboBoxModel();
        DefaultComboBoxModel modeloVariables3 = new DefaultComboBoxModel();
        jcb_operacionVar1.setModel(modeloVariables1);
        jcb_operacionVar2.setModel(modeloVariables2);
        jcb_operacionRes.setModel(modeloVariables3);
        
        //for
        DefaultComboBoxModel modeloIdxFor = new DefaultComboBoxModel();
        modeloIdxFor.addElement(new Variable("0", "int"));
        jcb_bucleForIdx.setModel(modeloIdxFor);
        DefaultComboBoxModel modeloVariableCondicionFor = new DefaultComboBoxModel();
        modeloVariableCondicionFor.addElement(new Variable("0", "int"));
        modeloVariableCondicionFor.addElement(new Variable("10", "int"));
        jcb_bucleForVariableCondicion.setModel(modeloVariableCondicionFor);
        
        //while
        DefaultComboBoxModel modeloVar1While = new DefaultComboBoxModel();
        jcb_bucleWhileVar1.setModel(modeloVar1While);
        DefaultComboBoxModel modeloVar2While = new DefaultComboBoxModel();
        jcb_bucleWhileVar2.setModel(modeloVar2While);
        
//jlist
        DefaultListModel modeloLista = new DefaultListModel();
        jl_variables.setModel(modeloLista);
        
        DefaultListModel modeloPadres = new DefaultListModel();
        jl_clasePadre.setModel(modeloPadres);

        DefaultListModel modeloHijos = new DefaultListModel();
        jl_claseHija.setModel(modeloHijos);
        
        DefaultListModel modeloListaHijos = new DefaultListModel();
        jl_hijosClasePadre.setModel(modeloListaHijos);
        
//jtree
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Clases");
        DefaultTreeModel arbol = new DefaultTreeModel(raiz);
        jtr_clases.setModel(arbol);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_agregarVariable = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_agregarVariable = new javax.swing.JButton();
        txt_nombreVariable = new javax.swing.JTextField();
        jcb_tipoVariable = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jd_propiedadesFigura = new javax.swing.JDialog();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_operacionGuardarPropiedad = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btn_propiedadCancelar = new javax.swing.JButton();
        txt_textoPropiedades = new javax.swing.JTextField();
        txt_nombrePropiedades = new javax.swing.JTextField();
        btn_propiedadColorFondo = new javax.swing.JButton();
        jcb_propiedadFuente = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jsp_propiedadTamanho = new javax.swing.JSpinner();
        jsp_propiedadWidth = new javax.swing.JSpinner();
        jsp_propiedadHeight = new javax.swing.JSpinner();
        jp_colorFondoPropiedades = new javax.swing.JPanel();
        btn_propiedadEnable = new javax.swing.JToggleButton();
        jd_crearClase = new javax.swing.JDialog();
        jLabel20 = new javax.swing.JLabel();
        btn_nombreClase = new javax.swing.JButton();
        txt_nombreClase = new javax.swing.JTextField();
        jpm_clases = new javax.swing.JPopupMenu();
        jmi_agregarPropiedad = new javax.swing.JMenuItem();
        jmi_eliminarPropiedad = new javax.swing.JMenuItem();
        jmi_agregarMetodo = new javax.swing.JMenuItem();
        jmi_eliminarMetodo = new javax.swing.JMenuItem();
        jmi_eliminarArbol = new javax.swing.JMenuItem();
        jpm_diagramas = new javax.swing.JPopupMenu();
        jmi_cambiarColor = new javax.swing.JMenuItem();
        jmi_cambiarTexto = new javax.swing.JMenuItem();
        jmi_eliminar = new javax.swing.JMenuItem();
        jmi_cambiarFuente = new javax.swing.JMenuItem();
        jmi_colorFuente = new javax.swing.JMenuItem();
        jmi_propiedades = new javax.swing.JMenuItem();
        jmi_copiar = new javax.swing.JMenuItem();
        jpm_agregarPropiedadDiagrama = new javax.swing.JMenuItem();
        jmi_accionEspecial = new javax.swing.JMenuItem();
        jd_herencia = new javax.swing.JDialog();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jl_claseHija = new javax.swing.JList<String>();
        jScrollPane6 = new javax.swing.JScrollPane();
        jl_hijosClasePadre = new javax.swing.JList<String>();
        jScrollPane7 = new javax.swing.JScrollPane();
        jl_clasePadre = new javax.swing.JList<String>();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        btn_asignarHerencia = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jd_cambiarFuente = new javax.swing.JDialog();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jcb_fuente = new javax.swing.JComboBox<>();
        jcb_estilo = new javax.swing.JComboBox<>();
        btn_guardarFuente = new javax.swing.JButton();
        jcb_tamanhoFuente = new javax.swing.JComboBox<>();
        jd_cambiarColorFuente = new javax.swing.JDialog();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbl_previewDelTexto = new javax.swing.JLabel();
        btn_seleccionarColor = new javax.swing.JButton();
        btn_cambiarColorFuente = new javax.swing.JButton();
        jd_operacion = new javax.swing.JDialog();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        btn_operacionAgregar = new javax.swing.JButton();
        jcb_operacionVar1 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jcb_operacionVar2 = new javax.swing.JComboBox<>();
        jcb_operacionRes = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jcb_operacionSelec = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jd_bucleFor = new javax.swing.JDialog();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jcb_bucleForCondicion = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jcb_bucleForSalto = new javax.swing.JComboBox<>();
        jcb_bucleForAccion = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        jcb_bucleForIdx = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jcb_bucleForVariableCondicion = new javax.swing.JComboBox<>();
        btn_bucleForAccion = new javax.swing.JButton();
        btn_bucleForAgregar = new javax.swing.JButton();
        jd_bucleWhile = new javax.swing.JDialog();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jcb_bucleWhileCondicion = new javax.swing.JComboBox<>();
        jcb_bucleWhileAccion = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jcb_bucleWhileVar1 = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jcb_bucleWhileVar2 = new javax.swing.JComboBox<>();
        btn_bucleWhileAgregar = new javax.swing.JButton();
        btn_bucleWhileAccion = new javax.swing.JButton();
        jd_if = new javax.swing.JDialog();
        jLabel39 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jcb_ifCondicion = new javax.swing.JComboBox<>();
        jcb_ifAccion = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        jcb_ifVar1 = new javax.swing.JComboBox<>();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jcb_ifVar2 = new javax.swing.JComboBox<>();
        btn_ifAgregarAccion = new javax.swing.JButton();
        btn_ifGuardar = new javax.swing.JButton();
        jd_sout = new javax.swing.JDialog();
        jLabel47 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_soutGuardar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jta_sout = new javax.swing.JTextArea();
        jd_agregarMetodo = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_agregarMetodo = new javax.swing.JButton();
        txt_nombreMetodo = new javax.swing.JTextField();
        jcb_metodoReturn = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jcb_metodoAlcance = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        btn_propiedadAgregarParametro = new javax.swing.JButton();
        jd_agregarParametro = new javax.swing.JDialog();
        jLabel61 = new javax.swing.JLabel();
        txt_metodoNombreParametro = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jcb_metodoTipoVariable = new javax.swing.JComboBox<>();
        btn_metodoAgregarParametro = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        jd_agregarPropiedad = new javax.swing.JDialog();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        btn_agregarPropiedad = new javax.swing.JButton();
        txt_nombrePropiedad = new javax.swing.JTextField();
        jcb_tipoPropiedad = new javax.swing.JComboBox<>();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jcb_alcancePropiedad = new javax.swing.JComboBox<>();
        jtp_principal = new javax.swing.JTabbedPane();
        jp_uml = new javax.swing.JPanel();
        jp_opciones = new javax.swing.JPanel();
        jp_medio = new javax.swing.JPanel();
        jtp_diagrama = new javax.swing.JTabbedPane();
        jp_diagrama = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jta_visualDiagrama = new javax.swing.JTextArea();
        jp_codigo = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jta_codigoDiagrama = new javax.swing.JTextArea();
        btn_pegar = new javax.swing.JButton();
        btn_generarCodigo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jp_variables = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jl_variables = new javax.swing.JList<>();
        btn_agregarVariablePrincipal = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jp_clases = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtr_clases = new javax.swing.JTree();
        btn_separador = new javax.swing.JButton();
        btn_nuevaClase = new javax.swing.JButton();
        btn_herencia = new javax.swing.JButton();
        btn_generarCodigoClases = new javax.swing.JButton();
        jtp_diagramaCodigo1 = new javax.swing.JTabbedPane();
        jp_diagramaClases = new javax.swing.JPanel();
        jp_codigoClases = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jta_codigoClases = new javax.swing.JTextArea();
        jmb_barraVentana = new javax.swing.JMenuBar();
        jm_archivo = new javax.swing.JMenu();
        jmi_guardarBinario = new javax.swing.JMenuItem();
        jmi_guardarTexto = new javax.swing.JMenuItem();
        jmi_abrirBinario = new javax.swing.JMenuItem();
        jmi_abrirTexto = new javax.swing.JMenuItem();
        jmi_nuevo = new javax.swing.JMenuItem();
        jm_exportar = new javax.swing.JMenu();

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Variable");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Tipo");

        btn_agregarVariable.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_agregarVariable.setText("Agregar");
        btn_agregarVariable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_agregarVariableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_agregarVariableMouseEntered(evt);
            }
        });

        jcb_tipoVariable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Int", "Float", "Char", "String" }));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Agregar Variable");

        javax.swing.GroupLayout jd_agregarVariableLayout = new javax.swing.GroupLayout(jd_agregarVariable.getContentPane());
        jd_agregarVariable.getContentPane().setLayout(jd_agregarVariableLayout);
        jd_agregarVariableLayout.setHorizontalGroup(
            jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarVariableLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_nombreVariable)
                    .addComponent(jcb_tipoVariable, 0, 115, Short.MAX_VALUE))
                .addGap(59, 59, 59))
            .addGroup(jd_agregarVariableLayout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(btn_agregarVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jd_agregarVariableLayout.setVerticalGroup(
            jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarVariableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addGap(4, 4, 4)
                .addGroup(jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombreVariable))
                .addGap(18, 18, 18)
                .addGroup(jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_tipoVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btn_agregarVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Color del Fondo");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Nombre");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Texto");

        btn_operacionGuardarPropiedad.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_operacionGuardarPropiedad.setText("Cerrar");
        btn_operacionGuardarPropiedad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_operacionGuardarPropiedadMouseClicked(evt);
            }
        });
        btn_operacionGuardarPropiedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_operacionGuardarPropiedadActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Propiedades");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Enable");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Fuente");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Width");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Height");

        btn_propiedadCancelar.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_propiedadCancelar.setText("Cancelar");
        btn_propiedadCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_propiedadCancelarMouseClicked(evt);
            }
        });

        btn_propiedadColorFondo.setText("Elegir Color");
        btn_propiedadColorFondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_propiedadColorFondoActionPerformed(evt);
            }
        });

        jcb_propiedadFuente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Times New Roman", "Arial", "Courier New", "Dialog", "Serif", "Monospaced", "Tahoma" }));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Tamaño");

        javax.swing.GroupLayout jp_colorFondoPropiedadesLayout = new javax.swing.GroupLayout(jp_colorFondoPropiedades);
        jp_colorFondoPropiedades.setLayout(jp_colorFondoPropiedadesLayout);
        jp_colorFondoPropiedadesLayout.setHorizontalGroup(
            jp_colorFondoPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jp_colorFondoPropiedadesLayout.setVerticalGroup(
            jp_colorFondoPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        btn_propiedadEnable.setText("Enable");

        javax.swing.GroupLayout jd_propiedadesFiguraLayout = new javax.swing.GroupLayout(jd_propiedadesFigura.getContentPane());
        jd_propiedadesFigura.getContentPane().setLayout(jd_propiedadesFiguraLayout);
        jd_propiedadesFiguraLayout.setHorizontalGroup(
            jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_propiedadesFiguraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_operacionGuardarPropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(btn_propiedadCancelar)
                .addGap(74, 74, 74))
            .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                        .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_propiedadesFiguraLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_propiedadesFiguraLayout.createSequentialGroup()
                        .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jsp_propiedadHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jsp_propiedadTamanho, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                        .addComponent(jsp_propiedadWidth, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                                        .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_nombrePropiedades)
                                            .addComponent(txt_textoPropiedades)
                                            .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                                                .addComponent(jp_colorFondoPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                                .addComponent(btn_propiedadColorFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(22, 22, 22))
                                    .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                                        .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jcb_propiedadFuente, javax.swing.GroupLayout.Alignment.LEADING, 0, 228, Short.MAX_VALUE)
                                            .addComponent(btn_propiedadEnable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))))
        );
        jd_propiedadesFiguraLayout.setVerticalGroup(
            jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombrePropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_textoPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_propiedadColorFondo))
                    .addComponent(jp_colorFondoPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_propiedadEnable))
                .addGap(10, 10, 10)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_propiedadFuente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jsp_propiedadTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jsp_propiedadWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jsp_propiedadHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_operacionGuardarPropiedad)
                    .addComponent(btn_propiedadCancelar))
                .addGap(39, 39, 39))
        );

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel20.setText("Nombre");

        btn_nombreClase.setText("Crear");
        btn_nombreClase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_nombreClaseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jd_crearClaseLayout = new javax.swing.GroupLayout(jd_crearClase.getContentPane());
        jd_crearClase.getContentPane().setLayout(jd_crearClaseLayout);
        jd_crearClaseLayout.setHorizontalGroup(
            jd_crearClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_crearClaseLayout.createSequentialGroup()
                .addGroup(jd_crearClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_crearClaseLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_nombreClase, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jd_crearClaseLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(btn_nombreClase)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jd_crearClaseLayout.setVerticalGroup(
            jd_crearClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_crearClaseLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jd_crearClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombreClase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(btn_nombreClase)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jmi_agregarPropiedad.setText("Agregar Propiedad");
        jmi_agregarPropiedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_agregarPropiedadActionPerformed(evt);
            }
        });
        jpm_clases.add(jmi_agregarPropiedad);

        jmi_eliminarPropiedad.setText("Eliminar Propiedad");
        jmi_eliminarPropiedad.setToolTipText("");
        jmi_eliminarPropiedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_eliminarPropiedadActionPerformed(evt);
            }
        });
        jpm_clases.add(jmi_eliminarPropiedad);

        jmi_agregarMetodo.setText("Agregar Metodo");
        jmi_agregarMetodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_agregarMetodoActionPerformed(evt);
            }
        });
        jpm_clases.add(jmi_agregarMetodo);

        jmi_eliminarMetodo.setText("Eliminar Metodo");
        jmi_eliminarMetodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_eliminarMetodoActionPerformed(evt);
            }
        });
        jpm_clases.add(jmi_eliminarMetodo);

        jmi_eliminarArbol.setText("Eliminar Arbol");
        jmi_eliminarArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_eliminarArbolActionPerformed(evt);
            }
        });
        jpm_clases.add(jmi_eliminarArbol);

        jmi_cambiarColor.setText("Cambiar Color");
        jmi_cambiarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_cambiarColorActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jmi_cambiarColor);

        jmi_cambiarTexto.setText("Cambiar el Texto");
        jmi_cambiarTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_cambiarTextoActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jmi_cambiarTexto);

        jmi_eliminar.setText("Eliminar");
        jmi_eliminar.setToolTipText("");
        jmi_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_eliminarActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jmi_eliminar);

        jmi_cambiarFuente.setText("Cambiar Fuente");
        jmi_cambiarFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_cambiarFuenteActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jmi_cambiarFuente);

        jmi_colorFuente.setText("Color de Fuente");
        jmi_colorFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_colorFuenteActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jmi_colorFuente);

        jmi_propiedades.setText("Propiedades");
        jmi_propiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_propiedadesActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jmi_propiedades);

        jmi_copiar.setText("Copiar");
        jmi_copiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_copiarActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jmi_copiar);

        jpm_agregarPropiedadDiagrama.setText("Agregar Propiedad");
        jpm_agregarPropiedadDiagrama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpm_agregarPropiedadDiagramaActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jpm_agregarPropiedadDiagrama);

        jmi_accionEspecial.setText("Accion Especial");
        jmi_accionEspecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_accionEspecialActionPerformed(evt);
            }
        });
        jpm_diagramas.add(jmi_accionEspecial);

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Declarar Herencia");

        jScrollPane5.setViewportView(jl_claseHija);

        jScrollPane6.setViewportView(jl_hijosClasePadre);

        jl_clasePadre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jl_clasePadreMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jl_clasePadre);

        jLabel52.setText("Seleccionar Clase Padre");

        jLabel53.setText("Hijos de Clase Padre Seleccionada");

        jLabel54.setText("Seleccionar Clase Hija");

        btn_asignarHerencia.setText("Asignar Herencia");
        btn_asignarHerencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_asignarHerenciaMouseClicked(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Seleccione un padre y un hijo");

        javax.swing.GroupLayout jd_herenciaLayout = new javax.swing.GroupLayout(jd_herencia.getContentPane());
        jd_herencia.getContentPane().setLayout(jd_herenciaLayout);
        jd_herenciaLayout.setHorizontalGroup(
            jd_herenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_herenciaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(jd_herenciaLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addGap(61, 61, 61))
            .addGroup(jd_herenciaLayout.createSequentialGroup()
                .addGroup(jd_herenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_herenciaLayout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jd_herenciaLayout.createSequentialGroup()
                        .addGap(362, 362, 362)
                        .addComponent(btn_asignarHerencia))
                    .addGroup(jd_herenciaLayout.createSequentialGroup()
                        .addGap(354, 354, 354)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jd_herenciaLayout.setVerticalGroup(
            jd_herenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_herenciaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jd_herenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jd_herenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_asignarHerencia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Estilo");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Fuente");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Tamaño");

        jcb_fuente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Times New Roman", "Arial", "Courier New", "Verdana", "Tahoma", "Monospaced", "Serif", "SansSerif", "Dialog", "DialogInput" }));

        jcb_estilo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Plano", "Negrita", "Cursiva", "Negrita Cursiva" }));

        btn_guardarFuente.setText("Guardar");
        btn_guardarFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarFuenteActionPerformed(evt);
            }
        });

        jcb_tamanhoFuente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Plano", "Negrita", "Cursiva", "Negrita Cursiva" }));

        javax.swing.GroupLayout jd_cambiarFuenteLayout = new javax.swing.GroupLayout(jd_cambiarFuente.getContentPane());
        jd_cambiarFuente.getContentPane().setLayout(jd_cambiarFuenteLayout);
        jd_cambiarFuenteLayout.setHorizontalGroup(
            jd_cambiarFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_cambiarFuenteLayout.createSequentialGroup()
                .addGroup(jd_cambiarFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_cambiarFuenteLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jd_cambiarFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(jd_cambiarFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcb_fuente, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcb_estilo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcb_tamanhoFuente, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jd_cambiarFuenteLayout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(btn_guardarFuente)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jd_cambiarFuenteLayout.setVerticalGroup(
            jd_cambiarFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_cambiarFuenteLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jd_cambiarFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_fuente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_cambiarFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_estilo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_cambiarFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_tamanhoFuente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_guardarFuente)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Cambiar Color de Fuente");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Preview del Texto:");

        lbl_previewDelTexto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_previewDelTexto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btn_seleccionarColor.setText("Seleccionar Color");
        btn_seleccionarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seleccionarColorActionPerformed(evt);
            }
        });

        btn_cambiarColorFuente.setText("Guardar");
        btn_cambiarColorFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cambiarColorFuenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jd_cambiarColorFuenteLayout = new javax.swing.GroupLayout(jd_cambiarColorFuente.getContentPane());
        jd_cambiarColorFuente.getContentPane().setLayout(jd_cambiarColorFuenteLayout);
        jd_cambiarColorFuenteLayout.setHorizontalGroup(
            jd_cambiarColorFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_cambiarColorFuenteLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(134, 134, 134))
            .addGroup(jd_cambiarColorFuenteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_previewDelTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jd_cambiarColorFuenteLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jd_cambiarColorFuenteLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btn_seleccionarColor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(btn_cambiarColorFuente, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jd_cambiarColorFuenteLayout.setVerticalGroup(
            jd_cambiarColorFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_cambiarColorFuenteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_previewDelTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jd_cambiarColorFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_seleccionarColor)
                    .addComponent(btn_cambiarColorFuente))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setText("Operacion");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setText("Variable 1");

        btn_operacionAgregar.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_operacionAgregar.setText("Agregar");
        btn_operacionAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_operacionAgregarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_operacionAgregarMouseEntered(evt);
            }
        });

        jcb_operacionVar1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Int", "Float", "Char", "String" }));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setText("Variable 2");

        jcb_operacionVar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_operacionVar2ActionPerformed(evt);
            }
        });

        jcb_operacionRes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Int", "Float", "Char", "String" }));
        jcb_operacionRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_operacionResActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setText("Resultado");

        jcb_operacionSelec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-", "*", "/" }));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Creacion de la Operacion");

        javax.swing.GroupLayout jd_operacionLayout = new javax.swing.GroupLayout(jd_operacion.getContentPane());
        jd_operacion.getContentPane().setLayout(jd_operacionLayout);
        jd_operacionLayout.setHorizontalGroup(
            jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_operacionLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcb_operacionVar1, 0, 115, Short.MAX_VALUE)
                    .addComponent(jcb_operacionVar2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcb_operacionRes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcb_operacionSelec, 0, 115, Short.MAX_VALUE))
                .addGap(59, 59, 59))
            .addGroup(jd_operacionLayout.createSequentialGroup()
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_operacionLayout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(btn_operacionAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jd_operacionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jd_operacionLayout.setVerticalGroup(
            jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_operacionLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_operacionSelec, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_operacionVar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_operacionVar2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_operacionRes, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(btn_operacionAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setText("int i =");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel32.setText("Condicion");

        jcb_bucleForCondicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "==", "<", "<=", ">", ">=", "!=" }));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel33.setText("Salto");

        jcb_bucleForSalto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "++", "--" }));
        jcb_bucleForSalto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_bucleForSaltoActionPerformed(evt);
            }
        });

        jcb_bucleForAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sout", "Operacion" }));
        jcb_bucleForAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_bucleForAccionActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel34.setText("Accion");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Creacion de bucle for");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel36.setText("Variable de Condicion");

        btn_bucleForAccion.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_bucleForAccion.setText("Agregar Acción");
        btn_bucleForAccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bucleForAccionMouseClicked(evt);
            }
        });

        btn_bucleForAgregar.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_bucleForAgregar.setText("Agregar For");
        btn_bucleForAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bucleForAgregarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jd_bucleForLayout = new javax.swing.GroupLayout(jd_bucleFor.getContentPane());
        jd_bucleFor.getContentPane().setLayout(jd_bucleForLayout);
        jd_bucleForLayout.setHorizontalGroup(
            jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_bucleForLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_bucleForLayout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_bucleForLayout.createSequentialGroup()
                        .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jd_bucleForLayout.createSequentialGroup()
                                .addGap(0, 2, Short.MAX_VALUE)
                                .addComponent(btn_bucleForAgregar)
                                .addGap(18, 18, 18)
                                .addComponent(btn_bucleForAccion))
                            .addGroup(jd_bucleForLayout.createSequentialGroup()
                                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jcb_bucleForCondicion, 0, 115, Short.MAX_VALUE)
                                    .addComponent(jcb_bucleForSalto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcb_bucleForAccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcb_bucleForIdx, 0, 115, Short.MAX_VALUE)
                                    .addComponent(jcb_bucleForVariableCondicion, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(59, 59, 59))))
        );
        jd_bucleForLayout.setVerticalGroup(
            jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_bucleForLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleForIdx, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleForCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleForVariableCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleForSalto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleForAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jd_bucleForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bucleForAccion)
                    .addComponent(btn_bucleForAgregar))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel37.setText("Variable 1");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel38.setText("Condicion");

        jcb_bucleWhileCondicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "==", "<", "<=", ">", ">=", "!=" }));

        jcb_bucleWhileAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sout", "Operacion" }));
        jcb_bucleWhileAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_bucleWhileAccionActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel40.setText("Accion");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Creacion de bucle while");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel42.setText("Variable de Condicion");

        btn_bucleWhileAgregar.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_bucleWhileAgregar.setText("Agregar While");
        btn_bucleWhileAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bucleWhileAgregarMouseClicked(evt);
            }
        });

        btn_bucleWhileAccion.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_bucleWhileAccion.setText("Agregar Acción");
        btn_bucleWhileAccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bucleWhileAccionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jd_bucleWhileLayout = new javax.swing.GroupLayout(jd_bucleWhile.getContentPane());
        jd_bucleWhile.getContentPane().setLayout(jd_bucleWhileLayout);
        jd_bucleWhileLayout.setHorizontalGroup(
            jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_bucleWhileLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_bucleWhileLayout.createSequentialGroup()
                        .addComponent(btn_bucleWhileAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_bucleWhileAccion)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jd_bucleWhileLayout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jd_bucleWhileLayout.createSequentialGroup()
                        .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel37)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcb_bucleWhileCondicion, 0, 115, Short.MAX_VALUE)
                            .addComponent(jcb_bucleWhileAccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcb_bucleWhileVar1, 0, 115, Short.MAX_VALUE)
                            .addComponent(jcb_bucleWhileVar2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(59, 59, 59))))
        );
        jd_bucleWhileLayout.setVerticalGroup(
            jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_bucleWhileLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleWhileVar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleWhileCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleWhileVar2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_bucleWhileAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_bucleWhileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bucleWhileAgregar)
                    .addComponent(btn_bucleWhileAccion))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel39.setText("Variable 1");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel43.setText("Condicion");

        jcb_ifCondicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "==", "<", "<=", ">", ">=", "!=" }));

        jcb_ifAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sout", "Operacion" }));
        jcb_ifAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_ifAccionActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel44.setText("Accion");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Creacion de condicion if");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel46.setText("Variable de Condicion");

        btn_ifAgregarAccion.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_ifAgregarAccion.setText("Agregar Accion");
        btn_ifAgregarAccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ifAgregarAccionMouseClicked(evt);
            }
        });

        btn_ifGuardar.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_ifGuardar.setText("Guardar If");
        btn_ifGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ifGuardarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jd_ifLayout = new javax.swing.GroupLayout(jd_if.getContentPane());
        jd_if.getContentPane().setLayout(jd_ifLayout);
        jd_ifLayout.setHorizontalGroup(
            jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_ifLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_ifLayout.createSequentialGroup()
                        .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel39)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcb_ifCondicion, 0, 115, Short.MAX_VALUE)
                            .addComponent(jcb_ifAccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcb_ifVar1, 0, 115, Short.MAX_VALUE)
                            .addComponent(jcb_ifVar2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(59, 59, 59))
                    .addGroup(jd_ifLayout.createSequentialGroup()
                        .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jd_ifLayout.createSequentialGroup()
                                .addComponent(btn_ifGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_ifAgregarAccion)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jd_ifLayout.setVerticalGroup(
            jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_ifLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_ifVar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_ifCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_ifVar2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_ifAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_ifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ifAgregarAccion)
                    .addComponent(btn_ifGuardar))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("Creacion de SOUT");

        jLabel6.setText("Texto a imprimir:");

        btn_soutGuardar.setText("Guardar");
        btn_soutGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_soutGuardarMouseClicked(evt);
            }
        });

        jta_sout.setColumns(20);
        jta_sout.setRows(5);
        jScrollPane4.setViewportView(jta_sout);

        javax.swing.GroupLayout jd_soutLayout = new javax.swing.GroupLayout(jd_sout.getContentPane());
        jd_sout.getContentPane().setLayout(jd_soutLayout);
        jd_soutLayout.setHorizontalGroup(
            jd_soutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_soutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_soutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_soutLayout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jd_soutLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jd_soutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jd_soutLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6))
                        .addContainerGap(18, Short.MAX_VALUE))))
            .addGroup(jd_soutLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(btn_soutGuardar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jd_soutLayout.setVerticalGroup(
            jd_soutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_soutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_soutGuardar)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Nombre");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Return");

        btn_agregarMetodo.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_agregarMetodo.setText("Agregar");
        btn_agregarMetodo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_agregarMetodoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_agregarMetodoMouseEntered(evt);
            }
        });

        jcb_metodoReturn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "void", "int", "float", "char", "String", "boolean" }));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Agregar Metodo");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Parametro");

        jcb_metodoAlcance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "private", "public" }));

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel50.setText("Alcance");

        btn_propiedadAgregarParametro.setText("Agregar");
        btn_propiedadAgregarParametro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_propiedadAgregarParametroMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jd_agregarMetodoLayout = new javax.swing.GroupLayout(jd_agregarMetodo.getContentPane());
        jd_agregarMetodo.getContentPane().setLayout(jd_agregarMetodoLayout);
        jd_agregarMetodoLayout.setHorizontalGroup(
            jd_agregarMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarMetodoLayout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(btn_agregarMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jd_agregarMetodoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jd_agregarMetodoLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_agregarMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addGroup(jd_agregarMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_nombreMetodo)
                    .addComponent(jcb_metodoReturn, 0, 115, Short.MAX_VALUE)
                    .addComponent(jcb_metodoAlcance, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_propiedadAgregarParametro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(59, 59, 59))
        );
        jd_agregarMetodoLayout.setVerticalGroup(
            jd_agregarMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarMetodoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_agregarMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombreMetodo))
                .addGap(18, 18, 18)
                .addGroup(jd_agregarMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_metodoReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_agregarMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcb_metodoAlcance, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jd_agregarMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_agregarMetodoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btn_propiedadAgregarParametro))
                    .addGroup(jd_agregarMetodoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btn_agregarMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel61.setText("Variable");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel62.setText("Tipo");

        jcb_metodoTipoVariable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Int", "Float", "Char", "String" }));

        btn_metodoAgregarParametro.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_metodoAgregarParametro.setText("Agregar");
        btn_metodoAgregarParametro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_metodoAgregarParametroMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_metodoAgregarParametroMouseEntered(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Agregar Parametro");

        javax.swing.GroupLayout jd_agregarParametroLayout = new javax.swing.GroupLayout(jd_agregarParametro.getContentPane());
        jd_agregarParametro.getContentPane().setLayout(jd_agregarParametroLayout);
        jd_agregarParametroLayout.setHorizontalGroup(
            jd_agregarParametroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarParametroLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_agregarParametroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addGroup(jd_agregarParametroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_metodoNombreParametro)
                    .addComponent(jcb_metodoTipoVariable, 0, 115, Short.MAX_VALUE))
                .addGap(59, 59, 59))
            .addGroup(jd_agregarParametroLayout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(btn_metodoAgregarParametro, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jd_agregarParametroLayout.setVerticalGroup(
            jd_agregarParametroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarParametroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63)
                .addGap(4, 4, 4)
                .addGroup(jd_agregarParametroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_metodoNombreParametro))
                .addGap(18, 18, 18)
                .addGroup(jd_agregarParametroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_metodoTipoVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btn_metodoAgregarParametro, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel57.setText("Nombre");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel58.setText("Tipo");

        btn_agregarPropiedad.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_agregarPropiedad.setText("Agregar");
        btn_agregarPropiedad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_agregarPropiedadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_agregarPropiedadMouseEntered(evt);
            }
        });

        jcb_tipoPropiedad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "int", "float", "char", "String" }));

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Agregar Propiedad");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel60.setText("Alcance");

        jcb_alcancePropiedad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "private", "public" }));
        jcb_alcancePropiedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_alcancePropiedadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jd_agregarPropiedadLayout = new javax.swing.GroupLayout(jd_agregarPropiedad.getContentPane());
        jd_agregarPropiedad.getContentPane().setLayout(jd_agregarPropiedadLayout);
        jd_agregarPropiedadLayout.setHorizontalGroup(
            jd_agregarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jd_agregarPropiedadLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_agregarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_agregarPropiedadLayout.createSequentialGroup()
                        .addGroup(jd_agregarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                        .addGroup(jd_agregarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nombrePropiedad)
                            .addComponent(jcb_tipoPropiedad, 0, 115, Short.MAX_VALUE)))
                    .addGroup(jd_agregarPropiedadLayout.createSequentialGroup()
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcb_alcancePropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
            .addGroup(jd_agregarPropiedadLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(btn_agregarPropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jd_agregarPropiedadLayout.setVerticalGroup(
            jd_agregarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarPropiedadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel59)
                .addGap(4, 4, 4)
                .addGroup(jd_agregarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombrePropiedad))
                .addGap(18, 18, 18)
                .addGroup(jd_agregarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_tipoPropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_agregarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_alcancePropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_agregarPropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jp_opcionesLayout = new javax.swing.GroupLayout(jp_opciones);
        jp_opciones.setLayout(jp_opcionesLayout);
        jp_opcionesLayout.setHorizontalGroup(
            jp_opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );
        jp_opcionesLayout.setVerticalGroup(
            jp_opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jta_visualDiagrama.setEditable(false);
        jta_visualDiagrama.setColumns(20);
        jta_visualDiagrama.setRows(5);
        jScrollPane2.setViewportView(jta_visualDiagrama);

        javax.swing.GroupLayout jp_diagramaLayout = new javax.swing.GroupLayout(jp_diagrama);
        jp_diagrama.setLayout(jp_diagramaLayout);
        jp_diagramaLayout.setHorizontalGroup(
            jp_diagramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_diagramaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jp_diagramaLayout.setVerticalGroup(
            jp_diagramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_diagramaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jtp_diagrama.addTab("Diagrama", jp_diagrama);

        jp_codigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jp_codigoMouseClicked(evt);
            }
        });

        jta_codigoDiagrama.setEditable(false);
        jta_codigoDiagrama.setColumns(20);
        jta_codigoDiagrama.setRows(5);
        jScrollPane3.setViewportView(jta_codigoDiagrama);

        javax.swing.GroupLayout jp_codigoLayout = new javax.swing.GroupLayout(jp_codigo);
        jp_codigo.setLayout(jp_codigoLayout);
        jp_codigoLayout.setHorizontalGroup(
            jp_codigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_codigoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jp_codigoLayout.setVerticalGroup(
            jp_codigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_codigoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jtp_diagrama.addTab("Codigo", jp_codigo);

        btn_pegar.setText("Pegar");
        btn_pegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pegarActionPerformed(evt);
            }
        });

        btn_generarCodigo.setText("Generar Codigo");
        btn_generarCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_generarCodigoMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("El codigo se genera en el orden en que se añaden figuras");

        javax.swing.GroupLayout jp_medioLayout = new javax.swing.GroupLayout(jp_medio);
        jp_medio.setLayout(jp_medioLayout);
        jp_medioLayout.setHorizontalGroup(
            jp_medioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_medioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtp_diagrama, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_medioLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(btn_pegar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_generarCodigo)
                .addGap(68, 68, 68))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_medioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp_medioLayout.setVerticalGroup(
            jp_medioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_medioLayout.createSequentialGroup()
                .addComponent(jtp_diagrama, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(jp_medioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_pegar)
                    .addComponent(btn_generarCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Bauhaus 93", 2, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Variables");
        jLabel1.setToolTipText("");

        jl_variables.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jl_variables);

        btn_agregarVariablePrincipal.setText("Agregar");
        btn_agregarVariablePrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_agregarVariablePrincipalMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jp_variablesLayout = new javax.swing.GroupLayout(jp_variables);
        jp_variables.setLayout(jp_variablesLayout);
        jp_variablesLayout.setHorizontalGroup(
            jp_variablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_variablesLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(jp_variablesLayout.createSequentialGroup()
                .addGroup(jp_variablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_variablesLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btn_agregarVariablePrincipal))
                    .addGroup(jp_variablesLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp_variablesLayout.setVerticalGroup(
            jp_variablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_variablesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_agregarVariablePrincipal)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Bauhaus 93", 2, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Opciones");
        jLabel2.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jp_umlLayout = new javax.swing.GroupLayout(jp_uml);
        jp_uml.setLayout(jp_umlLayout);
        jp_umlLayout.setHorizontalGroup(
            jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_umlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jp_opciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 539, Short.MAX_VALUE)
                .addComponent(jp_variables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jp_umlLayout.createSequentialGroup()
                    .addGap(294, 294, 294)
                    .addComponent(jp_medio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(295, Short.MAX_VALUE)))
        );
        jp_umlLayout.setVerticalGroup(
            jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_umlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_variables, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jp_umlLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jp_opciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jp_umlLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jp_medio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );

        jtp_principal.addTab("UML", jp_uml);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Clases Generadas");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Clases");
        jtr_clases.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jtr_clases.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtr_clasesMouseClicked(evt);
            }
        });

        btn_separador.setText("Separador");
        btn_separador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_separadorMouseClicked(evt);
            }
        });

        btn_nuevaClase.setText("Nueva Clase");
        btn_nuevaClase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_nuevaClaseMouseClicked(evt);
            }
        });

        btn_herencia.setText("Herencia");
        btn_herencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_herenciaMouseClicked(evt);
            }
        });

        btn_generarCodigoClases.setText("Generar Codigo");
        btn_generarCodigoClases.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_generarCodigoClasesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jp_diagramaClasesLayout = new javax.swing.GroupLayout(jp_diagramaClases);
        jp_diagramaClases.setLayout(jp_diagramaClasesLayout);
        jp_diagramaClasesLayout.setHorizontalGroup(
            jp_diagramaClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        jp_diagramaClasesLayout.setVerticalGroup(
            jp_diagramaClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );

        jtp_diagramaCodigo1.addTab("Diagrama", jp_diagramaClases);

        jta_codigoClases.setColumns(20);
        jta_codigoClases.setRows(5);
        jScrollPane8.setViewportView(jta_codigoClases);

        javax.swing.GroupLayout jp_codigoClasesLayout = new javax.swing.GroupLayout(jp_codigoClases);
        jp_codigoClases.setLayout(jp_codigoClasesLayout);
        jp_codigoClasesLayout.setHorizontalGroup(
            jp_codigoClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_codigoClasesLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jp_codigoClasesLayout.setVerticalGroup(
            jp_codigoClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_codigoClasesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jtp_diagramaCodigo1.addTab("Codigo", jp_codigoClases);

        javax.swing.GroupLayout jp_clasesLayout = new javax.swing.GroupLayout(jp_clases);
        jp_clases.setLayout(jp_clasesLayout);
        jp_clasesLayout.setHorizontalGroup(
            jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_clasesLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_clasesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_separador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_herencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_nuevaClase, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_generarCodigoClases)))
                    .addComponent(jtr_clases, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jtp_diagramaCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jp_clasesLayout.setVerticalGroup(
            jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_clasesLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtp_diagramaCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jp_clasesLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtr_clases, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_separador)
                            .addComponent(btn_nuevaClase))
                        .addGap(18, 18, 18)
                        .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_herencia)
                            .addComponent(btn_generarCodigoClases))))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jtp_principal.addTab("Clases", jp_clases);

        jm_archivo.setText("Archivo");

        jmi_guardarBinario.setText("Guardar Binario                                      ");
        jmi_guardarBinario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_guardarBinarioActionPerformed(evt);
            }
        });
        jm_archivo.add(jmi_guardarBinario);

        jmi_guardarTexto.setText("Guardar Texto                                      ");
        jmi_guardarTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_guardarTextoActionPerformed(evt);
            }
        });
        jm_archivo.add(jmi_guardarTexto);

        jmi_abrirBinario.setText("Abrir Binario                                             ");
        jmi_abrirBinario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_abrirBinarioActionPerformed(evt);
            }
        });
        jm_archivo.add(jmi_abrirBinario);

        jmi_abrirTexto.setText("Abrir Texto                                             ");
        jmi_abrirTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_abrirTextoTextoActionPerformed(evt);
            }
        });
        jm_archivo.add(jmi_abrirTexto);

        jmi_nuevo.setText("Nuevo                                           ");
        jmi_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_nuevoActionPerformed(evt);
            }
        });
        jm_archivo.add(jmi_nuevo);

        jmb_barraVentana.add(jm_archivo);

        jm_exportar.setText("Exportar");
        jm_exportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jm_exportarMouseClicked(evt);
            }
        });
        jmb_barraVentana.add(jm_exportar);

        setJMenuBar(jmb_barraVentana);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtp_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 1040, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtp_principal, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private BufferedImage capturarComponente(Component comp) {
        /*BufferedImage img = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        comp.paintAll(g2d);
        g2d.dispose();
        return img;*/
        BufferedImage img = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(java.awt.Color.WHITE);
        g2d.fillRect(0, 0, comp.getWidth(), comp.getHeight());
        comp.paint(g2d); 
        g2d.dispose();
        return img;
    }
    private void jm_exportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jm_exportarMouseClicked
        // TODO add your handling code here:
        // EXPORTAR A PDF
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Exportar Proyecto UML");

        job.setPrintable(new java.awt.print.Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {                
                if (pageIndex > 1) return NO_SUCH_PAGE;

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                int ancho = (int) pageFormat.getImageableWidth();

                g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
                g2d.setColor(java.awt.Color.BLACK);

                if (pageIndex == 0) {
                    g2d.drawString("UML", 0, 20);

                    //  UML
                    BufferedImage imgLienzoUml = capturarComponente(canvasPanel);
                    double escalaLienzo = Math.min((double) ancho / imgLienzoUml.getWidth(), 0.5);
                    int altoLienzoEscalado = (int) (imgLienzoUml.getHeight() * escalaLienzo);
                    g2d.drawImage(imgLienzoUml, 0, 30, (int)(imgLienzoUml.getWidth() * escalaLienzo), altoLienzoEscalado, null);

                    // Codigo UML
                    java.awt.image.BufferedImage imgCodigoUml = capturarComponente(jta_codigoDiagrama);
                    double escalaCodigo = Math.min((double) ancho / imgCodigoUml.getWidth(), 0.5);
                    g2d.drawImage(imgCodigoUml, 0, 40 + altoLienzoEscalado, (int)(imgCodigoUml.getWidth() * escalaCodigo), (int)(imgCodigoUml.getHeight() * escalaCodigo), null);

                } else if (pageIndex == 1) {
                    g2d.drawString("Clases Generadas", 0, 20);

                    // Clases
                    java.awt.image.BufferedImage imgLienzoClases = capturarComponente(canvasClasesPanel);
                    double escalaLienzo = Math.min((double) ancho / imgLienzoClases.getWidth(), 0.5);
                    int altoLienzoEscalado = (int) (imgLienzoClases.getHeight() * escalaLienzo);
                    g2d.drawImage(imgLienzoClases, 0, 30, (int)(imgLienzoClases.getWidth() * escalaLienzo), altoLienzoEscalado, null);

                    // Codigo de Clases
                    java.awt.image.BufferedImage imgCodigoClases = capturarComponente(jta_codigoClases); // Tu JTextArea de código
                    double escalaCodigo = Math.min((double) ancho / imgCodigoClases.getWidth(), 0.5);
                    g2d.drawImage(imgCodigoClases, 0, 40 + altoLienzoEscalado, (int)(imgCodigoClases.getWidth() * escalaCodigo), (int)(imgCodigoClases.getHeight() * escalaCodigo), null);
                }

                return PAGE_EXISTS;
            }
        });

        if (job.printDialog()) {
            try {
                job.print();
                javax.swing.JOptionPane.showMessageDialog(this, "Exportacion a PDF realizada");
            } catch (java.awt.print.PrinterException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_jm_exportarMouseClicked
    private void btn_agregarVariableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarVariableMouseClicked
        String nombre = txt_nombreVariable.getText();
        if(!(nombre.matches("^[a-zA-Z0-9_]*$")) || nombre.isEmpty() || Character.isDigit(nombre.charAt(0))) {
            JOptionPane.showMessageDialog(null, """
                                                Error, no se permiten 
                                                - Vacios
                                                - En blanco
                                                - Caracteres Especiales
                                                - Letra mayuscula inicial
                                                - Empezar con un numero
                                                """);
            txt_nombreVariable.setText("");
            return;
        } else {
            for (Variable v : variables) {
                if(v.getNombre().equals(nombre)) {
                    JOptionPane.showMessageDialog(null, "Variable ya existente");
                    txt_nombreVariable.setText("");
                    return;
                }
            }
            
            DefaultListModel modelo = (DefaultListModel) jl_variables.getModel();
            for (int i = 0; i < modelo.getSize(); i++) {
                if(modelo.getElementAt(i).toString().equals(nombre)) {
                    JOptionPane.showMessageDialog(null, "Error, ya existe una variable con ese nombre");
                    txt_nombreVariable.setText("");
                    return;
                }
            }
            String tipo = jcb_tipoVariable.getSelectedItem().toString();
            Variable variable = new Variable(nombre, tipo);
            variables.add(variable);
            modelo.addElement(variable);
            jd_agregarVariable.setVisible(false);
            txt_nombreVariable.setText("");
            
            //Añadir a jcb de operacion
            if(!variable.getTipo().equals("String") && !variable.getTipo().equals("Char")) {
                DefaultComboBoxModel modeloVariables1 =  (DefaultComboBoxModel) jcb_operacionVar1.getModel();
                modeloVariables1.addElement(variable);
                DefaultComboBoxModel modeloVariables2 =  (DefaultComboBoxModel) jcb_operacionVar2.getModel();
                modeloVariables2.addElement(variable);
                DefaultComboBoxModel modeloVariables3 =  (DefaultComboBoxModel) jcb_operacionRes.getModel();
                modeloVariables3.addElement(variable);
                
                // añadir a bucles y condicional
                if(variable.getTipo().toString().equals("Int"))  {
                    
                    // Añadir a for
                    //idx
                    DefaultComboBoxModel modeloIdxFor = (DefaultComboBoxModel) jcb_bucleForIdx.getModel();
                    modeloIdxFor.addElement(variable);
                    //condicion
                    DefaultComboBoxModel modeloVariableCondicionFor = (DefaultComboBoxModel) jcb_bucleForVariableCondicion.getModel();
                    modeloVariableCondicionFor.addElement(variable);
                    
                    //Añadir a while
                    DefaultComboBoxModel modeloVar1While = (DefaultComboBoxModel) jcb_bucleWhileVar1.getModel();
                    modeloVar1While.addElement(variable);
                    DefaultComboBoxModel modeloVar2While = (DefaultComboBoxModel) jcb_bucleWhileVar2.getModel();
                    modeloVar2While.addElement(variable);
                    
                    //Añadir a if
                    DefaultComboBoxModel modeloVar1If = (DefaultComboBoxModel) jcb_ifVar1.getModel();
                    modeloVar1If.addElement(variable);
                    DefaultComboBoxModel modeloVar2If = (DefaultComboBoxModel) jcb_ifVar2.getModel();
                    modeloVar2If.addElement(variable);
                }
            }
        }
            
    }//GEN-LAST:event_btn_agregarVariableMouseClicked
    private void btn_agregarVariablePrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarVariablePrincipalMouseClicked
        jd_agregarVariable.setVisible(true);
        jd_agregarVariable.pack();
        jd_agregarVariable.setLocationRelativeTo(null);
    }//GEN-LAST:event_btn_agregarVariablePrincipalMouseClicked
    private void btn_operacionGuardarPropiedadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_operacionGuardarPropiedadMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_operacionGuardarPropiedadMouseClicked
    private void btn_propiedadCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_propiedadCancelarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_propiedadCancelarMouseClicked
    private void btn_nuevaClaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nuevaClaseMouseClicked
        jd_crearClase.pack();
        jd_crearClase.setLocationRelativeTo(jtp_principal);
        jd_crearClase.setVisible(true);     
    }//GEN-LAST:event_btn_nuevaClaseMouseClicked
    private void btn_nombreClaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nombreClaseMouseClicked
        String nombre = txt_nombreClase.getText();
        if(nombre.isEmpty() || !(nombre.charAt(0) >= 65 && nombre.charAt(0) <= 90) || !(nombre.matches("^[a-zA-Z0-9_]*$"))) {
            JOptionPane.showMessageDialog(this, "Asegurese de que:"
                    + "\n1. No este vacio"
                    + "\n2. No tenga espacios"
                    + "\n3. Solo usar letras, números y guiones bajos (_)"
                    + "\n3. El primer caracter debe es mayuscula");
            txt_nombreClase.setText("");
        } else {
            DefaultTreeModel modeloArbol = (DefaultTreeModel) jtr_clases.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modeloArbol.getRoot();
            int cantidadClases = modeloArbol.getChildCount(root);
            String nombreCambiado = nombre;
            
            // verificar repetidos
            for (int i = 0; i < cantidadClases; i++) {
                if(root.getChildAt(i).toString().equals(nombreCambiado)) {
                    JOptionPane.showMessageDialog(this, "Esta clase ya existe");
                    txt_nombreClase.setText("");
                    return;
                }
            }
            DefaultMutableTreeNode nodoNuevaClase = new DefaultMutableTreeNode(nombreCambiado);
            root.add(nodoNuevaClase);
            nodoNuevaClase.add(new DefaultMutableTreeNode("Propiedades"));
            nodoNuevaClase.add(new DefaultMutableTreeNode("Metodos"));
            
            FiguraArbol arbolCanvas = new FiguraArbol(50, 50, nodoNuevaClase);
            nodoNuevaClase.setUserObject(arbolCanvas);
            
            figurasClases.add(arbolCanvas);
            
            ((DefaultListModel) jl_clasePadre.getModel()).addElement(arbolCanvas);
            ((DefaultListModel) jl_claseHija.getModel()).addElement(arbolCanvas);
            
            modeloArbol.reload();
            canvasClasesPanel.repaint(); 
            
            txt_nombreClase.setText("");
            jd_crearClase.setVisible(false);
        }
        
    }//GEN-LAST:event_btn_nombreClaseMouseClicked
    private void jmi_eliminarArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_eliminarArbolActionPerformed
        // Eliminar padre
        FiguraArbol figuraEliminar = (FiguraArbol) claseClickDerecho;
        for (FiguraArbol hija : figuraEliminar.getClasesHijas()) {
            hija.setClasePadre(null);
        }
        // Eliminar hijas;
        if (figuraEliminar.getClasePadre() != null) {
            figuraEliminar.getClasePadre().getClasesHijas().remove(figuraEliminar);
        }
        figurasClases.remove(figuraEliminar);
        
        DefaultTreeModel modeloArbol = (DefaultTreeModel) jtr_clases.getModel();
        modeloArbol.removeNodeFromParent(figuraEliminar.getNodoPrincipal());
        
        ((DefaultListModel) jl_clasePadre.getModel()).removeElement(figuraEliminar);
        ((DefaultListModel) jl_claseHija.getModel()).removeElement(figuraEliminar);
        
        canvasClasesPanel.repaint();
    }//GEN-LAST:event_jmi_eliminarArbolActionPerformed
    private void jtr_clasesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtr_clasesMouseClicked
        // TODO add your handling code here:
        /*
        MALOOOO ---- tiene que ser el que esta dentro del tabbedpane y no el de la izquierda
        jpm_clases.pack();
        jpm_clases.setLocation(evt.getX(), evt.getY());
        jpm_clases.setVisible(true);*/
    }//GEN-LAST:event_jtr_clasesMouseClicked
    private void btn_separadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_separadorMouseClicked
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) jtr_clases.getLastSelectedPathComponent();
        if(nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para añadir el separador");
            return;
        }
        //añadir separador
        DefaultMutableTreeNode separador = new DefaultMutableTreeNode("-------------------------------");
        
        DefaultTreeModel modeloArbol = (DefaultTreeModel) jtr_clases.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modeloArbol.getRoot();
        DefaultMutableTreeNode padre = (DefaultMutableTreeNode) nodoSeleccionado.getParent();
        if(padre == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para añadir el separador");
            return;
        }
        
        modeloArbol.insertNodeInto(separador, padre, padre.getIndex(nodoSeleccionado) + 1);
        while(nodoSeleccionado.getParent() != root) nodoSeleccionado = (DefaultMutableTreeNode) nodoSeleccionado.getParent();
        
        FiguraArbol figActualizada = (FiguraArbol) nodoSeleccionado.getUserObject();
        figActualizada.sincronizarArbolPrincipal();
        canvasClasesPanel.repaint();
        
        
       
    }//GEN-LAST:event_btn_separadorMouseClicked
    private void jmi_guardarBinarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_guardarBinarioActionPerformed
        JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fc.getSelectedFile()))) {
                    DefaultMutableTreeNode root = (DefaultMutableTreeNode) jtr_clases.getModel().getRoot();
                    DataBinario dataBin = new DataBinario(figuras, figurasClases, root);
                    oos.writeObject(dataBin);
                    JOptionPane.showMessageDialog(this, "Proyecto guardado");
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar");
                }
            }
        
    }//GEN-LAST:event_jmi_guardarBinarioActionPerformed
    private void jmi_abrirTextoTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_abrirTextoTextoActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File seleccionado = fc.getSelectedFile();
            try {
                FileInputStream fis = new FileInputStream(seleccionado);
                ObjectInputStream ois = new ObjectInputStream(fis);
                
                //Object obj = oos.readObject();
                //DataBinario db = (DataBinario) obj;
                //cargar jframe con esta data
                ois.close();
            } catch(Exception e) {
                JOptionPane.showConfirmDialog(this, "Error");
            }
        }
    }//GEN-LAST:event_jmi_abrirTextoTextoActionPerformed
    private void jmi_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_nuevoActionPerformed
        Main nuevaVentana = new Main();
        nuevaVentana.setVisible(true);
    }//GEN-LAST:event_jmi_nuevoActionPerformed
    private void jmi_cambiarTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_cambiarTextoActionPerformed
        try {
            String texto = JOptionPane.showInputDialog(this, "Ingrese el texto:");
            if(texto.isBlank() || texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No deje en blanco");
                return;
            }
            figuraClickDerecho.setTexto(texto);
            canvasPanel.repaint();
        } catch(Exception e) {
            return;
        }
    }//GEN-LAST:event_jmi_cambiarTextoActionPerformed
    private void jp_codigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jp_codigoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jp_codigoMouseClicked
    private void btn_agregarVariableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarVariableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_agregarVariableMouseEntered
    private void jmi_cambiarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_cambiarColorActionPerformed
        figuraClickDerecho.setColorFondo(JColorChooser.showDialog(this, "Seleccione Un color:", Color.white));
        canvasPanel.repaint();
    }//GEN-LAST:event_jmi_cambiarColorActionPerformed
    private void jmi_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_eliminarActionPerformed
        figuras.remove(figuraClickDerecho);
        canvasPanel.repaint();
    }//GEN-LAST:event_jmi_eliminarActionPerformed
    private void jmi_cambiarFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_cambiarFuenteActionPerformed
        jd_cambiarFuente.setVisible(true);
        jd_cambiarFuente.pack();
        jd_cambiarFuente.setLocationRelativeTo(jtp_principal);
        jcb_fuente.setSelectedItem(figuraClickDerecho.getFuente().getFamily());
        jcb_estilo.setSelectedIndex(figuraClickDerecho.getFuente().getStyle());
        jcb_tamanhoFuente.setSelectedItem(""+figuraClickDerecho.getFuente().getSize());
    }//GEN-LAST:event_jmi_cambiarFuenteActionPerformed
    private void btn_guardarFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarFuenteActionPerformed
        figuraClickDerecho.setFuente(new Font(jcb_fuente.getSelectedItem().toString(), jcb_estilo.getSelectedIndex(), Integer.parseInt(jcb_tamanhoFuente.getSelectedItem().toString())));
        jd_cambiarFuente.setVisible(false);
        jcb_fuente.setSelectedIndex(0);
        jcb_estilo.setSelectedIndex(0);
        jcb_tamanhoFuente.setSelectedIndex(3);
        canvasPanel.repaint();
    }//GEN-LAST:event_btn_guardarFuenteActionPerformed
    private void jpm_agregarPropiedadDiagramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpm_agregarPropiedadDiagramaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpm_agregarPropiedadDiagramaActionPerformed
    private void jmi_propiedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_propiedadesActionPerformed
        jd_propiedadesFigura.setVisible(true);
        jd_propiedadesFigura.pack();
        jd_propiedadesFigura.setLocationRelativeTo(jtp_principal);
        String nombre = figuraClickDerecho.getNombre();
        txt_nombrePropiedades.setText(nombre);
        txt_textoPropiedades.setText(figuraClickDerecho.getTexto());
        jp_colorFondoPropiedades.setBackground(figuraClickDerecho.getColorFondo());
        String txt = figuraClickDerecho.getTexto();
        // ENABLE => if(figuraClickDerecho)
        jcb_propiedadFuente.setSelectedItem(figuraClickDerecho.getFuente().getFamily());
        jsp_propiedadTamanho.setValue(figuraClickDerecho.getFuente().getSize());
        jsp_propiedadWidth.setValue(figuraClickDerecho.getAncho());
        jsp_propiedadHeight.setValue(figuraClickDerecho.getAlto());
    }//GEN-LAST:event_jmi_propiedadesActionPerformed
    private void btn_operacionGuardarPropiedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_operacionGuardarPropiedadActionPerformed
        figuraClickDerecho.setNombre(txt_nombrePropiedades.getText());
        figuraClickDerecho.setTexto(txt_textoPropiedades.getText());
        figuraClickDerecho.setColorFondo(jp_colorFondoPropiedades.getBackground());
        figuraClickDerecho.setFuente( new Font(jcb_propiedadFuente.getSelectedItem().toString(), 0, (int) jsp_propiedadTamanho.getValue()));
        figuraClickDerecho.setAncho((int) jsp_propiedadWidth.getValue());
        figuraClickDerecho.setAlto((int) jsp_propiedadHeight.getValue());
        jd_propiedadesFigura.setVisible(false);
        canvasPanel.repaint();
    }//GEN-LAST:event_btn_operacionGuardarPropiedadActionPerformed
    private void btn_propiedadColorFondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_propiedadColorFondoActionPerformed
        Color colorSelec = JColorChooser.showDialog(this, "Seleccione un Color", Color.white);
        jp_colorFondoPropiedades.setBackground(colorSelec);
    }//GEN-LAST:event_btn_propiedadColorFondoActionPerformed
    private void jmi_colorFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_colorFuenteActionPerformed
        lbl_previewDelTexto.setText(figuraClickDerecho.getTexto());
        lbl_previewDelTexto.setForeground(figuraClickDerecho.getColorTexto());
        lbl_previewDelTexto.setBackground(figuraClickDerecho.getColorFondo());
        jd_cambiarColorFuente.setVisible(true);
        jd_cambiarColorFuente.pack();
        jd_cambiarColorFuente.setLocationRelativeTo(jtp_principal);
    }//GEN-LAST:event_jmi_colorFuenteActionPerformed
    private void btn_seleccionarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seleccionarColorActionPerformed
        Color selec = JColorChooser.showDialog(this, "Seleccione un color", Color.yellow);
        lbl_previewDelTexto.setForeground(selec);
        canvasPanel.repaint();
    }//GEN-LAST:event_btn_seleccionarColorActionPerformed
    private void btn_cambiarColorFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cambiarColorFuenteActionPerformed
        figuraClickDerecho.setColorTexto(lbl_previewDelTexto.getForeground());
        jd_cambiarColorFuente.setVisible(false);
        canvasPanel.repaint();
    }//GEN-LAST:event_btn_cambiarColorFuenteActionPerformed
    private void jmi_copiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_copiarActionPerformed
        figuraCopiada = (figuraClickDerecho.copiar());
    }//GEN-LAST:event_jmi_copiarActionPerformed
    private void btn_pegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pegarActionPerformed
        if(figuraCopiada != null) {
            //offset
            int nuevoX = figuraCopiada.getX() + 20;
            int nuevoY = figuraCopiada.getY() + 20;
            
            Figura nuevaFigura = figuraCopiada.copiar(nuevoX, nuevoY);
            figuras.add(nuevaFigura);
            
            //evitar repeticiones si se copia la misma
            figuraCopiada = nuevaFigura;
            canvasPanel.repaint();
        } else JOptionPane.showMessageDialog(this, "Click derecho -> Copiar -> Presionar Boton Pegar");
    }//GEN-LAST:event_btn_pegarActionPerformed
    
// accion especial(jmi), bucles, condicional y sout
    private void jmi_accionEspecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_accionEspecialActionPerformed
        figuraClickDerecho.ejecutarAccionEspecial(this);
        canvasPanel.repaint();
    }//GEN-LAST:event_jmi_accionEspecialActionPerformed
    public void jd_abrirOperacion(Figura f) {
        if(variables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Crea al menos una variable");
            return;
        }
        jd_operacion.setVisible(true);
        jd_operacion.pack();
        jd_operacion.setLocationRelativeTo(jtp_principal);
    }
    private void btn_operacionAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_operacionAgregarMouseClicked
        Variable varResultado = (Variable) jcb_operacionRes.getSelectedItem();
        Variable var1 = (Variable) jcb_operacionVar1.getSelectedItem();
        Variable var2 = (Variable) jcb_operacionVar2.getSelectedItem();
        String operador = jcb_operacionSelec.getSelectedItem().toString();
        String res = varResultado.getNombre() + " = " + var1.getNombre() + " " + operador + " " + var2.getNombre() + ";";
        if (padreTemporal != null) {
            padreTemporal.getOperacionesInternas().add(res); 
            padreTemporal = null;
        } else {
            Paralelogramo p = (Paralelogramo) figuraClickDerecho;
            if(p.getOperaciones() == null) p.setOperaciones(res);
            else p.setOperaciones(p.getOperaciones() + "\n" + res);
        }
        jd_operacion.setVisible(false);
    }//GEN-LAST:event_btn_operacionAgregarMouseClicked
    private void btn_operacionAgregarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_operacionAgregarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_operacionAgregarMouseEntered
    private void jcb_operacionVar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_operacionVar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_operacionVar2ActionPerformed
    private void jcb_operacionResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_operacionResActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_operacionResActionPerformed
    private void jcb_bucleForSaltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_bucleForSaltoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_bucleForSaltoActionPerformed
    private void jcb_bucleForAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_bucleForAccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_bucleForAccionActionPerformed
    public void jd_abrirFor(Figura f) {
        if(variables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Crea al menos una variable");
            return;
        }
        jd_bucleFor.setVisible(true);
        jd_bucleFor.pack();
        jd_bucleFor.setLocationRelativeTo(jtp_principal);
    }
    private void btn_bucleForAccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bucleForAccionMouseClicked
        String opcionElegida = jcb_bucleForAccion.getSelectedItem().toString();
        padreTemporal = (Diamante) figuraClickDerecho;
        if (opcionElegida.equals("Operacion")) {
            jd_operacion.pack();
            jd_operacion.setLocationRelativeTo(this);
            jd_operacion.setVisible(true);
        } else if (opcionElegida.equals("Sout")) {
            jd_sout.pack();
            jd_sout.setLocationRelativeTo(this);
            jd_sout.setVisible(true);
        }
    }//GEN-LAST:event_btn_bucleForAccionMouseClicked
    private void jcb_bucleWhileAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_bucleWhileAccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_bucleWhileAccionActionPerformed
    public void jd_abrirWhile(Figura f) {
        if(variables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Crea al menos una variable");
            return;
        }
        jd_bucleWhile.setVisible(true);
        jd_bucleWhile.pack();
        jd_bucleWhile.setLocationRelativeTo(jtp_principal);
    }
    private void btn_bucleWhileAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bucleWhileAgregarMouseClicked
        Variable var1 = (Variable) jcb_bucleWhileVar1.getSelectedItem();
        Variable var2 = (Variable) jcb_bucleWhileVar2.getSelectedItem();
        String operador = jcb_bucleWhileCondicion.getSelectedItem().toString();
        String condicionLimpia = var1.getNombre() + " " + operador + " " + var2.getNombre();
        Diamante d = (Diamante) figuraClickDerecho;
        d.setTexto("while (" + condicionLimpia + ")"); 
        System.out.println("Condición While guardada: " + condicionLimpia);
        jd_bucleWhile.setVisible(false);
        canvasPanel.repaint();
    }//GEN-LAST:event_btn_bucleWhileAgregarMouseClicked
    private void jcb_ifAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_ifAccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_ifAccionActionPerformed
    public void jd_abrirIf(Figura f) {
        if(variables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Crea al menos una variable");
            return;
        }
        jd_if.setVisible(true);
        jd_if.pack();
        jd_if.setLocationRelativeTo(jtp_principal);
    }
    private void btn_ifAgregarAccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ifAgregarAccionMouseClicked
        String opcionElegida = jcb_ifAccion.getSelectedItem().toString();
        padreTemporal = (Diamante) figuraClickDerecho; 
        if (opcionElegida.equals("Operacion")) {
            jd_operacion.pack();
            jd_operacion.setLocationRelativeTo(this);
            jd_operacion.setVisible(true);
        } else if (opcionElegida.equals("Sout")) {
            jd_sout.pack();
            jd_sout.setLocationRelativeTo(this);
            jd_sout.setVisible(true);
        }
    }//GEN-LAST:event_btn_ifAgregarAccionMouseClicked
    public void jd_abrirSout(Figura f) {
        jd_sout.setVisible(true);
        jd_sout.pack();
        jd_sout.setLocationRelativeTo(jtp_principal);
    }
    private void btn_soutGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_soutGuardarMouseClicked
        String textoBruto = jta_sout.getText();
        String textoLimpio = textoBruto.replace("\n", "\\n");
        if (padreTemporal != null) {
            String codigoSout = "System.out.println(\"" + textoLimpio + "\");";
            padreTemporal.agregarOperacionInterna(codigoSout);
            padreTemporal = null;
        } else {
            RectanguloDoble r = (RectanguloDoble) figuraClickDerecho;
            r.setTexto("Imprimir..."); 
        }
        jd_sout.setVisible(false);
        jta_sout.setText("");
        canvasPanel.repaint();
    }//GEN-LAST:event_btn_soutGuardarMouseClicked
    private void btn_ifGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ifGuardarMouseClicked
        Variable var1 = (Variable) jcb_ifVar1.getSelectedItem();
        Variable var2 = (Variable) jcb_ifVar2.getSelectedItem();
        String operador = jcb_ifCondicion.getSelectedItem().toString();
        String condicion = var1.getNombre() + " " + operador + " " + var2.getNombre();
        figuraClickDerecho.setTexto("if (" + condicion + ")"); 
        jd_if.setVisible(false);
        canvasPanel.repaint();
    }//GEN-LAST:event_btn_ifGuardarMouseClicked
    private void btn_generarCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_generarCodigoMouseClicked
        StringBuilder codigoFinal = new StringBuilder();
        codigoFinal.append("public class MiPrograma {\n");
        codigoFinal.append("    public static void main(String[] args) {\n");
        
        boolean existeInicio = false;
        boolean existeFin = false;
        for (Figura fig : figuras) {
            if ("Inicio".equals(fig.getNombre())) existeInicio = true;
            if ("Fin".equals(fig.getNombre())) existeFin = true;
        }
        if (existeInicio) codigoFinal.append("//-----------------------------------Inicio de la Clase-----------------------------------\n\n");
        
        for (Variable v : variables) codigoFinal.append("        ").append(v.getTipo()).append(" ").append(v.getNombre()).append(";\n");
        if (!variables.isEmpty()) codigoFinal.append("\n");
        for (Figura fig : figuras) {
            if ("Inicio".equals(fig.getNombre()) || "Fin".equals(fig.getNombre())) continue;
            String lineaDeCodigo = fig.generarCodigo();
            if (lineaDeCodigo != null && !lineaDeCodigo.isEmpty()) codigoFinal.append("        ").append(lineaDeCodigo).append("\n");
        }
        if (existeFin) codigoFinal.append("\n//-------------------------------------Fin de la Clase------------------------------------//\n");
        codigoFinal.append("    }\n");
        codigoFinal.append("}\n");

        jta_codigoDiagrama.setText(codigoFinal.toString());
        jtp_diagrama.setSelectedIndex(1);
    }//GEN-LAST:event_btn_generarCodigoMouseClicked
    private void btn_bucleWhileAccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bucleWhileAccionMouseClicked
        String opcionElegida = jcb_bucleWhileAccion.getSelectedItem().toString();
        padreTemporal = (Diamante) figuraClickDerecho;
        if (opcionElegida.equals("Operacion")) {
            jd_operacion.pack();
            jd_operacion.setLocationRelativeTo(this);
            jd_operacion.setVisible(true);
        } else if (opcionElegida.equals("Sout")) {
            jd_sout.pack();
            jd_sout.setLocationRelativeTo(this);
            jd_sout.setVisible(true);
        }
    }//GEN-LAST:event_btn_bucleWhileAccionMouseClicked
    private void btn_bucleForAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bucleForAgregarMouseClicked
        Variable valorIdx = (Variable) jcb_bucleForIdx.getSelectedItem();
        Variable valorCondicion = (Variable) jcb_bucleForVariableCondicion.getSelectedItem();
        String operador = jcb_bucleForCondicion.getSelectedItem().toString();
        String salto = jcb_bucleForSalto.getSelectedItem().toString();
        String condicionLimpia = "int i = " + valorIdx.getNombre() + "; i " + operador + " " + valorCondicion.getNombre() + "; i" + salto;
        Diamante d = (Diamante) figuraClickDerecho;
        d.setTexto("for (" + condicionLimpia + ")");
        System.out.println("Condición For guardada: " + condicionLimpia);
        jd_bucleFor.setVisible(false);
        canvasPanel.repaint();
    }//GEN-LAST:event_btn_bucleForAgregarMouseClicked
    private void jmi_guardarTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_guardarTextoActionPerformed
        StringBuilder codigo = new StringBuilder();
        JFileChooser fc = new JFileChooser();
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selec = fc.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(selec, false);
                BufferedWriter bw = new BufferedWriter(fw);
                codigo.append(jta_codigoDiagrama.getText());
                bw.write(codigo.toString());
                bw.close();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            } catch (IOException ex) {
                System.out.println("Input/Output error");
            }
        }
    }//GEN-LAST:event_jmi_guardarTextoActionPerformed

    private void jmi_abrirBinarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_abrirBinarioActionPerformed
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File selec = fc.getSelectedFile();
                FileInputStream fis = new FileInputStream(selec);
                ObjectInputStream ois = new ObjectInputStream(fis);
                DataBinario dataBin = (DataBinario) ois.readObject();
                figuras = (ArrayList<Figura>) ois.readObject();
                figurasClases = (ArrayList<FiguraArbol>) ois.readObject();
                //DefaultMutableTreeNode root = (DefaultMutableTreeNode) ois.readObject();
                DefaultMutableTreeNode root = dataBin.raizArbol;
                
                jtr_clases.setModel(new DefaultTreeModel(root));
                DefaultListModel modeloPadres = new DefaultListModel();
                DefaultListModel modeloHijas = new DefaultListModel();
                for (Figura f : figurasClases) {
                    FiguraArbol fa = (FiguraArbol) f;
                    fa.reconstruirArbolVisual();
                    modeloPadres.addElement(fa);
                    modeloHijas.addElement(fa);
                }

                jl_clasePadre.setModel(modeloPadres);
                jl_claseHija.setModel(modeloHijas);
                ((DefaultListModel) jl_hijosClasePadre.getModel()).removeAllElements();

                canvasPanel.repaint();
                canvasClasesPanel.repaint();

                JOptionPane.showMessageDialog(this, "Proyecto cargado");
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al cargar");
            }
        }
    }//GEN-LAST:event_jmi_abrirBinarioActionPerformed

    private void btn_agregarMetodoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarMetodoMouseClicked
        String nombre = txt_nombreMetodo.getText();
        if(!(nombre.matches("^[a-zA-Z0-9_]*$")) || nombre.isEmpty() || Character.isDigit(nombre.charAt(0))) {
            JOptionPane.showMessageDialog(null, """
                                                Error, no se permiten 
                                                - Vacios
                                                - En blanco
                                                - Caracteres Especiales
                                                - Letra mayuscula inicial
                                                - Empezar con un numero
                                                """);
            txt_metodoNombreParametro.setText("");
            return;
        }
        String retorno = jcb_metodoReturn.getSelectedItem().toString();
        String alcance = jcb_metodoAlcance.getSelectedItem().toString();
        
        ArrayList<Variable> copiaParametros = new ArrayList<>(parametrosTemporales);
        Metodo nuevoMetodo = new Metodo(nombre, retorno, alcance, copiaParametros);
        
        FiguraArbol fig = (FiguraArbol) claseClickDerecho;
        DefaultMutableTreeNode nodoClase = fig.getNodoPrincipal();
        DefaultMutableTreeNode nodoMetodos = null;
        for (int i = 0; i < nodoClase.getChildCount(); i++) {
            DefaultMutableTreeNode hijoActual = (DefaultMutableTreeNode) nodoClase.getChildAt(i);
            if (hijoActual.getUserObject().toString().equals("Metodos")) {
                nodoMetodos = hijoActual;
                break;
            }
        }
        
        if (nodoMetodos == null) return;
        // evitar error de separador
        if (nodoMetodos.getChildCount() > 0) {
            DefaultMutableTreeNode ultimoNodo = (DefaultMutableTreeNode) nodoMetodos.getLastChild();
            if (ultimoNodo.getUserObject().toString().contains("-------------------------------")) {
                nodoMetodos = ultimoNodo;
            }
        }
        nodoMetodos.add(new DefaultMutableTreeNode(nuevoMetodo));
        DefaultTreeModel modeloArbol = (DefaultTreeModel) jtr_clases.getModel();
        modeloArbol.reload();
        
        fig.sincronizarArbolPrincipal();
        canvasClasesPanel.repaint();
        parametrosTemporales.clear();
        jd_agregarMetodo.setVisible(false);
        txt_nombreMetodo.setText("");
    }//GEN-LAST:event_btn_agregarMetodoMouseClicked
    private void btn_agregarMetodoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarMetodoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_agregarMetodoMouseEntered

    private void btn_herenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_herenciaMouseClicked
        jd_herencia.setVisible(true);
        jd_herencia.pack();
        jd_herencia.setLocationRelativeTo(jtp_principal);
    }//GEN-LAST:event_btn_herenciaMouseClicked

    private void btn_agregarPropiedadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarPropiedadMouseClicked
        String nombre = txt_nombrePropiedad.getText();
        if(!(nombre.matches("^[a-zA-Z0-9_]*$")) || nombre.isEmpty() || Character.isDigit(nombre.charAt(0)) || Character.isUpperCase(nombre.charAt(0))) {
            JOptionPane.showMessageDialog(null, """
                                                Error, no se permiten 
                                                - Vacios
                                                - En blanco
                                                - Caracteres Especiales
                                                - Letra mayuscula inicial
                                                - Empezar con un numero
                                                """);
            txt_nombrePropiedad.setText("");
            return;
        }
        String tipo = jcb_tipoPropiedad.getSelectedItem().toString();
        String alcance = jcb_alcancePropiedad.getSelectedItem().toString();
        Variable v = new Variable(nombre, tipo);
        v.setAlcance(alcance);
        
        // Añadir al arbol
        FiguraArbol fig = (FiguraArbol) claseClickDerecho;
        DefaultMutableTreeNode nodoClase = fig.getNodoPrincipal();
        DefaultMutableTreeNode nodoPropiedades = (DefaultMutableTreeNode) nodoClase.getChildAt(0);
        
        // evitar error de separador
        if (nodoPropiedades.getChildCount() > 0) {
            DefaultMutableTreeNode ultimoNodo = (DefaultMutableTreeNode) nodoPropiedades.getLastChild();
            if (ultimoNodo.getUserObject().toString().contains("-------------------------------")) {
                nodoPropiedades = ultimoNodo;
            }
        }
        nodoPropiedades.add(new DefaultMutableTreeNode(v));
        ((DefaultTreeModel) jtr_clases.getModel()).reload();
        fig.sincronizarArbolPrincipal();
        canvasClasesPanel.repaint();
        
        txt_nombrePropiedad.setText("");
        jd_agregarPropiedad.setVisible(false);
    }//GEN-LAST:event_btn_agregarPropiedadMouseClicked
    private void btn_agregarPropiedadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarPropiedadMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_agregarPropiedadMouseEntered
    
// jdialogs clase
    private void jmi_agregarPropiedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_agregarPropiedadActionPerformed
        jd_agregarPropiedad.setVisible(true);
        jd_agregarPropiedad.pack();
        jd_agregarPropiedad.setLocationRelativeTo(jtp_principal);
    }//GEN-LAST:event_jmi_agregarPropiedadActionPerformed
    private void jmi_eliminarPropiedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_eliminarPropiedadActionPerformed
        FiguraArbol fig = (FiguraArbol) claseClickDerecho;
        DefaultMutableTreeNode nodoClase = fig.getNodoPrincipal();
        DefaultMutableTreeNode nodoPropiedades = (DefaultMutableTreeNode) nodoClase.getChildAt(0);

        if (nodoPropiedades.getChildCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay propiedades para eliminar");
            return;
        }
        
        String[] opciones = new String[nodoPropiedades.getChildCount()];
        for (int i = 0; i < nodoPropiedades.getChildCount(); i++) {
            opciones[i] = nodoPropiedades.getChildAt(i).toString();
        }
        String seleccion = (String) JOptionPane.showInputDialog(this,
                "Seleccione la propiedad a eliminar:", 
                "Eliminar Propiedad", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            for (int i = 0; i < nodoPropiedades.getChildCount(); i++) {
                if (nodoPropiedades.getChildAt(i).toString().equals(seleccion)) {
                    nodoPropiedades.remove(i);
                    break;
                }
            }
            ((DefaultTreeModel) jtr_clases.getModel()).reload();
            fig.sincronizarArbolPrincipal();
            canvasClasesPanel.repaint();
        }
    }//GEN-LAST:event_jmi_eliminarPropiedadActionPerformed
    private void jmi_agregarMetodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_agregarMetodoActionPerformed
        parametrosTemporales.clear();
        jd_agregarMetodo.setVisible(true);
        jd_agregarMetodo.pack();
        jd_agregarMetodo.setLocationRelativeTo(jtp_principal);
    }//GEN-LAST:event_jmi_agregarMetodoActionPerformed
    private void jmi_eliminarMetodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_eliminarMetodoActionPerformed
        FiguraArbol fig = (FiguraArbol) claseClickDerecho;
        DefaultMutableTreeNode nodoClase = fig.getNodoPrincipal();
        DefaultMutableTreeNode nodoPropiedades = (DefaultMutableTreeNode) nodoClase.getChildAt(1);

        if (nodoPropiedades.getChildCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay metodos para eliminar");
            return;
        }
        
        String[] opciones = new String[nodoPropiedades.getChildCount()];
        for (int i = 0; i < nodoPropiedades.getChildCount(); i++) {
            opciones[i] = nodoPropiedades.getChildAt(i).toString();
        }
        String seleccion = (String) JOptionPane.showInputDialog(this,
                "Seleccione el metodo a eliminar:", 
                "Eliminar Propiedad", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            for (int i = 0; i < nodoPropiedades.getChildCount(); i++) {
                if (nodoPropiedades.getChildAt(i).toString().equals(seleccion)) {
                    nodoPropiedades.remove(i);
                    break;
                }
            }
            ((DefaultTreeModel) jtr_clases.getModel()).reload();
            fig.sincronizarArbolPrincipal();
            canvasClasesPanel.repaint();
        }
    }//GEN-LAST:event_jmi_eliminarMetodoActionPerformed

    private void btn_metodoAgregarParametroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_metodoAgregarParametroMouseClicked
        String nombre = txt_metodoNombreParametro.getText();
        if(!(nombre.matches("^[a-zA-Z0-9_]*$")) || nombre.isEmpty() || Character.isDigit(nombre.charAt(0))) {
            JOptionPane.showMessageDialog(null, """
                                                Error, no se permiten 
                                                - Vacios
                                                - En blanco
                                                - Caracteres Especiales
                                                - Letra mayuscula inicial
                                                - Empezar con un numero
                                                """);
            txt_metodoNombreParametro.setText("");
            return;
        }
        String tipo = jcb_metodoTipoVariable.getSelectedItem().toString();
        Variable nuevoParametro = new Variable(nombre, tipo);
        parametrosTemporales.add(nuevoParametro);
        jd_agregarParametro.setVisible(false);
        txt_metodoNombreParametro.setText("");
        
        
    }//GEN-LAST:event_btn_metodoAgregarParametroMouseClicked
    private void btn_metodoAgregarParametroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_metodoAgregarParametroMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_metodoAgregarParametroMouseEntered

    private void btn_propiedadAgregarParametroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_propiedadAgregarParametroMouseClicked
        jd_agregarParametro.setVisible(true);
        jd_agregarParametro.pack();
        jd_agregarParametro.setLocationRelativeTo(jtp_principal);
    }//GEN-LAST:event_btn_propiedadAgregarParametroMouseClicked

    private void btn_asignarHerenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_asignarHerenciaMouseClicked
        Object padreObj = (Object) jl_clasePadre.getSelectedValue();
        FiguraArbol padre = (FiguraArbol) padreObj;
        Object hijaObj = (Object) jl_claseHija.getSelectedValue();
        FiguraArbol hija = (FiguraArbol) hijaObj;
        System.out.println(padre);
        System.out.println(hija);
        if (padre == null || hija == null) return;
        if (padre.equals(hija)) {
            JOptionPane.showMessageDialog(this, "No puede heredar de sí misma");
            return;
        }
        
        hija.setClasePadre(padre);
        hija.setNombre(hija.getNombre());
        padre.getClasesHijas().add(hija);
        
        DefaultListModel modeloPadre = (DefaultListModel) jl_clasePadre.getModel();
        DefaultListModel modeloHija = (DefaultListModel) jl_claseHija.getModel();
        modeloPadre.removeElement(hija);
        modeloHija.removeElement(hija);
        
        canvasClasesPanel.repaint();
        jd_herencia.setVisible(false);
    }//GEN-LAST:event_btn_asignarHerenciaMouseClicked

    private void jl_clasePadreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jl_clasePadreMouseClicked
        DefaultListModel modeloLista = (DefaultListModel) jl_hijosClasePadre.getModel();
        modeloLista.removeAllElements();
        Object selecObj = (Object) jl_clasePadre.getSelectedValue();
        FiguraArbol padreSeleccionado = (FiguraArbol) selecObj;

        if (padreSeleccionado != null) {
            for (FiguraArbol hija : padreSeleccionado.getClasesHijas()) {
                modeloLista.addElement(hija);
            }
        }
    }//GEN-LAST:event_jl_clasePadreMouseClicked

    private void btn_generarCodigoClasesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_generarCodigoClasesMouseClicked
        StringBuilder codigoFinal = new StringBuilder();
        for (Figura f : figurasClases) {
            codigoFinal.append("// ---------------Inicio de la Clase---------------\n\n");
            FiguraArbol fa = (FiguraArbol) f;
            DefaultMutableTreeNode nodoClase = fa.getNodoPrincipal();
            DefaultMutableTreeNode nodoPropiedades = (DefaultMutableTreeNode) nodoClase.getChildAt(0);
            DefaultMutableTreeNode nodoMetodos = (DefaultMutableTreeNode) nodoClase.getChildAt(1);

            String textoHerencia = (fa.getClasePadre() != null) ? " extends " + fa.getClasePadre().getNombre() : "";
            codigoFinal.append("public class ").append(fa.getNombre()).append(textoHerencia).append(" {\n\n");

            for (int i = 0; i < nodoPropiedades.getChildCount(); i++) {
                String propiedadOriginal = nodoPropiedades.getChildAt(i).toString();
                int idxLimpio = propiedadOriginal.indexOf(")");
                DefaultMutableTreeNode objNodo = (DefaultMutableTreeNode) nodoPropiedades.getChildAt(i);
                Object contenido = objNodo.getUserObject();
                Variable variable = (Variable) contenido;
                String propiedadLimpia = variable.getAlcance() + " " + variable.getTipo() + " " + variable.getNombre();
                codigoFinal.append("    ").append(propiedadLimpia).append(";\n");
                if(i == nodoPropiedades.getChildCount() - 1) codigoFinal.append("\n");
            }

            for (int i = 0; i < nodoMetodos.getChildCount(); i++) {
                String metodoOriginal = nodoMetodos.getChildAt(i).toString();
                int idxLimpio = metodoOriginal.indexOf("]");
                DefaultMutableTreeNode objNodo = (DefaultMutableTreeNode) nodoMetodos.getChildAt(i);
                Object contenido = objNodo.getUserObject();
                if(contenido instanceof Metodo) {
                    Metodo metodo = (Metodo) contenido;
                    StringBuilder parametrosStr = new StringBuilder();
                    if (metodo.getParametros() != null && !metodo.getParametros().isEmpty()) {
                        for (int j = 0; j < metodo.getParametros().size(); j++) {
                            Variable param = metodo.getParametros().get(j);
                            parametrosStr.append(param.getTipo()).append(" ").append(param.getNombre());
                            if (j < metodo.getParametros().size() - 1) parametrosStr.append(", ");
                        }
                    }
                    codigoFinal.append("    ").append(metodo.getAlcance()).append(" "
                            + "").append(metodo.getTipo()).append(" "
                            + "").append(metodo.getNombre()).append("(").append(parametrosStr.toString()).append(") {");
                    codigoFinal.append("\n    }\n");
                } else if (contenido instanceof String) codigoFinal.append("    // ").append(contenido.toString()).append("\n");
            }
            codigoFinal.append("}\n\n");
            codigoFinal.append("// ---------------Fin de la Clase---------------\n\n\n\n");
        }

        jta_codigoClases.setText(codigoFinal.toString());
    }//GEN-LAST:event_btn_generarCodigoClasesMouseClicked

    private void jcb_alcancePropiedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_alcancePropiedadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_alcancePropiedadActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregarMetodo;
    private javax.swing.JButton btn_agregarPropiedad;
    private javax.swing.JButton btn_agregarVariable;
    private javax.swing.JButton btn_agregarVariablePrincipal;
    private javax.swing.JButton btn_asignarHerencia;
    private javax.swing.JButton btn_bucleForAccion;
    private javax.swing.JButton btn_bucleForAgregar;
    private javax.swing.JButton btn_bucleWhileAccion;
    private javax.swing.JButton btn_bucleWhileAgregar;
    private javax.swing.JButton btn_cambiarColorFuente;
    private javax.swing.JButton btn_generarCodigo;
    private javax.swing.JButton btn_generarCodigoClases;
    private javax.swing.JButton btn_guardarFuente;
    private javax.swing.JButton btn_herencia;
    private javax.swing.JButton btn_ifAgregarAccion;
    private javax.swing.JButton btn_ifGuardar;
    private javax.swing.JButton btn_metodoAgregarParametro;
    private javax.swing.JButton btn_nombreClase;
    private javax.swing.JButton btn_nuevaClase;
    private javax.swing.JButton btn_operacionAgregar;
    private javax.swing.JButton btn_operacionGuardarPropiedad;
    private javax.swing.JButton btn_pegar;
    private javax.swing.JButton btn_propiedadAgregarParametro;
    private javax.swing.JButton btn_propiedadCancelar;
    private javax.swing.JButton btn_propiedadColorFondo;
    private javax.swing.JToggleButton btn_propiedadEnable;
    private javax.swing.JButton btn_seleccionarColor;
    private javax.swing.JButton btn_separador;
    private javax.swing.JButton btn_soutGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JComboBox<String> jcb_alcancePropiedad;
    private javax.swing.JComboBox<String> jcb_bucleForAccion;
    private javax.swing.JComboBox<String> jcb_bucleForCondicion;
    private javax.swing.JComboBox<String> jcb_bucleForIdx;
    private javax.swing.JComboBox<String> jcb_bucleForSalto;
    private javax.swing.JComboBox<String> jcb_bucleForVariableCondicion;
    private javax.swing.JComboBox<String> jcb_bucleWhileAccion;
    private javax.swing.JComboBox<String> jcb_bucleWhileCondicion;
    private javax.swing.JComboBox<String> jcb_bucleWhileVar1;
    private javax.swing.JComboBox<String> jcb_bucleWhileVar2;
    private javax.swing.JComboBox<String> jcb_estilo;
    private javax.swing.JComboBox<String> jcb_fuente;
    private javax.swing.JComboBox<String> jcb_ifAccion;
    private javax.swing.JComboBox<String> jcb_ifCondicion;
    private javax.swing.JComboBox<String> jcb_ifVar1;
    private javax.swing.JComboBox<String> jcb_ifVar2;
    private javax.swing.JComboBox<String> jcb_metodoAlcance;
    private javax.swing.JComboBox<String> jcb_metodoReturn;
    private javax.swing.JComboBox<String> jcb_metodoTipoVariable;
    private javax.swing.JComboBox<String> jcb_operacionRes;
    private javax.swing.JComboBox<String> jcb_operacionSelec;
    private javax.swing.JComboBox<String> jcb_operacionVar1;
    private javax.swing.JComboBox<String> jcb_operacionVar2;
    private javax.swing.JComboBox<String> jcb_propiedadFuente;
    private javax.swing.JComboBox<String> jcb_tamanhoFuente;
    private javax.swing.JComboBox<String> jcb_tipoPropiedad;
    private javax.swing.JComboBox<String> jcb_tipoVariable;
    private javax.swing.JDialog jd_agregarMetodo;
    private javax.swing.JDialog jd_agregarParametro;
    private javax.swing.JDialog jd_agregarPropiedad;
    private javax.swing.JDialog jd_agregarVariable;
    private javax.swing.JDialog jd_bucleFor;
    private javax.swing.JDialog jd_bucleWhile;
    private javax.swing.JDialog jd_cambiarColorFuente;
    private javax.swing.JDialog jd_cambiarFuente;
    private javax.swing.JDialog jd_crearClase;
    private javax.swing.JDialog jd_herencia;
    private javax.swing.JDialog jd_if;
    private javax.swing.JDialog jd_operacion;
    private javax.swing.JDialog jd_propiedadesFigura;
    private javax.swing.JDialog jd_sout;
    private javax.swing.JList<String> jl_claseHija;
    private javax.swing.JList<String> jl_clasePadre;
    private javax.swing.JList<String> jl_hijosClasePadre;
    private javax.swing.JList<String> jl_variables;
    private javax.swing.JMenu jm_archivo;
    private javax.swing.JMenu jm_exportar;
    private javax.swing.JMenuBar jmb_barraVentana;
    private javax.swing.JMenuItem jmi_abrirBinario;
    private javax.swing.JMenuItem jmi_abrirTexto;
    private javax.swing.JMenuItem jmi_accionEspecial;
    private javax.swing.JMenuItem jmi_agregarMetodo;
    private javax.swing.JMenuItem jmi_agregarPropiedad;
    private javax.swing.JMenuItem jmi_cambiarColor;
    private javax.swing.JMenuItem jmi_cambiarFuente;
    private javax.swing.JMenuItem jmi_cambiarTexto;
    private javax.swing.JMenuItem jmi_colorFuente;
    private javax.swing.JMenuItem jmi_copiar;
    private javax.swing.JMenuItem jmi_eliminar;
    private javax.swing.JMenuItem jmi_eliminarArbol;
    private javax.swing.JMenuItem jmi_eliminarMetodo;
    private javax.swing.JMenuItem jmi_eliminarPropiedad;
    private javax.swing.JMenuItem jmi_guardarBinario;
    private javax.swing.JMenuItem jmi_guardarTexto;
    private javax.swing.JMenuItem jmi_nuevo;
    private javax.swing.JMenuItem jmi_propiedades;
    private javax.swing.JPanel jp_clases;
    private javax.swing.JPanel jp_codigo;
    private javax.swing.JPanel jp_codigoClases;
    private javax.swing.JPanel jp_colorFondoPropiedades;
    private javax.swing.JPanel jp_diagrama;
    private javax.swing.JPanel jp_diagramaClases;
    private javax.swing.JPanel jp_medio;
    private javax.swing.JPanel jp_opciones;
    private javax.swing.JPanel jp_uml;
    private javax.swing.JPanel jp_variables;
    private javax.swing.JMenuItem jpm_agregarPropiedadDiagrama;
    private javax.swing.JPopupMenu jpm_clases;
    private javax.swing.JPopupMenu jpm_diagramas;
    private javax.swing.JSpinner jsp_propiedadHeight;
    private javax.swing.JSpinner jsp_propiedadTamanho;
    private javax.swing.JSpinner jsp_propiedadWidth;
    private javax.swing.JTextArea jta_codigoClases;
    private javax.swing.JTextArea jta_codigoDiagrama;
    private javax.swing.JTextArea jta_sout;
    private javax.swing.JTextArea jta_visualDiagrama;
    private javax.swing.JTabbedPane jtp_diagrama;
    private javax.swing.JTabbedPane jtp_diagramaCodigo1;
    private javax.swing.JTabbedPane jtp_principal;
    private javax.swing.JTree jtr_clases;
    private javax.swing.JLabel lbl_previewDelTexto;
    private javax.swing.JTextField txt_metodoNombreParametro;
    private javax.swing.JTextField txt_nombreClase;
    private javax.swing.JTextField txt_nombreMetodo;
    private javax.swing.JTextField txt_nombrePropiedad;
    private javax.swing.JTextField txt_nombrePropiedades;
    private javax.swing.JTextField txt_nombreVariable;
    private javax.swing.JTextField txt_textoPropiedades;
    // End of variables declaration//GEN-END:variables
}
