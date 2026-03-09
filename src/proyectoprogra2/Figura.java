package proyectoprogra2;
import java.awt.Graphics2D;
public abstract class Figura {
    protected int x, y, ancho, alto;
    protected String texto;
    public abstract void dibujar(Graphics2D g2);
    public abstract boolean contiene(int mouseX, int mouseY); // d&d
}
