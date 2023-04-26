public class BubblePop {
    private Bubble[][] bubbles;
    private int score;
    private int movesLeft;
    private boolean isWin;
    private Arrow arrow;
    private BubblePopViewer BPV;

    public BubblePop()
    {
        // create bubbles 2d array
        score = 0;
        movesLeft = 30;
        isWin = false;

        BPV = new BubblePopViewer(this);
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

    public int getMovesLeft() {
        return movesLeft;
    }

    public void minusMovesLeft()
    {
        movesLeft--;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public void checkWon()
    {
        return;
    }

}
