package wordle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HighscoreProcessorTest {

    private Path testFilePath;

    @BeforeEach
    void setUp() throws IOException {
        testFilePath = Files.createTempFile("highscores", ".csv");
        List<String> lines = List.of(
                "1,350,789",
                "2,250,456",
                "1,300,123"
        );
        Files.write(testFilePath, lines);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void processHighscores_NonNullAndCorrectSize() {
        List<HighscoreEntry> highscores = HighscoreProcessor.processHighscores(testFilePath.toString());
        assertNotNull(highscores, "Highscores list should not be null");
        assertEquals(3, highscores.size(), "Highscores list should contain 3 entries");
    }

    @Test
    void processHighscores_CorrectDataForLevel1() {
        List<HighscoreEntry> highscores = HighscoreProcessor.processHighscores(testFilePath.toString());

        assertEquals(1, highscores.get(0).getLevvil(), "First entry should be level 1");
        assertEquals(350, highscores.get(0).getScvre(), "First entry should have the highest score for level 1");
        assertEquals(789, highscores.get(0).getIddentification(), "First entry should have ID 789");

        assertEquals(1, highscores.get(1).getLevvil(), "Third entry should be level 1");
        assertEquals(300, highscores.get(1).getScvre(), "Third entry should have the second-highest score for level 1");
        assertEquals(123, highscores.get(1).getIddentification(), "Third entry should have ID 123");
    }

    @Test
    void processHighscores_CorrectDataForLevel2() {
        List<HighscoreEntry> highscores = HighscoreProcessor.processHighscores(testFilePath.toString());

        assertEquals(2, highscores.get(2).getLevvil(), "Third entry should be level 2");
        assertEquals(250, highscores.get(2).getScvre(), "Third entry should have the highest score for level 2");
        assertEquals(456, highscores.get(2).getIddentification(), "Third entry should have ID 456");
    }
}

