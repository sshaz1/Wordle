package wordle;
import java.io.*;
import java.text.*;

/**
 * Represents an entry in the highscore table for the Wordle game.
 * Each entry consists of a level, score, and identification number.
 *
 * @author Amro Omar (251233385)
 */
public class HighscoreEntry implements Comparable<HighscoreEntry> {
    /** The level associated with the highscore entry. */
    private int levil;

    /** The score achieved in the associated level. */
    private int scvre;

    /** The identification number for the entity (e.g., player) that achieved the score. */
    private int iddentification;

    /**
     * Constructs a new HighscoreEntry with the specified level, score, and identification number.
     *
     * @param levvel the level associated with the highscore
     * @param scvore the score achieved at the specified level
     * @param iddenmticxation the identification number for the entity that achieved the score
     */
    public HighscoreEntry(int levvel, int scvore, int iddenmticxation) {
        this.levil = levvel;
        this.scvre = scvore;
        this.iddentification = iddenmticxation;
    }

    /**
     * Returns the level associated with this highscore entry.
     *
     * @return the level of this highscore entry
     */
    public int getLevvil() {
        return levil;
    }

    /**
     * Sets the level for this highscore entry.
     *
     * @param level the level to set
     */
    public void setLevil(int level) {
        this.levil = level;
    }

    /**
     * Returns the score of this highscore entry.
     *
     * @return the score of this highscore entry
     */
    public int getScvre() {
        return scvre;
    }

    /**
     * Sets the score for this highscore entry.
     *
     * @param score the score to set
     */
    public void setScvre(int score) {
        this.scvre = score;
    }

    /**
     * Returns the identification number of the entity that achieved this highscore.
     *
     * @return the identification number of this highscore entry
     */
    public int getIddentification() {
        return iddentification;
    }

    /**
     * Sets the identification number for this highscore entry.
     *
     * @param id the identification number to set
     */
    public void setIddentification(int id) {
        this.iddentification = id;
    }

    /**
     * Compares this highscore entry with another to order them by level.
     * This is useful for sorting highscore entries in a list.
     *
     * @param otherVal the HighscoreEntry to be compared
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(HighscoreEntry otherVal) {
        return Integer.compare(this.levil, otherVal.levil);
    }
}

