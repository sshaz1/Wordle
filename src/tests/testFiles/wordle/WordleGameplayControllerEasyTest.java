package wordle;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class WordleGameplayControllerEasyTest {
    private WordleGameplayControllerEasy controller;
    private Path tempFilePath;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        new JFXPanel();

        // Initialize controller and other stuff
        controller = new WordleGameplayControllerEasy();
        controller.pbText = new Text();
        controller.guessInput = new TextField();
        controller.feedbackText = new Label();

        tempFilePath = tempDir.resolve("test_file.csv");
        Files.writeString(tempFilePath, "1,1");
    }

    @Test
    void testHandleCheckGuessWithInvalidLength() {
        controller.guessInput.setText("ab");  // Less than 3 letters
        controller.handleCheckGuess();
        assertEquals("Invalid guess. Please enter a 3-letter word.", controller.feedbackText.getText());
        assertTrue(controller.guesses.isEmpty());
    }

    @Test
    void addLevelUnlocked() {
        assertDoesNotThrow(() -> WordleGameplayControllerEasy.addLevelUnlocked("1", tempFilePath.toString(), new Label("1")));
    }

    @Test
    void updatePersonalBests() {
        assertDoesNotThrow(() -> controller.updatePersonalBests(new Label("1"), new Label("1"), 100));
    }

    @Test
    void updateLevelHighScore() throws IOException {
        Path highScoreFilePath = Files.createTempFile("test_highscore", ".csv");
        assertDoesNotThrow(() -> controller.updateLevelHighScore(new Label("1"), new Label("1"), 100, highScoreFilePath.toString()));
    }
}
