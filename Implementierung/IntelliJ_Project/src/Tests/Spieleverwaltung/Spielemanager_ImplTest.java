package Tests.Spieleverwaltung;

import Bots.Bot_Leicht;
import Exception.*;
import Handel.HandelServer;
import LogIn.LogInImpl;
import Raumverwaltung.Spielraum_Impl;
import Server.DatenbankAdresse;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielemanager;
import Spieleverwaltung.Spielemanager_Impl;
import Spieleverwaltung.SpielerImpl;
import Spieleverwaltung.Spielobjekte.*;
import Spieleverwaltung.Spielobjekte.Karten.*;
import User.BenutzerImpl;
import org.junit.*;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.Vector;

import static org.junit.Assert.*;

public class Spielemanager_ImplTest {
    // JDBC Treiber name und Datenbanklink
    static final String JDBC_DRIVER = DatenbankAdresse.getJdbcDriver();
    static final String DB_URL = DatenbankAdresse.getDbUrl();

    //  Datenbank Login
    static final String USER = DatenbankAdresse.getUSER();
    static final String PASS = DatenbankAdresse.getPASS();

    // Connection
    static Connection conn;
    static Statement stat;
    private static LogInImpl login;
    private static BenutzerImpl user1;
    private static BenutzerImpl user2;
    private static BenutzerImpl user3;
    private static Karte bohne1;
    private static Karte bohne2;
    private static Karte bohne3;
    private static Vector<Karte> karten;
    private Spielemanager_Impl dummy1;
    private Spielemanager_Impl dummy2;
    private Vector<Akteur> player;
    private Vector<Akteur> player2;
    private Vector<Akteur> player3;
    private Akteur spieler1;
    private Akteur spieler2;
    private Akteur spieler3;
    private Bot_Leicht bot1;
    private Bot_Leicht bot2;

    /**
     * erstellt Benutzer sowie Bohnen für Tests, stellt eine Verbindung zur DB her und
     * registiert die Test-Benutzer in dieser
     *
     * @throws RemoteException        wenn bei Verbindung zum Server etwas schiefläuft
     * @throws ClassNotFoundException wenn die Klasse nicht gefunden wird
     * @throws SQLException           wenn bei Verbindung zur DB etwas schiefläuft
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException, ClassNotFoundException, SQLException {
        //SQL-Abfrage
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stat = conn.createStatement();
        //legt für Tests die User in der DB an:
        login = new LogInImpl();
        login.registrieren("TestUser", "test", "test");
        login.registrieren("TestUser2", "test", "test");
        login.registrieren("TestUser3", "test", "test");

        user1 = new BenutzerImpl("TestUser", "test", 0, 5);
        user2 = new BenutzerImpl("TestUser2", "test", 0, 5);
        user3 = new BenutzerImpl("TestUser3", "test", 0, 5);

        bohne1 = new Augenbohne();
        bohne2 = new BlaueBohne();
        bohne3 = new Brechbohne();
    }

    /**
     * schließt nach allen Tests Verbindung zur DB
     *
     * @throws SQLException wenn bei Verbindung zur DB etwas schiefläuft
     */
    @AfterClass
    public static void AfterClass() throws SQLException {
        conn.close();
        stat.close();
    }

    /**
     * legt vor jedem Test neue Spielemanager und Spieler an
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void before() throws RemoteException {
        Karte bohne = new Augenbohne();
        karten = new Vector<>();
        for (int i = 0; i < 20; i++) {
            karten.add(bohne);
        }

        spieler1 = new SpielerImpl(user1);
        spieler2 = new SpielerImpl(user2);
        spieler3 = new SpielerImpl(user3);
        bot1 = new Bot_Leicht(1);
        bot2 = new Bot_Leicht(2);
        //Spiel ohne Bot:
        player = new Vector<>();
        player.add(spieler1);
        player.add(spieler2);
        player.add(spieler3);
        dummy1 = new Spielemanager_Impl(player);
        dummy1.setAblage(new AblagestapelImpl());
        dummy1.setDeck(new DeckImpl(karten));
        dummy1.setActPlayer(null);
        //Spiel mit Bot:
        player2 = new Vector<>();
        player2.addAll(player);
        player2.add(bot1);
        //Spiel nur mit Bots:
        player3 = new Vector<>();
        player3.add(bot1);
        player3.add(bot2);
        player3.add(bot2);

        dummy2 = new Spielemanager_Impl(player2);
    }

    /**
     * testet, ob sich ein Spielemanager mit 2 Spielern erstellen lässt
     * soll eine NichtGenugSpielerException werfen
     *
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     */
    @Test(expected = NichtGenugSpielerException.class)
    public void createSpielemanager() throws RemoteException {
        Vector<Akteur> ak = new Vector<>();
        ak.add(bot1);
        ak.add(bot1);
        Spielemanager_Impl dummy = new Spielemanager_Impl(ak);
    }

    /**
     * testet, ob sich ein Spielemanager mit 6 Spielern erstellen lässt
     * soll eine ZuVieleSpieler werfen
     *
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     */
    @Test(expected = ZuVieleSpielerException.class)
    public void createSpielemanager2() throws RemoteException {
        Vector<Akteur> ak = new Vector<>();
        ak.add(bot1);
        ak.add(bot1);
        ak.add(bot1);
        ak.add(bot1);
        ak.add(bot1);
        ak.add(bot1);
        Spielemanager_Impl dummy = new Spielemanager_Impl(ak);
    }

    /**
     * testet, ob ein leerer Ablagestapel korrekt zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getAblage() throws RemoteException {
        assertEquals(0, dummy1.getAblage().getAnzahlKarten());
    }

    /**
     * testet, ob ein voller Ablagestapel korrekt zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getAblage2() throws RemoteException {

        dummy1.getAblage().addKarte(bohne3);
        dummy1.getAblage().addKarte(bohne3);
        dummy1.getAblage().addKarte(bohne3);

        Ablagestapel ablage = dummy1.getAblage();

        //Vergleich:
        assertEquals(3, ablage.getAnzahlKarten());
        Vector<Karte> stapel = ablage.getKarten();
        for (int i = 0; i < stapel.size(); i++) {
            assertEquals(bohne3, stapel.get(i));
        }

    }

    /**
     * testet, ob das Deck korrekt zurückgegeben wird:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getDeck() throws RemoteException {
        Vector<Karte> deck = dummy1.getDeck().getKarten();
        assertEquals(20, deck.size());
        for (int i = 0; i < 20; i++) {
            assertTrue(deck.get(i) instanceof Augenbohne);
        }
    }

    /**
     * testet, ob die korrekten Spieler zurückgegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getSpieler() throws RemoteException {
        Vector<Akteur> spieler = dummy1.getSpieler();
        assertEquals(3, spieler.size());
        assertEquals(spieler1, spieler.get(0));
        assertEquals(spieler2, spieler.get(1));
        assertEquals(spieler3, spieler.get(2));
    }

    /**
     * testet, ob actPlayer einer der Akteure des Spiels ist und auch nur einer
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getActPlayer() throws RemoteException {
        //vorher null:
        assertEquals(null, dummy1.getActPlayer());

        //setzt einen actPlayer:
        dummy1.setActPlayer(spieler2);
        Akteur actPlayer = dummy1.getActPlayer();

        //zählt die actPlayer des Spiels:
        int count = 0;
        for (int i = 0; i < player.size(); i++) {
            if (player.get(i) == actPlayer) {
                count++;
            }
        }

        //nur einer der Spieler ist actPlayer und dieser ist spieler2:
        assertEquals(1, count);
        assertEquals(spieler2, dummy1.getActPlayer());
    }

    /**
     * testet, ob sich der actPlayer verändern lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setActPlayer() throws RemoteException {
        dummy1.setActPlayer(spieler2);
        assertEquals(spieler2, dummy1.getActPlayer());
    }

    /**
     * testet, ob beim Aufruf von spielBeenden() alle Felder abgeerntet werden, Taler addiert werden
     * und Einträge in der Bestenliste aktualisiert werden
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws SQLException          wenn bei Verbindung zur DB etwas schiefläuft
     */
    @Test
    public void spielBeenden() throws MalformedURLException, RemoteException, SQLException {
        //am Anfang sind alle max_punkte 0;
        String sql = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser'";
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()) {
            int punkte = rs.getInt("max_punkte");
            assertEquals(0, punkte);
        }
        rs.close();
        String sql2 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser2'";
        ResultSet rs2 = stat.executeQuery(sql2);
        while (rs2.next()) {
            int punkte = rs2.getInt("max_punkte");
            assertEquals(0, punkte);
        }
        rs2.close();
        String sql3 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser3'";
        ResultSet rs3 = stat.executeQuery(sql3);
        while (rs3.next()) {
            int punkte = rs3.getInt("max_punkte");
            assertEquals(0, punkte);
        }
        rs3.close();
        //gewonnene Spiele vorher 0 für den Gewinner Spieler 2:
        String gewonnen2 = "SELECT gewonnene_spiele FROM benutzer WHERE nutzername='TestUser2'";
        ResultSet rsgewonnen2 = stat.executeQuery(gewonnen2);
        while (rsgewonnen2.next()) {
            int gewonnen = rsgewonnen2.getInt("gewonnene_spiele");
            assertEquals(0, gewonnen);
        }
        rsgewonnen2.close();


        dummy2.spielSetUp();

        //Anbauen von Bohnen auf den Feldern zum Abernten später:
        //Spieler 1 Bohnen im Wert von 1 + 0 Talern:
        spieler1.getFelder().get(0).anbauen(new Augenbohne());
        spieler1.getFelder().get(0).anbauen(new Augenbohne());
        spieler1.getFelder().get(1).anbauen(new Brechbohne());
        //Spieler 2 Bohnen im Wert von 1 + 2 Talern:
        spieler2.getFelder().get(0).anbauen(new Sojabohne());
        spieler2.getFelder().get(0).anbauen(new Sojabohne());
        spieler2.getFelder().get(1).anbauen(new Feuerbohne());
        spieler2.getFelder().get(1).anbauen(new Feuerbohne());
        spieler2.getFelder().get(1).anbauen(new Feuerbohne());
        spieler2.getFelder().get(1).anbauen(new Feuerbohne());
        spieler2.getFelder().get(1).anbauen(new Feuerbohne());
        spieler2.getFelder().get(1).anbauen(new Feuerbohne());
        //Spieler 3 Bohnen im Wert von 0 + 3 Talern:
        spieler3.getFelder().get(0).anbauen(new Sojabohne());
        spieler3.getFelder().get(1).anbauen(new Gartenbohne());
        spieler3.getFelder().get(1).anbauen(new Gartenbohne());
        spieler3.getFelder().get(1).anbauen(new Gartenbohne());
        //Bot Bohnen im Wert von 0 + 0 Talern:
        bot1.getFelder().get(0).anbauen(new Saubohne());
        bot1.getFelder().get(1).anbauen(new RoteBohne());

        //Taler festlegen:
        //Spieler 1 insgesamt 4 Taler:
        spieler1.addTaler(3);
        //Spieler 2 insgesamt 93 Taler:
        spieler2.addTaler(90);
        //Spieler 3 insgesamt 8 Taler:
        spieler3.addTaler(5);
        //Bot insgesamt 9 Taler:
        bot1.addTaler(9);

        dummy2.spielBeenden();

        //Vergleiche Taler:
        assertEquals(4, spieler1.getTaler());
        assertEquals(93, spieler2.getTaler());
        assertEquals(8, spieler3.getTaler());
        assertEquals(9, bot1.getTaler());
        //alle Felder abgeerntet:
        assertEquals(null, spieler1.getFelder().get(0).getBohnenArt());
        assertEquals(null, spieler1.getFelder().get(1).getBohnenArt());
        assertEquals(null, spieler2.getFelder().get(0).getBohnenArt());
        assertEquals(null, spieler2.getFelder().get(1).getBohnenArt());
        assertEquals(null, spieler3.getFelder().get(0).getBohnenArt());
        assertEquals(null, spieler3.getFelder().get(1).getBohnenArt());
        assertEquals(null, bot1.getFelder().get(0).getBohnenArt());
        assertEquals(null, bot1.getFelder().get(1).getBohnenArt());

        //max_punkte für alle Spieler neu gesetzt:
        String maxsql = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser'";
        ResultSet maxrs = stat.executeQuery(maxsql);
        while (maxrs.next()) {
            int punkte = maxrs.getInt("max_punkte");
            assertEquals(4, punkte);
        }
        maxrs.close();
        String maxsql2 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser2'";
        ResultSet maxrs2 = stat.executeQuery(maxsql2);
        while (maxrs2.next()) {
            int punkte = maxrs2.getInt("max_punkte");
            assertEquals(93, punkte);
        }
        maxrs2.close();
        String maxsql3 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser3'";
        ResultSet maxrs3 = stat.executeQuery(maxsql3);
        while (maxrs3.next()) {
            int punkte = maxrs3.getInt("max_punkte");
            assertEquals(8, punkte);
        }
        maxrs3.close();
        //gewonnene Spiele für den Gewinner wurde erhöht
        String gesql = "SELECT gewonnene_spiele FROM benutzer WHERE nutzername='TestUser2'";
        ResultSet gers = stat.executeQuery(gesql);
        while (gers.next()) {
            int gewonnen = gers.getInt("gewonnene_spiele");
            assertEquals(1, gewonnen);
        }
        gers.close();
    }

    /**
     * SpielSetUp ohne Bot
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void spielSetUp() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        //an alle Spieler wurden Handkarten verteilt:
        assertEquals(5, spieler1.getHandkarten().size());
        assertEquals(5, spieler2.getHandkarten().size());
        assertEquals(5, spieler3.getHandkarten().size());
        //bei allen Spielern wurden Bohnenfelder erstellt:
        assertEquals(3, spieler1.getFelder().size());
        assertEquals(3, spieler2.getFelder().size());
        assertEquals(3, spieler3.getFelder().size());
        //aktuelle Phase ist 1
        assertEquals(1, dummy1.getPhase());
        //einer der Spieler ist actPlayer:
        int count = 0;
        for (int i = 0; i < player.size(); i++) {
            if (player.get(i) == dummy1.getActPlayer()) {
                count++;
            }
        }
        assertEquals(1, count);

    }

    /**
     * SpielSetUp mit Bot
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws InterruptedException  wenn die Ausführung unterbrochen wird
     */
    @Test
    public void spielSetUp2() throws RemoteException, MalformedURLException, InterruptedException {
        //füge Bot hinzu + mehr Karten:
        dummy2.setAblage(new AblagestapelImpl());
        dummy2.setDeck(new DeckImpl());
        dummy2.setActPlayer(null);
        dummy2.spielSetUp();
        HandelServer hs = (HandelServer) dummy2.getHandelServer();
        //an alle Spieler wurden Handkarten verteilt:
        assertEquals(5, spieler1.getHandkarten().size());
        assertEquals(5, spieler2.getHandkarten().size());
        assertEquals(5, spieler3.getHandkarten().size());
        assertEquals(5, bot1.getHandkarten().size());
        //bei allen Spielern wurden Bohnenfelder erstellt:
        assertEquals(2, spieler1.getFelder().size());
        assertEquals(2, spieler2.getFelder().size());
        assertEquals(2, spieler3.getFelder().size());
        assertEquals(2, bot1.getFelder().size());
        Thread.sleep(1000);
        //für Bot wurde Spielemanager und Haendler festgelegt:
        assertNotNull(bot1.getSpielemanager());
        Thread.sleep(1000);
        assertNotNull(bot1.getHaendler());
        //aktuelle Phase ist 1
        assertEquals(1, dummy2.getPhase());
        //einer der Spieler ist actPlayer:
        int count = 0;
        for (int i = 0; i < player2.size(); i++) {
            if (player2.get(i) == dummy2.getActPlayer()) {
                count++;
            }
        }
        assertEquals(1, count);
        //Bot zum Handelserver hinzugefügt:
        assertEquals(1, hs.getClients().size());

    }

    /**
     * testet, ob isReady für ein nicht gestartetes Spiel korrekt zurückgegeben wird:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void isReady() throws RemoteException {
        Spielemanager_Impl dummy4 = new Spielemanager_Impl(player);
        assertFalse(dummy4.isReady());
    }

    /**
     * testet, ob isReady für ein gestartetes Spiel korrekt zurückgegeben wird:
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void isReady2() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertTrue(dummy1.isReady());
    }

    /**
     * Tested ob die Handkarten und Bohnenfelder bei aufruf von anbauenPhase1() richtig veraendert werden
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void anbauenPhase1Test1() throws RemoteException, MalformedURLException {
        // Spielemanager object
        dummy1.setAblage(new AblagestapelImpl());
        dummy1.setDeck(new DeckImpl(karten));
        dummy1.spielSetUp();
        dummy1.setActPlayer(dummy1.getSpieler().elementAt(2));
        //Custom object
        Vector<Bohnenfeld> bohnfeld = new Vector<>();
        bohnfeld.add(new BohnenfeldImpl());
        bohnfeld.add(new BohnenfeldImpl());
        bohnfeld.add(new BohnenfeldImpl());
        Vector<Karte> hand = new Vector<>();
        for (int i = 0; i < 5; i++) {
            hand.add(new Augenbohne());
        }
        Vector<Bohnenfeld> spielerFelder = dummy1.getSpieler().elementAt(2).getFelder();

        assertEquals(bohnfeld.size(), spielerFelder.size());
        for (int i = 0; i < spielerFelder.size(); i++) {
            assertEquals(bohnfeld.elementAt(i).getBohnenArt(), spielerFelder.elementAt(i).getBohnenArt());
            assertEquals(bohnfeld.elementAt(i).getAnzahlKarten(), spielerFelder.elementAt(i).getAnzahlKarten());
        }
        assertEquals(hand.size(), dummy1.getSpieler().elementAt(2).getHandkarten().size());
        for (int i = 0; i < dummy1.getSpieler().elementAt(2).getHandkarten().size(); i++) {
            assertEquals(hand.elementAt(i).getBohnenArt(), hand.elementAt(i).getBohnenArt());
        }
        bohnfeld.elementAt(0).anbauen(new Augenbohne());
        hand.remove(0);
        //anbauenPhase1
        dummy1.anbauenPhase1(dummy1.getSpieler().elementAt(2), 0);

        for (int i = 0; i < spielerFelder.size(); i++) {
            assertEquals(bohnfeld.elementAt(i).getBohnenArt(), spielerFelder.elementAt(i).getBohnenArt());
            assertEquals(bohnfeld.elementAt(i).getAnzahlKarten(), spielerFelder.elementAt(i).getAnzahlKarten());
        }
        assertEquals(hand.size(), dummy1.getSpieler().elementAt(2).getHandkarten().size());
        for (int i = 0; i < dummy1.getSpieler().elementAt(2).getHandkarten().size(); i++) {
            assertEquals(hand.elementAt(i).getBohnenArt(), hand.elementAt(i).getBohnenArt());
        }
    }

    /**
     * Tested ob eine exception geworfen wird bei einem versuch eine dritte karte in Phase 1 anzubauen
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test(expected = ZuVieleKartenAngebautException.class)
    public void anbauenPhase1Test2() throws RemoteException, MalformedURLException {
        Spielemanager_Impl dummy3 = new Spielemanager_Impl(player);
        dummy3.setAblage(new AblagestapelImpl());
        dummy3.setDeck(new DeckImpl(karten));
        dummy3.spielSetUp();
        dummy3.setActPlayer(dummy3.getSpieler().elementAt(2));
        dummy3.anbauenPhase1(dummy3.getSpieler().elementAt(2), 0);
        dummy3.anbauenPhase1(dummy3.getSpieler().elementAt(2), 0);
        dummy3.anbauenPhase1(dummy3.getSpieler().elementAt(2), 0);
    }

    /**
     * Tested ob eine exception geworfen wird bei einem versuch Karten anzubauen in Phase 1 wenn man nicht der Acting Player ist
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test(expected = SpielerNichtAmZugException.class)
    public void anbauenPhase1Test3() throws RemoteException, MalformedURLException {
        Spielemanager_Impl dummy3 = new Spielemanager_Impl(player);
        dummy3.setAblage(new AblagestapelImpl());
        dummy3.setDeck(new DeckImpl(karten));
        dummy3.spielSetUp();
        dummy3.setActPlayer(dummy3.getSpieler().elementAt(0));
        dummy3.anbauenPhase1(dummy3.getSpieler().elementAt(1), 0);
    }

    /**
     * Tested ob eine exception geworfen wird bei einem versuch Karten anzubauen in Phase 1 wenn alle Felder schon belegt sind mit andere Arten
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test(expected = FeldVollException.class)
    public void anbauenPhase1Test4() throws RemoteException, MalformedURLException {
        Spielemanager_Impl dummy3 = new Spielemanager_Impl(player);
        dummy3.setAblage(new AblagestapelImpl());
        dummy3.setDeck(new DeckImpl(karten));
        dummy3.spielSetUp();
        dummy3.setActPlayer(dummy3.getSpieler().elementAt(0));
        dummy3.getActPlayer().getFelder().elementAt(0).anbauen(new BlaueBohne());
        dummy3.getActPlayer().getFelder().elementAt(1).anbauen(new RoteBohne());
        dummy3.getActPlayer().getFelder().elementAt(2).anbauen(new Sojabohne());
        dummy3.anbauenPhase1(dummy3.getSpieler().elementAt(0), 0);
    }

    /**
     * Tested ob die gehandelten karten richtig angebaut werden.
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void anbauenPhase3Test1() throws RemoteException, MalformedURLException {
        Spielemanager_Impl dummy4 = new Spielemanager_Impl(player);
        dummy4.setAblage(new AblagestapelImpl());
        dummy4.setDeck(new DeckImpl(karten));
        dummy4.spielSetUp();
        Vector<Bohnenfeld> bohnfeld = new Vector<>();
        bohnfeld.add(new BohnenfeldImpl());
        bohnfeld.add(new BohnenfeldImpl());
        bohnfeld.add(new BohnenfeldImpl());
        Vector<Karte> handelfeld = new Vector<>();
        handelfeld.add(new Augenbohne());
        handelfeld.add(new Sojabohne());
        dummy4.getSpieler().elementAt(1).getGehandelteKarten().add(new Augenbohne());
        dummy4.getSpieler().elementAt(1).getGehandelteKarten().add(new Sojabohne());
        assertEquals(handelfeld.size(), dummy4.getSpieler().elementAt(1).getGehandelteKarten().size());
        for (int i = 0; i < dummy4.getSpieler().elementAt(1).getGehandelteKarten().size(); i++) {
            assertEquals(handelfeld.elementAt(i).getBohnenArt(), dummy4.getSpieler().elementAt(1).getGehandelteKarten().elementAt(i).getBohnenArt());
        }
        assertEquals(bohnfeld.size(), dummy4.getSpieler().elementAt(1).getFelder().size());
        for (int i = 0; i < dummy4.getSpieler().elementAt(1).getFelder().size(); i++) {
            assertEquals(bohnfeld.elementAt(i).getBohnenArt(), dummy4.getSpieler().elementAt(1).getFelder().elementAt(i).getBohnenArt());
            assertEquals(bohnfeld.elementAt(i).getAnzahlKarten(), dummy4.getSpieler().elementAt(1).getFelder().elementAt(i).getAnzahlKarten());
        }
        dummy4.anbauenPhase3(dummy4.getSpieler().elementAt(1), 0, 1);
        bohnfeld.elementAt(0).anbauen(new Sojabohne());
        handelfeld.removeElementAt(1);
        assertEquals(handelfeld.size(), dummy4.getSpieler().elementAt(1).getGehandelteKarten().size());
        for (int i = 0; i < dummy4.getSpieler().elementAt(1).getGehandelteKarten().size(); i++) {
            assertEquals(handelfeld.elementAt(i).getBohnenArt(), dummy4.getSpieler().elementAt(1).getGehandelteKarten().elementAt(i).getBohnenArt());
        }
        assertEquals(bohnfeld.size(), dummy4.getSpieler().elementAt(1).getFelder().size());
        for (int i = 0; i < dummy4.getSpieler().elementAt(1).getFelder().size(); i++) {
            assertEquals(bohnfeld.elementAt(i).getBohnenArt(), dummy4.getSpieler().elementAt(1).getFelder().elementAt(i).getBohnenArt());
            assertEquals(bohnfeld.elementAt(i).getAnzahlKarten(), dummy4.getSpieler().elementAt(1).getFelder().elementAt(i).getAnzahlKarten());
        }


    }

    /**
     * Tested ob eine exception geworfen wird wenn man gehandelte Karten versucht auf einen schon mit einer anderen Bohnenart belegtem Feld anzubauen
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test(expected = FeldVollException.class)
    public void anbauenPhase3Test2() throws RemoteException, MalformedURLException {
        Spielemanager_Impl dummy3 = new Spielemanager_Impl(player);
        dummy3.setAblage(new AblagestapelImpl());
        dummy3.setDeck(new DeckImpl(karten));
        dummy3.spielSetUp();
        dummy3.getSpieler().elementAt(0).getFelder().elementAt(0).anbauen(new Sojabohne());
        dummy3.getSpieler().elementAt(0).getFelder().elementAt(1).anbauen(new BlaueBohne());
        dummy3.getSpieler().elementAt(0).getFelder().elementAt(2).anbauen(new RoteBohne());
        dummy3.getSpieler().elementAt(0).getGehandelteKarten().add(new Augenbohne());
        dummy3.anbauenPhase3(dummy3.getSpieler().elementAt(0), 0, 0);
    }

    /**
     * Tested ob die korrekte Summe von Taller hinzugefuegt wird, ob der Ablagestapel korrekt veraendert wird
     * und ob die anzahl von gegebenen Taller von karten aus dem Spiel geloscht wird.
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void ernten1() throws RemoteException, MalformedURLException {
        Spielemanager_Impl dummy3 = new Spielemanager_Impl(player);
        dummy3.setAblage(new AblagestapelImpl());
        dummy3.setDeck(new DeckImpl(karten));
        dummy3.spielSetUp();
        Vector<Bohnenfeld> bohnfeld = new Vector<>();
        bohnfeld.add(new BohnenfeldImpl());
        bohnfeld.add(new BohnenfeldImpl());
        bohnfeld.add(new BohnenfeldImpl());
        bohnfeld.elementAt(0).anbauen(new Sojabohne());
        bohnfeld.elementAt(0).anbauen(new Sojabohne());
        bohnfeld.elementAt(0).anbauen(new Sojabohne());
        bohnfeld.elementAt(1).anbauen(new RoteBohne());
        bohnfeld.elementAt(1).anbauen(new RoteBohne());
        bohnfeld.elementAt(1).anbauen(new RoteBohne());
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(0).anbauen(new Sojabohne());
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(0).anbauen(new Sojabohne());
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(0).anbauen(new Sojabohne());
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(1).anbauen(new RoteBohne());
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(1).anbauen(new RoteBohne());
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(1).anbauen(new RoteBohne());
        assertEquals(bohnfeld.size(), dummy3.getSpieler().elementAt(1).getFelder().size());
        for (int i = 0; i < dummy3.getSpieler().elementAt(1).getFelder().size(); i++) {
            assertEquals(bohnfeld.elementAt(i).getBohnenArt(), dummy3.getSpieler().elementAt(1).getFelder().elementAt(i).getBohnenArt());
            assertEquals(bohnfeld.elementAt(i).getAnzahlKarten(), dummy3.getSpieler().elementAt(1).getFelder().elementAt(i).getAnzahlKarten());
        }
        AblagestapelImpl ablage = new AblagestapelImpl();
        assertEquals(ablage.getAnzahlKarten(), dummy3.getAblage().getAnzahlKarten());
        for (int i = 0; i < dummy3.getAblage().getAnzahlKarten(); i++) {
            assertEquals(ablage.getKarten().elementAt(i).getBohnenArt(), dummy3.getAblage().getKarten().elementAt(i).getBohnenArt());
        }
        int taler = 0;
        assertEquals(taler, dummy3.getSpieler().elementAt(1).getTaler());
        dummy3.ernten(dummy3.getSpieler().elementAt(1), 0);
        bohnfeld.elementAt(0).ernten();
        taler = taler + 1;
        assertEquals(taler, dummy3.getSpieler().elementAt(1).getTaler());
        assertEquals(bohnfeld.size(), dummy3.getSpieler().elementAt(1).getFelder().size());
        for (int i = 0; i < dummy3.getSpieler().elementAt(1).getFelder().size(); i++) {
            assertEquals(bohnfeld.elementAt(i).getBohnenArt(), dummy3.getSpieler().elementAt(1).getFelder().elementAt(i).getBohnenArt());
            assertEquals(bohnfeld.elementAt(i).getAnzahlKarten(), dummy3.getSpieler().elementAt(1).getFelder().elementAt(i).getAnzahlKarten());
        }
        ablage.addKarte(new Sojabohne());
        ablage.addKarte(new Sojabohne());
        assertEquals(ablage.getAnzahlKarten(), dummy3.getAblage().getAnzahlKarten());
        for (int i = 0; i < dummy3.getAblage().getAnzahlKarten(); i++) {
            assertEquals(ablage.getKarten().elementAt(i).getBohnenArt(), dummy3.getAblage().getKarten().elementAt(i).getBohnenArt());
        }
        dummy3.ernten(dummy3.getSpieler().elementAt(1), 1);
        bohnfeld.elementAt(1).ernten();
        taler = taler + 2;
        assertEquals(taler, dummy3.getSpieler().elementAt(1).getTaler());
        assertEquals(bohnfeld.size(), dummy3.getSpieler().elementAt(1).getFelder().size());
        for (int i = 0; i < dummy3.getSpieler().elementAt(1).getFelder().size(); i++) {
            assertEquals(bohnfeld.elementAt(i).getBohnenArt(), dummy3.getSpieler().elementAt(1).getFelder().elementAt(i).getBohnenArt());
            assertEquals(bohnfeld.elementAt(i).getAnzahlKarten(), dummy3.getSpieler().elementAt(1).getFelder().elementAt(i).getAnzahlKarten());
        }
        ablage.addKarte(new RoteBohne());
        assertEquals(ablage.getAnzahlKarten(), dummy3.getAblage().getAnzahlKarten());
        for (int i = 0; i < dummy3.getAblage().getAnzahlKarten(); i++) {
            assertEquals(ablage.getKarten().elementAt(i).getBohnenArt(), dummy3.getAblage().getKarten().elementAt(i).getBohnenArt());
        }
    }

    /**
     * Tested ob eine BohnenSchutzRegel exception geworfen wird bei verletzung von BohnenSchutzRegel
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test(expected = BohnenSchutzRegelException.class)
    public void ernten2() throws RemoteException, MalformedURLException {
        Spielemanager_Impl dummy3 = new Spielemanager_Impl(player);
        dummy3.setAblage(new AblagestapelImpl());
        dummy3.setDeck(new DeckImpl(karten));
        dummy3.spielSetUp();
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(0).anbauen(new Sojabohne());
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(0).anbauen(new Sojabohne());
        dummy3.getSpieler().elementAt(1).getFelder().elementAt(1).anbauen(new RoteBohne());
        dummy3.ernten(dummy3.getSpieler().elementAt(1), 1);
    }

    /**
     * Tested ob die Richtige Phase zurueck gegeben wird
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void getPhase() throws RemoteException, MalformedURLException {
        Spielemanager_Impl dummy3 = new Spielemanager_Impl(player);
        dummy3.setAblage(new AblagestapelImpl());
        dummy3.setDeck(new DeckImpl(karten));
        dummy3.spielSetUp();
        assertEquals(1, dummy3.getPhase());
        dummy3.anbauenPhase1(dummy3.getActPlayer(), 0);
        dummy3.nextPhase();
        assertEquals(2, dummy3.getPhase());
        dummy3.nextPhase();
        assertEquals(3, dummy3.getPhase());
        dummy3.anbauenPhase3(dummy3.getActPlayer(), 0, 1);
        dummy3.anbauenPhase3(dummy3.getActPlayer(), 0, 0);
        dummy3.nextPhase();
        assertEquals(4, dummy3.getPhase());

    }

    /**
     * Wechsel von Phase 1 auf Phase 2:
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void nextPhase() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
    }

    /**
     * Wechsel von Phase 2 auf Phase 3:
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void nextPhase2() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(3, dummy1.getPhase());
    }

    /**
     * Wechsel von Phase 3 auf Phase 4:
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void nextPhase3() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(3, dummy1.getPhase());

        for (int i = 0; i < player.size(); i++) {
            while (player.get(i).getGehandelteKarten().size() > 0) {
                player.get(i).removeGehandelteKarte(0);
            }
        }
        dummy1.nextPhase();
        assertEquals(4, dummy1.getPhase());
    }

    /**
     * Wechsel von Phase 4 auf Phase 1:
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void nextPhase4() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(3, dummy1.getPhase());

        for (int i = 0; i < player.size(); i++) {
            while (player.get(i).getGehandelteKarten().size() > 0) {
                player.get(i).removeGehandelteKarte(0);
            }
        }
        dummy1.nextPhase();
        assertEquals(4, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(1, dummy1.getPhase());
    }

    /**
     * Wechsel von Phase 1 auf Phase 2: actPlayer hat nicht angebaut
     * soll NixAngebautException werfen
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test(expected = NixAngebautException.class)
    public void nextPhase5() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.nextPhase();
    }

    /**
     * Wechsel von Phase 3 auf Phase 4: nicht alle aufgedeckten Karten angebaut
     * soll NichtAlleKartenAngebautException werfen
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test(expected = NichtAlleKartenAngebautException.class)
    public void nextPhase6() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(3, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(4, dummy1.getPhase());
    }

    /**
     * Wechsel von Phase 4 auf Phase 1: Anzahl Leerungen 3
     * soll spielBeenden() aufrufen, aktuellePhase = -1
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void nextPhase7() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(3, dummy1.getPhase());

        for (int i = 0; i < player.size(); i++) {
            while (player.get(i).getGehandelteKarten().size() > 0) {
                player.get(i).removeGehandelteKarte(0);
            }
        }
        dummy1.nextPhase();
        assertEquals(4, dummy1.getPhase());
        //leert das Deck und setzt anzahlLeerungen auf 3:
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        dummy1.neuesDeck();
        dummy1.neuesDeck();
        dummy1.nextPhase();

        //Phase ist -1:
        assertEquals(-1, dummy1.getPhase());
        //Spiel ist beendet:
        assertTrue(dummy1.getFinished());
    }

    /**
     * Wechsel von Phase 4 auf Phase 1: Stapel geht leer
     * für StapelLeerungen, setzt anzahlLeerungen hoch
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Test
    public void nextPhase8() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(3, dummy1.getPhase());

        for (int i = 0; i < player.size(); i++) {
            while (player.get(i).getGehandelteKarten().size() > 0) {
                player.get(i).removeGehandelteKarte(0);
            }
        }
        dummy1.nextPhase();
        assertEquals(4, dummy1.getPhase());
        //leert das Deck:
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        AblagestapelImpl ab = new AblagestapelImpl();
        for (Karte k : karten) {
            ab.addKarte(k);
        }
        dummy1.setAblage(ab);
        assertEquals(0, dummy1.getAnzahlLeerungen());
        dummy1.nextPhase();
        assertEquals(1, dummy1.getAnzahlLeerungen());
    }

    /**
     * Wechsel von Phase 4 auf Phase 1: Stapel geht leer
     * für StapelLeerungen =3
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void nextPhase9() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(3, dummy1.getPhase());

        for (int i = 0; i < player.size(); i++) {
            while (player.get(i).getGehandelteKarten().size() > 0) {
                player.get(i).removeGehandelteKarte(0);
            }
        }
        dummy1.nextPhase();
        assertEquals(4, dummy1.getPhase());
        //leert das Deck dreimal:
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        assertEquals(3, dummy1.getAnzahlLeerungen());
        //Deck ist leer:
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.nextPhase();

        //Phase ist -1 und Spiel ist beendet:
        assertEquals(-1, dummy1.getPhase());
        assertEquals(true, dummy1.getFinished());
    }

    /**
     * Wechsel von Phase 4 auf Phase 1: Stapel geht leer
     * für StapelLeerungen =2
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void nextPhase10() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        assertEquals(1, dummy1.getPhase());
        dummy1.getActPlayer().setHatAngebaut(1);
        dummy1.nextPhase();
        assertEquals(2, dummy1.getPhase());
        dummy1.nextPhase();
        assertEquals(3, dummy1.getPhase());

        for (int i = 0; i < player.size(); i++) {
            while (player.get(i).getGehandelteKarten().size() > 0) {
                player.get(i).removeGehandelteKarte(0);
            }
        }
        dummy1.nextPhase();
        assertEquals(4, dummy1.getPhase());
        //leert das Deck dreimal:
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        assertEquals(2, dummy1.getAnzahlLeerungen());
        //Deck ist leer:
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.nextPhase();

        //Phase ist -1 und Spiel ist beendet:
        assertEquals(-1, dummy1.getPhase());
        assertEquals(true, dummy1.getFinished());
    }

    /**
     * testet, ob ein HandelServer zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHandelServer() throws RemoteException {
        assertNotEquals(null, dummy1.getHandelServer());
    }

    /**
     * deckt 2 Karten auf
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void deckeKartenAuf() throws RemoteException {
        assertEquals(0, dummy1.getAufgedeckteKarten().size());
        assertEquals(20, dummy1.getDeck().getAnzahlKarten());
        dummy1.deckeKartenAuf();
        assertEquals(2, dummy1.getAufgedeckteKarten().size());
        assertEquals(18, dummy1.getDeck().getAnzahlKarten());
    }

    /**
     * deckeKartenAuf bei leerem Deck, anzahlLeerungen kleiner 3
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void deckeKartenAuf2() throws RemoteException {
        assertEquals(0, dummy1.getAnzahlLeerungen());
        DeckImpl d = new DeckImpl(new Vector<Karte>());
        dummy1.setDeck(d);
        AblagestapelImpl ablage = new AblagestapelImpl();
        for (Karte k : karten) {
            ablage.addKarte(k);
        }
        dummy1.setAblage(ablage);
        dummy1.deckeKartenAuf();
        assertEquals(1, dummy1.getAnzahlLeerungen());
        assertEquals(2, dummy1.getAufgedeckteKarten().size());
    }

    /**
     * deckeKartenAuf bei leerem Deck, anzahlLeerungen = 3
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void deckeKartenAuf3() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        DeckImpl d = new DeckImpl(new Vector<Karte>());
        dummy1.setDeck(d);
        dummy1.neuesDeck();
        dummy1.setDeck(d);
        dummy1.neuesDeck();
        dummy1.setDeck(d);
        dummy1.neuesDeck();
        assertEquals(3, dummy1.getAnzahlLeerungen());
        assertTrue(dummy1.getFinished());
    }

    /**
     * testet, ob die richtigen aufgedeckten Karten zurückgegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getAufgedeckteKarten() throws RemoteException {
        dummy1.deckeKartenAuf();
        assertNotEquals(0, dummy1.getAufgedeckteKarten().size());
    }

    /**
     * testet, ob sich aufgedeckte Karten entfernen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeAufgedeckteKarten() throws RemoteException {
        dummy1.deckeKartenAuf();
        Vector<Integer> indices = new Vector<>();
        indices.add(0);
        dummy1.removeAufgedeckteKarten(indices);
        assertEquals(0, dummy1.getAufgedeckteKarten().size());
    }

    /**
     * testet, ob ein FinisherServer zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getFinisherServer() throws RemoteException {
        assertNotEquals(null, dummy1.getFinisherServer());
    }

    /**
     * testet, ob getFinished für ein laufendes und ein beendetes Spiel den richtigen
     * Wert zurückgibt
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getFinished() throws MalformedURLException, RemoteException {
        dummy1.spielSetUp();
        assertEquals(false, dummy1.getFinished());
        dummy1.spielBeenden();
        assertEquals(true, dummy1.getFinished());
    }

    /**
     * schaut, ob sich die Taler für alle Spieler verändert haben:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     * @throws SQLException    wenn bei Verbindung zur DB etwas schiefläuft
     */
    @Test
    public void updateMaxPunkte() throws SQLException, RemoteException {
        //am Anfang sind alle max_punkte 0;
        String sql = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser'";
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()) {
            int punkte = rs.getInt("max_punkte");
            assertEquals(0, punkte);
        }
        rs.close();
        String sql2 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser2'";
        ResultSet rs2 = stat.executeQuery(sql2);
        while (rs2.next()) {
            int punkte = rs2.getInt("max_punkte");
            assertEquals(0, punkte);
        }
        rs2.close();
        String sql3 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser3'";
        ResultSet rs3 = stat.executeQuery(sql3);
        while (rs3.next()) {
            int punkte = rs3.getInt("max_punkte");
            assertEquals(0, punkte);
        }
        rs3.close();
        //aktualisiert die Taler:

        spieler1.addTaler(1);
        spieler2.addTaler(2);
        spieler3.addTaler(3);

        dummy1.updateMaxPunkte(player);
        //überprüft, ob die Punkte angepasst wurden:
        String sql4 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser'";
        ResultSet rs4 = stat.executeQuery(sql);
        while (rs4.next()) {
            int punkte = rs4.getInt("max_punkte");
            assertEquals(1, punkte);
        }
        rs4.close();
        String sql5 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser2'";
        ResultSet rs5 = stat.executeQuery(sql);
        while (rs5.next()) {
            int punkte = rs5.getInt("max_punkte");
            assertEquals(2, punkte);
        }
        rs.close();
        String sql6 = "SELECT max_punkte FROM benutzer WHERE nutzername='TestUser3'";
        ResultSet rs6 = stat.executeQuery(sql);
        while (rs6.next()) {
            int punkte = rs6.getInt("max_punkte");
            assertEquals(3, punkte);
        }
        rs6.close();

    }

    /**
     * Update bei leerem Vector
     *
     * @throws SQLException    wenn bei Verbindung zur DB etwas schiefläuft
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void updateMaxPunkte2() throws SQLException, RemoteException {
        Vector<Akteur> ak = new Vector<>();
        dummy1.updateMaxPunkte(ak);
    }

    /**
     * schaut, ob für den Gewinner die gewonnenen_spiele hochgesetzt wurden:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     * @throws SQLException    wenn bei Verbindung zur DB etwas schiefläuft
     */
    @Test
    public void updateGewonneneSpiele() throws RemoteException, SQLException {
        //vorher:
        String sql2 = "SELECT gewonnene_spiele FROM benutzer WHERE nutzername='TestUser'";
        ResultSet rs2 = stat.executeQuery(sql2);
        while (rs2.next()) {
            int gewonnen = rs2.getInt("gewonnene_spiele");
            assertEquals(0, gewonnen);
        }
        rs2.close();
        //Update:
        dummy1.updateGewonneneSpiele(spieler1);
        //Nachher:
        String sql = "SELECT gewonnene_spiele FROM benutzer WHERE nutzername='TestUser'";
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()) {
            int gewonnen = rs.getInt("gewonnene_spiele");
            assertEquals(1, gewonnen);
        }
        rs.close();
    }

    /**
     * testet, ob sich der Ablagestapel anpassen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setAblage() throws RemoteException {
        AblagestapelImpl stapel = new AblagestapelImpl();
        dummy1.setAblage(stapel);
        assertEquals(stapel, dummy1.getAblage());
    }

    /**
     * testet, ob sich das Deck verändern lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setDeck() throws RemoteException {
        DeckImpl deck = new DeckImpl();
        dummy1.setDeck(deck);
        assertEquals(deck, dummy1.getDeck());
    }

    /**
     * testet, ob mit nextPlayer() durch die Spieler des Spiels iteriert wird
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void nextPlayer() throws MalformedURLException, RemoteException {
        dummy1.spielSetUp();
        Vector<Akteur> actpl = new Vector<Akteur>();
        //iterieren durch alle actplayer:
        actpl.add(dummy1.getActPlayer());
        dummy1.nextPlayer();
        actpl.add(dummy1.getActPlayer());
        assertNotEquals(actpl.get(0), dummy1.getActPlayer());
        dummy1.nextPlayer();
        actpl.add(dummy1.getActPlayer());
        assertNotEquals(actpl.get(0), dummy1.getActPlayer());
        assertNotEquals(actpl.get(1), dummy1.getActPlayer());
        //alle Spieler wurden einmal actPlayer:
        assertTrue(actpl.containsAll(dummy1.getSpieler()));
        assertTrue(dummy1.getSpieler().containsAll(actpl));
        //hier beginnt eine neue Iteration durch alle Spieler:
        dummy1.nextPlayer();
        assertEquals(actpl.get(0), dummy1.getActPlayer());


    }

    /**
     * testet, ob mit nextPlayer() durch die Spieler des Spiels iteriert wird
     * mit Bots in den Spielern
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void nextPlayer2() throws MalformedURLException, RemoteException {
        Spielraum_Impl sp = new Spielraum_Impl(player2, "Spielraum");
        dummy2.spielSetUp();
        Vector<Akteur> actpl = new Vector<Akteur>();
        //iterieren durch alle actplayer:
        actpl.add(dummy2.getActPlayer());
        dummy2.nextPlayer();
        actpl.add(dummy2.getActPlayer());
        assertNotEquals(actpl.get(0), dummy2.getActPlayer());
        dummy2.nextPlayer();
        actpl.add(dummy2.getActPlayer());
        assertNotEquals(actpl.get(0), dummy2.getActPlayer());
        assertNotEquals(actpl.get(1), dummy2.getActPlayer());
        dummy2.nextPlayer();
        actpl.add(dummy2.getActPlayer());
        assertNotEquals(actpl.get(0), dummy2.getActPlayer());
        assertNotEquals(actpl.get(1), dummy2.getActPlayer());
        assertNotEquals(actpl.get(2), dummy2.getActPlayer());
        //alle Spieler wurden einmal actPlayer:
        assertTrue(actpl.containsAll(dummy2.getSpieler()));
        assertTrue(dummy2.getSpieler().containsAll(actpl));
        //hier beginnt eine neue Iteration durch alle Spieler:
        dummy2.nextPlayer();
        assertEquals(actpl.get(0), dummy2.getActPlayer());
    }

    /**
     * testet, ob ein neues Deck gesetzt wird, wenn altes leer ist
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void neuesDeck() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        AblagestapelImpl abl = new AblagestapelImpl();
        for (Karte k : karten) {
            abl.addKarte(k);
        }
        dummy1.setAblage(abl);
        dummy1.neuesDeck();
        assertTrue(dummy1.getDeck().getKarten().containsAll(karten));
        assertTrue(karten.containsAll(dummy1.getDeck().getKarten()));
    }

    /**
     * testet, ob ein altes Deck nicht verändert wird, wenn es nicht leer ist
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void neuesDeck2() throws RemoteException {
        dummy1.getDeck().ziehe();
        dummy1.getDeck().ziehe();
        DeckImpl deck = (DeckImpl) dummy1.getDeck();
        dummy1.neuesDeck();
        DeckImpl deck2 = (DeckImpl) dummy1.getDeck();
        //Deck wurde nicht verändert:
        assertTrue(deck.getKarten().containsAll(deck2.getKarten()));
        assertTrue(deck2.getKarten().containsAll(deck.getKarten()));
    }

    /**
     * testet, ob spielBeenden() aufgerufen wird, wenn anzahlLeerungen 3 ist, wenn
     * neuesDeck() aufgerufen wird
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void neuesDeck3() throws RemoteException, MalformedURLException {
        dummy1.spielSetUp();
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        dummy1.setDeck(new DeckImpl(new Vector<Karte>()));
        dummy1.neuesDeck();
        assertTrue(dummy1.getFinished());
    }

    /**
     * testet, ob starteSpiel den Zug eines Bots startet, wenn ein Bot am Zug ist
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws InterruptedException  wenn die Ausführung unterbrochen wird
     */
    @Test
    public void starteSpiel() throws MalformedURLException, RemoteException, InterruptedException {
        Spielraum_Impl sp = new Spielraum_Impl(player3, "Spielraum");
        bot1.setSpielraum(sp);
        Spielemanager manager = sp.getManager();
        manager.setActPlayer(bot1);
        assertEquals(manager.getActPlayer(), bot1);
        //Bot hat Handkarten und noch nichts angebaut:
        assertEquals(5, manager.getActPlayer().getHandkarten().size());
        assertEquals(3, manager.getActPlayer().getFelder().size());
        assertNull(manager.getActPlayer().getFelder().get(0).getBohnenArt());
        assertNull(manager.getActPlayer().getFelder().get(1).getBohnenArt());
        assertNull(manager.getActPlayer().getFelder().get(2).getBohnenArt());
        //nach dem Zug sind die 3., 4. und 5. Karten aus dem Deck am Ende in der Hand des Bots:
        Karte stapelkarte2 = manager.getDeck().getKarten().get(2);
        Karte stapelkarte3 = manager.getDeck().getKarten().get(3);
        Karte stapelkarte4 = manager.getDeck().getKarten().get(4);

        //starteSpiel() ruft in diesem Fall nur starteZug() des Bots aus
        //teste also, ob Bot1 Zug durchführt
        manager.starteSpiel();
        assertEquals(manager.getActPlayer(), bot1);
        //Bot hat angebaut, also seinen Zug mindestens begonnen:
        //funktioniert nicht immer, je nach CPU-Auslastung
        //Thread.sleep(1500);
        //Alternative: warte, bis Bot Zug beendet
        while (manager.getActPlayer() == bot1) {
            Thread.sleep(10);
        }
        //der Bot hat Karten gezogen, also seinen Zug beendet:
        int handkartensize = bot1.getHandkarten().size();
        assertEquals(stapelkarte2, bot1.getHandkarten().get(handkartensize - 3));
        assertEquals(stapelkarte3, bot1.getHandkarten().get(handkartensize - 2));
        assertEquals(stapelkarte4, bot1.getHandkarten().get(handkartensize - 1));
    }

    /**
     * testet, dasselbe wie starteSpiel(), nur mit 5 Bots
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws InterruptedException  wenn die Ausführung unterbrochen wird
     */
    @Test
    public void starteSpiel2() throws MalformedURLException, RemoteException, InterruptedException {
        Vector<Akteur> player4 = new Vector<>();
        player4.addAll(player3);
        player4.add(bot2);
        player4.add(bot2);
        Spielraum_Impl sp = new Spielraum_Impl(player3, "Spielraum");
        bot1.setSpielraum(sp);
        Spielemanager manager = sp.getManager();
        manager.setActPlayer(bot1);
        assertEquals(manager.getActPlayer(), bot1);
        //Bot hat Handkarten und noch nichts angebaut:
        assertEquals(5, manager.getActPlayer().getHandkarten().size());
        assertEquals(3, manager.getActPlayer().getFelder().size());
        assertNull(manager.getActPlayer().getFelder().get(0).getBohnenArt());
        assertNull(manager.getActPlayer().getFelder().get(1).getBohnenArt());
        assertNull(manager.getActPlayer().getFelder().get(2).getBohnenArt());
        //nach dem Zug sind die 3., 4. und 5. Karten aus dem Deck am Ende in der Hand des Bots:
        Karte stapelkarte2 = manager.getDeck().getKarten().get(2);
        Karte stapelkarte3 = manager.getDeck().getKarten().get(3);
        Karte stapelkarte4 = manager.getDeck().getKarten().get(4);

        //starteSpiel() ruft in diesem Fall nur starteZug() des Bots aus
        //teste also, ob Bot1 Zug durchführt
        manager.starteSpiel();
        assertEquals(manager.getActPlayer(), bot1);
        //Bot hat angebaut, also seinen Zug mindestens begonnen:
        //funktioniert nicht immer, je nach CPU-Auslastung
        //Thread.sleep(1500);
        //Alternative: warte, bis Bot Zug beendet
        while (manager.getActPlayer() == bot1) {
            Thread.sleep(10);
        }
        //der Bot hat Karten gezogen, also seinen Zug beendet:
        int handkartensize = bot1.getHandkarten().size();
        assertEquals(stapelkarte2, bot1.getHandkarten().get(handkartensize - 3));
        assertEquals(stapelkarte3, bot1.getHandkarten().get(handkartensize - 2));
        assertEquals(stapelkarte4, bot1.getHandkarten().get(handkartensize - 1));
    }

    /**
     * requestStart, wenn noch nicht alle Controller ready sind:
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void requestStart() throws MalformedURLException, RemoteException {
        assertEquals(0, dummy2.getAnzahlControllerReady());
        dummy2.requestStart();
        assertEquals(1, dummy2.getAnzahlControllerReady());
    }

    /**
     * requestStart, alle Controller ready, Spiel ist ready:
     *
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws InterruptedException  wenn die Ausführung unterbrochen wurde
     */
    @Test
    public void requestStart2() throws MalformedURLException, RemoteException, InterruptedException {
        //SetUp für das Spiel.
        Spielraum_Impl sp = new Spielraum_Impl(player, "Spielraum");
        bot1.setSpielraum(sp);
        dummy2.spielSetUp();
        assertEquals(0, dummy2.getAnzahlControllerReady());
        dummy2.setActPlayer(bot1);
        //requestStart für 3 Spieler:
        dummy2.requestStart();
        dummy2.requestStart();
        dummy2.requestStart();
        assertEquals(3, dummy2.getAnzahlControllerReady());
        //Spiel ist gestartet:
        assertTrue(dummy2.isStarted());
    }

    /**
     * überprüft, ob isStarted den richtigen Wert zurückgibt:
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void isStarted() throws RemoteException, MalformedURLException {
        assertFalse(dummy2.isStarted());
        dummy2.spielSetUp();
        dummy2.starteSpiel();
        assertTrue(dummy2.isStarted());
        dummy2.spielBeenden();
        assertFalse(dummy2.isStarted());
    }

    /**
     * löscht nach jedem Tests die Test-Benutzer aus der DB
     *
     * @throws SQLException wenn bei Verbindung zur DB etwas schiefläuft
     */
    @After
    public void after() throws SQLException {
        String sql3 = "DELETE FROM benutzer WHERE nutzername = 'TestUser'";
        String sql4 = "DELETE FROM benutzer WHERE nutzername = 'TestUser2'";
        String sql5 = "DELETE FROM benutzer WHERE nutzername = 'TestUser3'";
        stat.executeUpdate(sql3);
        stat.executeUpdate(sql4);
        stat.executeUpdate(sql5);
    }
}