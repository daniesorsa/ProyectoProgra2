package proyectoprogra2;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

public class DataBinario implements Serializable{
    private ArrayList<Figura> figurasUml = new ArrayList<Figura>();
    private ArrayList<FiguraArbol> figurasClases = new ArrayList<FiguraArbol>();
    public DefaultMutableTreeNode raizArbol;

    public DataBinario(ArrayList<Figura> figurasUml, ArrayList<FiguraArbol> figurasClases, DefaultMutableTreeNode raizArbol) {
        this.figurasUml = figurasUml;
        this.figurasClases = figurasClases;
        this.raizArbol = raizArbol;
    }
    
}
