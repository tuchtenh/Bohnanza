package Observer.Refresher.Spielvorraum;

import java.rmi.RemoteException;

public interface RefresherSpielvorraumServerInterface extends RefresherSpielvorraum {

    /**
     * Fügt ein Client dem Server hinzu
     *
     * @param client der hinzugefügt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void join(RefresherSpielvorraum client) throws RemoteException;

    /**
     * Entfernt einen Client aus dem Server
     *
     * @param client der entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void leave(RefresherSpielvorraum client) throws RemoteException;

    /**
     * Entfernt einen den Client aus dem Server über den Namen des Clients
     *
     * @param name des Cients, der entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void leave(String name) throws RemoteException;
}
