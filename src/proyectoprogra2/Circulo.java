package proyectoprogra2;
import java.awt.Graphics2D;
import java.io.Serializable;
public class Circulo extends Figura implements Serializable {

    public Circulo(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }
    
    @Override
    public void dibujar(Graphics2D g2) {
        g2.drawRoundRect(x, y, alto, ancho, ancho, ancho);
    }
}
