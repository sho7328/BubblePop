import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

// SOPHIE HO
// 5/12/23
// The BubblePopViewer class is the front-end of the game.
// It works with the BubblePop class to draw the visuals.

public class BubblePopViewer extends JFrame implements MouseListener, MouseMotionListener
{
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 800;
    private Image startBG;
    private Image playBG;
    private Image endBG;
    private Image winBG;
    private BubblePop BP;
    private ArrayList<Bubble> bubbleColors;

    // BubblePopViewer constructor defines images, sets up MouseListener and MouseMotionListener,
    // and sets up the display window
    public BubblePopViewer(BubblePop BP)
    {
        // Sync up with back-end
        this.BP = BP;

        // Define background images
        startBG = new ImageIcon("Images/startBG.png").getImage();
        playBG = new ImageIcon("Images/playBG.png").getImage();
        endBG = new ImageIcon("Images/endBG.png").getImage();
        winBG = new ImageIcon("Images/winBG.png").getImage();

        // Make an ArrayList of Bubbles that represent all the different colors
        bubbleColors = new ArrayList<Bubble>();
        bubbleColors.add(new Bubble(0, 0,"red", new ImageIcon("Images/red.png").getImage()));
        bubbleColors.add(new Bubble(0, 0,"yellow", new ImageIcon("Images/yellow.png").getImage()));
        bubbleColors.add(new Bubble(0, 0,"green", new ImageIcon("Images/green.png").getImage()));
        bubbleColors.add(new Bubble(0, 0,"blue", new ImageIcon("Images/blue.png").getImage()));
        bubbleColors.add(new Bubble(0, 0,"purple", new ImageIcon("Images/purple.png").getImage()));

        // Register the Mouse Listener and Mouse Motion Listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // Set up the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("BubblePop");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    // paint() is the method that controls all the visuals
    public void paint(Graphics g)
    {
        // If the game is starting, draw the starting background
        if(BP.getState() == BP.START_BG)
        {
            g.drawImage(startBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        }
        // Change the background to the play background when the state is changed to PLAY_BG
        if(BP.getState() == BP.PLAY_BG)
        {
            g.drawImage(playBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            BP.setState(BP.PLAYING_BG);
        }
        // Run all the playing background visuals when the state is changed to PLAYING_BG
        if(BP.getState() == BP.PLAYING_BG)
        {
            // Draw background and all valid bubbles in the 2D array
            g.drawImage(playBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            drawAllBubbles(g);

            // Display the user's number of moves left and bubbles collected
            g.setColor(Color.blue);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Moves left:", 30, 650);
            g.drawString("" + BP.getMovesLeft(), 55, 680);
            g.drawString("Bubbles collected:", 320, 650);
            g.drawString("" + BP.getScore(), 390, 680);

            // Set boundaries for where currBubble can go when following the direction of the mouse
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

            // Draw currBubble
            BP.getCurrBubble().draw(g, this);
        }
        // Once the state is CLICKED (meaning currBubble is being shot)
        if (BP.getState() == BP.CLICKED)
        {
            // Update background, draw valid bubbles, and draw currBubble
            g.drawImage(playBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            drawAllBubbles(g);
            BP.getCurrBubble().draw(g, this);
        }
        // If the user wins
        if (BP.getState() == BP.WIN_BG)
        {
            // Draw winning background
            g.drawImage(winBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.setColor(Color.blue);
            // Print user's score and how many moves it took them to get there
            g.setFont(new Font("Serif", Font.BOLD, 60));
            g.drawString("" + BP.getScore(), 210, 575);
            g.setFont(new Font("Serif", Font.BOLD, 40));
            g.drawString("" + (BP.MOVES_ALLOWED - BP.getMovesLeft()), 200, 715);

        }
        // If the user runs out of moves
        if (BP.getState() == BP.GAME_OVER)
        {
            // Draw the game over background
            g.drawImage(endBG, 0, 20, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            // Print the number of bubbles that the user was able to pop
            g.setColor(Color.blue);
            g.setFont(new Font("Serif", Font.BOLD, 100));
            g.drawString("" + BP.getScore(), 230, 686);
        }
        // Prevent any lag or glitches and make visuals look smoother when moving
        Toolkit.getDefaultToolkit().sync();
    }

    // Draws all valid bubbles
    public void drawAllBubbles(Graphics g)
    {
        // traverse 2D array
        for(int i = 0; i < BP.getBubbles().length; i++)
        {
            for(int j = 0; j < BP.getBubbles()[0].length; j++)
            {
                // If the bubble isn't null and hasn't been popped, draw it in its designated location
                if(BP.getBubbles()[i][j] != null && BP.getBubbles()[i][j].isPopped() == false)
                {
                    BP.getBubbles()[i][j].draw(g, this);
                }
            }
        }
    }

    // Getter for the ArrayList of example color bubbles (this arrList is used to randomly define
    // all the bubbles in the 2d array in BubblePop
    public ArrayList<Bubble> getBubbleColors()
    {
        return bubbleColors;
    }

    // If the user clicks:
    @Override
    public void mouseClicked(MouseEvent e) {
        // If the background is the starting background, change it to the playing background
        if(BP.getState() == BP.START_BG)
        {
            BP.setState(BP.PLAY_BG);
        }
        // If the user clicks to shoot currBubble
        if(BP.getState() == BP.PLAYING_BG)
        {
            // Start the clock/timer that triggers actionPerformed in BubblePop
            BP.getClock().start();
            // Change the state of the front-end to the clicked stage
            BP.setState(BP.CLICKED);
            // Set the destination of currBubble to the mouse's location so that
            // the bubble goes in the mouse's direction
            BP.getCurrBubble().setxDest(e.getX());
            BP.getCurrBubble().setyDest(e.getY());
            // Do some math and trig to calculate the angle of the direction of
            // the angle that the bubble needs to go in
            double Xdist = e.getX() - BP.getCurrBubble().getX();
            double Ydist = BP.getCurrBubble().getY() - e.getY();
            double angle = Math.atan(Ydist / Xdist);
            // Make sure the angle isn't negative (can't shoot the bubble downwards)
            if(angle < 0)
            {
                angle += Math.toRadians(180);
            }
            // Calculate and set the distance the bubble has to cover at a time based on the speed and angle
            double dy = BP.SPEED * Math.sin(angle);
            double dx = BP.SPEED * Math.cos(angle);
            BP.getCurrBubble().setDy(dy);
            BP.getCurrBubble().setDx(dx);
        }
        // Update front-end
        repaint();
    }

    // If the mouse is moved:
    @Override
    public void mouseMoved(MouseEvent e) {
        // If the game is in it's PLAYING_BG state
        if(BP.getState() == BP.PLAYING_BG)
        {
            // Have currBubble follow the mouse
            BP.getCurrBubble().setX(e.getX() - (Bubble.BUBBLE_WIDTH / 2));
            BP.getCurrBubble().setY(e.getY());
        }
        // Update front-end
        repaint();
    }

    // Unused mouseListener / mouseMotionListener methods
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
        return;
    }
}
