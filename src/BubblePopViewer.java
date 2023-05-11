import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class BubblePopViewer extends JFrame implements MouseListener, MouseMotionListener
{
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 800;
    private Image startBG;
    private Image playBG;
    private Image endBG;
    private BubblePop BP;
    private ArrayList<Bubble> bubbleColors;


    public BubblePopViewer(BubblePop BP)
    {
        this.BP = BP;

        startBG = new ImageIcon("Images/startBG.png").getImage();
        playBG = new ImageIcon("Images/playBG.png").getImage();
        endBG = new ImageIcon("Images/endBG.png").getImage();


        bubbleColors = new ArrayList<Bubble>();
        bubbleColors.add(new Bubble(0, 0,"red", new ImageIcon("Images/red.png").getImage()));
        bubbleColors.add(new Bubble(0, 0,"yellow", new ImageIcon("Images/yellow.png").getImage()));
        bubbleColors.add(new Bubble(0, 0,"green", new ImageIcon("Images/green.png").getImage()));
        bubbleColors.add(new Bubble(0, 0,"blue", new ImageIcon("Images/blue.png").getImage()));
        bubbleColors.add(new Bubble(0, 0,"purple", new ImageIcon("Images/purple.png").getImage()));

        // Register the Mouse Listener and Mouse Motion Listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("BubblePop");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void paint(Graphics g)
    {
        if(BP.getState() == BP.START_BG)
        {
            g.drawImage(startBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        }
        if(BP.getState() == BP.PLAY_BG)
        {
            g.drawImage(playBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            BP.setState(BP.PLAYING_BG);
        }
        if(BP.getState() == BP.PLAYING_BG)
        {
            g.drawImage(playBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            drawAllBubbles(g);

            g.setColor(Color.blue);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Moves left:", 30, 650);
            g.drawString("" + BP.getMovesLeft(), 55, 680);
            g.drawString("Bubbles collected:", 320, 650);
            g.drawString("" + BP.getScore(), 390, 680);

            if(BP.getCurrBubble().getX() < 170)
            {
                BP.getCurrBubble().setX(170);
            }
            else if(BP.getCurrBubble().getX() > 300)
            {
                BP.getCurrBubble().setX(300);
            }
            if(BP.getCurrBubble().getY() < 570)
            {
                BP.getCurrBubble().setY(570);
            }
            else if(BP.getCurrBubble().getY() > 700)
            {
                BP.getCurrBubble().setY(700);
            }

            BP.getCurrBubble().draw(g, this);
        }
        if (BP.getState() == BP.CLICKED)
        {
            g.drawImage(playBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            drawAllBubbles(g);
            BP.getCurrBubble().draw(g, this);
        }
        if (BP.getState() == BP.GAME_OVER)
        {
            g.drawImage(endBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            // DRAW THE SCORE
            g.setColor(Color.blue);
            g.setFont(new Font("Serif", Font.BOLD, 100));
            g.drawString("" + BP.getScore(), 230, 686);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public ArrayList<Bubble> getBubbleColors()
    {
        return bubbleColors;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(BP.getState() == BP.START_BG)
        {
            BP.setState(BP.PLAY_BG);
        }
        if(BP.getState() == BP.PLAYING_BG)
        {
            BP.getClock().start();
            BP.setState(BP.CLICKED);
            BP.getCurrBubble().setxDest(e.getX());
            BP.getCurrBubble().setyDest(e.getY());
            double Xdist = e.getX() - BP.getCurrBubble().getX();
            double Ydist = BP.getCurrBubble().getY() - e.getY();
            double angle = Math.atan(Ydist / Xdist);
            if(angle < 0)
            {
                angle += Math.toRadians(180);
            }
            double dy = BP.SPEED * Math.sin(angle);
            double dx = BP.SPEED * Math.cos(angle);
            BP.getCurrBubble().setDy(dy);
            BP.getCurrBubble().setDx(dx);
        }
        repaint();
    }

    public void drawAllBubbles(Graphics g)
    {
        for(int i = 0; i < BP.getBubbles().length; i++)
        {
            for(int j = 0; j < BP.getBubbles()[0].length; j++)
            {
                if(BP.getBubbles()[i][j] != null && BP.getBubbles()[i][j].isPopped() == false)
                {
                    BP.getBubbles()[i][j].draw(g, this);
                }
            }
        }
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
        if(BP.getState() == BP.PLAYING_BG)
        {
            BP.getCurrBubble().setX(e.getX() - (Bubble.BUBBLE_WIDTH / 2));
            BP.getCurrBubble().setY(e.getY());
        }
        repaint();
    }
}
