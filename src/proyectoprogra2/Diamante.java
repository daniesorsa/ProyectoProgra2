package proyectoprogra2;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;

public class Diamante extends Figura implements Serializable {
    public Diamante(int x, int y, int ancho, int alto) { super(x, y, ancho, alto); }

    @Override
    public void dibujar(Graphics2D g2) {
        Polygon rombo = new Polygon();
        rombo.addPoint(x + ancho / 2, y);
        rombo.addPoint(x + ancho, y + alto / 2);
        rombo.addPoint(x + ancho / 2, y + alto);
        rombo.addPoint(x, y + alto / 2);
        g2.setColor(colorFondo);
        g2.fillPolygon(rombo);
        g2.setColor(colorBorde);
        g2.drawPolygon(rombo);
        dibujarTexto(g2);
    }

    @Override
    public Figura copiar(int x, int y) {
        Diamante d = new Diamante(x, y, ancho, alto);
        copiarPropiedades(d);
        return d;
    }
}