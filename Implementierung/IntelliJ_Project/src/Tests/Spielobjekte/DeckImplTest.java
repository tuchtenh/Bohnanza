package Tests.Spielobjekte;

import Exception.StapelLeerException;
import Spieleverwaltung.Spielobjekte.DeckImpl;
import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.Augenbohne;
import Spieleverwaltung.Spielobjekte.Karten.BlaueBohne;
import Spieleverwaltung.Spielobjekte.Karten.Brechbohne;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Vector;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class DeckImplTest {

    //ein Stapel mit allen Karten:
    private DeckImpl stapel;
    //ein Stapel mit nur bestimmten Karten:
    private DeckImpl stapel2;
    //Vector mit den gleichen Karten wie stapel
    private Vector<Karte> k;
    //Vector mit den gleichen Karten wie stapel2
    private Vector<Karte> k2;

    /**
     * erstellt vor jedem Test zwei neue Stapel und Vektoren zum Vergleich
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void before() throws RemoteException {
        Vector<Karte> v = new Vector<Karte>();
        for (int i = 0; i < 5; i++) {
            v.add(new Augenbohne());
            v.add(new BlaueBohne());
            v.add(new Brechbohne());
        }
        k2 = v;
        stapel2 = new DeckImpl(v);
        stapel = new DeckImpl();

        k = stapel.getKarten();
    }


    /**
     * testet, ob sich Karten aus dem DeckImpl ziehen lassen.
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void ziehe() throws RemoteException {
        Karte kstapel = stapel.ziehe();
        Karte kstapel2 = stapel2.ziehe();
        assertNotEquals(null, kstapel);
        assertNotEquals(null, kstapel2);
        assertEquals(103, stapel.getAnzahlKarten());
        assertEquals(14, stapel2.getAnzahlKarten());
    }


    /**
     * testet, ob eine StapelLeerException geworfen wird, wenn versucht wird
     * von einem leeren DeckImpl eine KarteImpl zu ziehen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test(expected = StapelLeerException.class)
    public void ziehe2() throws RemoteException {
        while (true) {
            stapel.ziehe();
        }
    }


    /**
     * testet, ob sich ein Stapel mischen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void mischen() throws RemoteException {

        stapel.mischen();
        stapel2.mischen();

        //testet, ob die Anzahl der Karten gleich geblieben ist:
        assertEquals(104, stapel.getAnzahlKarten());
        assertEquals(15, stapel2.getAnzahlKarten());

        assertTrue(stapel2.getKarten().containsAll(k2));
        assertTrue(k2.containsAll(stapel2.getKarten()));
        assertTrue(k.containsAll(stapel.getKarten()));
        assertTrue(stapel.getKarten().containsAll(k));

    }


    /**
     * testet, ob ein leerer Stapel die richtige Anzahl Karten zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void getAnzahlKarten() throws RemoteException {
        for (int i = 0; i < 15; i++) {
            stapel2.ziehe();
        }
        assertEquals(0, stapel2.getAnzahlKarten());

        for (int i = 0; i < 104; i++) {
            stapel.ziehe();
        }
        assertEquals(0, stapel.getAnzahlKarten());
    }


    /**
     * testet, ob ein nicht-leerer Stapel die richtige Anzahl Karten zurückgibt
     */

    @Test
    public void getAnzahlKarten2() {
        assertEquals(104, stapel.getAnzahlKarten());
        assertEquals(15, stapel2.getAnzahlKarten());
    }


    /**
     * testet, ob sich ein Stapel neu belegen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void setKarten() throws RemoteException {

        assertEquals(104, stapel.getAnzahlKarten());

        Vector<Karte> newStapel = new Vector<Karte>();
        for (int i = 0; i < 4; i++) {
            newStapel.add(new Brechbohne());
            newStapel.add(new Augenbohne());
        }

        stapel.setKarten(newStapel);
        assertEquals(8, stapel.getAnzahlKarten());
        assertTrue(newStapel.containsAll(stapel.getKarten()));
        assertTrue(stapel.getKarten().containsAll(newStapel));
    }

    /**
     * testet, ob ein Stapel alle seine Karten zurückgibt
     */
    @Test
    public void getKarten() {

        assertEquals(104, stapel.getAnzahlKarten());
        assertEquals(104, stapel.getKarten().size());

        assertEquals(15, stapel2.getAnzahlKarten());
        assertEquals(15, stapel2.getKarten().size());

    }

    /**
     * testet, ob isEmpty den richtigen Wert zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void isEmpty() throws RemoteException {
        //Stapel voll:
        assertFalse(stapel2.isempty());
        assertEquals(15, stapel2.getAnzahlKarten());

        //ziehe alle Karten:
        for (int i = 0; i < 15; i++) {
            stapel2.ziehe();
        }
        //Stapel leer:
        assertEquals(0, stapel2.getAnzahlKarten());
        assertTrue(stapel2.isempty());

    }

}
