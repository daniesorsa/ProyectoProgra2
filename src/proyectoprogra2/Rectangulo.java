package proyectoprogra2;
import java.awt.Graphics2D;
public class Rectangulo extends Figura {

    public Rectangulo(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }
    
    @Override
    public void dibujar(Graphics2D g2) {
        g2.drawRect(x, y, ancho, alto);
    }
}
