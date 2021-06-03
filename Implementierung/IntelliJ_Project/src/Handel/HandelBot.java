package Handel;

import Bots.Bot;
import GUI.HandelController;
import GUI.SpielraumController;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielobjekte.Karte;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class HandelBot extends UnicastRemoteObject implements Haendler {

    Bot bot;
    Vector<Karte> angebot;
    Haendler partner;

    public HandelBot(Bot bot) throws RemoteException {
        this.bot = bot;
        this.angebot = new Vector<>();

    }

    @Override
    public void setHC(HandelController hc) throws RemoteException {
        //Tue nichts
        //Braucht ein Bot ja eigentlich nicht
    }

    @Override
    public void setSC(SpielraumController sc) throws RemoteException {
        //Tue nichts
        //Braucht ein Bot ja eigentlich nicht
    }


    @Override
    public Akteur getSpieler() throws RemoteException {
        return bot;
    }

    @Override
    public Vector<Karte> getAngebot() throws RemoteException {
        return angebot;
    }

    @Override
    public void setAngebot(Vector<Karte> angebot) throws RemoteException {
        this.angebot = angebot;
    }

    /**
     * startet einen Handel mit dem übergebenen Haendler
     *
     * @param partner der Handelspartner
     */
    @Override
    public void starteHandel(Haendler partner) {
        this.partner = partner;
    }

    /**
     * Teilt dem Handelcontroller mit, das Angebot des Gegenspielers zu aktualiseren
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch ist
     */
    @Override
    public void receiveAngebot() throws RemoteException, MalformedURLException {
        bot.receiveAngebot(partner);
    }

    /**
     * Teilt dem Handelcontroller mit, den Handel zu akzeptieren
     *
     * @return boolean, ob Handel angenommen wurde
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public boolean acceptHandel() throws RemoteException {
        //TODO: Was hält der Bot vom Handel?
        return bot.acceptHandeln(partner);
    }

    /**
     * Teilt dem Handelcontroller mit, das Fenster zu schließen
     * tut nichts, da Bot
     *
     * @throws RemoteException       - wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException - wenn die URL falsch ist
     */
    @Override
    public void schließeFenster() throws RemoteException, MalformedURLException {

    }

    @Override
    public Haendler getPartner() {
        return this.partner;
    }

    @Override
    public HandelController getHc() throws RemoteException {
        return null;
    }

    @Override
    public SpielraumController getSc() throws RemoteException {
        return null;
    }
}
