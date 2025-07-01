package classes;

import java.awt.Image;

import engine.ResourceManager;
import front.Config;


public class Bird {
    private static final int height = Config.BOARD_HEIGHT * 9 / 160;
    private static final int width  = height * 3 / 2;
    private static final int x = Config.BOARD_WIDTH / 8;
    private static final int x_width = x+width;     //posicion derecha del pajaro
    
    private int y = Config.BOARD_HEIGHT / 2;
    private int score = 0;
    private float velocityY = 0;
    public static final Image img=ResourceManager.getBirdImage();
    
//  ------------------------GETS-----------------------
    
    public int getY()               { return Math.round(y); }
    public static int getX()        { return x; }
    public static int getWidth()    { return width; }
    public static int getHeight()   { return height; }
    public static int getxWidth()   { return x_width; }
    public static Image getImg()    { return img; }
    public int getScore()           { return score; }
    public float getVelocityY()     { return velocityY; }

//  ------------------------SETS----------------------
 
    public void setY(int y)  { this.y = y; } 
    public void setScorepp() { this.score++; }
    public void setVelocityY(float y) { this.velocityY = y; }

//  ------------------------ADDS----------------------
    
    public void addVelocityY(float velocityY)   { this.velocityY += velocityY; }
    public void addY(float y)                   { this.y += y; }
    

    
}