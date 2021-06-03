package Tests.Spielobjekte;

import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.Feuerbohne;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class FeuerbohneTest {

    private static Karte bohne;

    /**
     * erstellt eine neue Feuerbohne für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException {
        bohne = new Feuerbohne();
    }

    /**
     * testet, ob die richtige Bohnenart zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getBohnenArt() throws RemoteException {
        assertEquals("Feuerbohne", bohne.getBohnenArt());
    }

    /**
     * testet, ob die richtige Häufigkeit zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHaeufigkeit() throws RemoteException {
        assertEquals(18, bohne.getHaeufigkeit());
    }

    /**
     * testet, ob der richtige Talerwert für jede Anzahl Karten zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getTalerwert() throws RemoteException {
        assertEquals(0, bohne.getTalerwert(-1));
        assertEquals(0, bohne.getTalerwert(2));
        assertEquals(1, bohne.getTalerwert(3));
        assertEquals(1, bohne.getTalerwert(5));
        assertEquals(2, bohne.getTalerwert(6));
        assertEquals(2, bohne.getTalerwert(7));
        assertEquals(3, bohne.getTalerwert(8));
        assertEquals(4, bohne.getTalerwert(9));
        assertEquals(4, bohne.getTalerwert(283823));
    }

    /**
     * testet, ob die richtige ImgURL zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getImgURL() throws RemoteException {
        assertEquals("/GUI/Bilder/Feuerbohne.jpg", bohne.getImgURL());
    }

    /**
     * testet, ob der richtige MaxWert zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getMaxWert() throws RemoteException {
        assertEquals(4, bohne.getMaxWert());
    }
}