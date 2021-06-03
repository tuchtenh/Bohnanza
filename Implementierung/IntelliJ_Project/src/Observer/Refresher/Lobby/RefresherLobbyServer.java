package Observer.Refresher.Lobby;

import Exception.BereitsEingeloggtException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RefresherServer der Lobby
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class RefresherLobbyServer extends UnicastRemoteObject implements RefresherLobbyServerInterface {

    Map<String, RefresherLobby> clients = new ConcurrentHashMap<String, RefresherLobby>();

    public RefresherLobbyServer() throws RemoteException {
    }

    /**
     * Fügt ein Client dem Server hinzu
     *
     * @param client der hinzugefügt wird
     * @throws RemoteException            wenn bei der Verbindung zum Server etwas schiefläuft
     * @throws BereitsEingeloggtException wennn der Benutzer bereits eingeloggt ist
     */
    @Override
    public void join(RefresherLobby client) throws RemoteException, BereitsEingeloggtException {
        clients.put(client.clientName(), client);
    }

    /**
     * Entfernt den Client vom Server
     *
     * @param client der entfernt wird
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void leave(RefresherLobby client) throws RemoteException {
        clients.remove(client.clientName());
    }

    /**
     * Entfernt den Client vom Server, nach Eingabe des Namens
     *
     * @param name des Clients der entfernt werden soll
     */
    @Override
    public void leave(String name) {
        clients.remove(name);
    }

    /**
     * Aktualisiert die Lobby der registrierten Clients
     *
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refresh() throws RemoteException {
        for (Map.Entry<String, RefresherLobby> c : clients.entrySet()) {
            c.getValue().refresh();
        }
    }

    /**
     * Methode zum Debuggen
     *
     * @return null
     */
    public String clientName() {
        return null;
    }
}
