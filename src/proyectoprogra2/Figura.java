package proyectoprogra2;
import java.awt.Graphics2D;
public abstract class Figura {
    protected int x;
    protected int y;
    protected int ancho;
    protected int   alto;
    protected String texto;
    public abstract void dibujar(Graphics2D g2);
    public abstract boolean contiene(int mouseX, int mouseY); // d&d
}
