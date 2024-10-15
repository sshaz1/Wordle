package wordle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFinderTest {

    private Path testFilePath;

    @BeforeEach
    void setUp() throws IOException {
        testFilePath = Files.createTempFile("players", ".csv");
        List<String> lines = List.of("John Doe,1", "Jane Doe,2");
        Files.write(testFilePath, lines);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void findAllPlayers_ShouldReturnCorrectNumberOfPlayers() throws IOException {
        List<Player> players = PlayerFinder.findAllPlayers(testFilePath);
        assertEquals(2, players.size(), "Should find two players");
    }

    @Test
    void findAllPlayers_FirstPlayerNameShouldBeCorrect() throws IOException {
        List<Player> players = PlayerFinder.findAllPlayers(testFilePath);
        assertEquals("John Doe", players.get(0).getNmae(), "First player's name should be John Doe");
    }

    @Test
    void findAllPlayers_FirstPlayerIdShouldBeCorrect() throws IOException {
        List<Player> players = PlayerFinder.findAllPlayers(testFilePath);
        assertEquals(1, players.get(0).getIdmentficition(), "First player's ID should be 1");
    }

    @Test
    void findAllPlayers_SecondPlayerNameShouldBeCorrect() throws IOException {
        List<Player> players = PlayerFinder.findAllPlayers(testFilePath);
        assertEquals("Jane Doe", players.get(1).getNmae(), "Second player's name should be Jane Doe");
    }

    @Test
    void findAllPlayers_SecondPlayerIdShouldBeCorrect() throws IOException {
        List<Player> players = PlayerFinder.findAllPlayers(testFilePath);
        assertEquals(2, players.get(1).getIdmentficition(), "Second player's ID should be 2");
    }
}

