package proyectoprogra2;
import java.awt.Graphics2D;
public abstract class Ovalo extends Figura {
    public Ovalo() {
    }
    @Override
    public void dibujar(Graphics2D g2) {
        g2.drawRoundRect(x, y, alto, ancho, ancho, ancho);
    }
}
