package wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A Controller class that manages all the interactions with the buttons and the information to be displayed in the "Debug Login" screen.
 *
 * @author sshahzai (251240894)
 */
public class DebugController {

    /** A button that, once clicked, goes to the "Level Select" screen. The button is displayed in the "Debug Login Screen"*/
    @FXML
    private Button continueButton;

    /** A button that, once clicked goes back to the "Main Menu" screen. The button is displayed in the "Debug Login" screen */
    @FXML
    private Button backToMainMenuButton;

    /** A text field where the user can input the required password to access the "Progress/Results" screen. */
    @FXML
    private TextField passwordInput;

    /** A label that will display "Incorrect Password!" when it is set to be visible. */
    @FXML
    private Label incorrectPassword;

    private Stage stage; // Assume this is initialized correctly

    /**
     * Sets the primary stage for the controller.
     *
     * @param stage The main application window or stage that this controller will control.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * A method that is called when the "Continue" button in the "Debug Login" screen is pressed.
     * It first checks if the password inputted is the correct password. If it is the correct password, then it will close the "Debug Login" screen,
     * and go to Level Select Screen with Debug Mode set to true
     * If the password is incorrect, it will clear the password text field and display "Incorrect Password!".
     *
     *
     * @throws IOException throws an IOException when FXMLLoader fails to locate or load "progress-results-screen.fxml"
     */
    @FXML
    public void onContinueButton() throws IOException {
        String input = passwordInput.getText();

        if (input.equals("Team75")) {
            // go to level select screen with debug mode on
            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/levelSelectionView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 700);

            LevelsController levelController = fxmlLoader.getController();
            PlayerModel.setPlayerId(0);
            levelController.updatePlayerIdLabel();
            levelController.setDebug(true);
            stage.setTitle("Level Select (Debug)");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }

        else{
            passwordInput.setText("");
            incorrectPassword.setVisible(true);
        }
    }

    /**
     * A method that is called when the "Back to Main Menu" button is clicked in the "Debug Login" screen.
     * It closes the current screen, which is the "Debug Login" screen, and opens the "Main Menu" screen.
     *
     * @throws IOException throws an IOException when FXMLLoader fails to locate or load "MainMenu-view.fxml"
     */
    @FXML
    protected void backToMainMenu() throws IOException {
        Stage stage = (Stage) backToMainMenuButton.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu-view.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
