import java.awt.*;

public class Bubble {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private String color;
    private Image bubbleImg;
    private boolean isPopped;

    public Bubble(String color, Image bubbleImg)
    {
        this.color = color;
        this.bubbleImg = bubbleImg;
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

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Image getBubbleImg() {
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

    public void shoot(int xdest, int ydest)
    {
        return;
    }

    public void draw(Graphics g, int x, int y)
    {
        return;
    }

}
