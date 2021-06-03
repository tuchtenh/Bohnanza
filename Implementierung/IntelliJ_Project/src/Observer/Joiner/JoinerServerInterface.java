package Observer.Joiner;

import Raumverwaltung.Spielraum;

import java.rmi.RemoteException;

public interface JoinerServerInterface extends Joiner {

    /**
     * Fügt einem Client einem JoinServer hinzu
     *
     * @param client der hinzugefügt wird
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */

    void join(Joiner client) throws RemoteException;

    /**
     * Entfernt den Client aus dem JoinServer
     *
     * @param client der den Server verlässt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void leave(Joiner client) throws RemoteException;

    /**
     * Startet das Spiel bei den Clients
     *
     * @param spiel Spielraum, der gestartet wird
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void starteSpiel(Spielraum spiel) throws RemoteException;
}
