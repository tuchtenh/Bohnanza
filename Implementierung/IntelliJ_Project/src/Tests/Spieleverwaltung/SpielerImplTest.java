package Tests.Spieleverwaltung;

import Spieleverwaltung.SpielerImpl;
import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.*;
import User.Benutzer;
import User.BenutzerImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpielerImplTest {

    private static Benutzer benutzer1;
    private static Benutzer benutzer2;
    private static SpielerImpl spieler1;
    private static SpielerImpl spieler2;
    private static Karte bohne;
    private static Karte bohne2;
    private static Karte bohne3;
    private static Karte bohne4;
    private static Karte bohne5;

    /**
     * erstellt neue Benutzer und Karten für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException {
        benutzer1 = new BenutzerImpl("testuser", "testpw", 0, 0);
        benutzer2 = new BenutzerImpl("testuser2", "testpw2", 1, 1);
        bohne = new Augenbohne();
        bohne2 = new BlaueBohne();
        bohne3 = new Brechbohne();
        bohne4 = new Sojabohne();
        bohne5 = new Saubohne();
    }

    /**
     * legt vor jedem Test neue Benutzer mit Standart-Namen und Feldern an
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void before() throws RemoteException {
        spieler1 = new SpielerImpl(benutzer1);
        spieler2 = new SpielerImpl(benutzer2);
        spieler1.addFelder(2);
        spieler1.setName("testuser");
        spieler2.setName("testuser2");
    }

    /**
     * testet, ob der richtige Name für den Benutzer des Spielers zurückgegeben wird
     */
    @Test
    public void getName() {
        assertEquals("testuser", spieler1.getName());
        assertEquals("testuser2", spieler2.getName());
    }

    /**
     * testet, ob sich der Name des Spielers verändern lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setName() throws RemoteException {
        assertEquals("testuser", spieler1.getName());
        assertEquals("testuser2", spieler2.getName());
        spieler1.setName("test");
        assertEquals("test", spieler1.getName());
        spieler2.setName("");
        assertEquals("", spieler2.getName());
    }

    /**
     * testet, ob der richtige User für den Spieler zurückgegeben wird
     */
    @Test
    public void getUser() {
        assertEquals(benutzer1, spieler1.getUser());
        assertEquals(benutzer1, spieler1.getUser());
    }

    /**
     * testet, ob die Felder korrekt zurückgegeben werden
     */
    @Test
    public void getFelder() {
        Vector<Bohnenfeld> felder = spieler1.getFelder();
        assertEquals(2, felder.size());
    }

    /**
     * testet, ob eine leere Hand korrekt zurückgegeben wird
     */
    @Test
    public void getHandkarten() {
        Vector<Karte> karten = spieler1.getHandkarten();
        assertEquals(0, karten.size());
    }

    /**
     * testet, ob eine volle Hand korrekt zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHandkarten2() throws RemoteException {
        Karte bohne = new Augenbohne();
        for (int i = 0; i < 5; i++) {
            spieler1.addKarte(bohne);
        }
        Vector<Karte> karten = spieler1.getHandkarten();
        assertEquals(5, karten.size());
        for (int i = 0; i < karten.size(); i++) {
            assertEquals(bohne, karten.get(i));
        }
    }

    /**
     * testet, ob sich Karten korrekt in die Hand des Spielers einfügen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addKarte() throws RemoteException {

        for (int i = 0; i < 5; i++) {
            spieler1.addKarte(bohne);
            spieler1.addKarte(bohne2);
        }

        //prüft, ob 10 Karten eingefügt wurden:
        Vector<Karte> karten = spieler1.getHandkarten();
        assertEquals(10, karten.size());

        //prüft, ob die richtigen 10 Karten eingefügt wurden
        int count = 0;
        for (int i = 0; i < karten.size(); i++) {
            if (karten.get(i) == bohne || karten.get(i) == bohne2) {
                count++;
            }
        }

        assertEquals(10, count);
    }

    /**
     * prüft, ob sich einzelne Karten aus der Hand des Spielers entfernen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeKarte() throws RemoteException {

        //fügt 5 Karten in die Hand des Spielers ein:
        spieler1.addKarte(bohne);
        spieler1.addKarte(bohne2);
        spieler1.addKarte(bohne3);
        spieler1.addKarte(bohne4);
        spieler1.addKarte(bohne5);

        //die 2te Bohne ist bohne2
        assertEquals(bohne2, spieler1.getHandkarten().get(1));

        //entferne die 2. Karte:
        spieler1.removeKarte(1);


        //überprüft, ob die Hand des Spielers noch alle anderen Karten enthält und
        //keine weiteren Karten
        Vector<Karte> karten = spieler1.getHandkarten();
        assertEquals(4, karten.size());
        assertEquals(bohne, karten.get(0));
        assertEquals(bohne3, karten.get(1));
        assertEquals(bohne4, karten.get(2));
        assertEquals(bohne5, karten.get(3));

        //versucht eine Karte mit größerem oder kleineren Index als Handkarten hat zu entfernen:
        spieler1.removeKarte(-1);
        spieler1.removeKarte(34);

        //überprüft, ob die Hand des Spielers noch alle anderen Karten enthält und
        //keine weiteren Karten
        Vector<Karte> karten2 = spieler1.getHandkarten();
        assertEquals(4, karten2.size());
        assertEquals(bohne, karten2.get(0));
        assertEquals(bohne3, karten2.get(1));
        assertEquals(bohne4, karten2.get(2));
        assertEquals(bohne5, karten2.get(3));

    }

    /**
     * prüft, ob sich mehrere Karten aus der Hand des Spielers entfernen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeKarten() throws RemoteException {

        //fügt Karten in die Hand des Spielers ein
        spieler1.addKarte(bohne);
        spieler1.addKarte(bohne2);
        spieler1.addKarte(bohne3);
        spieler1.addKarte(bohne4);
        spieler1.addKarte(bohne5);

        //die 2te Bohne ist bohne2
        assertEquals(bohne2, spieler1.getHandkarten().get(1));
        //die 3te Bohne ist bohne3
        assertEquals(bohne3, spieler1.getHandkarten().get(2));

        //entferne die 2. und 3. Karte:
        Vector<Integer> ints = new Vector<Integer>();
        ints.add(1);
        ints.add(2);
        spieler1.removeKarten(ints);

        //überprüft, ob die Hand des Spielers noch alle anderen Karten enthält und
        //keine weiteren Karten
        Vector<Karte> karten = spieler1.getHandkarten();
        assertEquals(3, karten.size());
        assertEquals(bohne, karten.get(0));
        assertEquals(bohne4, karten.get(1));
        assertEquals(bohne5, karten.get(2));

        //versucht Indices größer und kleiner als in Handkarten enthalten zu entfernen:
        Vector<Integer> ints2 = new Vector<Integer>();
        ints.add(-1);
        ints.add(22323);
        spieler1.removeKarten(ints2);

        //überprüft, ob die Hand des Spielers noch alle anderen Karten enthält und
        //keine weiteren Karten
        Vector<Karte> karten2 = spieler1.getHandkarten();
        assertEquals(3, karten2.size());
        assertEquals(bohne, karten2.get(0));
        assertEquals(bohne4, karten2.get(1));
        assertEquals(bohne5, karten2.get(2));

    }

    /**
     * testet, ob isUser für den Spieler true zurückliefert
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void isUser() throws RemoteException {
        assertTrue(spieler1.isUser());
    }

    /**
     * testet, ob sich Felder zu den Bohnenfeldern des Spielers hinzufügen lassen:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addFelder() throws RemoteException {
        //vorher 0 Felder:
        assertEquals(0, spieler2.getFelder().size());

        //füge 2 Felder hinzu:
        spieler2.addFelder(2);

        //überprüft, ob zwei Felder hinzugefügt wurden:
        Vector<Bohnenfeld> felder = spieler2.getFelder();
        assertEquals(2, felder.size());
        int count = 0;
        for (int i = 0; i < felder.size(); i++) {
            if (felder.get(i) instanceof Bohnenfeld) {
                count++;
            }
        }
        assertEquals(2, count);
        spieler1.addFelder(-1);
        assertEquals(2, spieler1.getFelder().size());
    }

    /**
     * prüft, ob sich die Anzahl der Taler des Spielers erhöhen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addTaler() throws RemoteException {

        assertEquals(0, spieler1.getTaler());
        spieler1.addTaler(2);
        spieler1.addTaler(4);
        spieler1.addTaler(5);
        assertEquals(11, spieler1.getTaler());
        //negative Anzahl Talern:
        spieler1.addTaler(-23);
        assertEquals(11, spieler1.getTaler());

    }

    /**
     * prüft, ob getTaler die richtige Anzahl an Talern zurückliefert
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getTaler() throws RemoteException {

        assertEquals(0, spieler1.getTaler());
        spieler1.addTaler(2);
        spieler1.addTaler(4);
        spieler1.addTaler(5);
        assertEquals(11, spieler1.getTaler());

    }

    /**
     * prüft, ob die richtigen gehandelten Karten zurückgegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getGehandelteKarten() throws RemoteException {
        //derzeit keine gehandelten Karten:
        assertEquals(0, spieler1.getGehandelteKarten().size());

        //füge gehandelte Karten hinzu:
        spieler1.addGehandelteKarten(bohne);
        spieler1.addGehandelteKarten(bohne2);

        //Vergleiche:
        Vector<Karte> gehandelteKarten = spieler1.getGehandelteKarten();
        assertEquals(2, gehandelteKarten.size());
        assertEquals(bohne, gehandelteKarten.get(0));
        assertEquals(bohne2, gehandelteKarten.get(1));
    }

    /**
     * testet, ob sich gehandelte Karten zu einem Spieler hinzufügen lassen:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addGehandelteKarten() throws RemoteException {
        //derzeit keine gehandelten Karten:
        assertEquals(0, spieler1.getGehandelteKarten().size());

        //füge gehandelte Karten hinzu:
        spieler1.addGehandelteKarten(bohne);
        spieler1.addGehandelteKarten(bohne2);

        //Vergleiche:
        Vector<Karte> gehandelteKarten = spieler1.getGehandelteKarten();
        assertEquals(2, gehandelteKarten.size());
        assertEquals(bohne, gehandelteKarten.get(0));
        assertEquals(bohne2, gehandelteKarten.get(1));
    }

    /**
     * testet, ob sich gehandelte Karten entfernen lassen:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeGehandelteKarte() throws RemoteException {
        //derzeit keine gehandelten Karten:
        assertEquals(0, spieler1.getGehandelteKarten().size());

        //füge gehandelte Karten hinzu:
        spieler1.addGehandelteKarten(bohne);
        spieler1.addGehandelteKarten(bohne2);
        spieler1.addGehandelteKarten(bohne3);

        //Vergleiche:
        Vector<Karte> gehandelteKarten = spieler1.getGehandelteKarten();
        assertEquals(3, gehandelteKarten.size());
        assertEquals(bohne, gehandelteKarten.get(0));
        assertEquals(bohne2, gehandelteKarten.get(1));
        assertEquals(bohne3, gehandelteKarten.get(2));

        //lösche eine der Karten:
        spieler1.removeGehandelteKarte(1);

        //Vergleiche:
        Vector<Karte> gehandelteKarten2 = spieler1.getGehandelteKarten();
        assertEquals(2, gehandelteKarten2.size());
        assertEquals(bohne, gehandelteKarten2.get(0));
        assertEquals(bohne3, gehandelteKarten2.get(1));

        //zu hoher oder niedriger Index:
        spieler1.removeGehandelteKarte(-1);
        spieler1.removeGehandelteKarte(2);
        spieler1.removeGehandelteKarte(23232);

        //Vergleiche:
        Vector<Karte> gehandelteKarten3 = spieler1.getGehandelteKarten();
        assertEquals(2, gehandelteKarten3.size());
        assertEquals(bohne, gehandelteKarten3.get(0));
        assertEquals(bohne3, gehandelteKarten3.get(1));
    }

    /**
     * testet, ob hatAngebaut den richtigen Wert liefert
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHatAngebaut() throws RemoteException {
        assertEquals(0, spieler1.getHatAngebaut());
        spieler1.setHatAngebaut(2);
        assertEquals(2, spieler1.getHatAngebaut());
    }

    /**
     * testet, ob sich HatAngebaut anpassen lässt:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setHatAngebaut() throws RemoteException {
        assertEquals(0, spieler1.getHatAngebaut());
        spieler1.setHatAngebaut(2);
        assertEquals(2, spieler1.getHatAngebaut());
        //negatives hat angebaut:
        spieler1.setHatAngebaut(-23);
        assertEquals(2, spieler1.getHatAngebaut());
    }
}