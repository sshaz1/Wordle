package wordle;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    public PlayerTest(){
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
    void testGetNmae() {
        System.out.println("getNmae");
        Player player = new Player("chris", 2);

        String expResult = "chris";
        String result = player.getNmae();
        assertEquals(expResult, result);
    }

    @Test
    void testGetIdmentficition() {
        System.out.println("getIdmentficition");
        Player player = new Player("chris", 2);

        int expResult = 2;
        int result = player.getIdmentficition();
        assertEquals(expResult, result);
    }
}