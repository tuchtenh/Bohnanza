package Tests;

import Bots.Bot_Schwer;
import Handel.Haendler;
import Handel.HandelBot;
import Handel.HandelServer;
import Raumverwaltung.Spielraum_Impl;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielemanager;
import Spieleverwaltung.Spielemanager_Impl;
import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;
import Spieleverwaltung.Spielobjekte.Karten.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Vector;

import static org.junit.Assert.*;

public class Bot_SchwerTest {


    private Bot_Schwer bot;
    private Bot_Schwer bot2;
    private Bot_Schwer bot3;
    private Spielemanager spielemanager;
    private HandelServer hs;

    /**
     * erstellt vor jedem Tests neue Bots, einen Spielemanager und einen Vektor zum Vergleich
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Before
    public void before() throws RemoteException, MalformedURLException {
        bot = new Bot_Schwer(0);
        bot2 = new Bot_Schwer(2);
        bot3 = new Bot_Schwer(3);
        Vector<Akteur> akteurs = new Vector<>();
        akteurs.add(bot);
        akteurs.add(bot2);
        akteurs.add(bot3);
        spielemanager = new Spielemanager_Impl(akteurs);
        bot.setSpielemanager(spielemanager);
        bot2.setSpielemanager(spielemanager);
        bot3.setSpielemanager(spielemanager);

        hs = new HandelServer();

    }

    /**
     * testet, ob acceptHandeln false liefert, wenn kein Angebot gestellt wurde
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void acceptHandeln() throws RemoteException, MalformedURLException {
        spielemanager.spielSetUp();
        assertFalse(bot.acceptHandeln(new HandelBot(bot2)));
    }

    /**
     * testet, ob acceptHandeln false liefert, wenn kein passendes Angebot gestellt wurde
     *
     * @throws IOException wenn ein IO Fehler auftritt
     */
    @Test
    public void acceptHandeln2() throws IOException {
        spielemanager.spielSetUp();
        hs.join(bot.getHaendler());
        hs.join(bot2.getHaendler());
        bot.addKarte(new Saubohne());
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(2).anbauen(new Gartenbohne());
        Vector<Karte> angebot2 = new Vector<>();
        angebot2.add(new Sojabohne());
        bot2.getHaendler().setAngebot(angebot2);
        hs.starteHandel(bot, bot2);
        //Handelspartner gesetzt:
        assertEquals(bot.getHaendler().getPartner(), bot2.getHaendler());
        //Handel abgelehnt:
        assertFalse(bot.acceptHandeln(bot2.getHaendler()));
        //alle eigenen Karten für den Handel sind weg:
        assertEquals(0, bot.getHaendler().getAngebot().size());
        //gehandelte Karten sind geblieben
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob acceptHandeln true liefert, wenn ein passendes Angebot gestellt wurde
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test
    public void acceptHandeln3() throws IOException {
        spielemanager.spielSetUp();
        hs.join(bot.getHaendler());
        hs.join(bot2.getHaendler());
        bot.addKarte(new Saubohne());
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(2).anbauen(new Sojabohne());
        Vector<Karte> angebot2 = new Vector<>();
        angebot2.add(new Sojabohne());
        angebot2.add(new Sojabohne());
        bot2.getHaendler().setAngebot(angebot2);
        hs.starteHandel(bot, bot2);
        //Handelspartner gesetzt:
        assertEquals(bot.getHaendler().getPartner(), bot2.getHaendler());
        //Handel angenommen:
        assertTrue(bot.acceptHandeln(bot2.getHaendler()));
        //alle eigenen Karten für den Handel sind weg:
        assertEquals(0, bot.getHaendler().getAngebot().size());
        //gehandelte Karten sind gestiegen
        assertEquals(2, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob acceptHandeln false liefert, wenn man ihm Gartenbohnen anbietet
     * aber keine Gartenbohne auf dem Feld liegt
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test
    public void acceptHandeln4() throws IOException {
        spielemanager.spielSetUp();
        hs.join(bot.getHaendler());
        hs.join(bot2.getHaendler());
        bot.addKarte(new Saubohne());
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(2).anbauen(new Sojabohne());
        Vector<Karte> angebot2 = new Vector<>();
        angebot2.add(new Gartenbohne());
        bot2.getHaendler().setAngebot(angebot2);
        hs.starteHandel(bot, bot2);
        //Handelspartner gesetzt:
        assertEquals(bot.getHaendler().getPartner(), bot2.getHaendler());
        //Handel angenommen:
        assertTrue(bot.acceptHandeln(bot2.getHaendler()));
        //alle eigenen Karten für den Handel sind weg:
        assertEquals(0, bot.getHaendler().getAngebot().size());
        //gehandelte Karten sind gestiegen
        assertEquals(1, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob acceptHandeln true liefert, wenn er ein leeres Feld hat
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test
    public void acceptHandeln5() throws IOException {
        spielemanager.spielSetUp();
        hs.join(bot.getHaendler());
        hs.join(bot2.getHaendler());
        bot.addKarte(new Saubohne());
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        Vector<Karte> angebot2 = new Vector<>();
        angebot2.add(new Sojabohne());
        bot2.getHaendler().setAngebot(angebot2);
        hs.starteHandel(bot, bot2);
        //Handelspartner gesetzt:
        assertEquals(bot.getHaendler().getPartner(), bot2.getHaendler());
        //Handel angenommen:
        assertTrue(bot.acceptHandeln(bot2.getHaendler()));
        //alle eigenen Karten für den Handel sind weg:
        assertEquals(0, bot.getHaendler().getAngebot().size());
        //gehandelte Karten sind gestiegen
        assertEquals(1, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob ein Angebot empfangen werden kann, für einen schweren Bot
     * wirft einen StackOverflowError, da es "nicht gedacht" ist, dass
     * zwei Bots untereinander handeln.
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Test(expected = StackOverflowError.class)
    public void receiveAngebot() throws IOException {

        Haendler hbot2 = bot2.setHaendler();
        Haendler hbot = bot.setHaendler();
        hs.join(hbot2);
        hs.join(hbot);
        hs.starteHandel(bot2, bot);
        Vector<Karte> angebot = new Vector<>();
        angebot.add(new Gartenbohne());
        hbot2.setAngebot(angebot);
        bot2.receiveAngebot(hbot2);

    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * Karte liegt nicht auf dem Feld, alle Felder sind voll
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Test
    public void anbauen() throws RemoteException, MalformedURLException {
        spielemanager.setActPlayer(bot);
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(2).anbauen(new BlaueBohne());
        bot.addKarte(new Sojabohne());
        bot.anbauen();
        //alle Bohensorten:
        assertEquals("Sojabohne", bot.getFelder().get(1).getBohnenArt());
        assertEquals("Augenbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals("Blaue Bohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * Karte liegt nicht auf dem Feld, ein Feld ist leer
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Test
    public void anbauen_2() throws RemoteException, MalformedURLException {
        spielemanager.setActPlayer(bot);
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.addKarte(new Sojabohne());
        bot.anbauen();
        //alle Bohensorten:
        assertEquals("Augenbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals("Brechbohne", bot.getFelder().get(1).getBohnenArt());
        assertEquals("Sojabohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(2, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * Karte liegt nicht auf dem Feld, alle Felder sind leer
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Test
    public void anbauen_3() throws RemoteException, MalformedURLException {
        spielemanager.setActPlayer(bot);
        bot.addFelder(3);
        bot.addKarte(new Sojabohne());
        bot.anbauen();
        //nur auf dem ersten Feld wurde angebaut:
        assertEquals("Sojabohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals(null, bot.getFelder().get(1).getBohnenArt());
        assertEquals(null, bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(0, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(0, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * Karte liegt auf dem Feld
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Test
    public void anbauen_4() throws RemoteException, MalformedURLException {
        spielemanager.setActPlayer(bot);
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(2).anbauen(new Sojabohne());
        bot.addKarte(new Sojabohne());
        bot.anbauen();
        //alle Bohnensorten:
        assertEquals("Augenbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals("Brechbohne", bot.getFelder().get(1).getBohnenArt());
        assertEquals("Sojabohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(2, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(2, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * Karte liegt auf dem Feld, es wird nicht auf einem leeren Feld angebaut
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Test
    public void anbauen_5() throws RemoteException, MalformedURLException {
        spielemanager.setActPlayer(bot);
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(2).anbauen(new Sojabohne());
        bot.addKarte(new Sojabohne());
        bot.anbauen();
        //alle Bohnensorten:
        assertEquals("Augenbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals(null, bot.getFelder().get(1).getBohnenArt());
        assertEquals("Sojabohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(0, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(2, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * gehandelte Karte liegt nicht auf dem Feld, alle Felder sind voll
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void anbauen3() throws RemoteException {
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(2).anbauen(new BlaueBohne());
        bot.addGehandelteKarten(new Sojabohne());
        bot.anbauen3();
        //nur auf dem zweiten Feld wurde angebaut:
        assertEquals("Sojabohne", bot.getFelder().get(1).getBohnenArt());
        assertEquals("Augenbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals("Blaue Bohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * gehandelte Karte liegt nicht auf dem Feld, ein Feld ist leer
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void anbauen3_2() throws RemoteException {
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.addGehandelteKarten(new Sojabohne());
        bot.anbauen3();
        //nur auf dem zweiten Feld wurde angebaut:
        assertEquals("Augenbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals("Brechbohne", bot.getFelder().get(1).getBohnenArt());
        assertEquals("Sojabohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(2, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * gehandelte Karte liegt nicht auf dem Feld, alle Felder sind leer
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void anbauen3_3() throws RemoteException {
        bot.addFelder(3);
        bot.addGehandelteKarten(new Sojabohne());
        bot.anbauen3();
        //nur auf dem zweiten Feld wurde angebaut:
        assertEquals("Sojabohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals(null, bot.getFelder().get(1).getBohnenArt());
        assertEquals(null, bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(0, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(0, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * gehandelte Karte liegt auf dem Feld
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void anbauen3_4() throws RemoteException {
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(2).anbauen(new Sojabohne());
        bot.addGehandelteKarten(new Sojabohne());
        bot.anbauen3();
        //alle Bohnensorten:
        assertEquals("Augenbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals("Brechbohne", bot.getFelder().get(1).getBohnenArt());
        assertEquals("Sojabohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(2, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(2, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * gehandelte Karte liegt auf dem Feld, es wird nicht auf einem leeren Feld angebaut
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void anbauen3_5() throws RemoteException {
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(2).anbauen(new Sojabohne());
        bot.addGehandelteKarten(new Sojabohne());
        bot.anbauen3();
        //alle Bohnensorten:
        assertEquals("Augenbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals(null, bot.getFelder().get(1).getBohnenArt());
        assertEquals("Sojabohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(0, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(2, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * erste gehandelte Karte liegt auf dem Feld, alle anderen nicht, alle Felder voll
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void anbauen3_6() throws RemoteException {
        bot.addFelder(3);
        bot.getFelder().get(0).anbauen(new Augenbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(1).anbauen(new Brechbohne());
        bot.getFelder().get(2).anbauen(new Sojabohne());
        //wird auf Feld 0 angebaut:
        bot.addGehandelteKarten(new RoteBohne());
        //wird auf Feld 2 angebaut:
        bot.addGehandelteKarten(new Gartenbohne());
        //wird auf Feld 1 angebaut:
        bot.addGehandelteKarten(new Feuerbohne());
        //wird auf Feld 2 angebaut:
        bot.addGehandelteKarten(new Sojabohne());

        bot.anbauen3();
        //alle Bohnensorten:
        assertEquals("Rote Bohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals("Feuerbohne", bot.getFelder().get(1).getBohnenArt());
        assertEquals("Gartenbohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob anbauen funktioniert für das Szenario:
     * erste gehandelte Karte liegt auf dem Feld, alle anderen nicht, nur erstes Feld belegt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void anbauen3_7() throws RemoteException {
        bot.addFelder(3);
        bot.getFelder().get(1).anbauen(new Sojabohne());
        //wird auf Feld 1 angebaut:
        bot.addGehandelteKarten(new RoteBohne());
        //wird auf Feld 2 angebaut:
        bot.addGehandelteKarten(new Gartenbohne());
        //wird auf Feld 0 angebaut:
        bot.addGehandelteKarten(new Feuerbohne());
        //wird auf Feld 1 angebaut:
        bot.addGehandelteKarten(new Sojabohne());
        bot.anbauen3();
        //alle Bohnensorten:
        assertEquals("Feuerbohne", bot.getFelder().get(0).getBohnenArt());
        assertEquals("Rote Bohne", bot.getFelder().get(1).getBohnenArt());
        assertEquals("Gartenbohne", bot.getFelder().get(2).getBohnenArt());
        //auf allen Feldern stimmt die Bohnenzahl
        assertEquals(1, bot.getFelder().get(0).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(1).getAnzahlKarten());
        assertEquals(1, bot.getFelder().get(2).getAnzahlKarten());
        //keine gehandelten Karten mehr
        assertEquals(0, bot.getGehandelteKarten().size());
    }

    /**
     * testet, ob der Bot seinen Namen zurückgibt:
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getName() throws RemoteException {
        assertEquals("Bot_Schwer0", bot.getName());
        assertEquals("Bot_Schwer2", bot2.getName());
        assertEquals("Bot_Schwer3", bot3.getName());
    }

    /**
     * testet, ob sich der Name des Bots nicht anpassen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setName() throws RemoteException {
        assertEquals("Bot_Schwer0", bot.getName());
        bot.setName("Test");
        assertEquals("Bot_Schwer0", bot.getName());
    }

    /**
     * testet, ob die Felder eines Bots korrekt zurückgegeben werden
     *
     * @throws MalformedURLException wenn URL falsch
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getFelder() throws MalformedURLException, RemoteException {
        spielemanager.setActPlayer(bot);

        //keine Felder:
        assertEquals(0, bot.getFelder().size());

        //leere Felder:
        bot.addFelder(3);
        Vector<Bohnenfeld> leerefelder = bot.getFelder();
        assertEquals(3, leerefelder.size());
        assertEquals(null, leerefelder.get(0).getBohnenArt());
        assertEquals(null, leerefelder.get(1).getBohnenArt());
        assertEquals(null, leerefelder.get(2).getBohnenArt());
        assertEquals(0, leerefelder.get(0).getAnzahlKarten());
        assertEquals(0, leerefelder.get(1).getAnzahlKarten());
        assertEquals(0, leerefelder.get(2).getAnzahlKarten());

        //volle Felder
        bot.addKarte(new Augenbohne());
        bot.addKarte(new BlaueBohne());
        bot.addKarte(new BlaueBohne());
        bot.addKarte(new Sojabohne());
        bot.anbauen();
        bot.setHatAngebaut(0);
        bot.anbauen();
        bot.setHatAngebaut(0);
        bot.anbauen();
        bot.setHatAngebaut(0);
        bot.anbauen();
        assertEquals(3, bot.getFelder().size());
        Vector<Bohnenfeld> felder = bot.getFelder();
        assertEquals("Augenbohne", felder.get(0).getBohnenArt());
        assertEquals("Blaue Bohne", felder.get(1).getBohnenArt());
        assertEquals("Sojabohne", felder.get(2).getBohnenArt());
        assertEquals(1, felder.get(0).getAnzahlKarten());
        assertEquals(2, felder.get(1).getAnzahlKarten());
        assertEquals(1, felder.get(2).getAnzahlKarten());

    }

    /**
     * testet, ob die Handkarten des Bots korrekt zurückgegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHandkarten() throws RemoteException {
        //keine Handkarten:
        assertEquals(0, bot.getHandkarten().size());

        //Handkarten:
        bot.addKarte(new Augenbohne());
        bot.addKarte(new Brechbohne());
        bot.addKarte(new Brechbohne());
        Vector<Karte> handkarten = bot.getHandkarten();
        assertEquals(3, handkarten.size());
        assertEquals("Augenbohne", handkarten.get(0).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(1).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(2).getBohnenArt());
    }

    /**
     * testet, ob sich Karten zur Hand des Bots hinzufügen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addKarte() throws RemoteException {
        //keine Handkarten in der Hand:
        assertEquals(0, bot.getHandkarten().size());

        //Handkarten zur Hand hinzufügen:
        bot.addKarte(new Augenbohne());
        bot.addKarte(new Brechbohne());
        bot.addKarte(new Brechbohne());
        Vector<Karte> handkarten = bot.getHandkarten();
        assertEquals(3, handkarten.size());
        assertEquals("Augenbohne", handkarten.get(0).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(1).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(2).getBohnenArt());
    }

    /**
     * testet, ob sich eine Karte aus der Hand des Bots entfernen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeKarte() throws RemoteException {
        //keine Handkarten in der Hand:
        assertEquals(0, bot.getHandkarten().size());

        //Handkarten zur Hand hinzufügen:
        bot.addKarte(new Augenbohne());
        bot.addKarte(new Brechbohne());
        bot.addKarte(new Brechbohne());
        Vector<Karte> handkarten = bot.getHandkarten();
        assertEquals(3, handkarten.size());
        assertEquals("Augenbohne", handkarten.get(0).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(1).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(2).getBohnenArt());

        //Karte entfernen:
        bot.removeKarte(1);
        Vector<Karte> handkarten2 = bot.getHandkarten();
        assertEquals(2, handkarten2.size());
        assertEquals("Augenbohne", handkarten2.get(0).getBohnenArt());
        assertEquals("Brechbohne", handkarten2.get(1).getBohnenArt());
        //Karten mit ungültigem Index entfernen:
        bot.removeKarte(-1);
        bot.removeKarte(2323);
        Vector<Karte> handkarten3 = bot.getHandkarten();
        assertEquals(2, handkarten3.size());
        assertEquals("Augenbohne", handkarten3.get(0).getBohnenArt());
        assertEquals("Brechbohne", handkarten3.get(1).getBohnenArt());
    }

    /**
     * testet, ob sich mehrere Karten aus der Hand des Bots entfernen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeKarten() throws RemoteException {
        //keine Handkarten in der Hand:
        assertEquals(0, bot.getHandkarten().size());

        //Handkarten zur Hand hinzufügen:
        bot.addKarte(new Augenbohne());
        bot.addKarte(new Brechbohne());
        bot.addKarte(new Brechbohne());
        bot.addKarte(new Gartenbohne());
        bot.addKarte(new Augenbohne());
        bot.addKarte(new Brechbohne());
        Vector<Karte> handkarten = bot.getHandkarten();
        assertEquals(6, handkarten.size());
        assertEquals("Augenbohne", handkarten.get(0).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(1).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(2).getBohnenArt());
        assertEquals("Gartenbohne", handkarten.get(3).getBohnenArt());
        assertEquals("Augenbohne", handkarten.get(4).getBohnenArt());
        assertEquals("Brechbohne", handkarten.get(5).getBohnenArt());

        //Karten entfernen:
        Vector<Integer> indices = new Vector<>();
        indices.add(2);
        indices.add(4);
        indices.add(5);
        bot.removeKarten(indices);
        Vector<Karte> handkarten2 = bot.getHandkarten();
        assertEquals(3, handkarten2.size());
        assertEquals("Augenbohne", handkarten2.get(0).getBohnenArt());
        assertEquals("Brechbohne", handkarten2.get(1).getBohnenArt());
        assertEquals("Gartenbohne", handkarten.get(2).getBohnenArt());

        //Karten mit ungültigem Index entfernen:
        Vector<Integer> indices2 = new Vector<>();
        indices2.add(-2);
        indices2.add(42323);
        bot.removeKarten(indices2);
        Vector<Karte> handkarten3 = bot.getHandkarten();
        assertEquals(3, handkarten3.size());
        assertEquals("Augenbohne", handkarten3.get(0).getBohnenArt());
        assertEquals("Brechbohne", handkarten3.get(1).getBohnenArt());
        assertEquals("Gartenbohne", handkarten3.get(2).getBohnenArt());

    }

    /**
     * testet, ob isUser für den Bot false zurückgibt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void isUser() throws RemoteException {
        assertFalse(bot.isUser());
    }

    /**
     * testet, ob sich Bohnenfelder zum Bot hinzufügen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addFelder() throws RemoteException {
        assertEquals(0, bot.getFelder().size());
        bot.addFelder(3);
        assertEquals(3, bot.getFelder().size());
        bot.addFelder(2);
        assertEquals(5, bot.getFelder().size());
        int count = 0;
        for (int i = 0; i < bot.getFelder().size(); i++) {
            if (bot.getFelder().get(i) instanceof Bohnenfeld) {
                count++;
            }
        }
        assertEquals(5, count);
        bot.addFelder(-1);
        assertEquals(5, bot.getFelder().size());
    }

    /**
     * testet, ob sich Taler zum Bot hinzufügen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addTaler() throws RemoteException {
        assertEquals(0, bot.getTaler());
        //positive Anzahl Taler hinzugefügt:
        bot.addTaler(13);
        assertEquals(13, bot.getTaler());
        //negative Anzahl Taler:
    }

    /**
     * testet, ob die richtige Anzahl an Talern zurückgegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getTaler() throws RemoteException {
        assertEquals(0, bot.getTaler());
        //positive Anzahl Taler hinzugefügt:
        bot.addTaler(13);
        assertEquals(13, bot.getTaler());
    }

    /**
     * testet, ob die gehandelten Karten korrekt zurückgegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getGehandelteKarten() throws RemoteException {
        //keine gehandelten Karten:
        assertEquals(0, bot.getGehandelteKarten().size());

        //gehandelte Karten hinzufügen:
        Karte bohne = new Augenbohne();
        Karte bohne2 = new Brechbohne();
        Karte bohne3 = new Gartenbohne();
        bot.addGehandelteKarten(bohne);
        bot.addGehandelteKarten(bohne2);
        bot.addGehandelteKarten(bohne3);

        //Vergleich:
        Vector<Karte> karten = bot.getGehandelteKarten();
        assertEquals(3, karten.size());
        assertEquals(bohne, karten.get(0));
        assertEquals(bohne2, karten.get(1));
        assertEquals(bohne3, karten.get(2));
    }

    /**
     * testet, ob sich gehandelte Karten hinzufügen lassen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void addGehandelteKarten() throws RemoteException {
        //keine gehandelten Karten:
        assertEquals(0, bot.getGehandelteKarten().size());

        //gehandelte Karten hinzufügen:
        Karte bohne = new Augenbohne();
        Karte bohne2 = new Brechbohne();
        Karte bohne3 = new Gartenbohne();
        bot.addGehandelteKarten(bohne);
        bot.addGehandelteKarten(bohne2);
        bot.addGehandelteKarten(bohne3);

        //Vergleich:
        Vector<Karte> karten = bot.getGehandelteKarten();
        assertEquals(3, karten.size());
        assertEquals(bohne, karten.get(0));
        assertEquals(bohne2, karten.get(1));
        assertEquals(bohne3, karten.get(2));
    }

    /**
     * testet, ob sich eine gehandelte Karte entfernen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void removeGehandelteKarte() throws RemoteException {
        //derzeit keine gehandelten Karten:
        assertEquals(0, bot.getGehandelteKarten().size());

        //füge gehandelte Karten hinzu:
        Karte bohne = new Augenbohne();
        Karte bohne2 = new Brechbohne();
        Karte bohne3 = new Gartenbohne();
        bot.addGehandelteKarten(bohne);
        bot.addGehandelteKarten(bohne2);
        bot.addGehandelteKarten(bohne3);

        //Vergleiche:
        Vector<Karte> gehandelteKarten = bot.getGehandelteKarten();
        assertEquals(3, gehandelteKarten.size());
        assertEquals(bohne, gehandelteKarten.get(0));
        assertEquals(bohne2, gehandelteKarten.get(1));
        assertEquals(bohne3, gehandelteKarten.get(2));

        //lösche eine der Karten:
        bot.removeGehandelteKarte(1);

        //Vergleiche:
        Vector<Karte> gehandelteKarten2 = bot.getGehandelteKarten();
        assertEquals(2, gehandelteKarten2.size());
        assertEquals(bohne, gehandelteKarten2.get(0));
        assertEquals(bohne3, gehandelteKarten2.get(1));

        //zu hoher oder niedriger Index:
        bot.removeGehandelteKarte(-1);
        bot.removeGehandelteKarte(2323);
        bot.removeGehandelteKarte(2);

        //Vergleiche:
        Vector<Karte> gehandelteKarten3 = bot.getGehandelteKarten();
        assertEquals(2, gehandelteKarten3.size());
        assertEquals(bohne, gehandelteKarten3.get(0));
        assertEquals(bohne3, gehandelteKarten3.get(1));
    }

    /**
     * testet, ob sich hatAngebaut anpassen lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getHatAngebaut() throws RemoteException {
        assertEquals(0, bot.getHatAngebaut());
        bot.setHatAngebaut(2);
        assertEquals(2, bot.getHatAngebaut());
    }

    /**
     * testet, ob sich HarAngebaut verändern lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setHatAngebaut() throws RemoteException {
        assertEquals(0, bot.getHatAngebaut());
        bot.setHatAngebaut(2);
        assertEquals(2, bot.getHatAngebaut());
        //negatives hatAngebaut:
        bot.setHatAngebaut(-2);
        assertEquals(2, bot.getHatAngebaut());
    }

    /**
     * testet, ob sich der Spielemanager verändern lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setSpielemanager() throws RemoteException {
        Vector<Akteur> ak = new Vector<>();
        ak.add(bot2);
        ak.add(bot2);
        ak.add(bot2);
        assertEquals(spielemanager, bot.getSpielemanager());
        Spielemanager_Impl sp = new Spielemanager_Impl(ak);
        bot.setSpielemanager(sp);
        assertEquals(sp, bot.getSpielemanager());
        bot.setSpielemanager(null);
        assertEquals(null, bot.getSpielemanager());
    }

    /**
     * testet, ob sich der Händler verändern lässt
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void setHaendler() throws RemoteException {

        assertEquals(null, bot.getHaendler());
        Haendler h = new HandelBot(bot);
        bot.setHaendler();
        assertNotEquals(null, bot.getHaendler());
    }

    /**
     * testet, ob der Bot bei starteZug einen ganzen Zug durchläuft:
     *
     * @throws MalformedURLException wenn URL falsch
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws InterruptedException  wenn bei der Ausführung unterbrochen
     * @throws NullPointerException  weil nicht alle GUI Objekte da sind...
     */
    @Test
    public void starteZug() throws MalformedURLException, RemoteException, InterruptedException, NullPointerException {
        //diese Version des Tests fängt die Veränderungen während der Phase ab, funktioniert aber
        //je nach CPU-Auslastung/Geschwindigkeit nicht immer
        /*Vector<Akteur> ak = new Vector<Akteur>();
        ak.add(bot);
        Spielraum_Impl sprm = new Spielraum_Impl(ak,"test");
        Spielemanager manager = sprm.getManager();
        bot.setSpielraum(sprm);
        assertNull(bot.getFelder().elementAt(0).getBohnenArt());
        assertNull(bot.getFelder().elementAt(1).getBohnenArt());
        assertNull(bot.getFelder().elementAt(2).getBohnenArt());
        assertEquals(5, bot.getHandkarten().size());
        assertEquals(0, bot.getGehandelteKarten().size());
        bot.starteZug();
        Thread.sleep(1000);
        assertNotNull(bot.getFelder().get(0).getBohnenArt());
        assertNotEquals(5, bot.getHandkarten().size());
        assertEquals(1, manager.getPhase());
        Thread.sleep(2000);
        assertEquals(2, manager.getPhase());
        Thread.sleep(1000);
        assertEquals(2, bot.getGehandelteKarten().size());
        assertEquals(3, manager.getPhase());
        Thread.sleep(1000);
        assertNotEquals(2, bot.getGehandelteKarten().size());
        Thread.sleep(1000);
        assertEquals(4, manager.getPhase());*/

        //diese Version testet die Veränderungen nach einem erfolgreich durchgeführtem Zug:
        Vector<Akteur> ak = new Vector<Akteur>();
        ak.add(bot);
        ak.add(bot2);
        ak.add(bot2);
        Spielraum_Impl sprm = new Spielraum_Impl(ak, "test");
        Spielemanager manager = sprm.getManager();
        bot.setSpielraum(sprm);
        bot2.setSpielraum(sprm);
        manager.setActPlayer(bot);
        assertNull(bot.getFelder().elementAt(0).getBohnenArt());
        assertNull(bot.getFelder().elementAt(1).getBohnenArt());
        assertNull(bot.getFelder().elementAt(2).getBohnenArt());
        assertEquals(5, bot.getHandkarten().size());
        assertEquals(0, bot.getGehandelteKarten().size());
        //nach dem Zug sind die 3., 4. und 5. Karten aus dem Deck am Ende in der Hand des Bots:
        Karte stapelkarte2 = manager.getDeck().getKarten().get(2);
        Karte stapelkarte3 = manager.getDeck().getKarten().get(3);
        Karte stapelkarte4 = manager.getDeck().getKarten().get(4);
        //theoretisch könnte noch geprüft werden, ob die Karten aus Hand und Deck auch wirklich
        //auf dem Bohnenfeld liegen, doch das geht schief, falls der Bot in Phase 1 zwei Karten
        //anbaut, dann liegt die vierte Karte
        Karte stapelkarte0 = manager.getDeck().getKarten().get(0);
        Karte stapelkarte1 = manager.getDeck().getKarten().get(1);
        Karte handkarte0 = bot.getHandkarten().get(0);
        Karte handkarte1 = bot.getHandkarten().get(1);
        //Vektoren zum Vergleich:
        Vector<Karte> wenigerangebaut = new Vector<>();
        wenigerangebaut.add(handkarte0);
        wenigerangebaut.add(stapelkarte0);
        wenigerangebaut.add(stapelkarte1);
        Vector<Karte> mehrangebaut = new Vector<>();
        mehrangebaut.add(handkarte0);
        mehrangebaut.add(handkarte1);
        mehrangebaut.add(stapelkarte0);
        mehrangebaut.add(stapelkarte1);

        //startet den Zug
        bot.starteZug();

        //wartet, bis der Zug beendet wird:
        while (bot == manager.getActPlayer()) {
            Thread.sleep(10);
        }

        //die Karten wurden auf dem Feld angebaut:
        int count = 0;
        for (Bohnenfeld feld : bot.getFelder()) {
            count = count + feld.getAnzahlKarten();
        }
        assertTrue(3 <= count);
        //lustige ifs, die Fälle abdecken, falls in der ersten Phase 2 oder 1 Karte angebaut wurden:
        //nur eine Handkarte angebaut:
        if (handkarte1 == bot.getHandkarten().get(0)) {
            //alle 3 Karten liegen auf dem Feld:
            int dreiangebaut = 0;
            for (Karte k : wenigerangebaut) {
                for (Bohnenfeld feld : bot.getFelder()) {
                    if (k.getBohnenArt() == feld.getBohnenArt()) {
                        dreiangebaut++;
                    }
                }
            }
            assertEquals(3, dreiangebaut);
        } else {
            //zwei Karten angebaut, liegen alle auf dem Feld?
            if (count == 4) {
                int vierangebaut = 0;
                for (Karte k : mehrangebaut) {
                    for (Bohnenfeld feld : bot.getFelder()) {
                        if (k.getBohnenArt() == feld.getBohnenArt()) {
                            vierangebaut++;
                        }
                    }
                }
                assertEquals(4, vierangebaut);
            } else {
                //eine Karte liegt auf dem Ablagestapel:
                int dreiangebauteinaufablage = 0;
                //die drei auf dem Bohnenfeld überprüfen:
                for (Karte k : mehrangebaut) {
                    for (Bohnenfeld feld : bot.getFelder()) {
                        if (k.getBohnenArt() == feld.getBohnenArt()) {
                            dreiangebauteinaufablage++;
                        }
                    }
                }
                //Ablage überprüfen
                for (Karte k : mehrangebaut) {
                    if (manager.getAblage().getKarten().get(0) == k) {
                        dreiangebauteinaufablage++;
                    }
                }
                assertEquals(3, dreiangebauteinaufablage);
            }
        }


        //der Bot hat Karten gezogen:
        int handkartensize = bot.getHandkarten().size();
        assertEquals(stapelkarte2, bot.getHandkarten().get(handkartensize - 3));
        assertEquals(stapelkarte3, bot.getHandkarten().get(handkartensize - 2));
        assertEquals(stapelkarte4, bot.getHandkarten().get(handkartensize - 1));
        //bot2 ist actPlayer:
        assertEquals(bot2, manager.getActPlayer());

    }

    /**
     * testet, ob sich der Spielraum des Bots verändern lässt
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch
     */
    @Test
    public void setSpielraum() throws RemoteException, MalformedURLException {
        assertEquals(null, bot.getSpielraum());
        Vector<Akteur> ak = new Vector<Akteur>();
        ak.add(bot2);
        ak.add(bot2);
        ak.add(bot2);
        Spielraum_Impl sprm = new Spielraum_Impl(ak, "test");
        bot.setSpielraum(sprm);
        assertEquals(sprm, bot.getSpielraum());
    }

    /**
     * prüft, ob checkErnte ein Feld aberntet, dass den maximalen Talerwert erzielt
     * bzw. ein Feld nicht aberntet, falls es noch nicht den maximalen Talerwert erzielt
     *
     * @throws RemoteException       wenn bei der Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Test
    public void checkErnte() throws RemoteException, MalformedURLException {

        spielemanager.spielSetUp();
        Bohnenfeld maxFeld = bot.getFelder().get(0);
        Bohnenfeld nichtmaxFeld = bot.getFelder().get(1);
        Karte bohne = new Gartenbohne();
        //baut gerade so viele Bohnen an, dass der maximale Talerwert erreicht wird:
        maxFeld.anbauen(bohne);
        maxFeld.anbauen(bohne);
        maxFeld.anbauen(bohne);
        //baut eine Bohne weniger an:
        nichtmaxFeld.anbauen(bohne);
        nichtmaxFeld.anbauen(bohne);
        //überprüft, ob die Felder geerntet werden können und baut sie unter Umständen auch ab
        bot.checkErnte(0);
        bot.checkErnte(1);
        assertNull(maxFeld.getBohnenArt());
        assertEquals("Gartenbohne", nichtmaxFeld.getBohnenArt());

    }

}