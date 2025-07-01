package classes;

public class ScoreEntry implements Comparable<ScoreEntry> {
    private final String name;
    private final int score;

    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(ScoreEntry other) {
        return Integer.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return score + " - " + name;
    }

    public static ScoreEntry fromString(String line) {
        String[] parts = line.split(" - ");
        if (parts.length == 2) {
            try {
                int score = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                return new ScoreEntry(name, score);
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }
}