package Tests.Spielobjekte;

import Exception.AbbauException;
import Exception.FeldVollException;
import Spieleverwaltung.Spielobjekte.BohnenfeldImpl;
import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.Augenbohne;
import Spieleverwaltung.Spielobjekte.Karten.BlaueBohne;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class BohnenfeldImplTest {

    private static BohnenfeldImpl feld;
    private static Vector<Karte> v;
    private static Karte k;

    /**
     * erstellt eine Augenbohne für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException {
        k = new Augenbohne();
    }

    /**
     * erstellt vor jedem Test ein neues Bohnenfeld und einen neuen Vektor zum Vergleich
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void before() throws RemoteException {
        feld = new BohnenfeldImpl();
        v = new Vector<Karte>();
    }


    /**
     * testet, ob sich Bohnen von einem leeren Feld abbauen lassen, soll eine
     * AbbauException werfen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test(expected = AbbauException.class)
    public void ernten() throws RemoteException {
        feld.ernten();
    }


    /**
     * testet, ob sich Bohnen von einem belegten Feld abbauen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void ernten2() throws RemoteException {
        feld.anbauen(k);
        feld.anbauen(k);
        Vector<Karte> karte = new Vector<>();
        karte.add(k);
        karte.add(k);

        Iterator feldit = feld.ernten().iterator();
        Iterator kartenit = karte.iterator();

        while (feldit.hasNext()) {
            while (kartenit.hasNext()) {
                assertEquals(kartenit.next(), feldit.next());
            }
        }

    }


    /**
     * testet, ob ein belegtes Feld die richtige Anzahl an Karten auf dem Feld zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void getAnzahlKarten() throws RemoteException {
        feld.anbauen(k);
        feld.anbauen(k);
        assertEquals(2, feld.getAnzahlKarten());
    }


    /**
     * testet, ob ein leeres Feld die richtige Anzahl an Karten auf dem Feld zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void getAnzahlKarten2() throws RemoteException {
        assertEquals(0, feld.getAnzahlKarten());
    }


    /**
     * testet, ob sich Karten auf einem leeren Feld anbauen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void anbauen() throws RemoteException {
        feld.anbauen(k);
        feld.anbauen(k);
        assertEquals("Augenbohne", feld.getBohnenArt());
        assertEquals(2, feld.getAnzahlKarten());
    }


    /**
     * testet, ob sich Karten auf einem bereits belegten Feld mit einer anderen
     * Art Bohnen anbauen lassen, soll eine FeldVollException werfen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test(expected = FeldVollException.class)
    public void anbauen2() throws RemoteException {
        feld.anbauen(k);
        feld.anbauen(new BlaueBohne());
    }

    /**
     * testet, ob ein leeres Feld null als Bohnenart zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void getBohnenart() throws RemoteException {
        assertEquals(null, feld.getBohnenArt());
    }

    /**
     * testet, ob ein belegtes Feld die richtige Bohnenart zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    @Test
    public void getBohnenart2() throws RemoteException {
        feld.anbauen(k);
        assertEquals("Augenbohne", feld.getBohnenArt());
    }

    /**
     * testet, ob die richtige ImgURL für die Bohnenart auf dem Feld zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getImgURL() throws RemoteException {
        feld.anbauen(k);
        assertEquals("Augenbohne", feld.getBohnenArt());
        assertEquals("/GUI/Bilder/Augenbohne.jpg", feld.getImgURL());
    }

    /**
     * testet, ob null als ImgURL für ein leeres Bohnenfeld zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getImgURL2() throws RemoteException {
        assertEquals(null, feld.getImgURL());
    }

    /**
     * prüft, iob die erste Bohne auf dem Feld zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getBohne() throws RemoteException {
        assertEquals(null, feld.getBohne());
        feld.anbauen(k);
        assertEquals(k, feld.getBohne());
    }
}
