package Spieleverwaltung.Spielobjekte;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import Exception.AbbauException;
import Exception.FeldVollException;
import java.util.Vector;

public class BohnenfeldTest {

    private static Bohnenfeld feld;
    private static Vector<Karte> v;
    private static Karte k;

    @BeforeClass
    public static void beforeClass() {
        k = new Karte("h",1,1);
    }

    @Before
    public void before() {
        feld = new Bohnenfeld();
        v = new Vector<Karte>();
    }

    @Test (expected = AbbauException.class)
    public void ernten() {
        feld.ernten();
    }

    @Test
    public void ernten2() {
        feld.anbauen(k);
        assertEquals(1,feld.ernten());
    }

    @Test
    public void getAnzahlKarten() {
        feld.anbauen(k);
        feld.anbauen(k);
        assertEquals(2,feld.getAnzahlKarten());
    }

    @Test
    public void getAnzahlKarten2() {
        assertEquals(0,feld.getAnzahlKarten());
    }

    @Test
    public void anbauen() {
        feld.anbauen(k);
        assertEquals("h", feld.getBohnenArt());
    }

    @Test (expected = FeldVollException.class)
    public void anbauen2() {
        feld.anbauen(k);
        feld.anbauen(new Karte("fail",1,1));
    }
}