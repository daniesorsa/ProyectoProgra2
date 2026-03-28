package proyectoprogra2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class FiguraArbol extends Figura implements Serializable{
    private transient JTree arbolActual;
    private DefaultMutableTreeNode raiz;
    private DefaultMutableTreeNode nodoPrincipal;
    
    private FiguraArbol clasePadre = null;
    private ArrayList<FiguraArbol> clasesHijas = new ArrayList<>();
    public FiguraArbol(int x, int y, DefaultMutableTreeNode nodoPrincipal) {
        super(x, y, 150, 50);
        this.nodoPrincipal = nodoPrincipal;
        this.nombre = nodoPrincipal.getUserObject().toString();
        
        raiz = new DefaultMutableTreeNode(this.nombre);
        arbolActual = new JTree(new DefaultTreeModel(raiz));
        
        sincronizarArbolPrincipal();
    }

    public JTree getArbolActual() {
        return arbolActual;
    }

    public void setArbolActual(JTree arbolActual) {
        this.arbolActual = arbolActual;
    }

    public DefaultMutableTreeNode getRaiz() {
        return raiz;
    }

    public void setRaiz(DefaultMutableTreeNode raiz) {
        this.raiz = raiz;
    }

    public DefaultMutableTreeNode getNodoPrincipal() {
        return nodoPrincipal;
    }

    public void setNodoPrincipal(DefaultMutableTreeNode nodoPrincipal) {
        this.nodoPrincipal = nodoPrincipal;
    }

    public FiguraArbol getClasePadre() {
        return clasePadre;
    }

    public void setClasePadre(FiguraArbol clasePadre) {
        this.clasePadre = clasePadre;
    }

    public ArrayList<FiguraArbol> getClasesHijas() {
        return clasesHijas;
    }

    public void setClasesHijas(ArrayList<FiguraArbol> clasesHijas) {
        this.clasesHijas = clasesHijas;
    }
    
    public void sincronizarArbolPrincipal() {
        raiz.removeAllChildren();
        for (int i = 0; i < nodoPrincipal.getChildCount(); i++) raiz.add(clonarNodos((DefaultMutableTreeNode) nodoPrincipal.getChildAt(i)));
        
        ((DefaultTreeModel) arbolActual.getModel()).reload();
        for (int i = 0; i < arbolActual.getRowCount(); i++) arbolActual.expandRow(i);
        
        arbolActual.setSize(arbolActual.getPreferredSize());
        this.ancho = arbolActual.getWidth() + 20; 
        this.alto = arbolActual.getHeight() + 20;
    }

    private DefaultMutableTreeNode clonarNodos(DefaultMutableTreeNode original) {
        DefaultMutableTreeNode copia = new DefaultMutableTreeNode(original.getUserObject());
        for (int i = 0; i < original.getChildCount(); i++) {
            copia.add(clonarNodos((DefaultMutableTreeNode) original.getChildAt(i)));
        }
        return copia;
    }

    @Override
    public void dibujar(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, ancho, alto);
        g2.translate(x + 5, y + 5);
        arbolActual.paint(g2);
        g2.translate(-(x + 5), -(y + 5));
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    @Override public Figura copiar(int x, int y) {
        return null;
    }
    @Override public Figura copiar() {
        return null;
    }

    @Override
    public void ejecutarAccionEspecial(Main m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void reconstruirArbolVisual() {
        arbolActual = new JTree(new DefaultTreeModel(raiz));
        sincronizarArbolPrincipal(); 
    }
}