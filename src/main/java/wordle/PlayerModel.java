package wordle;

/**
 * Model class representing a player in the Wordle game.
 * It handles the storage and retrieval of the player's identification number.
 * This class uses static fields and methods, indicating that player identification
 * is managed globally across the application.
 *
 * @author Syed Muhammed Shahzain (251240894)
 */
public class PlayerModel {
    /**
     * The player's identification number.
     */
    private static int playerId;

    /**
     * Retrieves the player's identification number.
     *
     * @return The identification number of the player.
     */
    public static int getPlayerId() {
        return playerId;
    }

    /**
     * Sets the player's identification number.
     *
     * @param id The new identification number for the player.
     */
    public static void setPlayerId(int id) {
        playerId = id;
    }
}
