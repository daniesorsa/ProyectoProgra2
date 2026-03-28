package proyectoprogra2;
import java.io.Serializable;
import java.util.ArrayList;
public class Metodo implements Serializable {
    private String nombre;
    private String tipo;
    private String alcance;
    private ArrayList<Variable> parametros;

    public Metodo(String nombre, String tipo, String alcance, ArrayList<Variable> parametros) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.alcance = alcance;
        this.parametros = parametros;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public ArrayList<Variable> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Variable> parametros) {
        this.parametros = parametros;
    }

    @Override
    public String toString() {
        return "[" + tipo + "]" + nombre;
    }
    
    
}
