package front;

import javax.swing.*;
import java.awt.*;

public class ScoresPanel extends JPanel {
    private final JList<String> scoreList;
    private final JButton btnBack;
    private final DefaultListModel<String> scores;

//  ------------------------GETS-----------------------

    public DefaultListModel<String> getScores() { return scores; }
    public void clearScores(){ scores.clear(); }
    public JButton getBackButton() { return btnBack; }

    public ScoresPanel() {
        setLayout(new BorderLayout());
        scores = new DefaultListModel<>();
        scoreList = new JList<>(scores);        //lista vertical

        btnBack = new JButton("Back");     //annade boton para volver al menu
        
        add(new JScrollPane(scoreList), BorderLayout.CENTER);   //annade panel con scroll
        add(btnBack, BorderLayout.SOUTH);
    }

    
}