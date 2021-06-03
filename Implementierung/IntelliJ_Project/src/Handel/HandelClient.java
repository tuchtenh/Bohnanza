package Handel;

import GUI.HandelController;
import GUI.SpielraumController;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spieler;
import Spieleverwaltung.Spielobjekte.Karte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * HandelClient, ermöglicht dem Spieler mit anderen Spielern zu handeln
 *
 * @author Lukas Bayer
 * @version 1.0
 */

public class HandelClient extends UnicastRemoteObject implements Haendler {

    Spieler spieler;
    SpielraumController sc;
    HandelController hc;
    Vector<Karte> angebot;
    Haendler partner;

    public HandelClient(Spieler spieler) throws RemoteException {
        this.spieler = spieler;
        this.angebot = new Vector<>();
    }

    /**
     * setzt den HandelController
     *
     * @param hc - der HandelController
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void setHC(HandelController hc) throws RemoteException {
        this.hc = hc;
    }

    /**
     * setzt den SpielraumController
     *
     * @param sc - der SpielraumController
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void setSC(SpielraumController sc) throws RemoteException {
        this.sc = sc;
    }

    /**
     * gibt den Spieler des Clients zurück
     *
     * @return spieler - der Spieler
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public Akteur getSpieler() throws RemoteException {
        return spieler;
    }

    /**
     * gibt das Angebot an Karten, das der Client machen möchte, zurück
     *
     * @return angebot - das Angebot
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public Vector<Karte> getAngebot() throws RemoteException {
        return angebot;
    }

    /**
     * setzt das Angebot
     *
     * @param angebot - die Karten, aus denen das Angebot ist
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void setAngebot(Vector<Karte> angebot) throws RemoteException {
        this.angebot = angebot;
    }

    /**
     * Startet den Handel mit Partner
     *
     * @param partner Gegenspieler, mit dem gehandelt werden soll
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Override
    public void starteHandel(Haendler partner) throws IOException {
        this.partner = partner;
        sc.starteHandel(partner);
    }

    /**
     * Teilt dem Handelcontroller mit, das Angebot des Gegenspielers zu aktualiseren
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch ist
     */
    @Override
    public void receiveAngebot() throws RemoteException, MalformedURLException {
        hc.refreshAngebot();
    }

    /**
     * Teilt dem Handelcontroller mit, den Handel zu akzeptieren
     *
     * @return boolean, ob Handel angenommen wurde
     * @throws RemoteException      wenn bei Verbindung zum Server etwas schiefläuft
     * @throws ExecutionException   wenn bei der Ausführung ein Fehler auftritt
     * @throws InterruptedException wenn die Ausführung unterbrochen wird
     */
    @Override
    public boolean acceptHandel() throws RemoteException, ExecutionException, InterruptedException {
        return hc.handelRequest();
    }

    /**
     * Teilt dem Handelcontroller mit, das Fenster zu schließen
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws ExecutionException    wenn bei der Ausführung ein Fehler auftritt
     * @throws InterruptedException  wenn die Ausführung unterbrochen wird
     */
    @Override
    public void schließeFenster() throws RemoteException, MalformedURLException, ExecutionException, InterruptedException {
        hc.closeRequest();
    }

    /**
     * gibt den Handelspartner zurück
     *
     * @return partner - der Partner
     */
    @Override
    public Haendler getPartner() {
        return this.partner;
    }

    /**
     * gibt den HandelController zurück
     *
     * @return hc der HandelController
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public HandelController getHc() throws RemoteException {
        return this.hc;
    }

    /**
     * gibt den SpielraumController zurück
     *
     * @return sc - der SpielraumController
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public SpielraumController getSc() throws RemoteException {
        return this.sc;
    }
}
