package wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.*;
import java.nio.file.*;
import java.util.Collections;

/**
 * Controller class for creating a new player in the Wordle game application.
 * Handles the logic for registering a new player and transitioning to the game or main menu.
 *
 * @author Syed Muhammed Shahzain (251240894)
 */
public class NewPlayerController {

    /** Button that triggers the creation of a new player and switches to the level selection screen. */
    @FXML
    private Button goButton;

    /** Button that returns the user to the main menu. */
    @FXML
    private Button backButton;

    /** Text field where the user inputs the new player's name. */
    @FXML
    private TextField name;

    /** A path to the "players_list.csv" */
    private Path path = Paths.get("player_list.csv");

    /** A path to the "personal_bests.csv" */
    private Path path2 = Paths.get("personal_bests.csv");

    /**
     * Searches for a player name in the player_list.csv file to check if the new player's player name already exists.
     *
     * @return true if the player name is found, false otherwise.
     * @throws IOException if there is an issue reading from the file.
     */
    static boolean playerNameExists(String name, Path path) throws IOException {

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while (line != null) {
                String[] playerInfo = line.split(",");
                String playerName = playerInfo[0];

                if (name.equals(playerName)){
                    return true;
                }
                line = reader.readLine();
            }

        }catch (NoSuchFileException e) {
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Reads the player list file to find the last used player ID and generates the next ID.
     *
     * @return the next available player ID.
     */
    public static int getNextPlayerId(Path path) {

        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String lastLine = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    lastLine = line;
                }
                if (!lastLine.isEmpty()) {
                    String[] parts = lastLine.split(",");
                    int lastId = Integer.parseInt(parts[1]);
                    return lastId + 1;
                }
            }catch (NoSuchFileException e) {
                // playername cant be empty
                showAlert("Player List Empty", "No Players to Show");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1; // Return 1 if file does not exist or is empty
    }
    /**
     * Saves the new player's name and ID to the player list file.
     *
     * @param name The name of the player to save.
     * @param id The ID of the player to save.
     */
    public static void savePlayer(String name, int id, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(name + "," + id);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes a new player's score records by adding an entry to the personal bests file.
     *
     * @param id The ID of the player for whom to initialize score records.
     */
    public static void addPlayerBests(int id, Path path2){
        try (BufferedWriter writer = Files.newBufferedWriter(path2,StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
            // Generate a string with 30 zeros separated by commas
            String zeros = String.join(",", Collections.nCopies(30, "0"));

            writer.write(id+","+zeros);
            writer.newLine();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of clicking the 'Go' button. Creates a new player record and transitions to the level selection screen.
     *
     * @throws IOException If there is an issue loading the next screen or saving the player data.
     */
    @FXML
    // load into level select screen and save player name and id to player_list csv file
    void onGoButtonClicked() throws IOException {
        // save playername and id to file
        String player_name = name.getText().trim();

        int unique_number = 0;
        if (!player_name.isEmpty()) {
            if (!playerNameExists(player_name, path)){
                unique_number = getNextPlayerId(path);
                savePlayer(player_name, unique_number, path);
                addPlayerBests(unique_number,path2);

                //create levels_file.csv if it doesnt exist
                // add playerid,1 to it

                Path path = Paths.get("levels_file.csv");
                // // Create the file if it doesn't exist, otherwise just append to it
                try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                    // write playerid,1 to it
                    writer.write(unique_number + ",1");
                    writer.newLine(); // Move to the next line
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // switch scenes
                Stage stage = (Stage) goButton.getScene().getWindow();

                FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/levelSelectionView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 700, 700);

                LevelsController levelController = fxmlLoader.getController();
                PlayerModel.setPlayerId(unique_number);
                levelController.updatePlayerIdLabel();
                stage.setTitle("Level Select");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
            else {
                // playername cant be taken
                showAlert("Player Name Taken", "Player Name can't be taken. Please try another.");
            }
        } else {
            // playername cant be empty
            showAlert("Player Name Empty", "Player Name can't be empty. Please try again.");
        }
    }

    /**
     * Displays an alert dialog with a specific title and message.
     *
     * @param title The title of the alert dialog.
     * @param message The message displayed in the alert dialog.
     */
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the action of clicking the 'Back' button. Returns the user to the main menu.
     *
     * @param event The mouse event that triggered this method.
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
