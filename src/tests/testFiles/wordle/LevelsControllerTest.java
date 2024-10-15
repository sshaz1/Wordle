package wordle;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Label;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelsControllerTest {

    private LevelsController controller;

    @BeforeEach
    void setUp() {
        new javafx.embed.swing.JFXPanel();

        controller = new LevelsController();
        controller.playerId = new Label();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setPlayerId() {
        System.out.println("setPlayerID");
        controller.setPlayerId(1);
        assertEquals("1", controller.playerId.getText());
    }

    @Test
    public void getLevelWordTest() {
        System.out.println("getLevelWord");
        int level = 3;

        String expResult = "mug";
        String result = LevelsController.getLevelWord(level);

        assertEquals(expResult, result);
    }

    @Test
    void updatePlayerIdLabel() {
        System.out.println("updatePlayerIdLabel");
        PlayerModel.setPlayerId(2);
        controller.updatePlayerIdLabel();
        assertEquals("2", controller.playerId.getText());
    }

    @Test
    void getPlayerHighestLevelUnlocked() {
        System.out.println("getPlayerHighestLevelUnlocked");

        // Assuming getPlayerHighestLevelUnlocked method works correctly,
        // and you have a file "test_levels_file.csv" with appropriate test data
        int highestLevel = controller.getPlayerHighestLevelUnlocked(1, "test_levels_file.csv");
        assertTrue(highestLevel >= 1); // The exact condition will depend on your test data
    }
}
