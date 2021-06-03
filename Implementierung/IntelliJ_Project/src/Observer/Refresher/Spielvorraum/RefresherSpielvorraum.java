package Observer.Refresher.Spielvorraum;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RefresherSpielvorraum extends Remote {

    /**
     * Ruft die Refresher-Methode der Clients auf, um Bot-Liste zu aktualisieren
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void refreshBots() throws RemoteException;

    /**
     * Ruft die Refresher-Methode der Clients auf, um Spieler-Liste zu aktualisieren
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void refreshSpieler() throws RemoteException;

    /**
     * Methode zum Debuggen
     *
     * @return null der Name des Clients
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    String getClientName() throws RemoteException;

    /**
     * Methode zum Debuggen
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void closeClient() throws RemoteException;

}

