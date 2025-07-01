package engine;

import javax.swing.SwingUtilities;

import front.Config;
import front.FlappyBirdPanel;

public class GameEngine implements Runnable {
    private final FlappyBirdPanel game;
    private static boolean running = false;

    public boolean isRunning() { return running; }
    public static void setRunning(boolean running) { GameEngine.running = running; }
    public GameEngine(FlappyBirdPanel game) { this.game = game; }
    public void stop() { running = false; }

    @Override
    public void run() {
        long previous = System.nanoTime();
        double accumulator = 0;
        double renderAccumulator = 0;

        while (running && !game.isGameOver()) {
            long now = System.nanoTime();
            double frameTime = now - previous;
            previous = now;
            if (frameTime > 250_000_000) frameTime = 250_000_000;   //si se atrasa un frame pone un tiempo maximo 
                                                                    //para que la actualizacion no sea muy brusca
            accumulator += frameTime;
            renderAccumulator += frameTime;

            while ( (accumulator >= Config.TARGET_FRAMETIME_NS ) && running) {
                game.updateGameLogic();         //actualiza la logica del juego respetando el frametime
                accumulator -= Config.TARGET_FRAMETIME_NS;
            }

            if (renderAccumulator >= Config.TARGET_FRAMETIME_NS) {
                SwingUtilities.invokeLater(game::repaint);  //actualiza la pantalla con todos los updates
                renderAccumulator = 0;
            }

            long busyTime = System.nanoTime() - now;
            long sleepTimeNanos = (long) (Config.TARGET_FRAMETIME_NS - busyTime);   //calcula el tiempo libre restante
            if (sleepTimeNanos > 0) {
                try {
                    Thread.sleep(sleepTimeNanos / 1_000_000, (int) (sleepTimeNanos % 1_000_000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                Thread.yield();
            }
        }
    }
}