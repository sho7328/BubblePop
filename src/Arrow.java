import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Arrow implements MouseListener
{
    private final int BOTTOM_X = 160;
    private final int BOTTOM_Y = 150;
    Image arrow;

    public Arrow()
    {
        arrow = new ImageIcon("Images/BubblePop arrow.png").getImage();;
    }

    public Image getArrowImage()
    {
        return arrow;
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

    public void draw(Graphics g)
    {
        return;
    }
}
