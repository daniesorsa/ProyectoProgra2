package proyectoprogra2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Figura implements Serializable {
    protected int x;
    protected int y;
    protected int ancho;
    protected int alto;
    protected Font fuente = new Font("Times New Roman", 0, 14);
    protected String texto = "";
    protected String nombre = "";
    protected Color colorFondo = Color.white;
    protected Color colorBorde = Color.darkGray;
    protected Color colorTexto = Color.BLACK;

    public Figura(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAncho() {
        return ancho;
    }

    public Color getColorTexto() {
        return colorTexto;
    }
    

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public Font getFuente() {
        return fuente;
    }

    public void setFuente(Font fuente) {
        this.fuente = fuente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setColorTexto(Color colorTexto) {
        this.colorTexto = colorTexto;
    }
    

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    public Color getColorBorde() {
        return colorBorde;
    }

    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
    }
    
    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }
    

    
    public abstract void dibujar(Graphics2D g2);
    public abstract Figura copiar();
    public abstract Figura copiar(int x, int y);
    protected void copiarPropiedades(Figura original) {
        original.texto = this.texto;
        original.colorFondo = this.colorFondo;
        original.colorBorde = this.colorBorde;
        original.fuente = this.fuente;
        original.colorTexto = this.colorTexto;
    }
    protected void dibujarTexto(Graphics2D g2) {
        if (texto == null || texto.isEmpty()) return;
        g2.setFont(fuente);
        FontMetrics fm = g2.getFontMetrics();
        int tx = x + (ancho - fm.stringWidth(texto)) / 2;
        int ty = y + (alto  + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(texto, tx, ty);
    }
    public boolean contiene(int mouseX, int mouseY) { 
        return mouseX >= x && mouseX <= x + ancho && mouseY >= y && mouseY <= y + alto;
    }
}
