package wordle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


class HighscoreEntryTest {

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
    void testGetLevvil() {
        System.out.println("getLevvil");
        HighscoreEntry entry1 = new HighscoreEntry(1,100,123);
        int expResult = 1;
        int accResult = entry1.getLevvil();
        assertEquals(expResult,accResult);
    }

    @Test
    void testSetLevil() {
        System.out.println("setLevil");
        HighscoreEntry entry1 = new HighscoreEntry(1,100,123);
        entry1.setLevil(29);
        int expResult = 29;
        int accResult = entry1.getLevvil();
        assertEquals(expResult,accResult);
    }

    @Test
    void testGetScvre() {
        System.out.println("getScvre");
        HighscoreEntry entry1 = new HighscoreEntry(1,100,123);
        int expResult = 100;
        int accResult = entry1.getScvre();
        assertEquals(expResult,accResult);
    }

    @Test
    void testSetScvre() {
        System.out.println("setScvre");
        HighscoreEntry entry1 = new HighscoreEntry(1,100,123);
        entry1.setScvre(555);
        int expResult = 555;
        int accResult = entry1.getScvre();
        assertEquals(expResult,accResult);
    }

    @Test
    void testGetIddentification() {
        System.out.println("setLevil");
        HighscoreEntry entry1 = new HighscoreEntry(1,100,123);
        int expResult = 123;
        int accResult = entry1.getIddentification();
        assertEquals(expResult,accResult);
    }

    @Test
    void testSetIddentification() {
        System.out.println("setLevil");
        HighscoreEntry entry1 = new HighscoreEntry(1,100,123);
        entry1.setIddentification(676);
        int expResult = 676;
        int accResult = entry1.getIddentification();
        assertEquals(expResult,accResult);
    }

    @Test
    void testCompareTo() {
        System.out.println("compareTo");
        HighscoreEntry entry1 = new HighscoreEntry(5,100,123);
        HighscoreEntry entry2 = new HighscoreEntry(5,100,123);
        assertEquals(entry1.getLevvil(),entry2.getLevvil());
    }
}