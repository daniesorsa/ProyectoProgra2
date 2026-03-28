package proyectoprogra2;
import java.io.Serializable;
import java.util.ArrayList;

public class ClaseVisual implements Serializable{
    private String nombre;
    private ClaseVisual clasePadre;
    private ArrayList<Variable> propiedades;
    private ArrayList<Metodo> metodos;
    private ArrayList<ClaseVisual> clasesHijas = new ArrayList<>();

    public ClaseVisual(String nombre, ClaseVisual clasePadre, ArrayList<Variable> propiedades, ArrayList<Metodo> metodos) {
        this.nombre = nombre;
        this.clasePadre = clasePadre;
        this.propiedades = propiedades;
        this.metodos = metodos;
    }

    public ArrayList<ClaseVisual> getClasesHijas() {
        return clasesHijas;
    }

    public void setClasesHijas(ArrayList<ClaseVisual> clasesHijas) {
        this.clasesHijas = clasesHijas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ClaseVisual getClasePadre() {
        return clasePadre;
    }

    public void setClasePadre(ClaseVisual clasePadre) {
        this.clasePadre = clasePadre;
    }

    public ArrayList<Variable> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(ArrayList<Variable> propiedades) {
        this.propiedades = propiedades;
    }

    public ArrayList<Metodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(ArrayList<Metodo> metodos) {
        this.metodos = metodos;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
