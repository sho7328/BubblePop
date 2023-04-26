import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BubblePopViewer extends JFrame implements MouseListener, MouseMotionListener
{
    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 800;
    private Image startBG;
    private Image playBG;
    private Image endBG;
    private BubblePop BP;

    public BubblePopViewer(BubblePop BP)
    {
        this.BP = BP;

        startBG = new ImageIcon("Images/BubblePop startBG.png").getImage();
        playBG = new ImageIcon("Images/BubblePop playBG.png").getImage();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("BubblePop");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void repaint(Graphics g)
    {
        g.drawImage(startBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        return;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        return;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        return;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        return;
    }
}
