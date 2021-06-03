package Spieleverwaltung;

import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;
import User.Benutzer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;
import java.util.Vector;

import static org.junit.Assert.assertEquals;;

public class SpielerTest {

    private static Spieler sp;
    private static Benutzer u;
    private static Karte k;

    @BeforeClass
    public static void beforeClass() {
        u = new Benutzer("User","pw",0,0);
        sp = new Spieler(u);
        k = new Karte("hi",1,1);
    }

     @Before
     public void before() {
        sp.setName("default");
     }

    @Test
    public void getName() {
        assertEquals("default",sp.getName());
    }

    @Test
    public void setName() {
        sp.setName("test");
        assertEquals("test",sp.getName());
    }

    @Test
    public void getUser() {
        assertEquals(u,sp.getUser());
    }
//TODO:
    @Test
    public void getFelder() {

        Vector<Bohnenfeld> spielerFeld = sp.getFelder();

        Vector<Bohnenfeld> feld = new Vector<Bohnenfeld>();


        for(Iterator it = feld.iterator(); it.hasNext();) {
            for(Iterator it2 = spielerFeld.iterator(); it2.hasNext();) {
                assertEquals(it.next(), it2.next());
            }
        }

    }

    @Test
    public void getHandkarten() {
        sp.addKarte(k);
        Vector<Karte> spielerKarten = sp.getHandkarten();

        Vector<Karte> kv = new Vector<Karte>();
        kv.add(k);

        for(Iterator it = kv.iterator(); it.hasNext();) {
            for(Iterator it2 = spielerKarten.iterator(); it2.hasNext();) {
                assertEquals(it.next(), it2.next());
            }
        }
    }

    @Test
    public void addKarte() {
        sp.addKarte(k);
        Vector<Karte> SpielerKarten = sp.getHandkarten();

        Vector<Karte> kv = new Vector<Karte>();
        kv.add(k);

        for(Iterator it = kv.iterator(); it.hasNext();) {
            for(Iterator it2 = SpielerKarten.iterator(); it2.hasNext();) {
                assertEquals(it.next(), it2.next());
            }
        }
    }
}