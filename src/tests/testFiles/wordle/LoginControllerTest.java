package wordle;

import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    private Path testFilePath;
    private Path testFilePath2;
    private Path testFilePath3;

    public LoginControllerTest() {
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
    public void setUp() throws IOException {
        testFilePath = Files.createTempFile("players", ".csv");
        List<String> lines = List.of("John Doe,1", "Jane Doe,2");
        Files.write(testFilePath, lines);

        testFilePath2 = Files.createTempFile("personalBests", ".csv");
        List<String> lines2 = List.of("1,300,150,200,100,500,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", "2,300,150,600,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
        Files.write(testFilePath2, lines2);

        testFilePath3 = Files.createTempFile("personalBests", ".csv");
        List<String> lines3 = List.of("1,300,2","2,100,1","3,250,1");
        Files.write(testFilePath3, lines3);

    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDown()");
    }

    @Test
    public void getPlayerNameTest() {
        System.out.println("getPlayerNames");

        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("John Doe");
        expResult.add("Jane Doe");

        ArrayList<String> result = LoginController.getPlayerNames(testFilePath);
        assertEquals(expResult, result);
    }

    @Test
    public void getPlayerIDTest() {
        System.out.println("getPlayerID");

        String expResult = "1";
        String result = LoginController.getPlayerID(testFilePath, true);

        assertEquals(expResult,result);
    }


    @Test
    public void getPlayerScoreTest() {
        System.out.println("getPlayerScore");

        int expResult = 1250;
        int result = LoginController.getPlayerScore(testFilePath2, true);
        assertEquals(expResult, result);
    }


    @Test
    public void getHighestScoreInFirstThreeLevelsTest() {
        System.out.println("getHighestScoreInAllLevels");


        ArrayList<Integer> expResult = new ArrayList<>();
        expResult.add(2);
        expResult.add(3);

        ArrayList<Integer> result = LoginController.getHighestScoreInAllLevels(testFilePath3, true);
        assertEquals(expResult, result);

    }

    @Test
    public void testPersonalHighestScoreInDifficultyEasy() {
        System.out.println("personalHighestScoreInDifficultyEasy");

        int easy[] = {200, 300, 400, 200, 150, 100, 50, 150, 200, 300};

        int expResult = 400;
        int result = LoginController.personalHighestScoreInDifficultyTesting(easy);
        assertEquals(expResult, result);
    }

    @Test
    public void testPersonalHighestScoreInDifficultyMedium() {
        System.out.println("personalHighestScoreInDifficultyMedium");

        int medium[] = {200, 100, 400, 300, 100, 100, 200, 300, 200, 300};

        int expResult = 400;
        int result = LoginController.personalHighestScoreInDifficultyTesting(medium);
        assertEquals(expResult, result);
    }

    @Test
    public void testPersonalHighestScoreInDifficultyHard() {
        System.out.println("personalHighestScoreInDifficultyHard");

        int hard[] = {600, 400, 400, 500, 100, 300, 400, 300, 200, 300};

        int expResult = 600;
        int result = LoginController.personalHighestScoreInDifficultyTesting(hard);
        assertEquals(expResult, result);
    }


}