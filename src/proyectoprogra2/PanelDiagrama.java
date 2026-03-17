package proyectoprogra2;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JPanel;
public class PanelDiagrama extends JPanel {
    private ArrayList<Figura> figuras;
    private Figura figuraSelec = null;
    private int offsetX;
    private int offsetY;
    public PanelDiagrama(ArrayList<Figura> figuras) {
        this.figuras = figuras;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Figura f : figuras) {
                    if (f.contiene(e.getX(), e.getY())) {
                        figuraSelec = f;
                        offsetX = e.getX() - f.getX();
                        offsetY = e.getY() - f.getY();
                        break;
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                figuraSelec = null;
            }
        });

        // Detect drag
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (figuraSelec != null) {
                    figuraSelec.setX(e.getX() - offsetX);
                    figuraSelec.setY(e.getY() - offsetY);
                    repaint();
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Figura f : figuras) {
            f.dibujar(g2);
        }
    }
}
