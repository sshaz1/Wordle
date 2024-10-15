package wordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Main class for the Wordle application. Initializes the user interface and manages game data files.
 *
 * @author Syed Muhammed Shahzain (251240894)
 */
public class MainMenu extends Application {

    /**
     * Initializes the main application window (stage), creates and displays the scene,
     * and ensures the existence of a file to track level high scores.
     * If the high score file does not exist, it is created with default values for each level.
     *
     * @param stage The primary window where the application's scenes are set and displayed.
     * @throws IOException If an error occurs during loading of the user interface or file handling.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // create level_highscore.csv file

        Path path = Paths.get("level_highscore.csv");
        // Create the file if it doesn't exist, Write to the file
        // Check if the file already exists
        if (!Files.exists(path)) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
                // Loop from 1 to 30
                for (int i = 1; i <= 30; i++) {
                    writer.write(i + ",0,0");
                    writer.newLine(); // Move to the next line
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/MainMenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Launches the application. This is the entry point from which the JavaFX application is started.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }
}