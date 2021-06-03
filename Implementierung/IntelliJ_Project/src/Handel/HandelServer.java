package Handel;

import Exception.KeinHandelspartnerException;
import Spieleverwaltung.Akteur;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Verwaltet den Handel zwischen zwei Handelclients
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class HandelServer extends UnicastRemoteObject implements HandelServerInterface {

    Vector<Haendler> clients;

    Haendler partner1;
    Haendler partner2;

    public HandelServer() throws RemoteException {
        clients = new Vector<Haendler>();
    }

    /**
     * Fügt den Handelclient der Liste hinzu
     *
     * @param client der handelt
     */
    @Override
    public void join(Haendler client) {
        clients.add(client);
    }

    /**
     * Entfernt den HandelClient aus der Liste
     *
     * @param client der entfernt werden soll
     */
    @Override
    public void leave(Haendler client) {
        clients.remove(client);
    }

    /**
     * Startet den Handel zwischen den entsprechenden Clients
     *
     * @param partner1 HandelsClient 1
     * @param partner2 HandelsClient 2
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @Override
    public void starteHandel(Akteur partner1, Akteur partner2) throws IOException {

        Haendler haendler1 = locateClient(partner1);
        Haendler haendler2 = locateClient(partner2);

        if (haendler1 == null || haendler2 == null) {
            throw new KeinHandelspartnerException();
        } else {
            haendler1.starteHandel(haendler2);
            haendler2.starteHandel(haendler1);

            this.partner1 = haendler1;
            this.partner2 = haendler2;
        }
    }

    /**
     * Returnt einen bestimmten HandelClient, entsprechend seines Namen
     *
     * @param spieler der als Handelclient registriert ist
     * @return den Client, der als Spieler eingegeben wurde
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public Haendler locateClient(Akteur spieler) throws RemoteException {

        //Nach Konevntion hat in jedem Spiel jeder Spieler einen eindeutigen Namen
        for (Haendler client : clients) {

            if (client.getSpieler().getName().equals(spieler.getName())) {
                return client;
            }
        }
        return null;
    }

    /**
     * gibt die HandelClients des Servers zurück
     *
     * @return die HandelClients des Servers
     */
    public Vector<Haendler> getClients() {
        return clients;
    }

}
