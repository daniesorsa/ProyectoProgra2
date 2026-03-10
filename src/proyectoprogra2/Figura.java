package proyectoprogra2;
import java.awt.Graphics2D;
public abstract class Figura {
    protected int x;
    protected int y;
    protected int ancho;
    protected int   alto;
    protected String texto;

    public Figura(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }
    
    public void dibujar(Graphics2D g2) {    }
    public boolean contiene(int mouseX, int mouseY) { 
        return mouseX >= x && mouseX <= x + ancho && mouseY >= y && mouseY <= y + alto;
    }
}
