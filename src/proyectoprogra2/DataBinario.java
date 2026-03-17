package proyectoprogra2;

import java.io.Serializable;
import java.util.ArrayList;

public class DataBinario implements Serializable{
    private ArrayList<Figura> figuras = new ArrayList<Figura>();
    private ArrayList<Figura> variables = new ArrayList<Figura>();

    public DataBinario() {
    }

    public ArrayList<Figura> getFiguras() {
        return figuras;
    }

    public ArrayList<Figura> getVariables() {
        return variables;
    }

    public void setFiguras(ArrayList<Figura> figuras) {
        this.figuras = figuras;
    }

    public void setVariables(ArrayList<Figura> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "DataBinario{" + "figuras=" + figuras + ", variables=" + variables + '}';
    }
    
}
