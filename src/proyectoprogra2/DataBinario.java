package proyectoprogra2;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

public class DataBinario implements Serializable{
    private ArrayList<Figura> figurasUml = new ArrayList<Figura>();
    private ArrayList<FiguraArbol> figurasClases = new ArrayList<FiguraArbol>();
    private ArrayList<Variable> variables = new ArrayList<>();
    public DefaultMutableTreeNode raizArbol;
    

    public DataBinario(ArrayList<Figura> figurasUml, ArrayList<FiguraArbol> figurasClases,ArrayList<Variable> variable, DefaultMutableTreeNode raizArbol) {
        this.figurasUml = figurasUml;
        this.figurasClases = figurasClases;
        this.variables = variable;
        this.raizArbol = raizArbol;
    }

    public ArrayList<Figura> getFigurasUml() {
        return figurasUml;
    }

    public void setFigurasUml(ArrayList<Figura> figurasUml) {
        this.figurasUml = figurasUml;
    }

    public ArrayList<FiguraArbol> getFigurasClases() {
        return figurasClases;
    }

    public void setFigurasClases(ArrayList<FiguraArbol> figurasClases) {
        this.figurasClases = figurasClases;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariable(ArrayList<Variable> variable) {
        this.variables = variable;
    }

    public DefaultMutableTreeNode getRaizArbol() {
        return raizArbol;
    }

    public void setRaizArbol(DefaultMutableTreeNode raizArbol) {
        this.raizArbol = raizArbol;
    }
    
    
}
