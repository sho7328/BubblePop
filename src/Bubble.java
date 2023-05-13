import java.awt.*;

// SOPHIE HO
// 5/12/23
// The Bubble class is responsible for each bubble that appears in the code and on screen.
// It has traits for every bubble in its instance variables.
// Each bubble has a color, image, x, y, dx, dy, size, etc.

public class Bubble{
    private int x;
    private int y;
    private double dx;
    private double dy;
    private int xDest;
    private int yDest;
    private String color;
    private Image bubbleImg;
    private boolean isPopped;
    public static final int BUBBLE_WIDTH = 33;
    public static final int BUBBLE_HEIGHT = 33;

    // Constructor passes in the bubble's designated x, y, color, and image.
    public Bubble(int x, int y, String color, Image bubbleImg)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.bubbleImg = bubbleImg;
    }

    // GETTERS AND SETTERS:
    public int getxDest() {
        return xDest;
    }

    public void setxDest(int xDest) {
        this.xDest = xDest;
    }

    public int getyDest() {
        return yDest;
    }

    public void setyDest(int yDest) {
        this.yDest = yDest;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public String getColor() {
        return color;
    }

    public Image getImg() {
        return bubbleImg;
    }

    public boolean isPopped() {
        return isPopped;
    }

    public void setPopped(boolean popped) {
        isPopped = popped;
    }

    // move() controls the bubble when it moves. dx is the speed of the bubble in the x direction
    // and dy is the speed of the bubble in the y direction.
    public void move()
    {
        // Change x by dx.
        x += dx;
        // If the bubble's x location goes out of bounds, reverse it's x so it bounces off the wall of the window.
        if((x + BUBBLE_WIDTH > 500 && dx > 0)|| (x < 0 && dx < 0))
        {
            dx *= -1;
        }
        // Change y by dy
        y -= dy;
    }

    // Draw the bubble at its (x,y) location at the scale of BUBBLE_WIDTH by BUBBLE_HEIGHT
    public void draw(Graphics g, BubblePopViewer bpv)
    {
        g.drawImage(bubbleImg, x, y, BUBBLE_WIDTH, BUBBLE_HEIGHT, bpv);
    }
}
