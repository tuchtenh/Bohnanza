package Raumverwaltung;

import Bots.Bot;
import Bots.Bot_Leicht;
import Bots.Bot_Schwer;
import Spieleverwaltung.Spieler;
import Spieleverwaltung.SpielerImpl;
import User.BenutzerImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Exception.SpielException;

import java.rmi.RemoteException;
import java.util.Vector;
import static org.junit.Assert.*;


public class Spielvorraum_ImplTest {

    private static Spielvorraum_Impl svr;
    private static Spielvorraum_Impl svr1;
    private static Spielvorraum_Impl svr2;
    private static Spielvorraum_Impl svr3;
    private static Spielvorraum_Impl svr4;
    private static Spielvorraum_Impl svr5;
    private static Spielvorraum_Impl svrstart1;
    private static Spielvorraum_Impl svrstart3;
    private static BenutzerImpl be1;
    private static SpielerImpl sp1;
    private static Bot_Leicht bot1;
    private static Bot_Schwer bot2;
    private static Vector<Spieler> vectorspieler;
    private static Vector<Bot> vboteasy;
    private static Vector<Bot> vbothard;
    private static Vector<Bot> vbotempty;
    private static Vector<Bot> vbotall;


    @BeforeClass
    public static void beforeclass() throws RemoteException {

        svr = new Spielvorraum_Impl("TestVorraum");
        svr1 = new Spielvorraum_Impl("TestVorraum1");
        svr2 = new Spielvorraum_Impl("TestVorraum2");
        svr3 = new Spielvorraum_Impl("TestVorraum3");
        svr4 = new Spielvorraum_Impl("TestVorraum4");
        svr5 = new Spielvorraum_Impl("TestVorraum5");
        svrstart1 = new Spielvorraum_Impl("TestVorraum5");
        svrstart3 = new Spielvorraum_Impl("TestVorraum5");
        be1 = new BenutzerImpl("Tim", "password", 0,0);
        bot1 = new Bot_Leicht();
        bot2 = new Bot_Schwer();
        vectorspieler = new Vector<Spieler>();
        sp1 = new SpielerImpl(be1);
        vectorspieler.add(sp1);
        vboteasy = new Vector<Bot>();
        vboteasy.add(bot1);
        vbothard = new Vector<Bot>();
        vbothard.add(bot2);
        vbotall = new Vector<Bot>();
        vbotall.add(bot1);
        vbotall.add(bot2);
        vbotempty = new Vector<Bot>();

    }



    //Tested ob der name zuruck gegeben wird.
    @Test
    public void getName(){
        assertEquals("TestVorraum1", svr1.getName());
    }

    //Tested ob der Spieler zum Spielvorraum hinzugefuegt wurde
    @Test
    public void addSpieler() {
        svr1.addSpieler(sp1);
        assertEquals(vectorspieler, svr1.getSpieler());
    }

    // Tested ob der Name verraendert werden kann
    @Test
    public void setName(){
        svr2.setName("Erfolg!");
        assertEquals("Erfolg!", svr2.getName());
    }

    //Remove Bot leicht
    @Test
    public void removeBot1() {
        svr1.addBot(bot1);
        svr1.removeBot(bot1);
        assertEquals(vbotempty, svr3.getBots());
    }

    //Remove Bot schwer
    @Test
    public void removeBot2() {
        svr4.addBot(bot2);
        svr4.removeBot(bot2);
        assertEquals(vbotempty, svr4.getBots());
    }

    //starteSpiel() mit weniger als 3 Spieler
    @Test(expected = SpielException.class)
    public void starteSpiel1() {
        svrstart1.addSpieler(sp1);
        svrstart1.addBot(bot2);
        svrstart1.starteSpiel();
    }

    //TODO starteSpiel() mit drei Spieler
    @Test
    public void starteSpiel3() {
        svrstart3.addSpieler(sp1);
        svrstart3.addBot(bot1);
        svrstart3.addBot(bot2);
        // Spielraum auf equals testen mit
        //assertTrue(svrstart3.starteSpiel());
    }


    // Fuegt einen leichten bot
    @Test
    public void addBot1() {
        svr2.addBot(bot1);
        assertEquals(vboteasy, svr2.getBots());
    }

    // Fuegt einen schweren bot
    @Test
    public void addBot2() {
        svr5.addBot(bot2);
        assertEquals(vbothard, svr5.getBots());
    }

    //Zeigt die Spieler in dem Spielvorraum
    @Test
    public void getSpieler(){
        svr5.addSpieler(sp1);
        assertEquals(vectorspieler, svr5.getSpieler());

    }

    //Zeigt die Bots in dem Spielvorraum
    @Test
    public void getBots(){
        svr3.addBot(bot1);
        svr1.addBot(bot2);
        assertEquals(vbotall, svr3.getBots());

    }

}