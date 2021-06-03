package Observer.Finisher;

import Spieleverwaltung.Akteur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * FinisherServer - steuert das Beenden und Abbrechen eines Spiels
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class FinisherServer extends UnicastRemoteObject implements FinisherServerInterface {

    private Vector<Finisher> clients;

    public FinisherServer() throws RemoteException {
        clients = new Vector<>();
    }

    /**
     * Joint dem FinisherServer
     *
     * @param client der hinzugefügt wird
     */
    @Override
    public void join(Finisher client) {
        clients.add(client);
    }

    /**
     * Verlässt den FinisherServer
     *
     * @param client der den Server verlässt
     */
    @Override
    public void leave(Finisher client) {
        clients.remove(client);
    }

    /**
     * Leitet das Beenden des Spiels bei den Clients ein
     *
     * @param gewinner Spieler oder Bot, der gewonnen hat.
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void spielBeenden(Akteur gewinner) throws RemoteException {
        for (Finisher client : clients) {
            client.spielBeenden(gewinner);
        }
        clients = new Vector<>();
    }

    /**
     * Leitet das Abbrechen des Spiels bei den Clients ein
     *
     * @param leaver Spieler, der das Spiel verlässt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void spielAbbrechen(Akteur leaver) throws RemoteException {
        for (Finisher client : clients) {
            client.spielAbbrechen(leaver);
        }
        clients = new Vector<>();
    }
}
