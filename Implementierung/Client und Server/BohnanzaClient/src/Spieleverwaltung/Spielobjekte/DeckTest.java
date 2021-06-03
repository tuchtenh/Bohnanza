package Spieleverwaltung.Spielobjekte;

import org.junit.Before;
import org.junit.Test;
import Exception.StapelLeerException;
import java.util.Iterator;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class DeckTest {

    private static Deck d;
    private static Vector<Karte> v;

    @Before
    public void before() {
        d = new Deck();
        v = new Vector<Karte>();
    }

    @Test
    public void ziehe() {
        Karte k = new Karte("Hi",0,0);
        d.addKarte(k);
        v.add(k);

        for(Iterator it = v.iterator(); it.hasNext();) {
            assertEquals(it.next(), d.ziehe());
        }
    }

    @Test (expected = StapelLeerException.class)
    public void ziehe2() {
        d.ziehe();
    }
}