package classes;

import java.awt.Image;

import engine.ResourceManager;

public class Pipe {
    private int x, y;
    private static Image img=ResourceManager.getPipePairImg();
    private static final int width=img.getWidth(null), height=img.getHeight(null);
    private boolean passed = false;
    
    public Pipe(int x, int y) {
        this.x = x;
        this.y = y;
    }
//  ------------------------GETS-----------------------

    public int getX()       { return x; }
    public int getY()       { return y; }
    public int getWidth()   { return width; }
    public int getHeight()  { return height; }
    public Image getImg()   { return img; }

//  ------------------------SETS----------------------

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setPassed(boolean passed) { this.passed = passed; }

    public boolean isPassed() { return passed;} 
    public void move(float x) { this.x += (int) x; }
}