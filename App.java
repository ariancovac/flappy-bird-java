import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import classes.ScoreEntry;
import engine.ResourceManager;
import engine.ScoreManager;
import front.FlappyBirdPanel;
import front.MenuPanel;
import front.ScoresPanel;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //cargar recursos y puntajes al inicio
            ScoreManager scoreManager = new ScoreManager();
            scoreManager.loadScores();
            ResourceManager.getInstance(); //carga unica de imagenes

            //crea la ventana principal
            JFrame frame = new JFrame("Flappy Bird");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            //configura el contenedor para alternar entre paneles
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);

            //crea los paneles
            MenuPanel menuPanel = new MenuPanel();
            ScoresPanel scoresPanel = new ScoresPanel();
            FlappyBirdPanel gamePanel = new FlappyBirdPanel();

            //agregar los paneles al contenedor
            mainPanel.add(menuPanel, "MENU");
            mainPanel.add(scoresPanel, "SCORES");
            mainPanel.add(gamePanel, "GAME");

            //boton para iniciar el juego
            menuPanel.getStartButton().addActionListener(e -> {
                gamePanel.stopEngine();
                gamePanel.resetGame();
                gamePanel.startGame();
                cardLayout.show(mainPanel, "GAME");
                gamePanel.requestFocusInWindow();
            });

            //boton de ver los puntajes
            menuPanel.getScoresButton().addActionListener(e -> {
                scoresPanel.getScores().clear();        //limpia la lista de puntajes y la vuelve a cargar del archivo
                for (ScoreEntry entry : scoreManager.getTopScores()) {
                    scoresPanel.getScores().addElement(entry.toString());
                }
                cardLayout.show(mainPanel, "SCORES");
            });

            //boton de volver
            scoresPanel.getBackButton().addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

            //boton de salir
            menuPanel.getExitButton().addActionListener(e -> {
                gamePanel.destroy();
                frame.dispose();
                System.exit(0);
            });

            //logica al perder la partida
            gamePanel.setGameOverListener(finalScore -> {
                //muestra un pop up para pedir el nombre del jugador
                String name = JOptionPane.showInputDialog(frame, "Enter your name:", "You lose", JOptionPane.PLAIN_MESSAGE);
                if (name == null || name.trim().isEmpty()) name = "Anonymous";    //si no ingreso nombre o solo ingreso espacios, se asigna "anonymous"
                
                scoresPanel.clearScores();                  //limpia la lista de puntajes
                scoreManager.addScore(name, finalScore);    //annade el puntaje 
                for (ScoreEntry entry : scoreManager.getTopScores()) {  //vuelve a llenar la lista actualizada
                    scoresPanel.getScores().addElement(entry.toString());
                }   
                cardLayout.show(mainPanel, "MENU");    //vuelve al menu
            });

            //muestra la ventana
            frame.setContentPane(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
