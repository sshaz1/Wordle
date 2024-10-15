package wordle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * A Controller class that manages all the interactions with the buttons and the information to be displayed in the "Login", "Progress/Results", and "Player's Progress/Results" screens.
 *
 * @author cboulos4 (251267786)
 */
public class LoginController {

    /** A button that, once clicked, goes to the "Progress/Results" screen. The button is displayed in the "Login Screen"*/
    @FXML
    private Button continueButton;

    /** A button that, once clicked goes back to the "Main Menu" screen. The button is displayed in the "Login" screen */
    @FXML
    private Button backToMainMenuButton;

    /** A button that, once clicked, goes back to the "Login" screen. The button is displayed in the "Progress/Results" screen */
    @FXML
    private Button backToLoginButton;

    /** A button that, once clicked, goes back to the Player List screen. The button is displayed in the "Player-Progress/Results" screen */
    @FXML
    private Button backToPlayerListButton;

    /** A text field where the user can input the required password to access the "Progress/Results" screen. */
    @FXML
    private TextField passwordInput;

    /** A label that will display "Incorrect Password!" when it is set to be visible. */
    @FXML
    private Label incorrectPassword;

    /** A list that holds all the player names. */
    private static ArrayList<String> playersNameList = new ArrayList<>();


    /** A label that displays "No players are currently registered in the game */
    @FXML
    private Label noPlayersRegistered;

    /** A label that will display "! Please choose a player. !" when the "Display Information" button is clicked and no player has been chosen from the ComboBox. */
    @FXML
    private Label noPlayerChosen;

    /** A label that will display  " */
    @FXML
    private Label playerInfoScreenIsOpen;

    /** The player name that was chosen in the ComboBox. */
    private static String chosenPlayerText;

    /** The player ID of the player whose player name was chosen in the ComboBox */
    private static String chosenPlayerID;

    /** The total score earned across different levels by the player chosen in the ComboBox */
    private static int chosenPlayerScore;

    /** An array list containing all the level scores completed by the player chosen in the ComboBox. It stores the score of a level if the level has been completed. */
    private static ArrayList<Integer> chosenPlayerLevelScores ;

    /** An array list containing the highest score achieved by players in all levels if they've been completed */
    private static ArrayList<Integer> highestScoreInAllLevels;

    /** An array list containing all the stages created when clicking the "Display Information" button in the "Progress/Results" screen. Made for the purpose of closing all parallel scenes when the "Back To Login" button is pressed which calls {@link #backToLogin()}  */
    private static ArrayList<Stage> parallelProgressStages = new ArrayList<>();

    /** An array list containing all the stage titles created when clicking the "Display Information" button in the "Progress/Results" screen. Made for the purpose of closing all parallel scenes when the "Back To Login" button is pressed which calls {@link #backToLogin()}  */
    private static ArrayList<String> openParallelStagesTitles = new ArrayList<>();

    private Stage stage; // Assume this is initialized correctly

    /** A path to "player_list.csv" */
    private final Path path = Paths.get("player_list.csv");

    /** A path to "personal_bests.csv" */
    private final Path path2 = Paths.get("personal_bests.csv");

    /** A path to "level_highscore.csv" */
    private final Path path3 = Paths.get("level_highscore.csv");

    /**
     * Sets the primary stage for the controller.
     *
     * @param stage The main application window or stage that this controller will control.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * A method that is called when the "Continue" button in the "Login" screen is pressed.
     * It first checks if the password inputted is the correct password. If it is the correct password, then it will close the "Login" screen,
     * populate the combo box "playersComboBox" with player information using the {@link #getPlayerNames(Path path)} method, and then show the "Progress/Results" screen.
     * If the password is incorrect, it will clear the password text field and display "Incorrect Password!".
     *
     * @throws IOException throws an IOException when FXMLLoader fails to locate or load "progress-results-screen.fxml"
     */
    @FXML
    protected void continueToProgressResults() throws IOException {
        String input = passwordInput.getText();

        if (input.equals("CS2212")) {
            getPlayerNames(path);

            // Checks if there are any players currently registered in the game and sets the "noPlayersRegistered" label's text to be visible and then returns
            if (playersNameList.size() == 0){
                noPlayersRegistered.setVisible(true);
                return;
            }

            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/progress-results-screen.fxml"));
            Stage primaryStage = new Stage();

            // Locating the playerComboBox combo box in the FXML file to add options to it and set an onAction.
            // A combo box that displays all the player names from the playersNameList as options in the ComboBox.
            ComboBox<String> playersComboBox = (ComboBox<String>) root.lookup("#playersComboBox");
            playersComboBox.getItems().addAll(playersNameList);
            EventHandler<ActionEvent> event =
                    new EventHandler<>() {
                        public void handle(ActionEvent e) {
                            chosenPlayerText = playersComboBox.getValue();
                        }
                    };
            playersComboBox.setOnAction(event);

            primaryStage.setTitle("Choose a Player");
            primaryStage.setScene(new Scene(root, 480, 300));
            primaryStage.centerOnScreen();
            primaryStage.show();
        }

        else{
            passwordInput.setText("");
            incorrectPassword.setVisible(true);
        }
    }

    /**
     * A method that is called when the "Display Information" button is clicked in the "Progress/Results".
     * It checks if the user (the administrator/teacher/developer) picked a player from the options in the combobox.
     * If they didn't choose a player, then it will make the "noPlayerChosen" label visible which will then display "! Please pick a player !".
     *
     * If they did choose a player, then it will open a parallel screen (so both the "Progress/Results" screen and the "Player's Progress/Results" screen will be open.
     * It will then set the "chosenPlayerLabel"'s text to the chosen player's name, and then call the method "getChosenPlayerInfo" {@link #getChosenPlayerInfo()} to gather the chosen player's information..
     * It will also set the background color of the level labels to green if the levels have been completed by the chosen player, and sets it to purple if they currently hold the highest score among all users in a certain level or levels.
     *
     * @throws IOException throws an IOException when FXMLLoader fails to locate or load "player-progressResults-screen.fxml"
     */
    @FXML
    private void checkPlayerProgressResults() throws IOException {

        // Checks to see if the administrator/developer/teacher chose a player from the options in the ComboBox.
        if (chosenPlayerText != null) {
            Stage primaryStage = new Stage();
            primaryStage.setTitle(chosenPlayerText);

            noPlayerChosen.setVisible(false); // Sets the label's visibility to false, in case it was ever set to be visible
            playerInfoScreenIsOpen.setVisible(false); // Sets the label's visibility to false, in case it was ever set to be visible

            // Removes the stage and its title from the parallelProgressStages and openParallelStagesTitles respectively whenever the "X" button on the top right is clicked
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    openParallelStagesTitles.remove(primaryStage.getTitle());
                    parallelProgressStages.remove(primaryStage);

                }
            });

            // First checks to see if the stage is already in the array list "openParallelStagesTitles". If it is not, then it adds the stage to the "parallelProgressStages"
            // so that the "backToLogin()" method can use the stages in the array list to close them when the "Back To Login" button is clicked in the "Progress/Results" screen.
            // If it is already in "openParallelStagesTitles", then the sets the "playerInfoScreenIsOpen" to be visible which would display "The player's information screen is already open!".
            if (!(openParallelStagesTitles.contains(primaryStage.getTitle()))){
                openParallelStagesTitles.add(primaryStage.getTitle());
                parallelProgressStages.add(primaryStage);
            }

            else if (openParallelStagesTitles.contains(primaryStage.getTitle())){
                playerInfoScreenIsOpen.setVisible(true);
                return;
            }

            Parent root = FXMLLoader.load(getClass().getResource("/player-progressResults-screen.fxml"));

            // Locating the chosenPlayerLabel in the FXML file to format it.
            // It is the label that will display the chosen player's player name.
            Label chosenPlayerLabel = (Label) root.lookup("#chosenPlayerLabel");
            chosenPlayerLabel.setText(chosenPlayerText + "'s ");

            // A method that contains other methods to acquire the chosen player's information such as their current score, level completions, and the levels they have the highest score in among other player.
            getChosenPlayerInfo();

            // Locating each level label in the FXML file to format them.
            Label level1 = (Label) root.lookup("#level1");
            Label level2 = (Label) root.lookup("#level2");
            Label level3 = (Label) root.lookup("#level3");
            Label level4 = (Label) root.lookup("#level4");
            Label level5 = (Label) root.lookup("#level5");
            Label level6 = (Label) root.lookup("#level6");
            Label level7 = (Label) root.lookup("#level7");
            Label level8 = (Label) root.lookup("#level8");
            Label level9 = (Label) root.lookup("#level9");
            Label level10 = (Label) root.lookup("#level10");
            Label level11 = (Label) root.lookup("#level11");
            Label level12 = (Label) root.lookup("#level12");
            Label level13 = (Label) root.lookup("#level13");
            Label level14 = (Label) root.lookup("#level14");
            Label level15 = (Label) root.lookup("#level15");
            Label level16 = (Label) root.lookup("#level16");
            Label level17 = (Label) root.lookup("#level17");
            Label level18 = (Label) root.lookup("#level18");
            Label level19 = (Label) root.lookup("#level19");
            Label level20 = (Label) root.lookup("#level20");
            Label level21 = (Label) root.lookup("#level21");
            Label level22 = (Label) root.lookup("#level22");
            Label level23 = (Label) root.lookup("#level23");
            Label level24 = (Label) root.lookup("#level24");
            Label level25 = (Label) root.lookup("#level25");
            Label level26 = (Label) root.lookup("#level26");
            Label level27 = (Label) root.lookup("#level27");
            Label level28 = (Label) root.lookup("#level28");
            Label level29 = (Label) root.lookup("#level29");
            Label level30 = (Label) root.lookup("#level30");

            // Locating each score label in the FXML file to format them. "score1" represents the score the chosen player acquired in the first level.
            Label score1 = (Label) root.lookup("#score1");
            Label score2 = (Label) root.lookup("#score2");
            Label score3 = (Label) root.lookup("#score3");
            Label score4 = (Label) root.lookup("#score4");
            Label score5 = (Label) root.lookup("#score5");
            Label score6 = (Label) root.lookup("#score6");
            Label score7 = (Label) root.lookup("#score7");
            Label score8 = (Label) root.lookup("#score8");
            Label score9 = (Label) root.lookup("#score9");
            Label score10 = (Label) root.lookup("#score10");
            Label score11 = (Label) root.lookup("#score11");
            Label score12 = (Label) root.lookup("#score12");
            Label score13 = (Label) root.lookup("#score13");
            Label score14 = (Label) root.lookup("#score14");
            Label score15 = (Label) root.lookup("#score15");
            Label score16 = (Label) root.lookup("#score16");
            Label score17 = (Label) root.lookup("#score17");
            Label score18 = (Label) root.lookup("#score18");
            Label score19 = (Label) root.lookup("#score19");
            Label score20 = (Label) root.lookup("#score20");
            Label score21 = (Label) root.lookup("#score21");
            Label score22 = (Label) root.lookup("#score22");
            Label score23 = (Label) root.lookup("#score23");
            Label score24 = (Label) root.lookup("#score24");
            Label score25 = (Label) root.lookup("#score25");
            Label score26 = (Label) root.lookup("#score26");
            Label score27 = (Label) root.lookup("#score27");
            Label score28 = (Label) root.lookup("#score28");
            Label score29 = (Label) root.lookup("#score29");
            Label score30 = (Label) root.lookup("#score30");

            // An array that holds the level labels that will be displayed in the chosen players information screen ("Player's Progress/Results" screen)
            Label[] levelLabels = {level1, level2, level3, level4, level5, level6, level7, level8, level9, level10,
                    level11, level12, level13, level14, level15, level16, level17, level18, level19, level20,
                    level21, level22, level23, level24, level25, level26, level27, level28, level29, level30};

            // An array that holds the score labels that will be displayed in the chosen players information screen ("Player's Progress/Results" screen)
            Label[] scoreLabels = {score1, score2, score3, score4, score5, score6, score7, score8, score9, score10,
                    score11, score12, score13, score14, score15, score16, score17, score18, score19, score20,
                    score21, score22, score23, score24, score25, score26, score27, score28, score29, score30};

            // Sets the background color of the level labels to green if the levels have been completed by the chosen player. It is completed if the level has a designated score not equal to 0.
            // Sets the text of the score labels to the respective score the chosen player acquired.
            for (int i = 0; i < chosenPlayerLevelScores.size(); i++) {
                if (chosenPlayerLevelScores.get(i) != 0) {
                    levelLabels[i].setStyle("-fx-background-color: #4bec7e;");
                    scoreLabels[i].setText(String.valueOf(chosenPlayerLevelScores.get(i)));
                }
            }

            Label[] easy = {score1, score2, score3, score4, score5, score6, score7, score8, score9, score10}; // The score labels that represent the easy levels respectively
            Label[] medium = {score11, score12, score13, score14, score15, score16, score17, score18, score19, score20}; // The score labels that represent the medium levels respectively
            Label[] hard = {score21, score22, score23, score24, score25, score26, score27, score28, score29, score30}; // The score labels that represent the hard levels respectively

            int personalHighestEasy = personalHighestScoreInDifficulty(easy); // Calls the highestScoreInDifficulty method with easy as its parameter to retrieve the highest score achieved in that difficulty.
            int personalHighestMedium = personalHighestScoreInDifficulty(medium); // Calls the highestScoreInDifficulty method with medium as its parameter to retrieve the highest score achieved in that difficulty.
            int personalHighestHard = personalHighestScoreInDifficulty(hard); // Calls the highestScoreInDifficulty method with hard as its parameter to retrieve the highest score achieved in that difficulty.

            // Looks up the labels in the FXML file to format them.
            // Sets the labels' text to the value returned from calling the "personalHighestScoreInDifficulty()" method
            Label personalBestEasyScore = (Label) root.lookup("#personalBestEasyScore");
            personalBestEasyScore.setText(Integer.toString(personalHighestEasy));
            Label personalBestMediumScore = (Label) root.lookup("#personalBestMediumScore");
            personalBestMediumScore.setText(Integer.toString(personalHighestMedium));
            Label personalBestHardScore = (Label) root.lookup("#personalBestHardScore");
            personalBestHardScore.setText(Integer.toString(personalHighestHard));

            getHighestScoreInAllLevels(path3, false); // Gets the levels that the chosen player has the highest score in among other players

            // Sets the background color of the level label purple if the chosen user holds the highest score in that level among other players
            for (int i = 0; i < highestScoreInAllLevels.size(); i++) {
                if (highestScoreInAllLevels.get(i) != null) {
                    levelLabels[i].setStyle("-fx-background-color:  #cc00ff;");
                }
            }

            // Locating the chosenPlayerScoreLabel in the FXML file to format it.
            // It is the label that will display the chosen player's total score among the levels they have completed.
            Label chosenPlayerScoreLabel = (Label) root.lookup("#chosenPlayerScoreLabel");
            chosenPlayerScoreLabel.setText(Integer.toString(chosenPlayerScore));

            primaryStage.setScene(new Scene(root, 650, 680));
            primaryStage.centerOnScreen();
            primaryStage.show();
        }

        else{
            noPlayerChosen.setVisible(true); // Sets the label to be visible and display "! Please pick a player !" if the user did not pick a player from the combo box
        }
    }

    /**
     * A method that is called when the "Back to Main Menu" button is clicked in the "Login" screen.
     * It closes the current screen, which is the "Login" screen, and opens the "Main Menu" screen.
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

    /**
     * A method that is called when the "Back to Login" button is clicked in the "Progress/Results" screen.
     * It closes all open parallel screens (the chosen players' information screens) and clears the array lists "parallelProgressStages" and "openParallelStages".
     * It also closes the "Progress/Results" screen and opens the "Login" screen.
     *
     * @throws IOException throws an IOException when FXMLLoader fails to locate or load "login-screen.fxml"
     */
    @FXML
    protected void backToLogin() throws IOException {
        Stage stage = (Stage) backToLoginButton.getScene().getWindow();
        stage.close();

        // Closes all the parallel stages if there are any currently open
        for (int i = 0; i < parallelProgressStages.size(); i++) {
            parallelProgressStages.get(i).close();
        }
        parallelProgressStages.clear(); // Clears the "parallelProgressStages" array list
        openParallelStagesTitles.clear(); // Clears the "openParallelStagesTitles" array list
        playersNameList.clear();

        // Sets the chosenPlayerText to null whenever the "Back To Login" button is pressed so that the "! Please pick a player !" label
        // is set to visible if no player was chosen in the "Progress/Results" screen after going back to the login screen
        chosenPlayerText = null;

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/login-screen.fxml"));
        primaryStage.setTitle("Choose a Player");
        primaryStage.setScene(new Scene(root, 580, 300));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Called when the back to player list button is clicked. This method switches the view back to the player list.
     *
     * @throws IOException If an error occurs during loading the player list screen FXML.
     */
    @FXML
    protected void backToPlayerList() throws IOException {
        Stage stage = (Stage) backToPlayerListButton.getScene().getWindow();
        stage.close();

        // Closes all the parallel stages if there are any currently open
        for (int i = 0; i < parallelProgressStages.size(); i++) {
            parallelProgressStages.get(i).close();
        }
        parallelProgressStages.remove(stage); // Removes the current stage from the "parallelProgressStages" array list
        openParallelStagesTitles.remove(stage.getTitle()); // Removes the current stage's title from the "openParallelStagesTitles" array list
    }


    /**
     * This method reads the CSV file containing each player's player name and player ID, and populates the "playerNameList" ArrayList with the player names.
     * This is used to occupy the ComboBox with the players' names as options.
     *
     * @throws RuntimeException throws a RuntimeException if an IOException occurs while reading the file.
     */
    public static ArrayList<String> getPlayerNames(Path path)  {

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while (line != null) {
                String[] playerInfo = line.split(",");
                String playerName = playerInfo[0];
                playersNameList.add(playerName);
                line = reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return playersNameList; // Unit testing component
    }

    /**
     * A method that calls other methods to acquire the chosen player's information such as their PlayerID {@link #getPlayerID(Path path, Boolean isTest)}, their current
     * total score from all completed levels {@link #getPlayerScore(Path path2, Boolean isTest)}, and their current completed level )}
     */
    private void getChosenPlayerInfo() {
        getPlayerID(path, false);
        getPlayerScore(path2, false);

    }

    /**
     * A method that gets the chosen player's PlayerID by reading the "player_list.csv" file.
     * It traverses each line, splits them at the commas, and adds them to an array "playerInfo".
     * The PlayerName would be the first element of the array ("playerInfo[0]").
     * It checks to see if the chosen player's PlayerName ("chosenPlayerText") is the same as the player name ("playerInfo[0]") that was read from the line.
     * If the PlayerNames match, then it would take the next element from the array "playerInfo", which is the PlayerID, and assign it to a variable called "chosenPlayerID".
     *
     * @return chosenPlayerID returns the chosen player's ID as a string and ends the search when the chosen player has been found (unit testing)
     * @return "" returns "" when the chosen player is not found in the csv file (unit testing)
     * @throws RuntimeException throws a RuntimeException if an IOException occurs while reading the file.
     */
    public static String getPlayerID(Path path, Boolean isTest) {

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while (line != null) {
                String[] playerInfo = line.split(",");
                String playerName = playerInfo[0];
                String unitTestName = "John Doe"; // Unit testing component

                if (playerName.equals(chosenPlayerText)){
                    chosenPlayerID = playerInfo[1];
                }

                // Unit testing component
                if (playerName.equals(unitTestName) && isTest) {
                    chosenPlayerID = playerInfo[1];
                    return chosenPlayerID;
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ""; // Unit testing component
    }

    /**
     * A method that gets the chosen player's PlayerScore by reading the "personal_bests".csv" file.
     * It traverses each line, splits them at the commas, and adds them to an array "playerInfo".
     * The PlayerID would be the first element of the array ("playerInfo[0]").
     * It checks to see if the chosen player's player ID ("chosenPlayerID") is the same as the PlayerID ("playerInfo[0]") that was read from the line.
     * If the player IDs match, then it traverses the array (starting at playerInfo[1]), turns the elements into an integer by using "Integer.parseInt(element)", and then adds them to a variable called "chosenPlayerScore".
     * It also adds them to the array list "chosenPlayerLevelScores".
     *
     * @return unitTestPlayerScore returns the chosen player's total score across all levels (unit testing)
     * @return 0 returns 0 when the chosen player ID is not found in the csv file (unit testing)
     * @throws RuntimeException throws a RuntimeException if an IOException occurs while reading the file.
     */
    public static int getPlayerScore(Path path2, Boolean isTest){

        try (BufferedReader reader = Files.newBufferedReader(path2)) {
            String line = reader.readLine();
            chosenPlayerScore = 0; // Set the chosenPlayerScore to 0 so that it resets whenever the "Display Information" button is pressed in the "Progress/Results" screen
            chosenPlayerLevelScores = new ArrayList<>(); // Create an ArrayList whenever a players level information needs to be found

            while (line != null) {
                String[] playerInfo = line.split(",");
                String playerID = playerInfo[0];
                String unitTestChosenPlayerID = "1"; // Unit testing component

                if (playerID.equals(chosenPlayerID)){
                    for (int i = 1; i < playerInfo.length; i++){
                        chosenPlayerScore += Integer.parseInt(playerInfo[i]);
                        chosenPlayerLevelScores.add(Integer.parseInt(playerInfo[i]));
                    }
                }

                // Unit testing component
                int unitTestPlayerScore = 0;
                if (unitTestChosenPlayerID.equals("1") && isTest) {
                    for (int i = 1; i < playerInfo.length; i++) {
                        unitTestPlayerScore += Integer.parseInt(playerInfo[i]);
                    }
                    return unitTestPlayerScore; // Unit testing component
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0; // Unit testing component
    }

    /**
     * A method that returns the highest score achieved by the chosen player in a certain difficulty.
     *
     * @param scores an array containing scores from 10 levels of a certain difficulty.
     * @return highestScore returns the highest score in the array "scores"
     */
    public int personalHighestScoreInDifficulty(Label[] scores){
        int highestScore = 0;

        for (int i = 0; i < scores.length; i++){
            int currentScore = Integer.parseInt(scores[i].getText());

            if (currentScore > highestScore) {
                highestScore = currentScore;
            }
        }
        return highestScore;
    }
    /**
     * A unit testing component.
     * A copy of {@link #personalHighestScoreInDifficulty} but takes in an array of integers rather than an array of Labels
     */
    public static int personalHighestScoreInDifficultyTesting(int[] scores){
        int highestScore = 0;

        for (int i = 0; i < scores.length; i++){
            int currentScore = scores[i];

            if (currentScore > highestScore) {
                highestScore = currentScore;
            }
        }
        return highestScore;
    }
    /**
     * A method that checks to see if the chosen player currently holds the highest score among other players in a certain level.
     *
     * @return levels return the levels that the chosen player holds the highest score in (Unit testing)
     * @throws RuntimeException throws a RuntimeException if an IOException occurs while reading the file.
     */
    public static ArrayList<Integer> getHighestScoreInAllLevels(Path path3, Boolean isTest) {

        try (BufferedReader reader = Files.newBufferedReader(path3)) {
            String line = reader.readLine();

            highestScoreInAllLevels = new ArrayList<>(); // Creates an array list for each chosen player
            int currentLevel = 1; // The current level

            ArrayList<Integer> levels = new ArrayList<>(); // Unit testing component

            while (line != null && currentLevel <= 30) {
                String[] playerInfo = line.split(",");
                String playerID = playerInfo[2]; // The player ID of the player with the highest score in the respective level

                if (playerID.equals(chosenPlayerID)){
                    highestScoreInAllLevels.add(currentLevel);
                }
                else{
                    highestScoreInAllLevels.add(null);
                }

                // Unit testing component
                String unitTestChosenPlayerID = "1"; // Unit testing component
                if (playerID.equals(unitTestChosenPlayerID) && isTest){
                    levels.add(currentLevel);
                }

                currentLevel++;
                line = reader.readLine();
            }
            return levels;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}