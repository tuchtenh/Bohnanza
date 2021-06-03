package Tests.Raumverwaltung;

import Bots.Bot;
import Bots.Bot_Leicht;
import Bots.Bot_Schwer;
import Exception.NichtGenugSpielerException;
import Exception.ZuVieleSpielerException;
import Raumverwaltung.Spielraum_Impl;
import Raumverwaltung.Spielvorraum_Impl;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spieler;
import Spieleverwaltung.SpielerImpl;
import User.BenutzerImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.*;

public class Spielvorraum_ImplTest {
    private static BenutzerImpl be1;
    private static BenutzerImpl be2;
    private static Spielvorraum_Impl svr;


    /**
     * erstellt neue Benutzer für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void BeforeClass() throws RemoteException {
        be1 = new BenutzerImpl("Boi1", "password", 0, 0);
        be2 = new BenutzerImpl("Boi2", "password", 0, 0);
    }

    /**
     * legt vor jedem Test einen neuen Spielvorraum an
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void Before() throws RemoteException {
        svr = new Spielvorraum_Impl("TestVorraum");
    }


    /**
     * Tested ob der name zuruck gegeben wird.
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getName() throws RemoteException {
        assertEquals("TestVorraum", svr.getName());
    }


    /**
     * Tested ob der name richtig gesetzt wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setName() throws RemoteException {
        svr.setName("Erfolg");
        assertEquals("Erfolg", svr.getName());
    }


    /**
     * Tested ob der Spieler zum Spielvorraum hinzugefuegt wurde
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addSpieler1() throws RemoteException {
        Vector<Spieler> v = new Vector<>();
        v.add(new SpielerImpl(be1));
        assertEquals(0, svr.getAkteure().size());
        assertTrue(svr.addSpieler(be1));
        assertEquals(1, svr.getAkteure().size());
        assertEquals(v.elementAt(0).getName(), svr.getAkteure().elementAt(0));

    }


    /**
     * Tested ob der Spieler zum Spielvorraum hinzugefuegt wird wenn es schon mehr als 5 Spieler+Bots im Spielvorraum gibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addSpieler2() throws RemoteException {
        svr.addSpieler(be1);
        svr.addBot(1);
        svr.addBot(0);
        svr.addBot(1);
        svr.addBot(0);
        assertEquals(5, svr.getAkteure().size());
        assertFalse(svr.addSpieler(be2));
        assertEquals(5, svr.getAkteure().size());
    }


    /**
     * testet, ob eine Exception geworfen wird, wenn ein 6. Spieler hinzugefügt wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test(expected = ZuVieleSpielerException.class)
    public void addSpieler3() throws RemoteException {
        svr.addSpieler(be1);
        svr.addBot(1);
        svr.addBot(0);
        svr.addBot(1);
        svr.addBot(0);
        assertFalse(svr.addBot(1));
        svr.addBot(0);
    }


    /**
     * Tested ob der richtige Spieler entfernt wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeSpieler() throws RemoteException {
        svr.addSpieler(be1);
        svr.addSpieler(be2);
        assertEquals(2, svr.getSpieler().size());
        assertEquals("Boi1", svr.getAkteure().elementAt(0));
        assertEquals("Boi2", svr.getAkteure().elementAt(1));
        svr.removeSpieler("Boi1");
        assertEquals(1, svr.getSpieler().size());
        assertEquals("Boi2", svr.getAkteure().elementAt(0));
        //testet, ob sich ein Spieler entfernen lässt, der sich vorher nicht im SVR befindet:
        assertFalse(svr.removeSpieler("Test"));
    }


    /**
     * Tested ob Bot aus dem Spielvorraum entfernt wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeBot() throws RemoteException {
        svr.addSpieler(be1);
        svr.addBot(0);
        svr.addBot(1);
        assertEquals(3, svr.getAkteure().size());
        assertEquals(2, svr.getBots().size());
        assertEquals(1, svr.getSpieler().size());
        assertTrue(svr.removeBot(svr.getBots().elementAt(1).getName()));
        assertEquals(2, svr.getAkteure().size());
        assertEquals(1, svr.getBots().size());
        assertEquals(1, svr.getSpieler().size());
        assertTrue(svr.removeBot(svr.getBots().elementAt(0).getName()));
        assertEquals(1, svr.getAkteure().size());
        assertEquals(0, svr.getBots().size());
        assertEquals(1, svr.getSpieler().size());
        //testet, ob sich ein Bot entfernen lässt, der vorher nicht im Spielvorraum war:
        assertFalse(svr.removeBot("Test"));
    }


    /**
     * Tested ob ein Spielraum mit den richtigen Spielern und Spielraum Namen erzeugt wird
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch ist
     */
    @Test
    public void starteSpiel() throws RemoteException, MalformedURLException {
        Vector<Akteur> v = new Vector<>();
        Spielvorraum_Impl svr2 = new Spielvorraum_Impl("Spielvorraum2");
        svr2.addSpieler(be1);
        svr2.addBot(0);
        svr2.addBot(1);
        v.addAll(svr2.getSpieler());
        v.addAll(svr2.getBots());
        Spielraum_Impl spiel = (Spielraum_Impl) svr2.starteSpiel();
        assertEquals(v.size(), spiel.getSpieler().size());
        assertTrue(v.containsAll(spiel.getSpieler()));
        assertTrue(spiel.getSpieler().containsAll(v));
    }

    /**
     * testet, ob sich mit starte Spiel ein Spielraum mit weniger als 3 Spielern
     * erstellen lässt, soll NichtGenugSpielerExcpetion werfen
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch ist
     */
    @Test(expected = NichtGenugSpielerException.class)
    public void starteSpiel2() throws RemoteException, MalformedURLException {
        Vector<Akteur> v = new Vector<>();
        Spielvorraum_Impl svr2 = new Spielvorraum_Impl("Spielvorraum2");
        svr2.addSpieler(be1);
        svr2.addBot(0);
        v.addAll(svr2.getSpieler());
        v.addAll(svr2.getBots());
        Spielraum_Impl spiel = (Spielraum_Impl) svr2.starteSpiel();
        assertEquals(v.size(), spiel.getSpieler().size());
        assertTrue(v.containsAll(spiel.getSpieler()));
        assertTrue(spiel.getSpieler().containsAll(v));
    }

    /**
     * testet, ob sich ein Spielraum mit 6 Spielern erstellen lässt, soll
     * eine ZuVieleSpielerException werfen
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch ist
     */
    @Test(expected = ZuVieleSpielerException.class)
    public void starteSpiel3() throws RemoteException, MalformedURLException {
        Vector<Akteur> v = new Vector<>();
        Spielvorraum_Impl svr2 = new Spielvorraum_Impl("Spielvorraum2");
        svr2.addSpieler(be1);
        svr2.addBot(0);
        svr2.addBot(0);
        svr2.addBot(0);
        svr2.addBot(0);
        svr2.addBot(0);
        v.addAll(svr2.getSpieler());
        v.addAll(svr2.getBots());
        Spielraum_Impl spiel = (Spielraum_Impl) svr2.starteSpiel();
        assertEquals(v.size(), spiel.getSpieler().size());
        assertTrue(v.containsAll(spiel.getSpieler()));
        assertTrue(spiel.getSpieler().containsAll(v));
    }


    /**
     * testet, ob ein ChatServer zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getChat() throws RemoteException {

        assertNotNull(null, svr.getChat());


    }


    /**
     * Tested ob die Richtigen Bots zum Spielvorraum eingefuegt werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addBot1() throws RemoteException {
        svr.addSpieler(be1);
        Vector<Bot> v = new Vector<>();
        Vector<String> vv = new Vector<>();
        assertEquals(1, svr.getAkteure().size());
        assertEquals(0, svr.getBots().size());
        svr.addBot(0);
        assertEquals(1, svr.getBots().size());
        assertEquals(2, svr.getAkteure().size());
        assertEquals("Bot_Leicht0", svr.getBots().elementAt(0).getName());
        svr.addBot(1);
        assertEquals(2, svr.getBots().size());
        assertEquals(3, svr.getAkteure().size());
        assertEquals("Bot_Schwer1", svr.getBots().elementAt(1).getName());
    }


    /**
     * Tested ob eine Exception geworfen wird, falls man einen neuen Bot zum Spielvorraum einfuegen mochete wenn schon 5 Spieler im raum sind
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test(expected = ZuVieleSpielerException.class)
    public void addBot2() throws RemoteException {
        svr.addSpieler(be1);
        svr.addBot(1);
        svr.addBot(1);
        svr.addBot(1);
        svr.addBot(1);
        svr.addBot(1);
    }


    /**
     * Tested ob ein ein Vektor mit den Spielern im Spielvorraum zurueck gegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getSpieler() throws RemoteException {
        Vector<Spieler> v = new Vector<>();
        v.add(new SpielerImpl(be1));
        v.add(new SpielerImpl(be2));
        svr.addSpieler(be1);
        svr.addBot(1);
        svr.addBot(0);
        svr.addSpieler(be2);
        assertEquals(2, svr.getSpieler().size());
        assertEquals(v.elementAt(0).getName(), svr.getSpieler().elementAt(0).getName());
        assertEquals(v.elementAt(1).getName(), svr.getSpieler().elementAt(1).getName());
    }


    /**
     * Tested ob die Namen in einem Vektor zurueck gegeben werden von den Spielern im Spielvorraum
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getSpielerNamen() throws RemoteException {
        Vector<String> v = new Vector<>();
        v.add(be1.getName());
        v.add(be2.getName());
        svr.addSpieler(be1);
        svr.addBot(1);
        svr.addSpieler(be2);
        assertEquals(v, svr.getSpielerNamen());
    }


    /**
     * Tested ob ein ein Vektor mit den Bots im Spielvorraum zurueck gegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getBots() throws RemoteException {
        Vector<Bot> v = new Vector<>();
        v.add(new Bot_Leicht(0));
        v.add(new Bot_Schwer(1));
        svr.addSpieler(be1);
        svr.addBot(0);
        svr.addBot(1);
        svr.addSpieler(be2);
        assertEquals(2, svr.getBots().size());
        assertEquals(v.elementAt(0).getName(), svr.getBots().elementAt(0).getName());
        assertEquals(v.elementAt(1).getName(), svr.getBots().elementAt(1).getName());
    }


    /**
     * Tested ob die Namen in einem Vektor zurueck gegeben werden von den Bots im Spielvorraum
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getBotNamen() throws RemoteException {
        Vector<String> v = new Vector<>();
        v.add(new Bot_Leicht(0).getName());
        v.add(new Bot_Schwer(1).getName());
        svr.addSpieler(be1);
        svr.addBot(0);
        svr.addSpieler(be2);
        svr.addBot(1);
        assertEquals(v, svr.getBotNamen());
    }

    /**
     * testet, ob ein JoinerServer zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getJoinerServer() throws RemoteException {
        assertNotEquals(null, svr.getJoinerServer());
    }

    /**
     * testet, ob ein RefresherServer zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getRefresherServer() throws RemoteException {
        assertNotEquals(null, svr.getRefresherServer());
    }


    /**
     * Tested ob ein Vektor mit den Namen von allen Spielern und Bots im Spielvorraum zurueck gegeben wird
     * Die Spieler Namen kommen zuerst und erst dan die Namen von den Bots
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getAkteure() throws RemoteException {
        Vector<String> v = new Vector<>();
        v.add(new SpielerImpl(be1).getName());
        v.add(new SpielerImpl(be2).getName());
        v.add(new Bot_Leicht(0).getName());
        v.add(new Bot_Leicht(1).getName());
        v.add(new Bot_Schwer(2).getName());
        svr.addSpieler(be1);
        svr.addBot(0);
        svr.addBot(0);
        svr.addBot(1);
        svr.addSpieler(be2);
        assertEquals(v, svr.getAkteure());

    }
}