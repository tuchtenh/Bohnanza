package Tests;

import Bots.Bot_Leicht;
import Exception.NichtAlleKartenAngebautException;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielemanager_Impl;
import Spieleverwaltung.SpielerImpl;
import Spieleverwaltung.Spielobjekte.AblagestapelImpl;
import Spieleverwaltung.Spielobjekte.DeckImpl;
import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.Augenbohne;
import Spieleverwaltung.Spielobjekte.Karten.BlaueBohne;
import Spieleverwaltung.Spielobjekte.Karten.Brechbohne;
import User.BenutzerImpl;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class NichtAlleKarten_Test {
    private static Spielemanager_Impl dummy1;
    private static Vector<Akteur> spieler;
    private static Akteur spieler1;
    private static Akteur spieler2;
    private static Akteur spieler3;
    private static Akteur spieler4;
    private static BenutzerImpl user1;
    private static BenutzerImpl user2;
    private static BenutzerImpl user3;
    private static Karte bohne1;
    private static Karte bohne2;
    private static Karte bohne3;
    private static Vector<Karte> karten;

    /**
     * erstellt vor jedem Test neue Benutzer, neue Karten, einen neuen Spielemanager und
     * einen Vektor zum Vergleich
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Before
    public void before() throws RemoteException, MalformedURLException {
        user3 = new BenutzerImpl("alex", "h", 0, 5);
        user2 = new BenutzerImpl("ale", "h", 0, 5);
        user1 = new BenutzerImpl("al", "h", 0, 5);

        bohne1 = new Augenbohne();
        bohne2 = new BlaueBohne();
        bohne3 = new Brechbohne();
        Karte bohne = new Augenbohne();
        karten = new Vector<>();
        for (int i = 0; i < 20; i++) {
            karten.add(bohne);
        }
        spieler = new Vector<>();
        spieler3 = new SpielerImpl(user3);
        spieler2 = new SpielerImpl(user2);
        spieler1 = new SpielerImpl(user1);
        spieler4 = new Bot_Leicht(1);
        spieler.add(spieler1);
        spieler.add(spieler2);
        spieler.add(spieler3);
        dummy1 = new Spielemanager_Impl(spieler);
        dummy1.setAblage(new AblagestapelImpl());
        dummy1.setDeck(new DeckImpl(karten));
        dummy1.setActPlayer(null);

    }

    /**
     * testet, ob der richtige Name zurückgegeben wird
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Test
    public void getName() throws RemoteException, MalformedURLException {
        try {
            dummy1.spielSetUp();
            assertEquals(1, dummy1.getPhase());
            dummy1.getActPlayer().setHatAngebaut(1);
            dummy1.nextPhase();
            assertEquals(2, dummy1.getPhase());
            dummy1.nextPhase();
            assertEquals(3, dummy1.getPhase());
            dummy1.nextPhase();
            assertEquals(4, dummy1.getPhase());
        } catch (NichtAlleKartenAngebautException e) {
            assertEquals(dummy1.getActPlayer().getName(), e.getName());
        }
    }

}
