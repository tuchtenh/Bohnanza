package Tests.Raumverwaltung;

import Bots.Bot_Leicht;
import Bots.Bot_Schwer;
import Raumverwaltung.Spielraum_Impl;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielemanager_Impl;
import Spieleverwaltung.SpielerImpl;
import User.BenutzerImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.*;

public class Spielraum_ImplTest {

    private static BenutzerImpl be2;
    private static Akteur akt;
    private static Akteur akt2;
    private static Akteur akt3;
    private static Spielraum_Impl sr;
    private static Vector<Akteur> akteurs;

    /**
     * erstellt Akteure, sowie einen Vektor mit Akteuren für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void BeforeClass() throws RemoteException {
        be2 = new BenutzerImpl("Test", "pass", 0, 0);
        akteurs = new Vector<Akteur>();
        akt = new SpielerImpl(be2);
        akt2 = new Bot_Schwer(0);
        akt3 = new Bot_Leicht(0);
        akteurs.add(akt);
        akteurs.add(akt2);
        akteurs.add(akt3);
    }

    /**
     * legt vor jedem Test einen neuen Spielraum an
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Before
    public void Before() throws RemoteException, MalformedURLException {
        sr = new Spielraum_Impl(akteurs, "Test");
    }

    /**
     * Tested ob die richtigen Spieler zuruck gegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getSpieler() throws RemoteException {
        Vector<Akteur> v = sr.getSpieler();
        assertTrue(v.containsAll(akteurs));
        assertTrue(akteurs.containsAll(v));

    }

    /**
     * Tested ob der richtige Spielraum Name zurueck gegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getName() throws RemoteException {
        assertEquals("Test", sr.getName());
    }


    /**
     * testet, ob ein ChatServer zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getChat() throws RemoteException {
        assertNotEquals(null, sr.getChat());
    }

    /**
     * Tested ob ein Spielmanager zurueck gegeben wird. Am anfang mit den Default werten
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getManager() throws RemoteException {
        assertNotEquals(null, sr.getManager());
        Spielemanager_Impl spielemanager = (Spielemanager_Impl) sr.getManager();
        Vector<Akteur> akts = spielemanager.getSpieler();
        //der Spielemanager hat alle Spieler übergeben bekommen:
        assertTrue(akts.containsAll(akteurs));
        assertTrue(akteurs.containsAll(akts));

    }

    /**
     * testet, ob ein RefresherServer zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getRefresherServer() throws RemoteException {
        assertNotEquals(null, sr.getRefresherServer());
    }
}