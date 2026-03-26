package proyectoprogra2;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;
public class Paralelogramo extends Figura implements Serializable {
    private int offset = 15; // offset horizontal

    public Paralelogramo(int x, int y, int ancho, int alto) { super(x, y, ancho, alto); }

    @Override
    public void dibujar(Graphics2D g2) {
        Polygon p = new Polygon();
        p.addPoint(x + offset, y);
        p.addPoint(x + ancho, y);
        p.addPoint(x + ancho - offset, y + alto);
        p.addPoint(x, y + alto);
        g2.setColor(colorFondo);
        g2.fillPolygon(p);
        g2.setColor(colorBorde);
        g2.drawPolygon(p);
        dibujarTexto(g2);
    }

    @Override
    public Figura copiar(int x, int y) {
        Paralelogramo par = new Paralelogramo(x, y, ancho, alto);
        copiarPropiedades(par);
        return par;
    }

    @Override
    public Figura copiar() {
        Paralelogramo par = new Paralelogramo(x, y, ancho, alto);
        copiarPropiedades(par);
        return par;
    }
    @Override
    public String getNombreAccionEspecial() {
        if ("Operacion".equals(this.nombre)) {
            return "Configurar Lectura de Datos";
        }
        return "Configurar Entrada/Salida";
    }

    @Override
    public void ejecutarAccionEspecial() {
        if ("Operacion".equals(this.nombre)) {
            System.out.println("Abriendo ventana para configurar Operación (Input/Scanner)...");
            // Lógica para solicitar al usuario la variable a leer
        }
    }
}