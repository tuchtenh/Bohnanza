package Spieleverwaltung.Spielobjekte;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AblagestapelTest {

    private static Ablagestapel testStapel;

    @BeforeClass
    public static void before() {
        testStapel = new Ablagestapel();
    }

    @Test
    public void getAnzahlLeerung() {
        assertEquals(0,testStapel.getAnzahlLeerung());
    }
}