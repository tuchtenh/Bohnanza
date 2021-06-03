package Tests.Spieldurchlauf;

import Bots.Bot_Leicht;
import Raumverwaltung.Spielraum_Impl;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielemanager_Impl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.assertTrue;

/**
 * Spieldurchlauf mit nur leichten Bots
 */
public class Durchlauf1 {

    private static Spielraum_Impl spielraum;
    private static Spielemanager_Impl spiel;
    private static Vector<Akteur> player;
    private static Bot_Leicht bot1;
    private static Bot_Leicht bot2;
    private static Bot_Leicht bot3;

    /**
     * Erstellt alle Bots und alles andere, was für einen Testdurchlauf notwendig ist
     *
     * @throws RemoteException       wenn bei der Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException, MalformedURLException {

        player = new Vector<>();
        bot1 = new Bot_Leicht(1);
        bot2 = new Bot_Leicht(2);
        bot3 = new Bot_Leicht(3);

        player.add(bot1);
        player.add(bot2);
        player.add(bot3);

        //Spielraum wird wegen den Refreshern benötigt:
        spielraum = new Spielraum_Impl(player, "Test");
        spiel = (Spielemanager_Impl) spielraum.getManager();

    }

    /**
     * startet einen Testdurchlauf des Spiels mit nur leichten Bots
     * alle Spielschritte werden dabei auf der Konsole ausgegeben
     * ergo lässt einen ein Spiel beobachten
     * ist auskommentiert, damit die Tests nicht 7+ Minuten dauern
     *
     * @throws RemoteException       wenn bei der Verbindung zum Server etwas schiefläuft
     * @throws InterruptedException  wenn der Thread unterbrochen wird
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void testdurchlauf() throws RemoteException, InterruptedException, MalformedURLException {
        /*
        spiel.starteSpiel();
        assertTrue(spiel.isStarted());
        while (!spiel.getFinished()) {
            Akteur ak = spiel.getActPlayer();
            System.out.println("ActPlay ist " + ak.getName());
            while (ak == spiel.getActPlayer()) {
                System.out.println("  aktuelle Phase ist " + spiel.getPhase());
                System.out.println("  Bohnenfelder sind:");
                System.out.println("    0. Bohnenfeld hat " + ak.getFelder().get(0).getAnzahlKarten() + " " + ak.getFelder().get(0).getBohnenArt());
                System.out.println("    1. Bohnenfeld hat " + ak.getFelder().get(1).getAnzahlKarten() + " " + ak.getFelder().get(1).getBohnenArt());
                System.out.println("    2. Bohnenfeld hat " + ak.getFelder().get(2).getAnzahlKarten() + " " + ak.getFelder().get(2).getBohnenArt());
                System.out.println("  ActPlayer hat " + ak.getTaler() + " Taler");
                System.out.println("  Karten im Deck sind " + spiel.getDeck().getAnzahlKarten());
                System.out.println("  Karten im Ablagestapel sind " + spiel.getAblage().getAnzahlKarten());
                System.out.println("  Anzahl Leerungen ist " + spiel.getAnzahlLeerungen());
                //für weniger Spam, aber weniger als ein "Zug" eines Bots dauert:
                Thread.sleep(750);
            }
        }
        System.out.println(bot1.getName() + " hat " + bot1.getTaler() + " Taler erzielt.");
        System.out.println(bot2.getName() + " hat " + bot2.getTaler() + " Taler erzielt.");
        System.out.println(bot3.getName() + " hat " + bot3.getTaler() + " Taler erzielt.");*/
    }
}
