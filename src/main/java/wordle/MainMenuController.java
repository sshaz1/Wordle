package wordle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Controller class for the main menu of the Wordle game application.
 * This class handles user interactions on the main menu, such as starting a new game, loading a game,
 * accessing the how-to-play guide, logging in, viewing the scoreboard, and quitting the game.
 *
 * @author Syed Muhammed Shahzain (251240894)
 */
public class MainMenuController {

    /** Button to start a new game. */
    @FXML
    private Button newplayButton;

    /** Button to load an existing game. Clicking this button allows the user to select and resume a previously saved game state. */
    @FXML
    private Button loadplayButton;

    /** Button to display the "How to Play" instructions. Clicking this button shows the user a guide or tutorial on how to play. */
    public Button howToPlayButton;

    /** Button for the user to log in to their account. */
    public Button logInButton;

    /** Button for the user to view debug mode */
    public Button debugButton;

    /** Button to view the scoreboard. Clicking this button displays the game's high scores and player rankings. */
    public Button scoreboardButton;

    /** Button to return to the previous screen or main menu. Clicking this button takes the user back to the main menu. */
    @FXML
    private Button backButton;

    /**
     * Handles the click event on the "New Player" button.
     * This method loads the new player view.
     *
     * @throws IOException If an error occurs during loading the FXML file for the new player view.
     */
    @FXML
    void newPlayerClicked() throws IOException {
        Stage stage = (Stage) newplayButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/newplayer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);

        // seting up enter key interaction
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                NewPlayerController controller = fxmlLoader.getController();
                try {
                    controller.onGoButtonClicked();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        stage.setTitle("New Player");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    /**
     * Handles the click event on the "Load Player" button.
     * This method loads the load player view.
     *
     * @throws IOException If an error occurs during loading the FXML file for the load player view.
     */
    @FXML
    protected void loadPlayerClicked() throws IOException {
        Stage stage = (Stage) loadplayButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/loadplayer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);

        // seting up enter key interaction
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                LoadPlayerController controller = fxmlLoader.getController();
                controller.onGoButtonClicked();
            }
        });

        stage.setTitle("Load Player");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Handles the click event on the "How to Play" button.
     * This method loads the tutorial view.
     *
     * @param event The mouse event that triggered this method.
     * @throws IOException If an error occurs during loading the FXML file for the tutorial view.
     */
    @FXML
    protected void onHowToButtonClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) howToPlayButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/tutorial-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 1100);
        stage.setTitle("How to Play");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    /**
     * Handles the click event on the "Debug" button.
     * This method loads the tutorial view.
     *
     * @throws IOException If an error occurs during loading the FXML file for the tutorial view.
     */
    @FXML
    protected void onLogInButtonClick() throws IOException {
        Stage stage = (Stage) logInButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 580, 300);
        LoginController loginController = fxmlLoader.getController();
        loginController.setStage(stage);

        // seting up enter key interaction
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    loginController.continueToProgressResults();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    /**
     * Handles the click event on the "Debug" button.
     * This method loads the login view
     *
     * @throws IOException If an error occurs during loading the FXML file for the login view.
     */
    @FXML
    protected void onDebugButtonClick() throws IOException {
        Stage stage = (Stage) debugButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/debugLogin-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 580, 300);
        stage.setTitle("Log In (Debug)");
        DebugController debugController = fxmlLoader.getController();
        debugController.setStage(stage);

        // seting up enter key interaction
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    debugController.onContinueButton();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    /**
     * Handles the click event on the "Scoreboard" button.
     * This method loads the scoreboard view.
     *
     * @throws IOException If an error occurs during loading the FXML file for the scoreboard view.
     */
    @FXML
    protected void onScoreboardButtonClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) scoreboardButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/scoreboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 810);
        stage.setTitle("Scoreboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Handles the click event on the "Back" button.
     * This method returns the user to the main menu.
     */
    @FXML
    protected void goToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu-view.fxml"));
            Parent mainRoot = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();

            Scene scene = new Scene(mainRoot,700, 700);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event on the "Quit" button.
     * This method closes the app
     */
    @FXML
    protected void onQuitButtonClick() {
        Platform.exit();
    }
}