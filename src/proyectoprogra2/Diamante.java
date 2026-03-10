package proyectoprogra2;
import java.awt.Graphics2D;
import java.awt.Polygon;
public class Diamante extends Figura {

    public Diamante(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }
    
    @Override
    public void dibujar(Graphics2D g2) {
        Polygon rombo = new Polygon();
        rombo.addPoint(x + (ancho / 2), y); // inferior
        rombo.addPoint(x + ancho, y + (alto/2)); // derecho
        rombo.addPoint(x, y + (alto/2)); //izquierdo 
        rombo.addPoint(x + (ancho / 2), y + alto); // superior
        g2.drawPolygon(rombo);
    }
}
