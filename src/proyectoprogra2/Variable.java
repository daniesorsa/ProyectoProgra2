package proyectoprogra2;
public class Variable {
    private String nombre;
    private String tipo;
    private String alcance;

    public Variable(String nombre, String tipo, String alcance) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.alcance = alcance;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAlcance() {
        return alcance;
    }
    

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }
    

    @Override
    public String toString() {
        return nombre;
    }
    
}
