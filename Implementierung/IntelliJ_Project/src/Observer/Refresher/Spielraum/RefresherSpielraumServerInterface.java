package Observer.Refresher.Spielraum;

import java.rmi.RemoteException;

public interface RefresherSpielraumServerInterface extends RefresherSpielraum {

    /**
     * Fügt ein Client dem Server hinzu
     *
     * @param client der hinzugefügt wird
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void join(RefresherSpielraum client) throws RemoteException;

    /**
     * Entfernt einen Bot aus dem Server
     *
     * @param client der entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void leave(RefresherSpielraum client) throws RemoteException;

}
