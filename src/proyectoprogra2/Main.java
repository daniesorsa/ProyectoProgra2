package proyectoprogra2;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {    
    ArrayList<Variable> variables = new ArrayList<Variable>();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Main.class.getName());
    public Main() {
        initComponents();
        this.pack();
        this.setLocationRelativeTo(null);
        
        DefaultListModel modeloLista = new DefaultListModel();
        jl_variables.setModel(modeloLista);
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_agregarVariable = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_agregarVariable = new javax.swing.JButton();
        txt_nombreVariable = new javax.swing.JTextField();
        jcb_tipoVariable = new javax.swing.JComboBox<>();
        jcb_alcanceVariable = new javax.swing.JComboBox<>();
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
        jmb_barraVentana = new javax.swing.JMenuBar();
        jm_archivo = new javax.swing.JMenu();
        jmi_guardar = new javax.swing.JMenuItem();
        jmi_abrir = new javax.swing.JMenuItem();
        jmi_nuevo = new javax.swing.JMenuItem();
        jm_exportar = new javax.swing.JMenu();

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Alcance");

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

        jcb_alcanceVariable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jd_agregarVariableLayout = new javax.swing.GroupLayout(jd_agregarVariable.getContentPane());
        jd_agregarVariable.getContentPane().setLayout(jd_agregarVariableLayout);
        jd_agregarVariableLayout.setHorizontalGroup(
            jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_agregarVariableLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_nombreVariable)
                    .addComponent(jcb_tipoVariable, 0, 115, Short.MAX_VALUE)
                    .addComponent(jcb_alcanceVariable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addGroup(jd_agregarVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_alcanceVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(btn_agregarVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jp_opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jp_medio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jp_variables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jp_medio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jp_variables, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jp_opciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        /*for (int i = 0; i < nombre.length(); i++) {
            if(!((nombre.charAt(i) >= 65 &&  nombre.charAt(i) <= 90)  ||  (nombre.charAt(i) >= 97 &&  nombre.charAt(i) <= 122)) ) {
                JOptionPane.showMessageDialog(null, "Erros, ingrese un nombre sin numeros");
            }
        }*/
        String tipo = jcb_tipoVariable.getSelectedItem().toString();
        String alcance = jcb_alcanceVariable.getSelectedItem().toString();
        Variable variable = new Variable(nombre, tipo, alcance);
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
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregarVariable;
    private javax.swing.JButton btn_agregarVariables;
    private javax.swing.JButton btn_generarCodigo;
    private javax.swing.JButton btn_pegar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcb_alcanceVariable;
    private javax.swing.JComboBox<String> jcb_tipoVariable;
    private javax.swing.JDialog jd_agregarVariable;
    private javax.swing.JList<String> jl_variables;
    private javax.swing.JMenu jm_archivo;
    private javax.swing.JMenu jm_exportar;
    private javax.swing.JMenuBar jmb_barraVentana;
    private javax.swing.JMenuItem jmi_abrir;
    private javax.swing.JMenuItem jmi_guardar;
    private javax.swing.JMenuItem jmi_nuevo;
    private javax.swing.JPanel jp_codigo;
    private javax.swing.JPanel jp_diagrama;
    private javax.swing.JPanel jp_medio;
    private javax.swing.JPanel jp_opciones;
    private javax.swing.JPanel jp_variables;
    private javax.swing.JTabbedPane jtp_diagramaCodigo;
    private javax.swing.JTextField txt_nombreVariable;
    // End of variables declaration//GEN-END:variables
}
