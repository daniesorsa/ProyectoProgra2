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
    protected String texto = "";
    protected String Nombre = "";
    protected Color colorFondo = Color.white;
    protected Color colorBorde = Color.darkGray;
    protected String fuente = "Times New Roman";
    protected int tamFuente = 14;


    public Figura(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public String getTexto() {
        return texto;
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public Color getColorBorde() {
        return colorBorde;
    }

    public String getFuente() {
        return fuente;
    }

    public int getTamFuente() {
        return tamFuente;
    }

    public String getNombre() {
        return Nombre;
    }
    

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public void setTamFuente(int tamFuente) {
        this.tamFuente = tamFuente;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    

    
    public abstract void dibujar(Graphics2D g2);
    public abstract Figura copiar(int x, int y);
    protected void copiarPropiedades(Figura dest) {
        dest.texto = this.texto;
        dest.colorFondo = this.colorFondo;
        dest.colorBorde = this.colorBorde;
        dest.fuente = this.fuente;
        dest.tamFuente = this.tamFuente;
    }
    protected void dibujarTexto(Graphics2D g2) {
        if (texto == null || texto.isEmpty()) return;
        g2.setFont(new Font(fuente, Font.PLAIN, tamFuente));
        FontMetrics fm = g2.getFontMetrics();
        int tx = x + (ancho - fm.stringWidth(texto)) / 2;
        int ty = y + (alto  + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(texto, tx, ty);
    }
    public boolean contiene(int mouseX, int mouseY) { 
        return mouseX >= x && mouseX <= x + ancho && mouseY >= y && mouseY <= y + alto;
    }
}
