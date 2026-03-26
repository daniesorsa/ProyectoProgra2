package proyectoprogra2;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;

public class Diamante extends Figura implements Serializable {
    private String forStr;
    private String whileStr;
    private String ifStr;
    private ArrayList<String> operacionesInternas = new ArrayList<>();
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

    public String getForStr() {
        return forStr;
    }

    public void setForStr(String forStr) {
        this.forStr = forStr;
    }

    public String getWhileStr() {
        return whileStr;
    }

    public void setWhileStr(String whileStr) {
        this.whileStr = whileStr;
    }

    public String getIfStr() {
        return ifStr;
    }

    public void setIfStr(String ifStr) {
        this.ifStr = ifStr;
    }
    public void agregarOperacionInterna(String operacion) {
        this.operacionesInternas.add(operacion);
    }
    public ArrayList<String> getOperacionesInternas() {
        return operacionesInternas;
    }

    @Override
    public Figura copiar(int x, int y) {
        Diamante d = new Diamante(x, y, ancho, alto);
        copiarPropiedades(d);
        return d;
    }

    @Override
    public Figura copiar() {
        Diamante d = new Diamante(x, y, ancho, alto);
        copiarPropiedades(d);
        return d;
    }

    // Mantenemos el método SIN parámetros para respetar el contrato de Figura
    @Override
    public String getNombreAccionEspecial() {
        if ("If".equals(this.nombre)) return "Configurar  'IF'";
        else if ("While".equals(this.nombre)) return "Configurar Bucle 'While'";
        else if ("For".equals(this.nombre)) return "Configurar Bucle 'For'";
        return null;
    }

    @Override
    public void ejecutarAccionEspecial(Main m) {
        switch (nombre) {
            case "For":    m.jd_abrirFor(this); break;
            case "While": m.jd_abrirWhile(this); break;
            case "If":   m.jd_abrirIf(this);   break;
        }
    }
    @Override
    public String generarCodigo() {
        StringBuilder codigoFinal = new StringBuilder();
        if ("If".equals(this.nombre)) codigoFinal.append("if (").append(this.texto).append(") {\n");
        else if ("While".equals(this.nombre)) codigoFinal.append("while (").append(this.texto).append(") {\n");
        else if ("For".equals(this.nombre)) codigoFinal.append("for (").append(this.texto).append(") {\n");
        for (String operacionInterna : operacionesInternas) codigoFinal.append("    ").append(operacionInterna).append("\n");
        codigoFinal.append("        }\n");
        return codigoFinal.toString();
    }
}
