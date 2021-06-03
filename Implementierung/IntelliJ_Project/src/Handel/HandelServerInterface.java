package Handel;

import Spieleverwaltung.Akteur;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HandelServerInterface extends Remote {

    /**
     * Fügt den HandelClient der Liste hinzu
     *
     * @param client der handelt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void join(Haendler client) throws RemoteException;

    /**
     * Entfernt den HandelClient aus der Liste
     *
     * @param client der entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void leave(Haendler client) throws RemoteException;

    /**
     * Startet den Handel zwischen den entsprechenden Clients
     *
     * @param partner1 HandelsClient 1
     * @param partner2 HandelsClient 2
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    void starteHandel(Akteur partner1, Akteur partner2) throws IOException;

    /**
     * Returnt einen bestimmten HandelClient, entsprechend seines Namen
     *
     * @param spieler der als Handelclient registriert ist
     * @return den Client, der als Spieler eingegeben wurde
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    Haendler locateClient(Akteur spieler) throws RemoteException;
}
