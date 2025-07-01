package engine;

import java.util.ArrayList;

import classes.Bird;
import classes.Pipe;
import front.Config;

public class GameController {
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private boolean gameOver;
    private GameOverListener gameOverListener;

    public Bird getBird() { return bird; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public boolean isGameOver() { return gameOver; }

    public interface GameOverListener{ void onGameOver(int finalScore); }
    public void setGameOverListener  (GameOverListener listener) { this.gameOverListener = listener; }

    public GameController(Bird bird, GameOverListener listener){
        this.bird = bird;
        this.pipes = new ArrayList<>();
        this.gameOver = false;
        setGameOverListener (listener);
    }
    
    public void updateGameLogic() {          //si la velocidad del pajaro es menor que la maxima entonces se 
        if(bird.getVelocityY() < Config.MAX_FALL_VELOCITY){ //actualiza entre el min de la acumulada actualizada y velociad maxima  
            bird.setVelocityY(  Math.min(Config.GRAVITY + bird.getVelocityY(), 
                                Config.MAX_FALL_VELOCITY));
        }
        bird.addY(bird.getVelocityY() * Config.TARGET_FRAMETIME_S); //mueve el pajaro en y
        bird.setY(Math.max(0, bird.getY()));    //evita que el pajaro supere el tablero
        
        if(bird.getY()>= Config.BOARD_HEIGHT){    //termina si el pajaro cae al fondo
            gameOver = true;
            gameOverListener.onGameOver(bird.getScore());
            GameEngine.setRunning(false);
            return;
        }
        for (Pipe p : pipes) {                    //mueve los tubos y detecta colisiones
            p.move(Config.PIPE_VELOCITY_FPS);
            if (collision(bird, p)) {
                gameOver = true;
                gameOverListener.onGameOver(bird.getScore());
                return;
            }
            if (!p.isPassed() && Bird.getX() > p.getX() + p.getWidth()) {   //detecta si se paso el tubo para sumar puntos
                bird.setScorepp();
                p.setPassed(true);
            }
        }

        pipes.removeIf(p -> p.getX() + p.getWidth() < 0);                   //remueve los tubos que ya no estan
        if (bird.getY() > Config.BOARD_HEIGHT) gameOver = true;
    }

    public void spawnPipe() {
        int randomY = (int) (-Config.GAP - Math.random() * Config.PIPE_HEIGHT_DIV2);
        pipes.add(new Pipe(Config.BOARD_WIDTH, randomY));
    }
    public void jump() {                //salta el pajaro, si cae sube la velocidad por completo, 
        if (bird.getVelocityY() > 0){   //si no, una velocidad amortiguada para no llegar al maximo muy rapido
            bird.addVelocityY(Config.JUMP_VELOCITY);
        } else
            bird.setVelocityY(Math.min( ((bird.getVelocityY() + Config.JUMP_VELOCITY) / 2f), Config.MAX_JUMP_VELOCITY));
        
    }

    private boolean collision(Bird b, Pipe p) {
        return Bird.getxWidth() > p.getX() &&
               Bird.getX() < p.getX() + Config.PIPE_WIDTH &&
              (b.getY() < p.getY() + Config.PIPE_HEIGHT ||
               b.getY() + Bird.getHeight() > p.getY() + Config.PIPE_HEIGHT_GAP);
    }

    public void resetGame() {
        bird = new Bird(); 
        pipes.clear();
        gameOver = false;
    }
}
