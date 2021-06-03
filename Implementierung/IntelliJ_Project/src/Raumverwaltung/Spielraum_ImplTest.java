package Raumverwaltung;

import Spieleverwaltung.Spieler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class Spielraum_ImplTest {
    private static Spielraum_Impl a;
    private static Spieler s;

    @BeforeClass
    public static void BeforeClass(){
        a = new Spielraum_Impl();
    }
    @Before
    public void before() {
        a.setName("TestRaum");
        a.setId(5);
    }

    @Test
    public void setName() {
        a.setName("TestErfolg");
        assertEquals("TestErfolg", a.getName());
    }

    @Test
    public void setId() {
        a.setId(1);
        assertEquals(1, a.getId());
    }
    //TODO
    @Test
    public void getSpieler() {

    }
    //TODO
    @Test
    public void getBots() {
    }

    @Test
    public void getId() {
        assertEquals(5, a.getId());
    }

    @Test
    public void getName() {
        assertEquals("TestRaum", a.getName());
    }

    @Test
    public void loescheSpiel() {
        a.loescheSpiel();
        assertNull(a);
    }
}