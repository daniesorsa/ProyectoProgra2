package proyectoprogra2;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Ovalo extends Figura implements Serializable {
    public Ovalo(int x, int y, int ancho, int alto) { super(x, y, ancho, alto); }

    @Override
    public void dibujar(Graphics2D g2) {
        g2.setColor(colorFondo);
        g2.fillOval(x, y, ancho, alto);
        g2.setColor(colorBorde);
        g2.drawOval(x, y, ancho, alto);
        dibujarTexto(g2);
    }

    @Override
    public Figura copiar(int x, int y) {
        Ovalo o = new Ovalo(x, y, ancho, alto);
        copiarPropiedades(o);
        return o;
    }

    @Override
    public Figura copiar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}