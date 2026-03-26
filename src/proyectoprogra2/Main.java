package proyectoprogra2;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.DefaultBoundedRangeModel;
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
    private ArrayList<Variable> variables = new ArrayList<>();
    private ArrayList<Figura> figuras = new ArrayList<>();
    private ArrayList<Figura> plantillas = new ArrayList<>();
    private JPanel canvasPanel;
    private Figura figuraClickDerecho = null;
    private Figura figuraCopiada = null;
    public Main() {
        initComponents();
        jtp_diagrama.setSelectedComponent(jp_diagrama);
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
        ifFig.setTexto("If");
        
        RectanguloDoble sout = new RectanguloDoble(130, 150, 90, 40);
        sout.setNombre("sout");
        sout.setTexto("Sout");
        
        Diamante whileFig = new Diamante(10, 220, 90, 50);
        whileFig.setNombre("While");
        whileFig.setTexto("While");
        
        Diamante forFig = new Diamante(130, 220, 90, 50);
        forFig.setNombre("For");
        forFig.setTexto("For");
        
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
                        //offset para poder mover
                        int x = Math.max(10, canvasPanel.getWidth()  / 2 - f.getAncho() / 2)+ (figuras.size() % 6) * 15;
                        int y = Math.max(10, canvasPanel.getHeight() / 2 - f.getAlto()  / 2) + (figuras.size() % 6) * 15;
                        figuras.add(f.copiar(x, y));
                        figuras.getLast().setTexto("Lorem Ipsum");
                        figuras.getLast().setNombre(f.getNombre());
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
        jtp_diagrama.setSelectedIndex(1);
        
                
        this.pack();
        this.setLocationRelativeTo(null);
        
        //eventos para nuevo, abrir o guardar
        jmi_nuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        jmi_guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        jmi_abrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        
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
        
        //jlist
        DefaultListModel modeloLista = new DefaultListModel();
        jl_variables.setModel(modeloLista);
        
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
        jmi_eliminarArbol = new javax.swing.JMenuItem();
        jmi_eliminarPropiedad = new javax.swing.JMenuItem();
        jmi_descripcion = new javax.swing.JMenuItem();
        jmi_agregarMetodo = new javax.swing.JMenuItem();
        jmi_eliminarMetodo = new javax.swing.JMenuItem();
        jmi_descripcionMetodo = new javax.swing.JMenuItem();
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
        jtp_principal = new javax.swing.JTabbedPane();
        jp_uml = new javax.swing.JPanel();
        jp_opciones = new javax.swing.JPanel();
        jp_medio = new javax.swing.JPanel();
        jtp_diagrama = new javax.swing.JTabbedPane();
        jp_diagrama = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jta_codigoDiagrama = new javax.swing.JTextArea();
        jp_codigo = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jta_codigoDiagrama1 = new javax.swing.JTextArea();
        btn_pegar = new javax.swing.JButton();
        btn_generarCodigo = new javax.swing.JButton();
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
        btn_codigoUni = new javax.swing.JButton();
        btn_generarCodigoClases = new javax.swing.JButton();
        jtp_diagramaCodigo1 = new javax.swing.JTabbedPane();
        jp_diagrama1 = new javax.swing.JPanel();
        jp_codigo1 = new javax.swing.JPanel();
        jmb_barraVentana = new javax.swing.JMenuBar();
        jm_archivo = new javax.swing.JMenu();
        jmi_guardar = new javax.swing.JMenuItem();
        jmi_abrir = new javax.swing.JMenuItem();
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
        );
        jd_agregarVariableLayout.setVerticalGroup(
            jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarVariableLayout.createSequentialGroup()
                .addGap(42, 42, 42)
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
        jpm_clases.add(jmi_agregarPropiedad);

        jmi_eliminarArbol.setText("Eliminar Arbol");
        jmi_eliminarArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_eliminarArbolActionPerformed(evt);
            }
        });
        jpm_clases.add(jmi_eliminarArbol);

        jmi_eliminarPropiedad.setText("Eliminar Propiedad");
        jmi_eliminarPropiedad.setToolTipText("");
        jpm_clases.add(jmi_eliminarPropiedad);

        jmi_descripcion.setText("Descripcion");
        jpm_clases.add(jmi_descripcion);

        jmi_agregarMetodo.setText("Agregar Metodo");
        jpm_clases.add(jmi_agregarMetodo);

        jmi_eliminarMetodo.setText("Eliminar Metodo");
        jpm_clases.add(jmi_eliminarMetodo);

        jmi_descripcionMetodo.setText("Descripcion Metodo");
        jpm_clases.add(jmi_descripcionMetodo);

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

        javax.swing.GroupLayout jd_herenciaLayout = new javax.swing.GroupLayout(jd_herencia.getContentPane());
        jd_herencia.getContentPane().setLayout(jd_herenciaLayout);
        jd_herenciaLayout.setHorizontalGroup(
            jd_herenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 862, Short.MAX_VALUE)
        );
        jd_herenciaLayout.setVerticalGroup(
            jd_herenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
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

        jta_codigoDiagrama.setEditable(false);
        jta_codigoDiagrama.setColumns(20);
        jta_codigoDiagrama.setRows(5);
        jScrollPane2.setViewportView(jta_codigoDiagrama);

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

        jta_codigoDiagrama1.setEditable(false);
        jta_codigoDiagrama1.setColumns(20);
        jta_codigoDiagrama1.setRows(5);
        jScrollPane3.setViewportView(jta_codigoDiagrama1);

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

        javax.swing.GroupLayout jp_medioLayout = new javax.swing.GroupLayout(jp_medio);
        jp_medio.setLayout(jp_medioLayout);
        jp_medioLayout.setHorizontalGroup(
            jp_medioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_medioLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(btn_pegar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(btn_generarCodigo)
                .addGap(68, 68, 68))
            .addGroup(jp_medioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtp_diagrama, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(34, 34, 34))
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

        btn_codigoUni.setText("Codig Uni");

        btn_generarCodigoClases.setText("jButton3");

        javax.swing.GroupLayout jp_diagrama1Layout = new javax.swing.GroupLayout(jp_diagrama1);
        jp_diagrama1.setLayout(jp_diagrama1Layout);
        jp_diagrama1Layout.setHorizontalGroup(
            jp_diagrama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        jp_diagrama1Layout.setVerticalGroup(
            jp_diagrama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );

        jtp_diagramaCodigo1.addTab("Diagrama", jp_diagrama1);

        javax.swing.GroupLayout jp_codigo1Layout = new javax.swing.GroupLayout(jp_codigo1);
        jp_codigo1.setLayout(jp_codigo1Layout);
        jp_codigo1Layout.setHorizontalGroup(
            jp_codigo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        jp_codigo1Layout.setVerticalGroup(
            jp_codigo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );

        jtp_diagramaCodigo1.addTab("Codigo", jp_codigo1);

        javax.swing.GroupLayout jp_clasesLayout = new javax.swing.GroupLayout(jp_clases);
        jp_clases.setLayout(jp_clasesLayout);
        jp_clasesLayout.setHorizontalGroup(
            jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_clasesLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_clasesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_separador)
                            .addComponent(btn_herencia)
                            .addComponent(btn_generarCodigoClases))
                        .addGap(30, 30, 30)
                        .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_nuevaClase)
                            .addComponent(btn_codigoUni, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jtr_clases, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtp_diagramaCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jp_clasesLayout.setVerticalGroup(
            jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_clasesLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtp_diagramaCodigo1)
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
                            .addComponent(btn_codigoUni))
                        .addGap(18, 18, 18)
                        .addComponent(btn_generarCodigoClases)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtp_principal.addTab("Clases", jp_clases);

        jm_archivo.setText("Archivo");

        jmi_guardar.setText("Guardar                                       ");
        jmi_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_guardarActionPerformed(evt);
            }
        });
        jm_archivo.add(jmi_guardar);

        jmi_abrir.setText("Abrir                                             ");
        jmi_abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_abrirActionPerformed(evt);
            }
        });
        jm_archivo.add(jmi_abrir);

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

    private void jm_exportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jm_exportarMouseClicked
        // TODO add your handling code here:
        // EXPORTAR A PDF
    }//GEN-LAST:event_jm_exportarMouseClicked
    private void btn_agregarVariableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarVariableMouseClicked
        String nombre = txt_nombreVariable.getText();
        if(!(nombre.matches("^[a-zA-Z0-9_]*$")) || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error, no se permiten "
                    + "\n- Vacios"
                    + "\n- En blanco"
                    + "\n- Caracteres Especiales"
                    + "\n- Letra mayuscula inicial");
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
            // AGREGAR AL CODIGO: Se agrega en variable operaciones dentro de clase paralelogramo
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
        // TODO add your handling code here:
        jd_crearClase.pack();
        jd_crearClase.setLocationRelativeTo(jtp_principal);
        jd_crearClase.setVisible(true);     
    }//GEN-LAST:event_btn_nuevaClaseMouseClicked
    private void btn_nombreClaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nombreClaseMouseClicked
        // TODO add your handling code here:
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
            DefaultMutableTreeNode nodoClase = (DefaultMutableTreeNode) modeloArbol.getRoot();
            int cantidadClases = modeloArbol.getChildCount(nodoClase);
            int claseIgual = 0;
            String nombreCambiado = nombre;
            
            for (int i = 0; i < cantidadClases; i++) {
                if(nodoClase.getChildAt(i).toString().equals(nombreCambiado)) {
                    claseIgual++;
                    if(claseIgual == 1) {
                        nombreCambiado += " (" + claseIgual + ")";
                    } else {
                        for (int j = 0; j < nombreCambiado.length(); j++) {
                            if(nombreCambiado.charAt(j) == ' ') {
                                nombreCambiado = nombreCambiado.substring(0, j+1) + "(" + claseIgual + ")";
                                break;
                            }
                        }
                    }
                }
            }
            DefaultMutableTreeNode nodoNuevaClase = new DefaultMutableTreeNode(nombreCambiado);
            nodoClase.add(nodoNuevaClase);
            
            DefaultMutableTreeNode nodoPropiedades = new DefaultMutableTreeNode("Propiedades");
            DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode("Metodos");
            nodoNuevaClase.add(nodoPropiedades);
            nodoNuevaClase.add(nodoMetodos);
            modeloArbol.reload();
            txt_nombreClase.setText("");
            jd_crearClase.setVisible(false);
        }
    }//GEN-LAST:event_btn_nombreClaseMouseClicked
    private void jmi_eliminarArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_eliminarArbolActionPerformed
        // TODO add your handling code here:
        DefaultTreeModel modeloArbol = (DefaultTreeModel) jtr_clases.getModel();
        DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) modeloArbol.getRoot();
        int confirmarInt = JOptionPane.showConfirmDialog(this, "Realizar eliminacion de arbol?", "Confirmacion", JOptionPane.YES_NO_OPTION);
        boolean confirmar = confirmarInt == JOptionPane.YES_OPTION;
        if(confirmar) {
            raiz.removeAllChildren();
            modeloArbol.reload();
        }
        else return;
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
        // TODO add your handling code here:
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) jtr_clases.getLastSelectedPathComponent();
        if(nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para añadir el separador");
            return;
        }
        //añadir separador
        DefaultMutableTreeNode separador = new DefaultMutableTreeNode("-------------------------------");
        
        DefaultTreeModel modeloArbol = (DefaultTreeModel) jtr_clases.getModel();
        DefaultMutableTreeNode padre = (DefaultMutableTreeNode) nodoSeleccionado.getParent();
        if(padre == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para añadir el separador");
            return;
        }
        modeloArbol.insertNodeInto(separador, padre, padre.getIndex(nodoSeleccionado) + 1);
        
        //padre.add(padre);
        
       
    }//GEN-LAST:event_btn_separadorMouseClicked
    private void jmi_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_guardarActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File seleccionado = fc.getSelectedFile();
            try {
                FileOutputStream fos = new FileOutputStream(seleccionado);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                //oos.writeObject();
                oos.close();
            } catch(Exception e) {
                JOptionPane.showConfirmDialog(this, "Error");
            }
        }
        
    }//GEN-LAST:event_jmi_guardarActionPerformed
    private void jmi_abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_abrirActionPerformed
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
    }//GEN-LAST:event_jmi_abrirActionPerformed
    private void jmi_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_nuevoActionPerformed
        // TODO add your handling code here:
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
    private void jmi_accionEspecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_accionEspecialActionPerformed
        figuraClickDerecho.ejecutarAccionEspecial(this);
        canvasPanel.repaint();
    }//GEN-LAST:event_jmi_accionEspecialActionPerformed

    private void btn_operacionAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_operacionAgregarMouseClicked
        Variable varResultado = (Variable) jcb_operacionRes.getSelectedItem();
        Variable var1 = (Variable) jcb_operacionVar1.getSelectedItem();
        Variable var2 = (Variable) jcb_operacionVar2.getSelectedItem();
        String operador = jcb_operacionSelec.getSelectedItem().toString();
        String res = varResultado.getNombre() + " = " + var1.getNombre() + " " + operador + " " + var2.getNombre() + ";";
        //System.out.println("RES: " + res);
        Paralelogramo p = (Paralelogramo) figuraClickDerecho;
        if(p.getOperaciones() == null) p.setOperaciones(res);
        else p.setOperaciones(p.getOperaciones() + "\n" + res);
        //System.out.println("OP:\n" + p.getOperaciones());
        // \n se puede usar como delimitador
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
    
    public void jd_abrirOperacion(Figura f) {
        if(variables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Crea al menos una variable");
            return;
        }
        jd_operacion.setVisible(true);
        jd_operacion.pack();
        jd_operacion.setLocationRelativeTo(jtp_principal);
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregarVariable;
    private javax.swing.JButton btn_agregarVariablePrincipal;
    private javax.swing.JButton btn_cambiarColorFuente;
    private javax.swing.JButton btn_codigoUni;
    private javax.swing.JButton btn_generarCodigo;
    private javax.swing.JButton btn_generarCodigoClases;
    private javax.swing.JButton btn_guardarFuente;
    private javax.swing.JButton btn_herencia;
    private javax.swing.JButton btn_nombreClase;
    private javax.swing.JButton btn_nuevaClase;
    private javax.swing.JButton btn_operacionAgregar;
    private javax.swing.JButton btn_operacionGuardarPropiedad;
    private javax.swing.JButton btn_pegar;
    private javax.swing.JButton btn_propiedadCancelar;
    private javax.swing.JButton btn_propiedadColorFondo;
    private javax.swing.JToggleButton btn_propiedadEnable;
    private javax.swing.JButton btn_seleccionarColor;
    private javax.swing.JButton btn_separador;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> jcb_estilo;
    private javax.swing.JComboBox<String> jcb_fuente;
    private javax.swing.JComboBox<String> jcb_operacionRes;
    private javax.swing.JComboBox<String> jcb_operacionSelec;
    private javax.swing.JComboBox<String> jcb_operacionVar1;
    private javax.swing.JComboBox<String> jcb_operacionVar2;
    private javax.swing.JComboBox<String> jcb_propiedadFuente;
    private javax.swing.JComboBox<String> jcb_tamanhoFuente;
    private javax.swing.JComboBox<String> jcb_tipoVariable;
    private javax.swing.JDialog jd_agregarVariable;
    private javax.swing.JDialog jd_cambiarColorFuente;
    private javax.swing.JDialog jd_cambiarFuente;
    private javax.swing.JDialog jd_crearClase;
    private javax.swing.JDialog jd_herencia;
    private javax.swing.JDialog jd_operacion;
    private javax.swing.JDialog jd_propiedadesFigura;
    private javax.swing.JList<String> jl_variables;
    private javax.swing.JMenu jm_archivo;
    private javax.swing.JMenu jm_exportar;
    private javax.swing.JMenuBar jmb_barraVentana;
    private javax.swing.JMenuItem jmi_abrir;
    private javax.swing.JMenuItem jmi_accionEspecial;
    private javax.swing.JMenuItem jmi_agregarMetodo;
    private javax.swing.JMenuItem jmi_agregarPropiedad;
    private javax.swing.JMenuItem jmi_cambiarColor;
    private javax.swing.JMenuItem jmi_cambiarFuente;
    private javax.swing.JMenuItem jmi_cambiarTexto;
    private javax.swing.JMenuItem jmi_colorFuente;
    private javax.swing.JMenuItem jmi_copiar;
    private javax.swing.JMenuItem jmi_descripcion;
    private javax.swing.JMenuItem jmi_descripcionMetodo;
    private javax.swing.JMenuItem jmi_eliminar;
    private javax.swing.JMenuItem jmi_eliminarArbol;
    private javax.swing.JMenuItem jmi_eliminarMetodo;
    private javax.swing.JMenuItem jmi_eliminarPropiedad;
    private javax.swing.JMenuItem jmi_guardar;
    private javax.swing.JMenuItem jmi_nuevo;
    private javax.swing.JMenuItem jmi_propiedades;
    private javax.swing.JPanel jp_clases;
    private javax.swing.JPanel jp_codigo;
    private javax.swing.JPanel jp_codigo1;
    private javax.swing.JPanel jp_colorFondoPropiedades;
    private javax.swing.JPanel jp_diagrama;
    private javax.swing.JPanel jp_diagrama1;
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
    private javax.swing.JTextArea jta_codigoDiagrama;
    private javax.swing.JTextArea jta_codigoDiagrama1;
    private javax.swing.JTabbedPane jtp_diagrama;
    private javax.swing.JTabbedPane jtp_diagramaCodigo1;
    private javax.swing.JTabbedPane jtp_principal;
    private javax.swing.JTree jtr_clases;
    private javax.swing.JLabel lbl_previewDelTexto;
    private javax.swing.JTextField txt_nombreClase;
    private javax.swing.JTextField txt_nombrePropiedades;
    private javax.swing.JTextField txt_nombreVariable;
    private javax.swing.JTextField txt_textoPropiedades;
    // End of variables declaration//GEN-END:variables
}
