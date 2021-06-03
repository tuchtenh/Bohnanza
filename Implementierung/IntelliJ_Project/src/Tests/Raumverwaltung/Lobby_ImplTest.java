package Tests.Raumverwaltung;

import LogIn.LogInImpl;
import Raumverwaltung.Lobby_Impl;
import Raumverwaltung.Spielvorraum;
import Raumverwaltung.Spielvorraum_Impl;
import Server.DatenbankAdresse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

public class Lobby_ImplTest {
    // JDBC Treiber name und Datenbanklink
    static final String JDBC_DRIVER = DatenbankAdresse.getJdbcDriver();
    static final String DB_URL = DatenbankAdresse.getDbUrl();

    //  Datenbank Login
    static final String USER = DatenbankAdresse.getUSER();
    static final String PASS = DatenbankAdresse.getPASS();

    // Connection
    static Connection conn;
    static Statement stat;

    private static LogInImpl log;
    private static Lobby_Impl lobby;
    private static Spielvorraum_Impl svr1;
    private static Spielvorraum_Impl svr2;
    private static Spielvorraum_Impl svr3;

    /**
     * Erstellt Spielvorräume für Tests, sowie eine LoginImpl und eine Verbindung zur DB
     *
     * @throws RemoteException        wenn bei Verbindung zum Server etwas schiefläuft
     * @throws SQLException           wenn bei Verbindung zur DB etwas schiefläuft
     * @throws ClassNotFoundException wenn die Klasse nicht gefunden wird
     */
    @BeforeClass
    public static void beforeclass() throws RemoteException, SQLException, ClassNotFoundException {
        //SQL-Abfrage
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stat = conn.createStatement();
        svr1 = new Spielvorraum_Impl("TestVorraum1");
        svr2 = new Spielvorraum_Impl("TestVorraum2");
        svr3 = new Spielvorraum_Impl("Test");
        log = new LogInImpl();
    }

    /**
     * legt vor jedem Test eine neue Lobby an
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void before() throws RemoteException {
        lobby = new Lobby_Impl();
    }

    /**
     * Loescht den Benutzer aus der Datenbank
     *
     * @throws SQLException    wenn bei Verbindung zur DB etwas schiefläuft
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void loescheSpieler() throws SQLException, RemoteException {
        assertTrue(log.registrieren("loescheSpieler", "passwort", "passwort"));
        String sql = "SELECT count(nutzername) FROM benutzer WHERE nutzername = 'loescheSpieler'";
        ResultSet rset = stat.executeQuery(sql);
        while (rset.next()) {
            assertEquals(1, rset.getInt(1));
            System.out.println(rset.getString(1));
        }

        assertTrue(lobby.loescheSpieler("loescheSpieler", "passwort"));
        ResultSet rset2 = stat.executeQuery(sql);
        while (rset2.next()) {
            if (0 != parseInt(rset2.getString(1))) {
                System.out.println(rset2.getString(1));
                fail();
            }
        }
        String sql1 = "DELETE FROM benutzer WHERE nutzername = 'loescheSpieler'";
        stat.executeUpdate(sql1);

        stat.close();
        conn.close();
        rset.close();
        rset2.close();
    }

    /**
     * Tested ob ein Spielraum erstellt wurde.
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void spielVorraumErstellen1() throws RemoteException {
        Vector<Spielvorraum> vv = new Vector<>();
        vv.add(svr1);
        lobby.spielVorraumErstellen("TestVorraum1");
        assertEquals(vv.size(), lobby.getRaeume().size());
        for (int i = 0; i < lobby.getRaeume().size(); i++) {
            assertEquals(vv.elementAt(i).getName(), lobby.getRaeume().elementAt(i).getName());
        }
    }


    /**
     * Spielrraumname existiert mit diesen Namen schon
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void spielVorraumErstellen2() throws RemoteException {
        lobby.spielVorraumErstellen("Test");
        assertFalse(lobby.spielVorraumErstellen("Test"));
    }

    /**
     * Tested ob die richtigen Spielvorraeume zuruck gegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getRaeume() throws RemoteException {
        Vector<Spielvorraum_Impl> vvv;
        vvv = new Vector<>();
        vvv.add(svr1);
        vvv.add(svr3);
        vvv.add(svr2);
        lobby.spielVorraumErstellen("TestVorraum1");
        lobby.spielVorraumErstellen("Test");
        lobby.spielVorraumErstellen("TestVorraum2");
        assertEquals(vvv.size(), lobby.getRaeume().size());
        for (int i = 0; i < lobby.getRaeume().size(); i++) {
            assertEquals(vvv.elementAt(i).getName(), lobby.getRaeume().elementAt(i).getName());
        }
    }

    /**
     * tested ob die richtige information ueber den Spielvorraum zuruck gegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getRaum() throws RemoteException {
        lobby.spielVorraumErstellen("TestVorraum5");
        assertEquals("TestVorraum5", lobby.getRaum("TestVorraum5").getName());
        assertEquals(1, lobby.getRaeume().size());
        assertEquals(null, lobby.getRaum("Test"));
    }

    /**
     * Tested ob alle namen von Spielvorraeumen zuruck gegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getNamen() throws RemoteException {
        Vector<String> namen = new Vector<>();
        namen.add("Test");
        namen.add("Test2");
        namen.add("Test3");
        lobby.spielVorraumErstellen("Test");
        lobby.spielVorraumErstellen("Test2");
        lobby.spielVorraumErstellen("Test3");
        for (int i = 0; i < lobby.getRaeume().size(); i++) {
            assertEquals(namen.elementAt(i), lobby.getNamen().elementAt(i));
        }
    }

    /**
     * Tested ob ein raum geloescht wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void loescheRaum() throws RemoteException {
        Vector<String> namen = new Vector<>();
        namen.add("Test");
        namen.add("Test3");
        lobby.spielVorraumErstellen("Test");
        lobby.spielVorraumErstellen("Test2");
        lobby.spielVorraumErstellen("Test3");
        lobby.loescheRaum("Test2");
        for (int i = 0; i < lobby.getRaeume().size(); i++) {
            assertEquals(namen.elementAt(i), lobby.getRaeume().elementAt(i).getName());
        }
    }

    /**
     * Test, ob sich ein nicht angelegter Spielraum loeschen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void loescheRaum2() throws RemoteException {
        Vector<String> namen = new Vector<>();
        namen.add("Test");
        namen.add("Test2");
        namen.add("Test3");
        lobby.spielVorraumErstellen("Test");
        lobby.spielVorraumErstellen("Test2");
        lobby.spielVorraumErstellen("Test3");
        lobby.loescheRaum("Test4");
        for (int i = 0; i < lobby.getRaeume().size(); i++) {
            assertEquals(namen.elementAt(i), lobby.getRaeume().elementAt(i).getName());
        }
    }

}