package engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import classes.ScoreEntry;
import front.Config;

public class ScoreManager {
    private final List<ScoreEntry> scoreEntries = new ArrayList<>();
    private final File scoreFile = new File(System.getProperty("user.dir"), "scores.txt");

    public void loadScores() {
        scoreEntries.clear();
        if (scoreFile.exists()) {   //lee linea y guarda las entradas no nulas, luego las ordena
            try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    ScoreEntry entry = ScoreEntry.fromString(line);
                    if (entry != null) scoreEntries.add(entry);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            scoreEntries.sort(null);
        }
    }

    public void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile))) {
            for (ScoreEntry entry : scoreEntries) { //escribe las entradas en el archivo
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving scores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addScore(String name, int score) {  
        if (name == null || name.trim().isEmpty()) name = "Anonymous";  //si el nombre es nulo o vacio le annade un nombre generico
        scoreEntries.add(new ScoreEntry(name.trim(), score));           //annade el nuevo puntaje a la lista y luego la ordena
        scoreEntries.sort(null);
        saveScores();
    }

//  ------------------------GETS-----------------------

    public List<ScoreEntry> getTopScores()    { return scoreEntries.stream().limit(Config.MAX_ENTRIES).toList(); }
    public List<ScoreEntry> getScoreEntries() { return scoreEntries; }
}