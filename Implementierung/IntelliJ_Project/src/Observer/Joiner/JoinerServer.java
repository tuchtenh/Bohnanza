package Observer.Joiner;

import Raumverwaltung.Spielraum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * JoinerServer, der JoinClients verwaltet
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class JoinerServer extends UnicastRemoteObject implements JoinerServerInterface {

    Vector<Joiner> clients;

    public JoinerServer() throws RemoteException {
        clients = new Vector<Joiner>();
    }

    /**
     * Fügt einem Client einem JoinServer hinzu
     *
     * @param client der hinzugefügt wird
     */
    @Override
    public void join(Joiner client) {
        clients.add(client);

    }

    /**
     * Entfernt den Client aus dem JoinServer
     *
     * @param client der den Server verlässt
     */
    @Override
    public void leave(Joiner client) {
        clients.remove(client);

    }

    /**
     * Startet das Spiel bei den Clients
     *
     * @param spiel Spielraum, der gestartet wird
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void starteSpiel(Spielraum spiel) throws RemoteException {
        for (Joiner c : clients) {
            c.starteSpiel(spiel);
        }
    }
}
