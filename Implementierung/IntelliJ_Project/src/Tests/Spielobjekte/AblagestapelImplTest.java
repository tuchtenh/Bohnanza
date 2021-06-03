package Tests.Spielobjekte;

import Spieleverwaltung.Spielobjekte.AblagestapelImpl;
import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.Augenbohne;
import Spieleverwaltung.Spielobjekte.Karten.BlaueBohne;
import Spieleverwaltung.Spielobjekte.Karten.Brechbohne;
import Spieleverwaltung.Spielobjekte.Karten.RoteBohne;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;

import static org.junit.Assert.*;

public class AblagestapelImplTest {

    private static AblagestapelImpl stapel;
    //Vector zum Vergleich für die Tests
    private static Vector<Karte> k;

    /**
     * legt vor jedem Test einen neuen Ablagestapel und Vektor an
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void before() throws RemoteException {
        stapel = new AblagestapelImpl();
        k = new Vector<Karte>();
    }

    /**
     * testet, ob ein leerer Stapel die richtige Anzahl Karten zurückgibt
     */

    @Test
    public void getAnzahlKarten() {
        assertEquals(0, stapel.getAnzahlKarten());
    }


    /**
     * testet, ob ein nicht-leerer Stapel die richtige Anzahl Karten zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void getAnzahlKarten2() throws RemoteException {
        stapel.addKarte(new RoteBohne());
        assertEquals(1, stapel.getAnzahlKarten());
    }


    /**
     * testet, ob sich Karten korrekt in einen Stapel einfügen lassen:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void addKarte() throws RemoteException {
        Karte a = new RoteBohne();
        Karte b = new Augenbohne();

        stapel.addKarte(a);
        stapel.addKarte(b);
        k.add(a);
        k.add(b);
        //testet, ob die Anzahl der Karten im Stapel erhöht wurde:
        assertEquals(k.size(), stapel.getAnzahlKarten());

        //testet, ob die richtige KarteImpl zum Stapel hinzugefügt wurde:
        int gleicheKarten = 0;
        Iterator it = k.iterator();

        while (it.hasNext()) {
            Iterator itStapel = stapel.getKarten().iterator();
            while (itStapel.hasNext()) {
                if (it.next() == itStapel.next()) {
                    gleicheKarten++;
                }
            }
        }

        assertEquals(2, gleicheKarten);

    }

    /**
     * testet, ob ein leerer Stapel true für isEmpty zurückgibt und ein
     * nicht leerer Stapel false
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void isempty() throws RemoteException {
        assertTrue(stapel.isempty());
        stapel.addKarte(new Brechbohne());
        stapel.addKarte(new BlaueBohne());
        assertFalse(stapel.isempty());
    }

    /**
     * testet getKarten für einen vollen Stapel:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getKarten() throws RemoteException {

        Karte a = new RoteBohne();
        Karte b = new Augenbohne();

        for (int i = 0; i < 10; i++) {
            stapel.addKarte(a);
            stapel.addKarte(b);
            k.add(a);
            k.add(b);
        }

        //flag, die testet, ob sich der Vector k von getKarten unterscheidet
        assertTrue(k.containsAll(stapel.getKarten()));
        assertTrue(stapel.getKarten().containsAll(k));

    }

    /**
     * testet getKarten für einen leeren Stapel:
     */
    @Test
    public void getKarten2() {

        Vector<Karte> emptytest = stapel.getKarten();
        assertEquals(0, emptytest.size());

    }

}
