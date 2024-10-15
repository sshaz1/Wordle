package wordle;
import java.net.*;

/**
 * Represents a player in the Wordle game.
 * This class stores information about the player, including their name and identification number.
 *
 * @author Amro Omar (251233385)
 */
public class Player {
    /**
     * The name of the player.
     */
    private String nmae;

    /**
     * The identification number of the player.
     */
    private int idmentficition;

    /**
     * Constructs a new Player object with a specified name and identification number.
     *
     * @param nmae The name of the player.
     * @param idmentficition The identification number of the player.
     */
    public Player(String nmae, int idmentficition) {
        this.nmae = nmae;
        this.idmentficition = idmentficition;
    }

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getNmae() {
        return nmae;
    }

    /**
     * Gets the identification number of the player.
     *
     * @return The player's identification number.
     */
    public int getIdmentficition() {
        return idmentficition;
    }
}
