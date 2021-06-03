package Tests;


import Exception.NameZuKurzException;
import Exception.PasswortZuKurzException;
import User.Benutzer;
import User.BenutzerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class BenutzerTest {

    private static Benutzer testUser;

    /**
     * erstellt einen neuen Benutzer für Tests
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @BeforeClass
    public static void before() throws RemoteException {
        testUser = new BenutzerImpl("User", "pw", 0, 1);
    }

    /**
     * testet, ob der korrekte Name zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getName() throws RemoteException {
        testUser.setName("User");
        assertEquals("User", testUser.getName());
    }

    /**
     * testet, ob sich das Passwort verändern lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getPasswort() throws RemoteException {
        testUser.setPasswort("pw");
        assertEquals("pw", testUser.getPasswort());
    }

    /**
     * testet, ob der korrekte Punktestand abgerufen werden kann
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getPunktestand() throws RemoteException {
        testUser.setPunktestand(3);
        assertEquals(3, testUser.getPunktestand());
    }

    /**
     * testet, ob die korrekte Anzahl gewonnener Spiele zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getGewonnene_Spiele() throws RemoteException {
        testUser.setGewonnene_Spiele(2);
        assertEquals(2, testUser.getGewonnene_Spiele());
    }

    /**
     * testet, ob der Name angepasst werden kann
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setName() throws RemoteException {
        testUser.setName("User1");
        assertEquals("User1", testUser.getName());
    }

    /**
     * testet, ob der Name auf null gesetzt werden kann, soll eine NameZuKurzException
     * auslösen
     *
     * @throws NameZuKurzException wenn der Name zu kurz ist
     * @throws RemoteException     wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test(expected = NameZuKurzException.class)
    public void setName2() throws NameZuKurzException, RemoteException {
        String n = null;
        testUser.setName(n);
    }

    /**
     * testet, ob ein leerer String als Name gesetzt werden kann, soll eine
     * NameZuKurzException auslösen
     *
     * @throws NameZuKurzException der Name soll zu kurz sein, wird daher geworfen
     * @throws RemoteException     wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test(expected = NameZuKurzException.class)
    public void setName3() throws NameZuKurzException, RemoteException {
        testUser.setName("");
    }

    /**
     * testet, ob das Passwort verändert werden kann
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setPasswort() throws RemoteException {
        testUser.setPasswort("pw2");
        assertEquals("pw2", testUser.getPasswort());
    }

    /**
     * testet, ob das Passwort zu einem leeren String geändert werden kann, soll
     * eine PasswortZuKurzException auslösen
     *
     * @throws PasswortZuKurzException soll gewerfen werden, da Passwort zu kurz
     * @throws RemoteException         wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test(expected = PasswortZuKurzException.class)
    public void setPasswort2() throws PasswortZuKurzException, RemoteException {
        testUser.setPasswort("");
    }

    /**
     * testet, ob das Passwort zu null geändert werden kann, soll
     * eine PasswortZuKurzException auslösen
     *
     * @throws PasswortZuKurzException soll geworfen werden, da Passwort zu kurz
     * @throws RemoteException         wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test(expected = PasswortZuKurzException.class)
    public void setPasswort3() throws PasswortZuKurzException, RemoteException {
        String p = null;
        testUser.setPasswort(p);
    }

    /**
     * testet, ob der Punktestand verändert werden kann
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setPunktestand() throws RemoteException {
        testUser.setPunktestand(4);
        assertEquals(4, testUser.getPunktestand());
    }

    /**
     * testet, ob die Anzahl der gewonnenen Spiele veraendert werden kann
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setGewonnene_Spiele() throws RemoteException {
        testUser.setGewonnene_Spiele(5);
        assertEquals(5, testUser.getGewonnene_Spiele());
    }
}