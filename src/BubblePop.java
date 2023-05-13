import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// SOPHIE HO
// 5/12/23
// The BubblePop class is the back-end of the game.
// It runs all the behind-the scenes action and updates the visuals of the game using repaint.

public class BubblePop implements ActionListener {
    private BubblePopViewer BPV;
    private Bubble[][] bubbles;
    private Bubble currBubble;
    private int numCurrs;
    private Timer clock;
    private int score;
    private boolean gameOver;
    public static final int MOVES_ALLOWED = 20;
    public static final int SPEED = 25;
    public static final int START_BG = 1;
    public static final int PLAY_BG = 2;
    public static final int PLAYING_BG = 3;
    public static final int CLICKED = 4;
    public static final int GAME_OVER = 5;
    public static final int WIN_BG = 6;
    private static int state = START_BG;
    private static final int DELAY = 100;

    // BubblePop constructor defines instance variables and creates bubbles 2D array.
    public BubblePop()
    {
        gameOver = false;
        score = 0;
        // Define BubblePopViewer object to sync and create access
        BPV = new BubblePopViewer(this);
        // A clock for ActionPerformed method
        clock = new Timer(DELAY, this);

        // Define bubbles 2d array
        // bubbles is 18x15 but the top bubbles show up 9x15.
        bubbles = new Bubble[18][15];
        // Start at these bubble x and bubble y values so that the bubbles will show up at a nice place on screen.
        int bx = 3;
        int by = 30;
        // Traverse through bubbles
        for(int i = 0; i < bubbles.length; i++)
        {
            // Generate a random number from 0-4 that will represent the bubble's color.
            int randNum = (int)(Math.random() * 5);
            // Generate a random number from 0-19 that will be used to determine the order of the bubbles
            int a = (int)(Math.random() * 20);
            for (int j = 0; j < bubbles[0].length; j++)
            {
                // Change the color of the bubble every time a is >= than j
                // This is to ensure that there are still groups of the same color and
                // that they are not completely randomized.
                if (a >= j)
                {
                    randNum = (int)(Math.random() * 5);
                }
                // If i is greater than 9, then don't add more bubbles
                if(i > 9)
                {
                    bubbles[i][j] = null;
                }
                else
                {
                    // Make a new bubble using the random colors
                    bubbles[i][j] = new Bubble(bx, by, BPV.getBubbleColors().get(randNum).getColor(),
                            BPV.getBubbleColors().get(randNum).getImg());
                    // Increase the x value for the columns so the bubble's don't overlap
                    bx += Bubble.BUBBLE_WIDTH;
                }
            }
            // Reset the x value for a new row
            bx = 3;
            // Increase the y value for a new row
            by += Bubble.BUBBLE_HEIGHT;
        }

        // Create a new currBubble (the bubble at the bottom that's being shot to the top bubbles)
        // randNum generates a random number for currBubble's color
        int randNum = (int)(Math.random() * 5);
        currBubble = new Bubble(250, 700, BPV.getBubbleColors().get(randNum).getColor(),
                BPV.getBubbleColors().get(randNum).getImg());
        // The number of currBubbles has been updated to 1 (keeping track of numCurrs
        // to keep track of the number of moves left.
        numCurrs = 1;
    }

    // Make and generate a new currBubble
    public void newCurrBubble()
    {
        // If the user hasn't run out of moves yet
        if(numCurrs < MOVES_ALLOWED)
        {
            // Create a new currBubble (the bubble at the bottom that's being shot to the top bubbles)
            // randNum generates a random number for currBubble's color
            int randNum = (int)(Math.random() * 5);
            currBubble = new Bubble(250, 700, BPV.getBubbleColors().get(randNum).getColor(),
                    BPV.getBubbleColors().get(randNum).getImg());
            // Increase numCurrs
            numCurrs++;
            // Change the state of the game
            state = PLAYING_BG;
        }
        else
        // If the game is over, change the state of the game
        {
            setGameOver(true);
        }
    }

    // This method checks if currBubble is touching another bubble when it's shot, and also runs pop()
    // if it is touching another bubble.
    public boolean isTouching()
    {
        // Get currBubble's x,y and set them as int variables
        int cX = currBubble.getX();
        int cY = currBubble.getY();

        // Iterate through all bubbles and find the bubble that is in overlapping range
        for(int i = 0; i < bubbles.length; i++)
        {
            for (int j = 0; j < bubbles[0].length; j++)
            {
                // If currBubble is touching another bubble (that is valid):
                if((bubbles[i][j] != null && !bubbles[i][j].isPopped())
                        && cY + Bubble.BUBBLE_HEIGHT + 15 >= bubbles[i][j].getY()
                        && cY <= bubbles[i][j].getY() + Bubble.BUBBLE_HEIGHT + 15)
                {
                    if(cX + Bubble.BUBBLE_WIDTH >= bubbles[i][j].getX() &&
                            cX <= bubbles[i][j].getX() + Bubble.BUBBLE_WIDTH)
                    {
                        // Set the currBubble's new x and y values to its new fixed place.
                        currBubble.setX(bubbles[i][j].getX());
                        currBubble.setY(bubbles[i][j].getY() + Bubble.BUBBLE_HEIGHT);
                        // Add currBubble to the 2D array of bubbles.
                        bubbles[i + 1][j] = currBubble;
                        // Run pop() to pop any bubbles needed
                        pop(i + 1, j);
                        return true;
                    }
                }
            }
        }
        // currBubble has not yet touched any bubbles.
        return false;
    }

    // pop() is a recursive method that checks if a bubble needs to be popped, and if so, pops the bubble.
    // The parameters i and j represent the row (i) and col (j) location of the bubble being looked at
    public void pop(int i, int j)
    {
        // Check if the bubble passed in is valid to be popped:
        // (Base case) If the bubble isn't valid
        if(i >= bubbles.length || i < 0 || j >= bubbles[0].length || j < 0 || bubbles[i][j] == null
                || bubbles[i][j].isPopped() || !(bubbles[i][j].getColor().equals(currBubble.getColor())))
        {
            // return and exit
            return;
        }

        // If the bubble is valid to be popped:
        if(bubbles[i][j] != currBubble && bubbles[i][j].getColor().equals(currBubble.getColor()))
        {
            // Set the bubble as popped
            bubbles[i][j].setPopped(true);
            // Set currBubble as popped too
            currBubble.setPopped(true);
            // Increase the score
            addOneToScore();
            // Set the popped bubble to null in the 2D array.
            bubbles[i][j] = null;
        }

        // Recurse by checking the
        // Upper bubble
        pop(i + 1, j);
        // Lower bubble
        pop(i - 1, j);
        // Left bubble
        pop(i, j + 1);
        // Right bubble
        pop(i, j - 1);
    }

    // Remove any floating, gravity-defying bubbles that are not currBubble.
    public void removeSolo()
    {
        // Traverse the 2D array of bubbles
        for(int i = 1; i < bubbles.length; i++)
        {
            for(int j = 1; j < bubbles[0].length; j++)
            {
                // solo is the bubble at bubbles[i][j] is
                Bubble solo = bubbles[i][j];
                // Check if solo is valid to be floating (can't be null or popped already)
                if (solo != null && !solo.isPopped())
                {
                    // numSolo is a counter for the number of null or popped bubbles around solo
                    int numSolo = 0;
                    // numBubbles is the number of bubbles looked at in the following nested for loop
                    int numBubbles = 0;
                    // Look at the bubbles around the perimeter of solo to see if solo is floating
                    for(int row = i - 1; row < i + 1; row++)
                    {
                        for(int col = j - 1; col < j + 1; col++)
                        {
                            // Increase numBubbles to update the number of bubbles visited
                            numBubbles++;
                            // If the bubbles around solo are already popped, or are null
                            if (bubbles[row][col] == null || bubbles[row][col].isPopped())
                            {
                                // Increment numSolo
                                numSolo++;
                            }
                        }
                    }
                    // If all the bubbles around solo are popped/null, pop the floater bubble
                    if(numSolo == numBubbles - 1)
                    {
                        solo.setPopped(true);
                        // Increase score
                        addOneToScore();
                    }
                }
            }
        }
    }

    // GETTERS AND SETTERS
    public Bubble getCurrBubble() {
        return currBubble;
    }

    public int getState()
    {
        return state;
    }

    public int getMovesLeft()
    {
        return MOVES_ALLOWED - numCurrs + 1;
    }

    public void setState(int newState)
    {
        state = newState;
        // Repaint to update and sync the front-end
        BPV.repaint();
    }

    public Bubble[][] getBubbles() {
        return bubbles;
    }

    public int getScore() {
        return score;
    }

    public Timer getClock() {
        return clock;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        if(gameOver == true)
        {
            state = GAME_OVER;
        }
    }

    public void addOneToScore()
    {
        score++;
        // Check if the game has been won yet (if all bubbles have been popped)
        checkWin();
    }

    // Check if all bubbles have been popped
    public void checkWin()
    {
        // Traverse the bubbles 2d array
        for(int i = 0; i < bubbles.length; i++)
        {
            for(int j = 0; i < bubbles[0].length; j++)
            {
                // If the bubble isn't null or popped, the game hasn't been won yet
                if(bubbles[i][j] != null && !bubbles[i][j].isPopped())
                {
                    return;
                }
            }
        }
        // If all bubbles are null/popped, the user wins
        // Change the state
        state = WIN_BG;
    }

    // actionPerformed updates the (x,y) values of currBubble when it's being shot up
    // Begins when the clock/timer starts
    @Override
    public void actionPerformed(ActionEvent e) {
        // If currBubble's touching another bubble
        if (isTouching() == true)
        {
            // Stop the clock and don't move the bubble anymore
            clock.stop();
            // Make sure to remove floating bubbles
            removeSolo();
            // Update front-end
            BPV.repaint();
            // Create a new currBubble
            newCurrBubble();
            return;
        }
        // If currBubble has not touched another bubble, keep moving and updating front-end
        currBubble.move();
        BPV.repaint();
    }

    // Main runs the game
    public static void main(String[] args)
    {
        BubblePop bp = new BubblePop();
        bp.playGame();
    }

    // Run repaint() to draw and run the front-end
    public void playGame()
    {
        BPV.repaint();
    }
}

