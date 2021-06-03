package Tests;

import Bestenliste.Bestenliste_Impl;
import LogIn.LogInImpl;
import Server.DatenbankAdresse;
import User.Benutzer;
import User.BenutzerImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class Bestenliste_ImplTest {
    // JDBC Treiber name und Datenbanklink
    static final String JDBC_DRIVER = DatenbankAdresse.getJdbcDriver();
    static final String DB_URL = DatenbankAdresse.getDbUrl();

    //  Datenbank Login
    static final String USER = DatenbankAdresse.getUSER();
    static final String PASS = DatenbankAdresse.getPASS();

    // Connection
    static Connection conn;
    static Statement stat;


    private static Vector<Benutzer> vec;
    private static Benutzer be1;
    private static Benutzer be2;
    private static Benutzer be3;
    private static LogInImpl login;
    private static Bestenliste_Impl best;
    private static int lengthbefore;

    /**
     * erstellt neue Benutzer und einen Vektor zum Vergleich, stellt eine Verbindung zur DB her
     *
     * @throws RemoteException        wenn bei Verbindung zum Server etwas schiefläuft
     * @throws ClassNotFoundException wenn Klasse nicht gefunden wird
     * @throws SQLException           wenn bei Verbindung zur DB etwas schiefläuft
     */
    @BeforeClass
    public static void BeforeClass() throws RemoteException, ClassNotFoundException, SQLException {
        //SQL-Abfrage
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stat = conn.createStatement();

        login = new LogInImpl();
        vec = new Vector<>();
        best = new Bestenliste_Impl();
        be1 = new BenutzerImpl("getListe0", "pass", 0, 0);
        be2 = new BenutzerImpl("getListe1", "pass", 0, 1);
        be3 = new BenutzerImpl("getListe99", "pass", 0, 99);
        vec.addElement(be3);
        vec.addElement(be2);
        vec.addElement(be1);
        lengthbefore = best.getListe().size();
    }

    /**
     * schließt nach allen Tests die Verbindung zur DB
     *
     * @throws SQLException wenn bei Verbindung zur DB etwas schiefläuft
     */
    @AfterClass
    public static void afterclass() throws SQLException {
        stat.close();
        conn.close();
    }

    /**
     * Tested ob eine Bestenliste zuruck gegeben wird mit den TOP 5 Spielern mit den meisten gewonnenen Spielen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     * @throws SQLException    wenn bei Verbindung zur DB etwas schiefläuft
     */
    @Test
    public void getListe() throws RemoteException, SQLException {
        //registriert User in der Liste zum Vergleich:
        login.registrieren("getListe0", "pass", "pass");
        login.registrieren("getListe1", "pass", "pass");
        login.registrieren("getListe99", "pass", "pass");
        String sql1 = "UPDATE benutzer SET gewonnene_spiele = 1 WHERE nutzername = 'getListe1'";
        String sql2 = "UPDATE benutzer SET gewonnene_spiele = 99 WHERE nutzername = 'getListe99'";
        stat.executeUpdate(sql1);
        stat.executeUpdate(sql2);

        assertEquals(vec.size() + lengthbefore, best.getListe().size());

        //checkt, ob die Liste absteigend sortiert ist:
        boolean sorted = true;
        Benutzer prev = best.getListe().elementAt(0);

        for (int i = 1; i < best.getListe().size(); i++) {
            if (prev.getGewonnene_Spiele() < best.getListe().get(i).getGewonnene_Spiele()) {
                sorted = false;
            }
        }

        assertTrue(sorted);
    }

    /**
     * löscht die Test-Benutzer nach jedem Tests aus der DB
     *
     * @throws SQLException wenn bei Verbindung zur DB etwas schiefläuft
     */
    @After
    public void after() throws SQLException {
        String sql3 = "DELETE FROM benutzer WHERE nutzername = 'getListe0'";
        String sql4 = "DELETE FROM benutzer WHERE nutzername = 'getListe1'";
        String sql5 = "DELETE FROM benutzer WHERE nutzername = 'getListe99'";
        stat.executeUpdate(sql3);
        stat.executeUpdate(sql4);
        stat.executeUpdate(sql5);
    }
}