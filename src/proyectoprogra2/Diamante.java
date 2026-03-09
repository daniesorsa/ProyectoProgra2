package proyectoprogra2;
import java.awt.Graphics2D;
public abstract class Diamante extends Figura {
    public Diamante() {
    }
    @Override
    public void dibujar(Graphics2D g2) {
        int alto = (x+ancho/2);
        g2.drawRect(x, y, ancho, alto);
    }
}
