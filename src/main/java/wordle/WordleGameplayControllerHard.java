package wordle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller class for the Wordle game interface (hard mode).
 *
 * @author Emily Christina Atyeo (251281573)
 */
public class WordleGameplayControllerHard implements WordleGameplayController{
    /** TextField where the user inputs their guess. */
    @FXML
    public TextField guessInput;

    /** Label to provide feedback to the user about their guess. */
    @FXML
    public Label feedbackText;

    /** Labels representing the grid of boxes where the letters of the guesses are displayed. */
    @FXML
    private Label box00, box01, box02, box03, box04, box05, box06, box07, box08, box09, box10, box11, box12, box13, box14 = new Label();
    @FXML
    private Label box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25, box26, box27, box28, box29 = new Label();

    /** Button to submit the guess and check it against the word. */
    @FXML
    private Button checkButton;

    /** Button to proceed after completing the game, leading to the next screen. */
    @FXML
    private Button continueButton;

    /** Button to return to the main menu. */
    @FXML
    private Button backButton;

    /** Label displaying the current player's name. */
    @FXML
    private Label playerName;

    /** Label displaying the current player's ID. */
    @FXML
    private Label playerId;

    /** Text field for displaying personal best score information. */
    @FXML
    public Text pbText;

    /** Text field for displaying high score information. */
    @FXML
    private Text hsText;

    /** Label displaying the current game level. */
    @FXML
    private Label game_level;

    /** The word that the player needs to guess. */
    private String word;

    /**
     * Sets the ID of the player. This ID is used to track the player's progress and scores.
     *
     * @param id The unique identifier for the player.
     */
    @Override
    public void setPlayerId(int id) {
        playerId.setText(String.valueOf(id));
    }

    /**
     * Sets the current game level. This level is used to determine the difficulty and the word to be guessed.
     *
     * @param gamelevel The level of the game to be set.
     */
    @Override
    public void setGame_level(int gamelevel) {
        game_level.setText(String.valueOf(gamelevel));
    }

    /**
     * Sets the word that the player needs to guess in the current game level.
     *
     * @param word The word to be guessed in the current level of the game.
     */
    @Override
    public void setGame_word(String word) {
        this.word = word;
    }

    private boolean gameWon = false;

    /** List to store the guesses made by the player. */
    public final ArrayList<String> guesses = new ArrayList<String>();

    /** Player's score for the current game. */
    private static int playerScore = 900;

    /**
     * Handles the action when the user checks their guess.
     *
     */
    @FXML
    public void handleCheckGuess() {
        String guess = guessInput.getText();

        if (guess.length() != 5) {
            feedbackText.setText("Invalid guess. Please enter a 5-letter word.");
            return; // Exit the method if guess is invalid
        } else {
            feedbackText.setText(""); // Clear the text if the guess is valid
        }

        guesses.add(guess); // Store the guess in an array

        int numberOfGuesses = guesses.size();

        if (numberOfGuesses == 1) {
            Label[] row1 = {box00, box01, box02, box03, box04};
            checkGuess(row1);
            playerScore = playerScore - 150;

        } else if (numberOfGuesses == 2) {
            Label[] row2 = {box05, box06, box07, box08, box09};
            checkGuess(row2);
            playerScore = playerScore - 150;

        } else if (numberOfGuesses == 3) {
            Label[] row3 = {box10, box11, box12, box13, box14};
            checkGuess(row3);
            playerScore = playerScore - 150;

        } else if (numberOfGuesses == 4) {
            Label[] row4 = {box15, box16, box17, box18, box19};
            checkGuess(row4);
            playerScore = playerScore - 150;

        } else if (numberOfGuesses == 5) {
            Label[] row5 = {box20, box21, box22, box23, box24};
            checkGuess(row5);
            playerScore = playerScore - 150;

        } else if (numberOfGuesses == 6) {
            Label[] row6 = {box25, box26, box27, box28, box29};
            checkGuess(row6);
            playerScore = playerScore - 150;
        }

    }

    /**
     * Checks the user's guess against the mystery word.
     *
     * @param rowNum An array of Labels representing a row of letter boxes.
     */
    @FXML
    protected void checkGuess(Label[] rowNum) {
        String guess = guessInput.getText().toLowerCase();

        boolean allGreen = true;

        // Loop through each character of the guessed word
        for (int i = 0; i < guess.length(); i++) {
            String letter = guess.substring(i, i + 1);
            rowNum[i].setText(letter);

            // Check if the guessed letter matches the corresponding letter in the mystery word
            if (letter.equals(word.substring(i, i + 1))) {
                // If the guessed letter matches, set the background color of the Label to green
                rowNum[i].setStyle("-fx-background-color: #8EEDA1;");

            } else if (word.indexOf(letter) > -1) {
                // If the guessed letter exists in the mystery word but is not in the correct position,
                // set the background color of the Label to yellow
                rowNum[i].setStyle("-fx-background-color: #FFF569;");
                allGreen = false; // If any letter is not green, set the flag to false

            } else {
                // If the guessed letter doesn't exist in the mystery word,
                // set the background color of the Label to gray
                rowNum[i].setStyle("-fx-background-color: #A0A0A0;");
                allGreen = false; // If any letter is not green, set the flag to false

            }
        }

        if (allGreen) {
            feedbackText.setText("Congratulations! You've won this level.");
            gameWon = true;

            // Increment the highest level unlocked
            //levelService.incrementHighestLevelUnlocked();

            // Disable the input field and the check button
            guessInput.setDisable(true);
            checkButton.setDisable(true);
            continueButton.setVisible(true);

            // update scores if not in debug mode
            if (Integer.parseInt(playerId.getText())!=0){
                updatePersonalBests(playerId, game_level, playerScore);
                updateLevelHighScore(playerId, game_level, playerScore, "level_highscore.csv");

                //update levels file, increment lvls unlocked by 1 for correct playerid
                addLevelUnlocked(playerId.getText(), "levels_file.csv", game_level);
            }

        } else if (guesses.size() == 6) {
            feedbackText.setText("Sorry, you've lost this level.");

            playerScore = 0;

            guessInput.setDisable(true);
            checkButton.setDisable(true);
            continueButton.setVisible(true);

        }
    }

    /**
     * Increments the number of levels unlocked for a specific player in the game based on current game level
     * This method reads from a file to find the current number of unlocked levels for the player,
     * increments current game level by 1, and then writes the updated data back to the file.
     *
     * @param playerId The ID of the player for whom the unlocked level count should be incremented.
     * @param filePath The path to the file where player progress data, including unlocked levels, is stored.
     * @param game_level current game level chosen to play by player
     */
    public static void addLevelUnlocked (String playerId, String filePath, Label game_level) {
        int level = Integer.parseInt(game_level.getText());
        Path path = Paths.get(filePath);
        Path tempPath = Paths.get(filePath + ".tmp");

        try (BufferedReader reader = Files.newBufferedReader(path);
             BufferedWriter writer = Files.newBufferedWriter(tempPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].trim().equals(playerId)) {
                    // Found the line to modify

                    //check if old level unlocked higher than current. So going back to a previously completed level doesn't revert progress
                    int oldLevelsUnlocked = Integer.parseInt(parts[1]);
                    int levelsUnlocked = level+1;
                    if (oldLevelsUnlocked<levelsUnlocked){
                        parts[1] = String.valueOf(levelsUnlocked);
                    }
                    else {
                        parts[1] = String.valueOf(oldLevelsUnlocked);
                    }
                    line = String.join(",", parts);
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            // Replace the original file with the updated temporary file
            Files.move(tempPath, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the personal best scores for a player in a game.
     *
     * @param playerId    The label containing the player's ID.
     * @param game_level  The label containing the level of the game.
     * @param playerScore The player's score to be compared with personal bests.
     */
    @FXML
    protected void updatePersonalBests(Label playerId, Label game_level, int playerScore) {
        // Extract level and player ID from the provided labels
        int level = Integer.parseInt(game_level.getText());
        int playerIdValue = Integer.parseInt(playerId.getText());

        // File containing personal best scores
        Path path = Paths.get("personal_bests.csv");

        // Check if the file exists
        if (!Files.exists(path)) {
            // If the file does not exist, create it
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // ArrayList to store personal best scores
        String[] personalBests = new String[0];
        int counter=0;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // find correct player id
                if (Integer.parseInt(data[0]) == playerIdValue) {
                    // "Slicing" the array to exclude the first element
                    personalBests= Arrays.copyOfRange(data, 1, data.length);
                    // Add the data to the personalBests list
                    break;
                }
                // the line number the played id was found
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int currentScore = Integer.parseInt(personalBests[level-1]);

        // Check if the new score is better than the current one
        if (currentScore < playerScore) {
            // Update the score if it's better
            personalBests[level-1] = String.valueOf(playerScore);
            String pb = String.join(",",personalBests);
            String newLine = playerId.getText()+","+pb;

            //now write it back
            try {
                // Read all lines from the file
                List<String> lines = Files.readAllLines(path);

                // Replace the  line
                lines.set(counter, newLine); // List is 0-indexed

                // Write the lines back to the file (this overwrites the original file)
                Files.write(path, lines);

                pbText.setVisible(true);

                //System.out.println("The line has been successfully replaced.");
            } catch (IOException e) {
                System.err.println("An error occurred while reading or writing the file: " + e.getMessage());
            }
        }
    }

    /**
     * Updates the high scores for a specific level in the game.
     *
     * @param playerId    The label containing the player's ID.
     * @param game_level  The label containing the level of the game.
     * @param playerScore The player's score to be compared with high scores.
     * @param pathName the name of the csv file.
     */
    @FXML
    protected void updateLevelHighScore(Label playerId, Label game_level, int playerScore, String pathName) {
        // Extract level and player ID from the provided labels
        int level = Integer.parseInt(game_level.getText());
        int playerIdValue = Integer.parseInt(playerId.getText());

        // File containing level-wise high scores
        Path path = Paths.get(pathName);

        try {
            List<String> lines = Files.readAllLines(path);


            // Adjust for zero-based indexing
            int lineIndex = level - 1;


            if (lineIndex < lines.size()) {
                String[] parts = lines.get(lineIndex).split(",");
                int currentHighScore = Integer.parseInt(parts[1]);


                // Update the high score if the new score is higher
                if (playerScore > currentHighScore) {
                    String updatedLine = game_level.getText() + "," + playerScore + "," + playerId.getText();
                    lines.set(lineIndex, updatedLine);


                    // Write the updated content back to the file
                    Files.write(path, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                    //System.out.println("High score updated for game level " + gameLevel);


                    hsText.setVisible(true);
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Advances to the result screen after completing a game.
     *
     * @param event The MouseEvent triggered by clicking the continue button.
     * @throws IOException If there is an issue loading the result screen.
     */
    @FXML
    private void onContinueButtonClicked(MouseEvent event) throws IOException {
        // Going to result screen
        Stage stage = (Stage) continueButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(GameResultController.class.getResource("/gameResult-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);

        // Pass gameWon and playerScore to the GameResultController
        GameResultController resultController = fxmlLoader.getController();
        resultController.setGameOutcome(gameWon);
        resultController.setPlayerScore(playerScore+150);

        stage.setTitle("Game Result Screen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Returns to the main menu screen.
     *
     * @param event The MouseEvent triggered by clicking the back button.
     * @throws IOException If there is an issue loading the main menu screen.
     */
    @FXML
    private void onBackButtonClicked(MouseEvent event) throws IOException {
        // switch scenes
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/MainMenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
