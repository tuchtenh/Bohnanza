package Tests.Spielobjekte;

import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.Gartenbohne;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class GartenbohneTest {

    private static Karte bohne;

    /**
     * erstellt eine neue Gartenbohne für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException {
        bohne = new Gartenbohne();
    }

    /**
     * testet, ob die richtige Bohnenart zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getBohnenArt() throws RemoteException {
        assertEquals("Gartenbohne", bohne.getBohnenArt());
    }

    /**
     * testet, ob die richtige Häufigkeit zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHaeufigkeit() throws RemoteException {
        assertEquals(6, bohne.getHaeufigkeit());
    }

    /**
     * testet, ob der richtige Talerwert für jede Anzahl Karten zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getTalerwert() throws RemoteException {
        assertEquals(0, bohne.getTalerwert(-1));
        assertEquals(0, bohne.getTalerwert(1));
        assertEquals(2, bohne.getTalerwert(2));
        assertEquals(3, bohne.getTalerwert(3));
        assertEquals(3, bohne.getTalerwert(283823));
    }

    /**
     * testet, ob die richtige ImgURL zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getImgURL() throws RemoteException {
        assertEquals("/GUI/Bilder/Gartenbohne.jpg", bohne.getImgURL());
    }

    /**
     * testet, ob der richtige MaxWert zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getMaxWert() throws RemoteException {
        assertEquals(3, bohne.getMaxWert());
    }
}