package proyectoprogra2;
import java.awt.Graphics2D;
public abstract class Rectangulo extends Figura {
    public Rectangulo() {
    }
    @Override
    public void dibujar(Graphics2D g2) {
        g2.drawRect(x, y, ancho, alto);
    }
}
