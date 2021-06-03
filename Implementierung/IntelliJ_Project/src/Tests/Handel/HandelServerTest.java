package Tests.Handel;

import Bots.Bot_Leicht;
import Exception.KeinHandelspartnerException;
import GUI.SpielraumController;
import Handel.Haendler;
import Handel.HandelBot;
import Handel.HandelServer;
import Spieleverwaltung.SpielerImpl;
import User.BenutzerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandelServerTest {

    private static HandelServer hsv;
    private static Bot_Leicht akt1;
    private static Bot_Leicht akt2;
    private static Haendler hn1;
    private static Haendler hn2;
    private static SpielraumController cnt;

    /**
     * erstellt den HandelServer, sowie HandelBots für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException {
        //neuer HandelServer:
        hsv = new HandelServer();
        //Akteure für HandelServer:
        akt1 = new Bot_Leicht(0);
        akt2 = new Bot_Leicht(1);
        hn1 = new HandelBot(akt1);
        hn2 = new HandelBot(akt2);
    }

    /**
     * fügt die HandelBots vor jedem Test dem Server hinzu
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void before() throws RemoteException {
        hsv.join(hn1);
        hsv.join(hn2);
    }

    /**
     * testet, ob HandelClients dem Server joinen können:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void join() throws RemoteException {
        Vector<Haendler> haendlers = hsv.getClients();
        assertEquals(2, haendlers.size());
        assertTrue(haendlers.contains(hn1));
        assertTrue(haendlers.contains(hn2));
    }

    /**
     * testet, ob HandelClients den Server verlassen können:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void leave() throws RemoteException {
        Vector<Haendler> haendlers = hsv.getClients();
        assertEquals(2, haendlers.size());
        hsv.leave(hn1);
        hsv.leave(hn2);
        Vector<Haendler> haendlers2 = hsv.getClients();
        assertEquals(0, haendlers2.size());
        hsv.join(hn1);
        hsv.join(hn2);
    }

    /**
     * testet, ob starteHandel ohne Partner eine KeinHandelspartnerException wirft
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test(expected = KeinHandelspartnerException.class)
    public void starteHandel() throws IOException {
        hsv.starteHandel(new Bot_Leicht(2), new Bot_Leicht(4));
    }

    /**
     * testet, ob starteHandel bei beiden die Partner anpasst:
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test
    public void starteHandel2() throws IOException {
        hsv.starteHandel(akt1, akt2);
        assertEquals(hn1, hn2.getPartner());
        assertEquals(hn2, hn1.getPartner());
    }

    /**
     * testet, ob locateClient den richtigen Client zurückliefert, falls Client im Server, sonst null
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void locateClient() throws RemoteException {
        //Client nicht in der Liste:
        assertEquals(null, hsv.locateClient(new SpielerImpl(new BenutzerImpl("test", "test", 0, 0))));
        assertEquals(hn1, hsv.locateClient(akt1));
        assertEquals(hn2, hsv.locateClient(akt2));

    }

    /**
     * sorgt dafür, dass die Clients den Server nach jedem Test wieder verlassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @After
    public void after() throws RemoteException {
        hsv.leave(hn1);
        hsv.leave(hn2);
    }
}