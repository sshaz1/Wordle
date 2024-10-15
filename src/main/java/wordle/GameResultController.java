package wordle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the game result screen in the Wordle game application.
 * This class manages the display of game outcomes and scores, and navigation to the level selection screen.
 *
 * @author Emily Christina Atyeo (251281573)
 */
public class GameResultController {

    /** Button that allows the user to return to the level selection screen. */
    @FXML
    private Button levelSelectionButton;

    /** Label to display the outcome of the game. */
    @FXML
    public Label gameOutcome;

    /** Label to display the player's score achieved in the game. */
    @FXML
    public Label gameScore;

    /** The score achieved by the player in the current game. */
    public int playerScore;

    /**
     * Handler for click events on the level selection button.
     * This method loads and displays the level selection screen.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during loading the FXML file for the level selection screen.
     */
    @FXML
    protected void levelSelectionClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) levelSelectionButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/levelSelectionView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Level Select");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Sets the game outcome message based on the player's performance.
     * Displays a congratulatory message if the game is won, or a failure message otherwise.
     *
     * @param gameWon A boolean indicating whether the game was won or not.
     */
    @FXML
    public void setGameOutcome(boolean gameWon) {
        if (gameWon) {
            gameOutcome.setText("Congratulations! Level complete.");
        } else {
            gameOutcome.setText("Sorry, level failed.");
        }
    }

    /**
     * Sets the player's score on the game result screen.
     *
     * @param playerScore The player's score to display.
     */
    @FXML
    public void setPlayerScore(int playerScore) {
        String score = String.valueOf(playerScore);
        String message = "Score: " + score;
        gameScore.setText(message);}
}
