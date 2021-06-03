package Tests.Spieldurchlauf;

import Bots.Bot_Leicht;
import Bots.Bot_Schwer;
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
 * Spieldurchlauf mit leichten und schweren Bots
 */
public class Durchlauf3 {

    private static Spielraum_Impl spielraum;
    private static Spielemanager_Impl spiel;
    private static Vector<Akteur> player;
    private static Bot_Schwer bot1;
    private static Bot_Schwer bot2;
    private static Bot_Schwer bot3;
    private static Bot_Leicht bot4;
    private static Bot_Leicht bot5;

    /**
     * Erstellt alle Bots und alles andere, was für einen Testdurchlauf notwendig ist
     *
     * @throws RemoteException       wenn bei der Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @BeforeClass
    public static void beforeClass() throws RemoteException, MalformedURLException {

        player = new Vector<>();
        bot1 = new Bot_Schwer(1);
        bot2 = new Bot_Schwer(2);
        bot3 = new Bot_Schwer(3);
        bot4 = new Bot_Leicht(4);
        bot5 = new Bot_Leicht(5);

        player.add(bot1);
        player.add(bot2);
        player.add(bot3);
        player.add(bot4);
        player.add(bot5);

        //Spielraum wird wegen den Refreshern benötigt:
        spielraum = new Spielraum_Impl(player, "Test");
        spiel = (Spielemanager_Impl) spielraum.getManager();

    }

    /**
     * startet einen Testdurchlauf des Spiels mit leichten und schweren Bots
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
        System.out.println(bot3.getName() + " hat " + bot3.getTaler() + " Taler erzielt.");
        System.out.println(bot4.getName() + " hat " + bot4.getTaler() + " Taler erzielt.");
        System.out.println(bot5.getName() + " hat " + bot5.getTaler() + " Taler erzielt.");*/
    }
}
