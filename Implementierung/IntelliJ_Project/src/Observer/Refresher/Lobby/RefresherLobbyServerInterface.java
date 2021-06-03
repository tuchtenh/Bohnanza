package Observer.Refresher.Lobby;

import java.rmi.RemoteException;

public interface RefresherLobbyServerInterface extends RefresherLobby {

    /**
     * Fügt ein Client dem Server hinzu
     *
     * @param client der hinzugefügt wird
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     */
    void join(RefresherLobby client) throws RemoteException;

    /**
     * Entfernt den Client vom Server
     *
     * @param client der entfernt wird
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     */
    void leave(RefresherLobby client) throws RemoteException;

    /**
     * Entfernt den Client vom Server, nach Eingabe des Namens
     *
     * @param name des Clients der entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void leave(String name) throws RemoteException;

    /**
     * Methode zum Debuggen
     *
     * @return null
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    String clientName() throws RemoteException;
}
