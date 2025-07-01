package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import classes.Bird;
import classes.Pipe;
import engine.GameController;
import engine.GameEngine;
import engine.ResourceManager;

public class FlappyBirdPanel extends JPanel implements KeyListener {

    private final Font scoreFont = new Font("Arial", Font.PLAIN, 32);
    private BufferedImage backBuffer;
    private Graphics2D bufferG;
    private final GameController controller;
    private final Timer pipeTimer;
    private GameEngine engine;

    public void setGameOverListener(GameController.GameOverListener listener) { controller.setGameOverListener(listener); }
    public void updateGameLogic() { controller.updateGameLogic(); }
    public void startGame(){GameEngine.setRunning(true);}
    public boolean isGameOver() { return controller.isGameOver(); }

    public FlappyBirdPanel() {
        setPreferredSize(new Dimension(Config.BOARD_WIDTH, Config.BOARD_HEIGHT));   //setea el tamanno del panel
       
        backBuffer = new BufferedImage(Config.BOARD_WIDTH, Config.BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        bufferG = backBuffer.createGraphics();
        setFocusable(true);     //permite que el panel reciba eventos de teclado
        addKeyListener(this);

        Bird bird = new Bird();
        controller = new GameController(bird, null); 
        pipeTimer = new Timer(1500, e -> controller.spawnPipe());   //annade tubos respetando el delay
        pipeTimer.start();
        startGameLoop();
    }

    private void startGameLoop() {
        engine = new GameEngine(this);
        new Thread(engine).start();
    }
    public void stopEngine() { if(engine != null) engine.stop(); }

    public void resetGame()  {
        stopEngine();
        controller.resetGame();
        pipeTimer.restart();
        startGameLoop();
    }

    public void destroy() {
        bufferG.dispose();
        stopEngine();
        pipeTimer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Bird bird = controller.getBird();

        //junta todas las imagenes en un buffer en vez de dibujarlas una por una
        bufferG.setColor(Color.BLACK);
        bufferG.fillRect(0, 0, Config.BOARD_WIDTH, Config.BOARD_HEIGHT);
        bufferG.drawImage(ResourceManager.getBackgroundImage(), 0, 0, Config.BOARD_WIDTH, Config.BOARD_HEIGHT, null);
        bufferG.drawImage(Bird.getImg(), Bird.getX(), bird.getY(), Bird.getWidth(), Bird.getHeight(), null);
        
        for (Pipe pipes : controller.getPipes()) {
            bufferG.drawImage(pipes.getImg(), pipes.getX(), pipes.getY(), pipes.getWidth(), pipes.getHeight(), null);
        }

        bufferG.setFont(scoreFont);
        bufferG.setColor(Color.white);
        bufferG.drawString(String.valueOf(bird.getScore()), 10, 35);    //impreme el puntaje actual
       
        g.drawImage(backBuffer, 0, 0, null);    //dibuja el buffer final
    }

    @Override
    public void keyPressed(KeyEvent e) {    //detecta que se presiona la tecla espacio y salta
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            controller.jump();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
