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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Controller for the Load Player view in the Wordle game.
 * This class handles the functionality for loading a player's profile based on their ID.
 *
 * @author Syed Muhammed Shahzain (251240894)
 */
public class LoadPlayerController {

    @FXML
    private TextField id;

    @FXML
    private Button goButton;
    @FXML
    private Button backButton;
    private String player_name;

    /**
     * Searches for a player ID in the player_list.csv file and updates the player_name if found.
     *
     * @param playerId The ID of the player to search for.
     * @param filename the filename of the csv file.
     * @return true if the player ID is found, false otherwise.
     * @throws IOException if there is an issue reading from the file.
     */
    public boolean searchForPlayerId(int playerId, String filename) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && Integer.parseInt(parts[1]) == playerId) {
                    player_name = parts[0];
                    reader.close();
                    return true; // ID found
                }
            }
            reader.close();
        }catch (FileNotFoundException e){
            return false;
        }
        return false; // ID not found
    }

    /**
     * Shows an alert dialog with the given title and message.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to be displayed in the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the action of clicking the "Go" button.
     * This method tries to load a player based on the ID entered in the text field.
     *
     */
    @FXML
    public void onGoButtonClicked() {
        // Retrieve the text from the TextField and trim whitespace.
        String idText = id.getText().trim();

        // Attempt to parse the ID as an integer.
        try {
            int playerId = Integer.parseInt(idText);

            // Assuming searchForPlayerId(int id) is a method that returns a boolean.
            if (searchForPlayerId(playerId, "player_list.csv")) {

                // switch scenes
                Stage stage = (Stage) goButton.getScene().getWindow();

                FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/levelSelectionView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 700, 700);

                LevelsController levelController = fxmlLoader.getController();
                PlayerModel.setPlayerId(playerId);
                levelController.updatePlayerIdLabel();
                stage.setTitle("Level Select");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

            } else {
                // ID not found, show an alert.
                showAlert("ID Not Found", "The ID you entered does not exist. Please try again.");
            }
        } catch (NumberFormatException e) {
            // The input text is not a valid integer.
            showAlert("Invalid Input", "Please enter a valid integer ID.");
        } catch (IOException e) {
            // An IO exception occurred while attempting to switch scenes or search for the player ID.
            e.printStackTrace();
            showAlert("Error", "An error occurred while reading the player list.");
        }
    }

    /**
     * Handles the action of clicking the "Back" button.
     * This method returns the user to the main menu.
     *
     * @param event The mouse event that triggered this method.
     * @throws IOException If an error occurs while loading the FXML file for the main menu.
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
