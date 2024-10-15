package wordle;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerModelTest {

    public PlayerModelTest(){
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
    void setUp() {
        System.out.println("setUp()");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown()");
    }

    @Test
    void testGetPlayerId() {
        System.out.println("getPlayerId");
        PlayerModel.setPlayerId(5);

        int expResult = 5;
        int result = PlayerModel.getPlayerId();
        assertEquals(expResult, result);
    }

    @Test
    void testSetPlayerId() {
        System.out.println("getPlayerId");
        PlayerModel.setPlayerId(7);

        int expResult = 7;
        int result = PlayerModel.getPlayerId();
        assertEquals(expResult, result);
    }
}