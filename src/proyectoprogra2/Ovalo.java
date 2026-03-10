package proyectoprogra2;
import java.awt.Graphics2D;
public class Ovalo extends Figura {

    public Ovalo(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }
    
    @Override
    public void dibujar(Graphics2D g2) {
        g2.drawRoundRect(x, y, alto, ancho, ancho, ancho);
    }
}
