package Spieleverwaltung.Spielobjekte;

import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class KartenstapelTest {

    private Kartenstapel stapel;
    private Vector<Karte> k;

    @Before
    public void before() {
        k = new Vector<Karte>();
        stapel = new Kartenstapel();
    }

    @Test
    public void mischen() {
        assertEquals(0,stapel.getAnzahlKarten());
    }

    @Test
    public void getAnzahlKarten() {
        assertEquals(0,stapel.getAnzahlKarten());
    }

    @Test
    public void addKarte() {
        Karte a = new Karte("a",1,1);

        stapel.addKarte(a);
        k.add(a);

        assertEquals(k.size(),stapel.getAnzahlKarten());
/*TODO:
        for(Iterator it = k.iterator(); it.hasNext();) {
            while(stapel.getAnzahlKarten() > 1) {
                assertEquals(it.next(), stapel.removeKarte());
            }
        }*/
    }

    @Test
    public void removeKarte() {
        //todo
        assertEquals(k.size(), stapel.getAnzahlKarten());
    }
}