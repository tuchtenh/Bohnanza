package Raumverwaltung;

import Bots.Bot;
import Bots.Bot_Leicht;
import Bots.Bot_Schwer;
import Spieleverwaltung.Spieler;
import Spieleverwaltung.SpielerImpl;
import User.BenutzerImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.*;

public class Spielraum_ImplTest {
    private static BenutzerImpl be1;
    private static SpielerImpl spieler1;
    private static Bot_Leicht bot1;
    private static Bot_Schwer bot2;
    private static Spielvorraum_Impl svr;
    private static Spielraum_Impl a;
    private static Vector<Spieler> vspieler;
    private static Vector<Bot> bots;

    @BeforeClass
    public static void BeforeClass() throws RemoteException {
        be1 = new BenutzerImpl("Tim", "password", 0,0);
        bot1 = new Bot_Leicht();
        bot2 = new Bot_Schwer();
        spieler1 = new SpielerImpl(be1);
        a = new Spielraum_Impl();
        svr = new Spielvorraum_Impl("TestSpielVorraum");
        svr.addSpieler(spieler1);
        svr.addBot(bot1);
        svr.addBot(bot2);
        vspieler = new Vector<Spieler>();
        bots = new Vector<Bot>();
        vspieler.add(spieler1);
        bots.add(bot1);
        bots.add(bot2);
    }
    @Before
    public void before() {
        a.setName("TestRaum");
        a.setId(5);
    }

    //Der name des Spielraumes wird veraendert
    @Test
    public void setName() {
        a.setName("TestErfolg");
        assertEquals("TestErfolg", a.getName());
    }

    // Id des Spielraeumes wird veraendert
    @Test
    public void setId() {
        a.setId(1);
        assertEquals(1, a.getId());
    }
    //Tested ob die Spieler eines Spielraums zurueck gegeben werden
    @Test
    public void getSpieler() {
        assertEquals(vspieler, svr.starteSpiel().getSpieler());
    }
    //TODO
    //Tested ob die Bots eines Spielraums zurueck gegeben werden
    @Test
    public void getBots() {
        assertEquals(bots, svr.starteSpiel().getBots());
    }

    @Test
    public void getId() {
        assertEquals(5, a.getId());
    }

    @Test
    public void getName() {
        assertEquals("TestRaum", a.getName());
    }

    @Test
    public void loescheSpiel() {
        a.loescheSpiel();
        assertNull(a);
    }

}