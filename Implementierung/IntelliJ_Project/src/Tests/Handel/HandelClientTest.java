package Tests.Handel;

import GUI.HandelController;
import GUI.SpielraumController;
import Handel.HandelClient;
import Spieleverwaltung.Spieler;
import Spieleverwaltung.SpielerImpl;
import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.Augenbohne;
import User.BenutzerImpl;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

public class HandelClientTest {
    private static HandelClient hc;
    private static Spieler sp;
    private static BenutzerImpl bn;
    private static HandelClient hc2;
    private static Spieler sp2;
    private static BenutzerImpl bn2;
    private static Vector<Karte> karten;
    private static Karte bohne;
    private static SpielraumController sc;
    private static HandelController hcon;
    private static SpielraumController sc2;
    private static HandelController hcon2;

    /**
     * erstellt Spielern mit Benutzern, sowie einen Vektor mit Karten für Tests
     *
     * @throws RemoteException      wenn bei Verbindung zum Server etwas schiefläuft
     * @throws InterruptedException wenn bei der Ausführung unterbrochen
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException, InterruptedException {
        bn = new BenutzerImpl("test", "test", 0, 0);
        sp = new SpielerImpl(bn);
        hc = new HandelClient(sp);
        bn2 = new BenutzerImpl("test2", "test2", 1, 1);
        sp2 = new SpielerImpl(bn2);
        hc2 = new HandelClient(sp2);
        bohne = new Augenbohne();
        karten = new Vector<>();
        karten.add(bohne);
        karten.add(bohne);

        //damit Controller funktionieren:
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel();
            latch.countDown();
        });
        latch.await();
    }

    /**
     * erstellt vor jedem Test neue HandelsController und SpielraumController
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void before() throws RemoteException {
        hcon = new HandelController();
        hcon2 = new HandelController();
        sc = new SpielraumController();
        sc2 = new SpielraumController();
        hc.setSC(sc2);
        hc2.setSC(sc2);
        hc.setHC(hcon2);
    }

    /**
     * testet, ob getSpieler den richtigen Spieler zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getSpieler() throws RemoteException {
        assertEquals(sp, hc.getSpieler());
        assertEquals(sp2, hc2.getSpieler());
    }

    /**
     * testet, ob ein Angebot richtig zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getAngebot() throws RemoteException {
        assertEquals(new Vector<Karte>(), hc.getAngebot());
        hc.setAngebot(karten);
        assertEquals(karten, hc.getAngebot());

        hc.setAngebot(new Vector<Karte>());

    }

    /**
     * testet, ob sich ein Angebot anpassen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setAngebot() throws RemoteException {
        hc.setAngebot(karten);
        assertEquals(karten, hc.getAngebot());
        Vector<Karte> nu = null;
        hc.setAngebot(nu);
        assertEquals(nu, hc.getAngebot());
    }

    /**
     * testet, ob sich hc anpassen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setHc() throws RemoteException {
        hc.setHC(hcon);
        assertEquals(hcon, hc.getHc());
    }

    /**
     * testet, ob hc richtig zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHc() throws RemoteException {
        hc.setHC(hcon);
        assertEquals(hcon, hc.getHc());
    }

    /**
     * testet, ob setSc den sc anpasst
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setSc() throws RemoteException {
        hc.setSC(sc);
        assertEquals(sc, hc.getSc());
    }

    /**
     * testet, ob getSc den richtigen sc zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getSc() throws RemoteException {
        hc.setSC(sc);
        assertEquals(sc, hc.getSc());
    }

    /**
     * testet, ob starteHandel den Partner setzt
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test
    public void starteHandel() throws IOException {
        hc.starteHandel(hc2);
        assertEquals(hc2, hc.getPartner());
    }

    /**
     * testet, ob receiveAngebot für einen Partner richtig funktioniert
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test
    public void receiveAngebot() throws IOException {
        hcon2.setPartner(hc2);
        hc.starteHandel(hc2);
        Vector<Karte> angebot = new Vector<>();
        hc2.setAngebot(angebot);
        assertEquals(hc2, hc.getPartner());
        hc.receiveAngebot();
        assertEquals(angebot, hcon2.getAngebot());
    }

}