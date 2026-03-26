package proyectoprogra2;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Ovalo extends Figura implements Serializable {
    public Ovalo(int x, int y, int ancho, int alto) { super(x, y, ancho, alto); }

    @Override
    public void dibujar(Graphics2D g2) {
        g2.setColor(colorFondo);
        g2.fillOval(x, y, ancho, alto);
        g2.setColor(colorBorde);
        g2.drawOval(x, y, ancho, alto);
        dibujarTexto(g2);
    }

    @Override
    public Figura copiar(int x, int y) {
        Ovalo o = new Ovalo(x, y, ancho, alto);
        copiarPropiedades(o);
        return o;
    }

    @Override
    public Figura copiar() {
        Ovalo o = new Ovalo(x, y, ancho, alto);
        copiarPropiedades(o);
        return o;
    }

    @Override
    public String getNombreAccionEspecial() {
        if ("Inicio".equals(this.nombre)) {
            return "Configurar Inicio";
        } else if ("Fin".equals(this.nombre)) {
            return "Configurar Fin";
        }
        return null; // Si no es ni inicio ni fin, no mostramos menú especial
    }

    @Override
    public void ejecutarAccionEspecial() {
        if ("Inicio".equals(this.nombre)) {
            System.out.println("Abriendo ventana para configurar Inicio...");
            // Lógica para el Inicio
        } else if ("Fin".equals(this.nombre)) {
            System.out.println("Abriendo ventana para configurar Fin...");
            // Lógica para el Fin
        }
    }
    
}