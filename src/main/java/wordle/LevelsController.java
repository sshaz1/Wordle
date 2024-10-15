package wordle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller class for handling level selection and management in the Wordle game.
 * This class allows the player to choose and access game levels based on their difficulty and unlocked status.
 *
 * @author Izzeddin Albirawi (251278038)
 */
public class LevelsController {

    /** Button to select the easy difficulty level. */
    @FXML
    private Button easyButton;

    /** Button to navigate back to the previous screen. */
    @FXML
    private Button backButton;

    /** Button to select the medium difficulty level. */
    @FXML
    private Button mediumButton;

    /** Button to select the hard difficulty level. */
    @FXML
    private Button hardButton;

    /** Buttons representing individual levels, allowing selection of specific game levels. */
    @FXML
    private Button level1, level2, level3, level4, level5, level6, level7, level8, level9, level10;
    @FXML
    private Button level11, level12, level13, level14, level15, level16, level17, level18, level19, level20;
    @FXML
    private Button level21, level22, level23, level24, level25, level26, level27, level28, level29, level30;

    /** Array of buttons for each level, used for managing level access and states. */
    private Button[] levelButtons;

    /** The highest level unlocked by the player, determining which levels are accessible. */
    public int highestLevelUnlocked;

    /** Label displaying the player's ID, used for tracking progress and scores. */
    @FXML
    Label playerId;

    /** Debug mode flag, used for enabling or disabling debug features. */
    private boolean debugMode=false;

    /** Path to the CSV file storing the levels data. */
    private static final String levels_file = "levels_file.csv";

    /**
     * Sets the player ID in the label on the level selection screen.
     *
     * @param id the player's ID to set
     */
    public void setPlayerId(int id) {
        playerId.setText(String.valueOf(id));
    }

    /**
     * Enables or disables debug mode.
     *
     * @param debug true to enable debug mode, false to disable
     */
    public void setDebug(boolean debug){debugMode=debug;}

    /**
     * Updates the label displaying the player's ID with the current value.
     */
    public void updatePlayerIdLabel() {
        int id = PlayerModel.getPlayerId();
        playerId.setText(String.valueOf(id));
    }

    /**
     * Retrieves the highest level unlocked by the player.
     *
     * @param playerId the player's ID to query the highest level unlocked
     * @param filename the filename of the csv file.
     * @return the highest level unlocked by the player
     */
    public int getPlayerHighestLevelUnlocked(int playerId, String filename) {
        Path path = Paths.get(filename);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int currentId = Integer.parseInt(parts[0].trim());
                    if (currentId == playerId) {
                        return Integer.parseInt(parts[1].trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1; // Return 1 if file does not exist, the player ID is not found, or is empty
    }

    /**
     * Handles actions performed on level buttons.
     * Loads the game for the selected level if it is unlocked.
     *
     * @param event the action event that triggered this method
     */
    @FXML
    private void handleLevelButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int levelNumber = Integer.parseInt(clickedButton.getText());

        if (levelNumber <= highestLevelUnlocked) {
            loadLevel(levelNumber, event);
        } else {
            System.out.println("Level " + levelNumber + " is locked.");
        }
    }

    /**
     * Loads the game for a given level number.
     * Determines the scene to load based on the level difficulty.
     *
     * @param levelNumber the level number to load
     */
    private void loadLevel(int levelNumber, ActionEvent event) {
        String sceneFile;
        if (levelNumber <= 10) {
            sceneFile = "/wordleGameplayEasy-view.fxml"; //Needs to be going to the gameplay with 3 letters
        } else if (levelNumber <= 20) {
            sceneFile = "/wordleGameplayMedium-view.fxml"; //Needs to be going to the gameplay with 4 letters
        } else {
            sceneFile = "/wordleGameplayHard-view.fxml"; //Needs to be going to the gameplay with 5 letters
        }

        try {
            updatePlayerIdLabel();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFile));
            Parent levelRoot = loader.load();

            WordleGameplayController gameplayController = loader.getController();

            gameplayController.setPlayerId(Integer.parseInt(playerId.getText()));
            gameplayController.setGame_level(levelNumber);
            gameplayController.setGame_word(getLevelWord(levelNumber));

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = (new Scene(levelRoot,800, 800));

            // Setting up enter key interaction
            scene.setOnKeyPressed(keyEvent -> { // Renamed to avoid confusion with the outer 'event'
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    // Assuming onGoButtonClicked() is what you want to call
                    // and it's a method in WordleGameplayController based on the context
                    gameplayController.handleCheckGuess(); // Directly use the gameplayController reference
                }
            });

            stage.setTitle("Level " + levelNumber);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading level " + levelNumber);
        }
    }

    /**
     * Updates the accessibility of level buttons based on the highest level unlocked by the player.
     */
    private void updateLevelAccess() {
        updatePlayerIdLabel();
        if ((Integer.parseInt(playerId.getText()))==0){
            highestLevelUnlocked=30;
        }
        else{
            highestLevelUnlocked = getPlayerHighestLevelUnlocked(Integer.parseInt(playerId.getText()), "levels_file.csv");
        }
        for (int i = 0; i < levelButtons.length; i++) {
            if (levelButtons[i] == null) {
                //System.out.println("Button at index " + i + " is null.");
                continue;
            }
            //updatePlayerIdLabel();
            levelButtons[i].setDisable(i + 1 > highestLevelUnlocked);
        }
    }

    /**
     * An array of words corresponding to levels in the game.
     * Words are categorized by difficulty: easy (1-10), medium (11-20), and hard (21-30).
     */
    private static String[] levelWords = {
            // Easy
            "cat", "hat", "mug", "ant", "fan", "box", "pot", "fog", "wig", "ivy",
            //Medium
            "desk", "ring", "cave", "coat", "boat", "park", "cape", "clip", "isle", "veil",
            //Hard
            "glove", "ocean", "frame", "braid", "zebra", "olive", "ghost", "delta", "write", "coral"
    };

    /**
     * Retrieves the word associated with a specific level number.
     *
     * @param levelNumber the level number for which to get the word
     * @return the word associated with the given level number
     */
    static String getLevelWord(int levelNumber) {
        return levelWords[levelNumber - 1];
    }

    /**
     * Initializes the controller after the FXML file has been loaded. This method sets up the level buttons
     * and updates the level access and player ID label based on the current player data.
     */
    @FXML
    public void initialize() {
        levelButtons = new Button[]{
                level1, level2, level3, level4, level5,
                level6, level7, level8, level9, level10,
                level11, level12, level13, level14, level15,
                level16, level17, level18, level19, level20,
                level21, level22, level23, level24, level25,
                level26, level27, level28, level29, level30
        };
        updateLevelAccess();
        updatePlayerIdLabel();
    }

    /**
     * Handles the action to go back to the level selection menu.
     */
    @FXML
    private void handleBackButtonAction(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/levelSelectionView.fxml"));
            Parent easyLevelRoot = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow(); //Hard button and Hardlevel view are placeholders, needs to go to the main menu

            Scene scene = new Scene(easyLevelRoot, 700, 700);
            stage.setScene(scene);
            stage.setTitle("Level Select");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles the action when the Easy button is clicked. This method loads the view for easy levels.
     */
    @FXML
    protected void handleEasyButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EasyLevelView.fxml"));
            Parent easyLevelRoot = loader.load();

            Stage stage = (Stage) easyButton.getScene().getWindow();

            Scene scene = new Scene(easyLevelRoot,700,700);
            stage.setScene(scene);
            stage.setTitle("Easy Level");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the Medium button is clicked. This method loads the view for medium levels.
     */
    @FXML
    protected void handleMediumButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MediumLevelView.fxml"));
            Parent easyLevelRoot = loader.load();

            Stage stage = (Stage) mediumButton.getScene().getWindow();

            Scene scene = new Scene(easyLevelRoot,700,700);
            stage.setScene(scene);
            stage.setTitle("Medium Level");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the Hard button is clicked. This method loads the view for hard levels.
     */
    @FXML
    protected void handleHardButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HardLevelView.fxml"));
            Parent easyLevelRoot = loader.load();

            Stage stage = (Stage) hardButton.getScene().getWindow();

            Scene scene = new Scene(easyLevelRoot,700,700);
            stage.setScene(scene);
            stage.setTitle("Hard Level");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to return to the main menu of the game.
     */
    @FXML
    protected void goToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu-view.fxml"));
            Parent mainRoot = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();

            Scene scene = new Scene(mainRoot,700, 700);
            stage.setScene(scene);
            stage.setTitle("Main Menu");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
