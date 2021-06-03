package Raumverwaltung;

import User.Benutzer;
import jdk.internal.dynalink.beans.StaticClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class Lobby_ImplTest {
    private static Lobby_Impl lobby;
    private static Benutzer benutzer;
    //private static Vector<Spielvorraum_Impl> v;
    private static Spielvorraum_Impl svr1;
    private static Spielvorraum_Impl svr2;

    @BeforeClass
    public static void beforeclass(){
        svr1 = new Spielvorraum_Impl("TestVorraum1");
        svr2 = new Spielvorraum_Impl("TestVorraum2");
        lobby = new Lobby_Impl();
        benutzer = new Benutzer("Boi", "passwort", 0,0);
        //v = new Vector<>(4);
        //v.add(svr1);
    }

    // Abmelden von der Lobby
    @Test
    public void abmelden() {
        assertTrue(lobby.abmelden());
    }

    // Loescht den Benutzer aus der Datenbank
    @Test
    public void loescheSpieler() {
        assertTrue(lobby.loescheSpieler(benutzer));
    }

    //TODO
    //@Test
    //public void spielRaumErstellen() {
   //     lobby.spielRaumErstellen("TestRaum");
   //     assertEquals();lobby.getRaeume();
    //}

    //TODO
   // @Test(expected = KeinNameException.class)
    //public void spielRaumErstellen() {
    //    lobby.spielRaumErstellen("");
   // }

    //TODO
    @Test
    public void zeigeSpielraum() {
    }

    //TODO
    @Test
    public void getRaeume() {
        Vector<Spielvorraum_Impl> vv;
        vv = new Vector<>(4);
        vv.add(svr1);
        vv.add(svr2);
        lobby.spielRaumErstellen("TestVorraum2");
        assertEquals(vv, lobby.getRaeume());
    }
}