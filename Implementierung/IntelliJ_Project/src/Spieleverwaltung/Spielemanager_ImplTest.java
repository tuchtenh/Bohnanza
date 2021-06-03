package Spieleverwaltung;

import User.Benutzer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Spielemanager_ImplTest {

    private static Spielemanager_Impl manager;
    private static Spieler sp;

    @BeforeClass
    public static void beforeClass() {
        manager = new Spielemanager_Impl();
        sp = new Spieler(new Benutzer("benutzer","pw",0,0));
    }

    @Test
    public void getActPlayer() {
        manager.setActPlayer(sp);
        assertEquals(sp, manager.getActPlayer());
    }

    @Test
    public void setActPlayer() {
        manager.setActPlayer(sp);
        assertEquals(sp, manager.getActPlayer());
    }
//TODO:
    @Test
    public void spielBeenden() {

    }

    @Test
    public void spielStarten() {
    }

    @Test
    public void spielVerlassen() {
    }

    @Test
    public void spielMain() {
    }
}