package Tests.Handel;

import Bots.Bot_Leicht;
import Bots.Bot_Schwer;
import GUI.HandelController;
import GUI.SpielraumController;
import Handel.HandelBot;
import Handel.HandelServer;
import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.Gartenbohne;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HandelBotTest {
    private static HandelBot hb;
    private static Bot_Leicht bot;
    private static HandelBot hb2;
    private static Bot_Leicht bot2;
    private static Bot_Schwer bot3;
    private static Bot_Schwer bot4;
    private static HandelBot hb3;
    private static HandelBot hb4;
    private static Vector<Karte> karten;
    private static Karte bohne;

    /**
     * erstellt HandelBots und einen Vektor mit Karten für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException {

        bot = new Bot_Leicht(1);
        hb = new HandelBot(bot);
        bot2 = new Bot_Leicht(2);
        hb2 = new HandelBot(bot2);
        bot3 = new Bot_Schwer(3);
        hb3 = (HandelBot) bot3.setHaendler();
        bot4 = new Bot_Schwer(4);
        hb4 = (HandelBot) bot4.setHaendler();

        bohne = new Gartenbohne();
        karten = new Vector<>();
        karten.add(bohne);
        karten.add(bohne);
    }

    /**
     * testet, ob der richtige Bot zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getSpieler() throws RemoteException {
        assertEquals(bot, hb.getSpieler());
        assertEquals(bot2, hb2.getSpieler());
        assertEquals(bot3, hb3.getSpieler());
        assertEquals(bot4, hb4.getSpieler());
    }

    /**
     * testet, ob ein Angebot richtig zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getAngebot() throws RemoteException {
        assertEquals(new Vector<Karte>(), hb.getAngebot());
        hb.setAngebot(karten);
        assertEquals(karten, hb.getAngebot());

        hb.setAngebot(null);

    }

    /**
     * testet, ob sich ein Angebot anpassen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setAngebot() throws RemoteException {
        hb.setAngebot(karten);
        assertEquals(karten, hb.getAngebot());
        Vector<Karte> nu = null;
        hb.setAngebot(nu);
        assertEquals(nu, hb.getAngebot());
    }

    /**
     * testet, ob sich ein Handel starten lässt
     */
    @Test
    public void starteHandel() {
        hb.starteHandel(hb2);
        hb2.starteHandel(hb);
        assertEquals(hb2, hb.getPartner());
        assertEquals(hb, hb2.getPartner());
    }

    /**
     * testet, ob ein Angebot empfangen werden kann, für einen leichten Bot
     *
     * @throws MalformedURLException wenn URL falsch
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void receiveAngebot() throws MalformedURLException, RemoteException {
        hb.receiveAngebot();
        assertFalse(hb.acceptHandel());
    }

    /**
     * testet, ob ein Angebot empfangen werden kann, für einen schweren Bot
     * wirft einen StackOverflowError, da es "nicht gedacht" ist, dass
     * zwei Bots untereinander handeln.
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test(expected = StackOverflowError.class)
    public void receiveAngebot2() throws IOException {

        HandelServer hs = new HandelServer();
        hs.join(hb3);
        hs.join(hb4);
        hs.starteHandel(bot3, bot4);
        hb3.setAngebot(karten);
        hb4.receiveAngebot();

    }

    /**
     * testet, ob ein leichter Bot einen Handel annimmt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void acceptHandel() throws RemoteException {
        assertFalse(hb.acceptHandel());
    }

    /**
     * testet, ob schließeFenster nichts tut:
     *
     * @throws MalformedURLException wenn URL falsch
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void schließeFenster() throws MalformedURLException, RemoteException {
        hb.schließeFenster();
    }

    /**
     * testet, ob sich ein Handelspartner setzen lässt
     */
    @Test
    public void getPartner() {
        hb.starteHandel(hb2);
        assertEquals(hb2, hb.getPartner());
    }

    /**
     * testet, ob Sc korrekt funktioniert
     * sc soll immer null sein
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getSc() throws RemoteException {
        hb.setSC(new SpielraumController());
        assertEquals(null, hb.getSc());
    }

    /**
     * testet, ob getHc korrekt funktioniert
     * hc soll immer null sein
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHc() throws RemoteException {
        hb.setHC(new HandelController());
        assertEquals(null, hb.getHc());
    }

    /**
     * testet, ob setSc nichts tut:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setSc() throws RemoteException {
        hb.setSC(new SpielraumController());
        assertEquals(null, hb.getSc());
    }

    /**
     * testet, ob setHc nichts tut:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setHc() throws RemoteException {
        hb.setHC(new HandelController());
        assertEquals(null, hb.getHc());
    }
}