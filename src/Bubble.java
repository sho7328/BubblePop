import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bubble{
    private int x;
    private int y;
    private double dx;
    private double dy;
    private int xRange;
    private int yRange;
    private int xDest;
    private int yDest;
    private String color;
    private Image bubbleImg;
    private boolean isPopped;
    private BubblePopViewer bpv;
    public static final int BUBBLE_WIDTH = 33;
    public static final int BUBBLE_HEIGHT = 33;

    public Bubble(int x, int y, String color, Image bubbleImg)
    {
        this.x = x;
        this.y = y;
        xRange = (this.getX() - Bubble.BUBBLE_WIDTH) + (Bubble.BUBBLE_WIDTH * 3);
        yRange = (this.getY() - Bubble.BUBBLE_HEIGHT) + (Bubble.BUBBLE_HEIGHT * 3);
        this.color = color;
        this.bubbleImg = bubbleImg;
        dx = 1;
        dy = 1;
    }

    public int getxRange() {
        return xRange;
    }

    public int getyRange() {
        return yRange;
    }

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

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Image getImg() {
        return bubbleImg;
    }

    public void setBubbleImg(Image bubbleImg) {
        this.bubbleImg = bubbleImg;
    }

    public boolean isPopped() {
        return isPopped;
    }

    public void setPopped(boolean popped) {
        isPopped = popped;
    }

    public void move()
    {
        x += dx;
        if((x + BUBBLE_WIDTH > 500 && dx > 0)|| (x < 0 && dx < 0))
        {
            dx *= -1;
        }
        y -= dy;
    }

    public void draw(Graphics g, BubblePopViewer bpv)
    {
        g.drawImage(bubbleImg, x, y, BUBBLE_WIDTH, BUBBLE_HEIGHT, bpv);
    }

}
