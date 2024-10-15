package wordle;

/**
 * Interface for the gameplay controller in the Wordle game.
 * Defines the essential methods required for setting up and managing the gameplay.
 * This includes setting player information, the game level, and the word to be guessed.
 *
 * @author Syed Muhammed Shahzain (251240894)
 */
public interface WordleGameplayController {
    /**
     * Sets the ID of the player.
     *
     * @param playerId The ID of the player.
     */
    void setPlayerId(int playerId);

    /**
     * Sets the game level.
     *
     * @param gamelevel The level of the game.
     */
    void setGame_level(int gamelevel);

    /**
     * Sets the word for the current game level.
     *
     * @param word The word to be guessed in the current level of the game.
     */
    void setGame_word(String word);

    /**
     * Will handle guess inputted by user in gameplay
     *
     */
    void handleCheckGuess();
}
