package proyectoprogra2;
import java.awt.Graphics2D;
import java.io.Serializable;
public abstract class Figura implements Serializable {
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void dibujar(Graphics2D g2) {    }
    public boolean contiene(int mouseX, int mouseY) { 
        return mouseX >= x && mouseX <= x + ancho && mouseY >= y && mouseY <= y + alto;
    }
}
