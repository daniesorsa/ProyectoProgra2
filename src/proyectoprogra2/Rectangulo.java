package proyectoprogra2;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Rectangulo extends Figura implements Serializable {
    public Rectangulo(int x, int y, int ancho, int alto) { super(x, y, ancho, alto); }

    @Override
    public void dibujar(Graphics2D g2) {
        g2.setColor(colorFondo);
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(colorBorde);
        g2.drawRect(x, y, ancho, alto);
        dibujarTexto(g2);
    }

    @Override
    public Figura copiar(int x, int y) {
        Rectangulo r = new Rectangulo(x, y, ancho, alto);
        copiarPropiedades(r);
        return r;
    }

    @Override
    public Figura copiar() {
        return new Rectangulo(x, y, ancho, alto);
    }
}