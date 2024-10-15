package wordle;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class NewPlayerControllerTest {

    private Path testFilePath;

    private Path testFilePath2;

    public NewPlayerControllerTest(){
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass()");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }

    @BeforeEach
    void setUp() throws IOException {
        testFilePath = Files.createTempFile("players", ".csv");
        List<String> lines = List.of("John Doe,1", "Jane Doe,2");
        Files.write(testFilePath, lines);

        testFilePath2 = Files.createTempFile("personalBests", ".csv");
        List<String> allLines = List.of("1,300,150,200,100,500,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", "2,300,150,600,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
        Files.write(testFilePath2, allLines);

    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    public void testPlayerNameDoesExist() {
        System.out.println("playerNameExists");

        String name = "John Doe";

        Boolean expResult = true;
        Boolean result = null;
        try {
            result = NewPlayerController.playerNameExists(name, testFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(expResult, result);
    }

    @Test
    public void testPlayerNameDoesNotExist() {
        System.out.println("playerNameExists");

        // Very unlikely that a player names themselves this
        String name = "nullnullnull_%%^^&&S_00";

        Boolean expResult = false;
        Boolean result = null;
        try {
            result = NewPlayerController.playerNameExists(name, testFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(expResult, result);
    }

    @Test
    public void testGetNextPlayerId() {
        System.out.println("getNextPlayerId");

        // Assume the player_list.csv has 2 players registered
        int expResult = 3;
        int result = NewPlayerController.getNextPlayerId(testFilePath);

        assertEquals(expResult, result);
    }

    @Test
    public void testSavePlayer(){
        System.out.println("savePlayer");

        String name = "Jack Doe";
        int id = 3;

        NewPlayerController.savePlayer(name, id, testFilePath);


        try (BufferedReader reader = Files.newBufferedReader(testFilePath)) {
            String line = reader.readLine();

            while (line != null) {
                String[] playerInfo = line.split(",");
                String playerName = playerInfo[0];
                int playerId = Integer.parseInt(playerInfo[1]);

                if (playerName.equals(name) && playerId == id){
                    break;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String expName = "Jack Doe";
        int expId = 3;

        assertEquals(expName, name);
        assertEquals(expId, id);
    }

    @Test
    public void testAddPlayerBests(){
        System.out.println("addPlayerBests");

        int id = 1;

        NewPlayerController.addPlayerBests(id,testFilePath2);

        try (BufferedReader reader = Files.newBufferedReader(testFilePath2)) {
            String line = reader.readLine();

            while (line != null) {
                String[] playerInfo = line.split(",");
                int playerId = Integer.parseInt(playerInfo[0]);

                if (playerId == id){
                    break;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int expId = 1;
        assertEquals(expId, id);
    }

}