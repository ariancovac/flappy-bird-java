package front;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final JButton btnStart;
    private final JButton btnScores;
    private final JButton btnExit;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints layout = new GridBagConstraints();                //layout
        layout.insets = new Insets(10, 10, 10, 10);    //margen de los botones
        layout.fill = GridBagConstraints.NONE;      //evita que se estiren los botones

        btnStart = new JButton("Start Game");                   
        btnScores = new JButton("View Scores");
        btnExit = new JButton("Exit Game");

        //annade los botones de forma vertical
        layout.gridy = 0;
        add(btnStart, layout);
        layout.gridy = 1; 
        add(btnScores, layout);
        layout.gridy = 2;
        add(btnExit, layout);
    }

//  ------------------------GETS-----------------------

    public JButton getStartButton() { return btnStart; }
    public JButton getScoresButton() { return btnScores; }
    public JButton getExitButton() { return btnExit; }
}