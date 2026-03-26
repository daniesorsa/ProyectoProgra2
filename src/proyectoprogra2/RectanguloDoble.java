package proyectoprogra2;
import java.awt.Graphics2D;
import java.io.Serializable;

public class RectanguloDoble extends Figura implements Serializable {
    private String sout;
    public RectanguloDoble(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }

    public String getSout() {
        return sout;
    }

    public void setSout(String sout) {
        this.sout = sout;
    }

    @Override
    public void dibujar(Graphics2D g2) {
        g2.setColor(colorFondo);
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(colorBorde);
        g2.drawRect(x, y, ancho, alto);
        int margen = 8;
        g2.drawLine(x + margen, y, x + margen, y + alto);
        g2.drawLine(x + ancho - margen, y, x + ancho - margen, y + alto);
        dibujarTexto(g2);
    }

    @Override
    public Figura copiar(int x, int y) {
        RectanguloDoble rd = new RectanguloDoble(x, y, ancho, alto);
        copiarPropiedades(rd);
        return rd;
    }

    @Override
    public Figura copiar() {
        RectanguloDoble rd = new RectanguloDoble(x, y, ancho, alto);
        copiarPropiedades(rd);
        return rd;
    }
    @Override
    public String getNombreAccionEspecial() {
        return "Configurar Impresión (System.out)";
    }

    @Override
    public void ejecutarAccionEspecial(Main m) {
        m.jd_abrirSout(this);
    }
}