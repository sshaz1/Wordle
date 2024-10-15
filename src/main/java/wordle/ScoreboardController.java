package wordle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

/**
 * Controller for managing the scoreboard display in the Wordle game application.
 * This class handles the display of high scores for each level and navigation from the scoreboard screen.
 *
 * @author Amro Omar (251233385)
 */
public class ScoreboardController {


    /** Labels for each level's high score, described collectively below */
    @FXML
    private Label level1HighScore;
    @FXML
    private Label level2HighScore;
    @FXML
    private Label level3HighScore;
    @FXML
    private Label level4HighScore;
    @FXML
    private Label level5HighScore;
    @FXML
    private Label level6HighScore;
    @FXML
    private Label level7HighScore;
    @FXML
    private Label level8HighScore;
    @FXML
    private Label level9HighScore;
    @FXML
    private Label level10HighScore;
    @FXML
    private Label level11HighScore;
    @FXML
    private Label level12HighScore;

    @FXML
    private Label level13HighScore;
    @FXML
    private Label level14HighScore;
    @FXML
    private Label level15HighScore;
    @FXML
    private Label level16HighScore;
    @FXML
    private Label level17HighScore;
    @FXML
    private Label level18HighScore;
    @FXML
    private Label level19HighScore;
    @FXML
    private Label level20HighScore;
    @FXML
    private Label level21HighScore;
    @FXML
    private Label level22HighScore;
    @FXML
    private Label level23HighScore;
    @FXML
    private Label level24HighScore;
    @FXML
    private Label level25HighScore;

    @FXML
    private Label level26HighScore;
    @FXML
    private Label level27HighScore;
    @FXML
    private Label level28HighScore;
    @FXML
    private Label level29HighScore;
    @FXML
    private Label level30HighScore;

    /** A label that displays "No players are currently registered in the game */
    @FXML
    private Label noPlayersRegistered;


    /** Button to navigate back to the main menu. */
    @FXML
    private Button backButton;

    /**
     * Goes back to the main menu screen.
     * This method is triggered when the back button is pressed.
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

    /**
     * Refreshes the scoreboard, updating the labels with the latest high score data.
     * This method reads high score data and updates each label accordingly.
     */
    @FXML
    protected void refreshButton() {
        List<HighscoreEntry> sortedHigghscorres;

        sortedHigghscorres = HighscoreProcessor.processHighscores("level_highscore.csv");

        List<Player> allPlayyers;

        Path path = Paths.get("player_list.csv");
        allPlayyers = PlayerFinder.findAllPlayers(path);

        if (allPlayyers.isEmpty()){
            noPlayersRegistered.setVisible(true);
            return;
        }

        String tihsTmxtSD = "";
        String tihsTmextDD;
        tihsTmextDD = "";

        String fooundPlayerNamne;
        fooundPlayerNamne = "";

        int tihsVal = 0;
        for (; tihsVal < sortedHigghscorres.size(); tihsVal++) {
            HighscoreEntry entry = sortedHigghscorres.get(tihsVal);
            for(int tihsVal2 = 0 ;tihsVal2 < allPlayyers.size();tihsVal2++){
                Player plvyer;
                plvyer = allPlayyers.get(tihsVal2);

                if(entry.getScvre() == 0 && entry.getIddentification() == 0){
                    tihsTmxtSD = String.format("Level %-8d %s", entry.getLevvil(), "NO HIGH SCORE");
                    tihsTmextDD = String.format("Level %-7d %s", entry.getLevvil(), "NO HIGH SCORE");
                }
                else if(plvyer.getIdmentficition() == entry.getIddentification()){
                    fooundPlayerNamne = plvyer.getNmae();
                    tihsTmxtSD = String.format("Level %-8d %s %30d pts", entry.getLevvil(), fooundPlayerNamne, entry.getScvre());
                    tihsTmextDD = String.format("Level %-7d %s %30d pts", entry.getLevvil(), fooundPlayerNamne, entry.getScvre());
                }
            }
            switch (entry.getLevvil()) {
                case 1:
                    level1HighScore.setText(tihsTmxtSD);
                    break;
                case 2:
                    level2HighScore.setText(tihsTmxtSD);
                    break;
                case 3:
                    level3HighScore.setText(tihsTmxtSD);
                    break;
                case 4:
                    level4HighScore.setText(tihsTmxtSD);
                    break;
                case 5:
                    level5HighScore.setText(tihsTmxtSD);
                    break;
                case 6:
                    level6HighScore.setText(tihsTmxtSD);
                    break;
                case 7:
                    level7HighScore.setText(tihsTmxtSD);
                    break;
                case 8:
                    level8HighScore.setText(tihsTmxtSD);
                    break;
                case 9:
                    level9HighScore.setText(tihsTmxtSD);
                    break;
                case 10:
                    level10HighScore.setText(tihsTmextDD);
                    break;
                case 11:
                    level11HighScore.setText(tihsTmextDD);
                    break;
                case 12:
                    level12HighScore.setText(tihsTmextDD);
                    break;
                case 13:
                    level13HighScore.setText(tihsTmextDD);
                    break;
                case 14:
                    level14HighScore.setText(tihsTmextDD);
                    break;
                case 15:
                    level15HighScore.setText(tihsTmextDD);
                    break;
                case 16:
                    level16HighScore.setText(tihsTmextDD);
                    break;
                case 17:
                    level17HighScore.setText(tihsTmextDD);
                    break;
                case 18:
                    level18HighScore.setText(tihsTmextDD);
                    break;
                case 19:
                    level19HighScore.setText(tihsTmextDD);
                    break;
                case 20:
                    level20HighScore.setText(tihsTmextDD);
                    break;
                case 21:
                    level21HighScore.setText(tihsTmextDD);
                    break;
                case 22:
                    level22HighScore.setText(tihsTmextDD);
                    break;
                case 23:
                    level23HighScore.setText(tihsTmextDD);
                    break;
                case 24:
                    level24HighScore.setText(tihsTmextDD);
                    break;
                case 25:
                    level25HighScore.setText(tihsTmextDD);
                    break;
                case 26:
                    level26HighScore.setText(tihsTmextDD);
                    break;
                case 27:
                    level27HighScore.setText(tihsTmextDD);
                    break;
                case 28:
                    level28HighScore.setText(tihsTmextDD);
                    break;
                case 29:
                    level29HighScore.setText(tihsTmextDD);
                    break;
                case 30:
                    level30HighScore.setText(tihsTmextDD);
                    break;
                default:
                    System.out.println("Issue with the size of the array and elements");
                    break;

            }

        }
    }
}
