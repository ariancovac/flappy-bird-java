package engine;

import javax.swing.*;

import front.Config;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ResourceManager {
    private static ResourceManager instance;
    
    private static Image backgroundImg;
    private static Image birdImg;
    private static Image topPipeImg;
    private static Image bottomPipeImg;
    private static Image pipePairImg;
   
    private ResourceManager() {loadResources();}

    public static ResourceManager getInstance() {   //carga las imagenes por unica vez
        if (instance == null) { instance = new ResourceManager(); }
        return instance;
    }

    private void loadResources() {
        try {
            backgroundImg   = new ImageIcon(ClassLoader.getSystemResource("res/flappybirdbg.png")).getImage();
            birdImg         = new ImageIcon(ClassLoader.getSystemResource("res/flappybird.png")).getImage();
            topPipeImg      = new ImageIcon(ClassLoader.getSystemResource("res/toppipe.png")).getImage();
            bottomPipeImg   = new ImageIcon(ClassLoader.getSystemResource("res/bottompipe.png")).getImage();
            
            //combina los tubos respetando el GAP asignado
            BufferedImage combined = new BufferedImage(Config.PIPE_WIDTH, Config.PIPE_HEIGHT * 2 + Config.GAP, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = combined.createGraphics();
            g2.drawImage(getTopPipeImage(), 0, 0, Config.PIPE_WIDTH, Config.PIPE_HEIGHT, null);
            g2.drawImage(getBottomPipeImage(), 0, Config.PIPE_HEIGHT + Config.GAP, Config.PIPE_WIDTH, Config.PIPE_HEIGHT, null);
            g2.dispose();
            pipePairImg = combined;

        } catch (Exception e) {
            throw new RuntimeException("The resources could not be loaded", e);
        }
    }
    
//  ------------------------GETS-----------------------    

    public static Image getBackgroundImage(){ return backgroundImg; }
    public static Image getBirdImage()      { return birdImg; }
    public static Image getTopPipeImage()   { return topPipeImg; }
    public static Image getBottomPipeImage(){ return bottomPipeImg; }
    public static Image getPipePairImg()    { return pipePairImg;}
}
