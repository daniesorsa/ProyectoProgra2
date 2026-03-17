package proyectoprogra2;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Circulo extends Figura implements Serializable {
    public Circulo(int x, int y, int ancho, int alto) { super(x, y, ancho, alto); }

    @Override
    public void dibujar(Graphics2D g2) {
        int size = Math.min(ancho, alto);
        g2.setColor(colorFondo);
        g2.fillOval(x, y, size, size);
        g2.setColor(colorBorde);
        g2.drawOval(x, y, size, size);
        dibujarTexto(g2);
    }

    @Override
    public Figura copiar(int x, int y) {
        Circulo c = new Circulo(x, y, ancho, alto);
        copiarPropiedades(c);
        return c;
    }
}