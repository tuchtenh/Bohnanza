package Raumverwaltung;

import Bots.Bot_Leicht;
import Bots.Bot_Schwer;
import Spieleverwaltung.Spieler;
import User.Benutzer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class Spielvorraum_ImplTest {

    private static Spielvorraum_Impl svr;
    private static Benutzer be1;
    private static Benutzer be2;
    private static Spieler sp1;
    private static Spieler sp2;

    private static Bot_Leicht bot1;
    private static Bot_Schwer bot2;


    @BeforeClass
    public static void beforeclass(){
        svr = new Spielvorraum_Impl("TestVorraum");
        be1 = new Benutzer("Tim1", "password", 0,0);
        be2 = new Benutzer("Tim2", "password", 0,0);
        bot1 = new Bot_Leicht();
        bot2 = new Bot_Schwer();
        sp1 = new Spieler(be1);
        sp2 = new Spieler(be2);

    }

    //TODO assertequals ob der Spiler hinzugefuegt wurde
    @Test
    public void addSpieler() {
        svr.addSpieler(sp1);
    }
    //Remove Bot leicht
    //TODO asserEquals ob der Bot wirklich geloesch wurde
    @Test
    public void removeBot1() {
        svr.addBot(bot1);
        svr.removeBot(bot1);
    }
    //Remove Bot schwer
    //TODO asserEquals ob der Bot wirklich geloesch wurde
    @Test
    public void removeBot2() {
        svr.addBot(bot2);
        svr.removeBot(bot2);
    }
    //TODO
    @Test
    public void starteSpiel() {
    }

    // Fuegt einan leichten bot
    //TODO assertequals ob der bot hinzugefuegt wurde
    @Test
    public void addBot1() {
        svr.addBot(bot1);
    }

    // Fuegt einan schweren bot
    //TODO assertequals ob der bot hinzugefuegt wurde
    @Test
    public void addBot2() {
        svr.addBot(bot2);
    }

}