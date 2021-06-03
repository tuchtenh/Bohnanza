package Spieleverwaltung.Spielobjekte;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class KarteTest {

    private static Karte karte;

    @BeforeClass
    public static void before() {
        karte = new Karte("Feuerbohne",3,4);
    }
    @Test
    public void getBohnenArt() {
        assertEquals("Feuerbohne",karte.getBohnenArt());
    }

    @Test
    public void getHaeufigkeit() {
        assertEquals(3,karte.getHaeufigkeit());
    }

    @Test
    public void getTalerwert() {
        assertEquals(4,karte.getTalerwert());
    }
}