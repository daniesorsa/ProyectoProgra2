package proyectoprogra2;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Main extends javax.swing.JFrame {    
    private ArrayList<Variable> variables = new ArrayList<Variable>();
    private ArrayList<Figura> figuras = new ArrayList<Figura>();
    private Figura figuraSelec = null;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Main.class.getName());
    public Main() {
        initComponents();
        this.pack();
        this.setLocationRelativeTo(null);
        figuras.add(new Rectangulo(10, 10, 80, 40));
        figuras.add(new Ovalo(10, 60, 80, 40));
        
        
        DefaultComboBoxModel modeloComboBox = new DefaultComboBoxModel();
        jcb_operacionVariable1.setModel(modeloComboBox);
        jcb_operacionVariable2.setModel(modeloComboBox);
        
        DefaultListModel modeloLista = new DefaultListModel();
        jl_variables.setModel(modeloLista);
        
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
        jd_operacion = new javax.swing.JDialog();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_operacionGuardar = new javax.swing.JButton();
        jcb_operacionVariable1 = new javax.swing.JComboBox<>();
        jcb_operacionVariable2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jcb_operacionResultado = new javax.swing.JComboBox<>();
        jcb_operacionTipo = new javax.swing.JComboBox<>();
        jd_propiedadesFigura = new javax.swing.JDialog();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_operacionGuardar1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btn_propiedadCancelar = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        btn_propiedadColor = new javax.swing.JButton();
        jcb_propiedadFuente = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jsp_propiedadTamanho = new javax.swing.JSpinner();
        jsp_propiedadWidth = new javax.swing.JSpinner();
        jsp_propiedadHeight = new javax.swing.JSpinner();
        tbtn_propiedadEnable = new javax.swing.JToggleButton();
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
        jtp_principal = new javax.swing.JTabbedPane();
        jp_uml = new javax.swing.JPanel();
        jp_opciones = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jp_medio = new javax.swing.JPanel();
        jtp_diagramaCodigo = new javax.swing.JTabbedPane();
        jp_diagrama = new javax.swing.JPanel();
        jp_codigo = new javax.swing.JPanel();
        btn_pegar = new javax.swing.JButton();
        btn_generarCodigo = new javax.swing.JButton();
        jp_variables = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jl_variables = new javax.swing.JList<>();
        btn_agregarVariables = new javax.swing.JButton();
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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Variable 2");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Operacion");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Variable 1");

        btn_operacionGuardar.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_operacionGuardar.setText("Guardar");
        btn_operacionGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_operacionGuardarMouseClicked(evt);
            }
        });

        jcb_operacionVariable1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Int", "Float", "Char", "String" }));

        jcb_operacionVariable2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Creacion de la Operacion");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Resultado");

        jcb_operacionResultado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jcb_operacionTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-", "*", "/" }));

        javax.swing.GroupLayout jd_operacionLayout = new javax.swing.GroupLayout(jd_operacion.getContentPane());
        jd_operacion.getContentPane().setLayout(jd_operacionLayout);
        jd_operacionLayout.setHorizontalGroup(
            jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_operacionLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jd_operacionLayout.createSequentialGroup()
                        .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcb_operacionVariable1, 0, 115, Short.MAX_VALUE)
                            .addComponent(jcb_operacionVariable2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcb_operacionResultado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcb_operacionTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(59, 59, 59))
            .addGroup(jd_operacionLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btn_operacionGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jd_operacionLayout.setVerticalGroup(
            jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_operacionLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_operacionTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_operacionVariable1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_operacionVariable2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_operacionResultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(btn_operacionGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Color");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Nombre");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Texto");

        btn_operacionGuardar1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_operacionGuardar1.setText("Cerrar");
        btn_operacionGuardar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_operacionGuardar1MouseClicked(evt);
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

        btn_propiedadColor.setText("Elegir Color");

        jcb_propiedadFuente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Times New Roman", "Arial", "Courier New", "Dialog", "Serif", "Monospaced", "Tahoma" }));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Tamaño");

        tbtn_propiedadEnable.setText("Enable");

        javax.swing.GroupLayout jd_propiedadesFiguraLayout = new javax.swing.GroupLayout(jd_propiedadesFigura.getContentPane());
        jd_propiedadesFigura.getContentPane().setLayout(jd_propiedadesFiguraLayout);
        jd_propiedadesFiguraLayout.setHorizontalGroup(
            jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_propiedadesFiguraLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                        .addGap(59, 59, 59))
                    .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                        .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jsp_propiedadHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jsp_propiedadTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jsp_propiedadWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2)
                                    .addComponent(btn_propiedadColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcb_propiedadFuente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tbtn_propiedadEnable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(22, 22, 22))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_propiedadesFiguraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_operacionGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(btn_propiedadCancelar)
                .addGap(74, 74, 74))
        );
        jd_propiedadesFiguraLayout.setVerticalGroup(
            jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_propiedadesFiguraLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_propiedadColor))
                .addGap(18, 18, 18)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbtn_propiedadEnable))
                .addGap(10, 10, 10)
                .addGroup(jd_propiedadesFiguraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_propiedadFuente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
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
                    .addComponent(btn_operacionGuardar1)
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Bauhaus 93", 2, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Opciones");
        jLabel2.setToolTipText("");

        javax.swing.GroupLayout jp_opcionesLayout = new javax.swing.GroupLayout(jp_opciones);
        jp_opciones.setLayout(jp_opcionesLayout);
        jp_opcionesLayout.setHorizontalGroup(
            jp_opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_opcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jp_opcionesLayout.setVerticalGroup(
            jp_opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_opcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jp_diagramaLayout = new javax.swing.GroupLayout(jp_diagrama);
        jp_diagrama.setLayout(jp_diagramaLayout);
        jp_diagramaLayout.setHorizontalGroup(
            jp_diagramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );
        jp_diagramaLayout.setVerticalGroup(
            jp_diagramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );

        jtp_diagramaCodigo.addTab("Diagrama", jp_diagrama);

        javax.swing.GroupLayout jp_codigoLayout = new javax.swing.GroupLayout(jp_codigo);
        jp_codigo.setLayout(jp_codigoLayout);
        jp_codigoLayout.setHorizontalGroup(
            jp_codigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );
        jp_codigoLayout.setVerticalGroup(
            jp_codigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );

        jtp_diagramaCodigo.addTab("Codigo", jp_codigo);

        btn_pegar.setText("Pegar");

        btn_generarCodigo.setText("Generar Codigo");

        javax.swing.GroupLayout jp_medioLayout = new javax.swing.GroupLayout(jp_medio);
        jp_medio.setLayout(jp_medioLayout);
        jp_medioLayout.setHorizontalGroup(
            jp_medioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_medioLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(btn_pegar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_generarCodigo)
                .addGap(68, 68, 68))
            .addGroup(jp_medioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtp_diagramaCodigo)
                .addContainerGap())
        );
        jp_medioLayout.setVerticalGroup(
            jp_medioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_medioLayout.createSequentialGroup()
                .addComponent(jtp_diagramaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        btn_agregarVariables.setText("Agregar");
        btn_agregarVariables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_agregarVariablesMouseClicked(evt);
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
                        .addComponent(btn_agregarVariables))
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
                .addComponent(btn_agregarVariables)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jp_umlLayout = new javax.swing.GroupLayout(jp_uml);
        jp_uml.setLayout(jp_umlLayout);
        jp_umlLayout.setHorizontalGroup(
            jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
            .addGroup(jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jp_umlLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jp_opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jp_medio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jp_variables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jp_umlLayout.setVerticalGroup(
            jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
            .addGroup(jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jp_umlLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jp_umlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jp_medio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jp_variables, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jp_opciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
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
            .addGap(0, 721, Short.MAX_VALUE)
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
            .addGap(0, 721, Short.MAX_VALUE)
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
                .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jp_clasesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_separador)
                            .addComponent(btn_herencia)
                            .addComponent(btn_generarCodigoClases))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(jp_clasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_nuevaClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_codigoUni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jtr_clases, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37)
                .addComponent(jtp_diagramaCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jmi_guardar.setText("Guardar                                       Ctrl + G");
        jm_archivo.add(jmi_guardar);

        jmi_abrir.setText("Abrir                                             Ctrl + A");
        jm_archivo.add(jmi_abrir);

        jmi_nuevo.setText("Nuevo                                           Ctrl + N");
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtp_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 1040, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtp_principal)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jm_exportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jm_exportarMouseClicked
        // TODO add your handling code here:
        // EXPORTAR A PDF
    }//GEN-LAST:event_jm_exportarMouseClicked

    private void btn_agregarVariableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarVariableMouseClicked
        // TODO add your handling code here:
        String nombre = txt_nombreVariable.getText();
        if(nombre.isBlank() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erros, no deje vacio o solo con espacio");
            return;
        }
        String tipo = jcb_tipoVariable.getSelectedItem().toString();
        Variable variable = new Variable(nombre, tipo);
        variables.add(variable);
        DefaultListModel modelo = (DefaultListModel) jl_variables.getModel();
        modelo.addElement(variable);
        jd_agregarVariable.setVisible(false);
    }//GEN-LAST:event_btn_agregarVariableMouseClicked

    private void btn_agregarVariablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_agregarVariablesMouseClicked
        // TODO add your handling code here:
        jd_agregarVariable.setVisible(true);
        jd_agregarVariable.pack();
        jd_agregarVariable.setLocationRelativeTo(null);
    }//GEN-LAST:event_btn_agregarVariablesMouseClicked

    private void btn_operacionGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_operacionGuardarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_operacionGuardarMouseClicked

    private void btn_operacionGuardar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_operacionGuardar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_operacionGuardar1MouseClicked

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
        if(nombre.isEmpty() || nombre.isBlank() || !(nombre.charAt(0) >= 65 && nombre.charAt(0) <= 90)) {
            JOptionPane.showMessageDialog(this, "Asegurese de que:"
                    + "\n1. No este vacio"
                    + "\n2. No tenga unicamentre espacios"
                    + "\n3. El primer caracter debe es mayuscula");
            txt_nombreClase.setText("");
        } else {
            DefaultTreeModel modeloArbol = (DefaultTreeModel) jtr_clases.getModel();
            DefaultMutableTreeNode nodoClase = (DefaultMutableTreeNode) modeloArbol.getRoot();
            DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(nombre);
            nodoClase.add(nuevoNodo);
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
        jpm_clases.pack();
        jpm_clases.setLocation(evt.getX(), evt.getY());
        jpm_clases.setVisible(true);
    }//GEN-LAST:event_jtr_clasesMouseClicked
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregarVariable;
    private javax.swing.JButton btn_agregarVariables;
    private javax.swing.JButton btn_codigoUni;
    private javax.swing.JButton btn_generarCodigo;
    private javax.swing.JButton btn_generarCodigoClases;
    private javax.swing.JButton btn_herencia;
    private javax.swing.JButton btn_nombreClase;
    private javax.swing.JButton btn_nuevaClase;
    private javax.swing.JButton btn_operacionGuardar;
    private javax.swing.JButton btn_operacionGuardar1;
    private javax.swing.JButton btn_pegar;
    private javax.swing.JButton btn_propiedadCancelar;
    private javax.swing.JButton btn_propiedadColor;
    private javax.swing.JButton btn_separador;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JComboBox<String> jcb_operacionResultado;
    private javax.swing.JComboBox<String> jcb_operacionTipo;
    private javax.swing.JComboBox<String> jcb_operacionVariable1;
    private javax.swing.JComboBox<String> jcb_operacionVariable2;
    private javax.swing.JComboBox<String> jcb_propiedadFuente;
    private javax.swing.JComboBox<String> jcb_tipoVariable;
    private javax.swing.JDialog jd_agregarVariable;
    private javax.swing.JDialog jd_crearClase;
    private javax.swing.JDialog jd_operacion;
    private javax.swing.JDialog jd_propiedadesFigura;
    private javax.swing.JList<String> jl_variables;
    private javax.swing.JMenu jm_archivo;
    private javax.swing.JMenu jm_exportar;
    private javax.swing.JMenuBar jmb_barraVentana;
    private javax.swing.JMenuItem jmi_abrir;
    private javax.swing.JMenuItem jmi_agregarMetodo;
    private javax.swing.JMenuItem jmi_agregarPropiedad;
    private javax.swing.JMenuItem jmi_descripcion;
    private javax.swing.JMenuItem jmi_descripcionMetodo;
    private javax.swing.JMenuItem jmi_eliminarArbol;
    private javax.swing.JMenuItem jmi_eliminarMetodo;
    private javax.swing.JMenuItem jmi_eliminarPropiedad;
    private javax.swing.JMenuItem jmi_guardar;
    private javax.swing.JMenuItem jmi_nuevo;
    private javax.swing.JPanel jp_clases;
    private javax.swing.JPanel jp_codigo;
    private javax.swing.JPanel jp_codigo1;
    private javax.swing.JPanel jp_diagrama;
    private javax.swing.JPanel jp_diagrama1;
    private javax.swing.JPanel jp_medio;
    private javax.swing.JPanel jp_opciones;
    private javax.swing.JPanel jp_uml;
    private javax.swing.JPanel jp_variables;
    private javax.swing.JPopupMenu jpm_clases;
    private javax.swing.JSpinner jsp_propiedadHeight;
    private javax.swing.JSpinner jsp_propiedadTamanho;
    private javax.swing.JSpinner jsp_propiedadWidth;
    private javax.swing.JTabbedPane jtp_diagramaCodigo;
    private javax.swing.JTabbedPane jtp_diagramaCodigo1;
    private javax.swing.JTabbedPane jtp_principal;
    private javax.swing.JTree jtr_clases;
    private javax.swing.JToggleButton tbtn_propiedadEnable;
    private javax.swing.JTextField txt_nombreClase;
    private javax.swing.JTextField txt_nombreVariable;
    // End of variables declaration//GEN-END:variables
}
