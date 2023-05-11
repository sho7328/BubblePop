import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BubblePop implements ActionListener {
    private Bubble[][] bubbles;
    private Bubble currBubble;
    private int score;
    private final int MOVES_ALLOWED = 35;
    private BubblePopViewer BPV;
    private Timer clock;
    private boolean gameOver;
    private int numCurrs;
    public static final int SPEED = 25;
    public static final int START_BG = 1;
    public static final int PLAY_BG = 2;
    public static final int PLAYING_BG = 3;
    public static final int CLICKED = 4;
    public static final int GAME_OVER = 5;
    private static int state = START_BG;
    private static final int DELAY = 100;

    public BubblePop()
    {
        gameOver = false;
        score = 0;
        BPV = new BubblePopViewer(this);
        clock = new Timer(DELAY, this);

        // create bubbles 2d array
        bubbles = new Bubble[18][15];
        int bx = 3;
        int by = 30;
        for(int i = 0; i < bubbles.length; i++)
        {
            int randNum = (int)(Math.random() * 5);
            int a = (int)(Math.random() * 20);
            for (int j = 0; j < bubbles[0].length; j++)
            {
                if (a >= j)
                {
                    randNum = (int)(Math.random() * 5);
                }
                if(i > 9)
                {
                    bubbles[i][j] = null;
                }
                else
                {
                    bubbles[i][j] = new Bubble(bx, by, BPV.getBubbleColors().get(randNum).getColor(), BPV.getBubbleColors().get(randNum).getImg());
                    bx += Bubble.BUBBLE_WIDTH;
                }
            }
            bx = 3;
            by += Bubble.BUBBLE_HEIGHT;
        }
        int randNum = (int)(Math.random() * 5);
        currBubble = new Bubble(250, 700, BPV.getBubbleColors().get(randNum).getColor(), BPV.getBubbleColors().get(randNum).getImg());
        numCurrs = 1;
    }

    public void newCurrBubble()
    {
        if(numCurrs < MOVES_ALLOWED)
        {
            int randNum = (int)(Math.random() * 5);
            currBubble = new Bubble(250, 700, BPV.getBubbleColors().get(randNum).getColor(), BPV.getBubbleColors().get(randNum).getImg());
            numCurrs++;
            state = PLAYING_BG;
        }
        else
        {
            setGameOver(true);
        }
    }

    public Timer getClock() {
        return clock;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        if(gameOver == true)
        {
            state = GAME_OVER;
        }
    }

    public boolean isTouching()
    {
        // get currBubble's x,y
        int cX = currBubble.getX();
        int cY = currBubble.getY();
        // iterate through all bubbles and find the bubble that is in overlapping range
        for(int i = 0; i < bubbles.length; i++)
        {
            for (int j = 0; j < bubbles[0].length; j++)
            {
                if((bubbles[i][j] != null && !bubbles[i][j].isPopped()) && cY + Bubble.BUBBLE_HEIGHT + 15 >= bubbles[i][j].getY()
                        && cY <= bubbles[i][j].getY() + Bubble.BUBBLE_HEIGHT + 15)
                {
                    if(cX + Bubble.BUBBLE_WIDTH >= bubbles[i][j].getX() && cX <= bubbles[i][j].getX() + Bubble.BUBBLE_WIDTH)
                    {
                        currBubble.setX(bubbles[i][j].getX());
                        currBubble.setY(bubbles[i][j].getY() + Bubble.BUBBLE_HEIGHT);
                        bubbles[i + 1][j] = currBubble;
                        pop(i + 1, j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void pop(int i, int j)
    {
        //Check if i and j are out of bounds and if it's been popped and if it's been visited already (set a bool for that)
        if(i >= bubbles.length || i < 0 || j >= bubbles[0].length || j < 0 || bubbles[i][j] == null || bubbles[i][j].isPopped()
                || !(bubbles[i][j].getColor().equals(currBubble.getColor())))
        {
            return;
        }

        if(bubbles[i][j] != currBubble && bubbles[i][j].getColor().equals(currBubble.getColor()))
        {
            bubbles[i][j].setPopped(true);
            currBubble.setPopped(true);
            score++;
            bubbles[i][j] = null;
        }

        // upper bubble
        pop(i + 1, j);
        // lower bubble
        pop(i - 1, j);
        // left bubble
        pop(i, j + 1);
        // right bubble
        pop(i, j - 1);
    }

    public void removeSolo()
    {
        for(int i = 1; i < bubbles.length; i++)
        {
            for(int j = 1; j < bubbles[0].length; j++)
            {
                Bubble solo = bubbles[i][j];
                if (solo != null && !solo.isPopped())
                {
                    int numSolo = 0;
                    int numBubbles = 0;

                    for(int row = i - 1; row < i + 1; row++)
                    {
                        for(int col = j - 1; col < j + 1; col++)
                        {
                            numBubbles++;
                            if (bubbles[row][col] == null || bubbles[row][col].isPopped())
                            {
                                numSolo++;
                            }
                        }
                    }
                    if(numSolo == numBubbles - 1)
                    {
                        solo.setPopped(true);
                        score++;
                    }
                }
            }
        }
    }

    public Bubble getCurrBubble() {
        return currBubble;
    }

    public static void main(String[] args)
    {
        BubblePop bp = new BubblePop();
        bp.playGame();
    }

    public void playGame()
    {
        BPV.repaint();
    }

    public int getState()
    {
        return state;
    }

    public void setState(int newState)
    {
        state = newState;
        BPV.repaint();
    }

    public int getStartBG()
    {
        return START_BG;
    }

    public int getPlayBg()
    {
        return PLAY_BG;
    }

    public Bubble[][] getBubbles() {
        return bubbles;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int toAdd)
    {
        score += toAdd;
    }

    public int getMovesLeft()
    {
        return MOVES_ALLOWED - numCurrs + 1;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isTouching() == true)
        {
            // Pop all bubbles
            clock.stop();
            removeSolo();
            BPV.repaint();

            newCurrBubble();
            return;
        }
        currBubble.move();
        BPV.repaint();
    }
}
