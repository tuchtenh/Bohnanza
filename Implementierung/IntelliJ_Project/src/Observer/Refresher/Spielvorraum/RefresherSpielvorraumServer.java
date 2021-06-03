package Observer.Refresher.Spielvorraum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Refresher Server des Spielvorraums
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class RefresherSpielvorraumServer extends UnicastRemoteObject implements RefresherSpielvorraumServerInterface {
    Map<String, RefresherSpielvorraum> clients = new ConcurrentHashMap<String, RefresherSpielvorraum>();

    public RefresherSpielvorraumServer() throws RemoteException {

    }

    /**
     * Fügt ein Client dem Server hinzu
     *
     * @param client der hinzugefügt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void join(RefresherSpielvorraum client) throws RemoteException {
        clients.put(client.getClientName(), client);
    }

    /**
     * Entfernt einen Client aus dem Server
     *
     * @param client der entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void leave(RefresherSpielvorraum client) throws RemoteException {
        clients.remove(client.getClientName());
        client.closeClient();
    }

    /**
     * Entfernt einen den Client aus dem Server über den Namen des Clients
     *
     * @param name des Cients, der entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void leave(String name) throws RemoteException {
        RefresherSpielvorraum client = clients.get(name);
        clients.remove(name);
        client.closeClient();
    }

    /**
     * Ruft die Refresher-Methode der Clients auf, um Bot-Liste zu aktualisieren
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refreshBots() throws RemoteException {
        for (Map.Entry<String, RefresherSpielvorraum> c : clients.entrySet()) {
            c.getValue().refreshBots();
        }
    }

    /**
     * Ruft die Refresher-Methode der Clients auf, um Spieler-Liste zu aktualisieren
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refreshSpieler() throws RemoteException {
        for (Map.Entry<String, RefresherSpielvorraum> c : clients.entrySet()) {
            c.getValue().refreshSpieler();
        }
    }

    /**
     * Methode zum Debuggen
     * todo
     *
     * @return null der Name des Clients
     */
    @Override
    public String getClientName() {
        return null;
    }

    /**
     * Methode zum Debuggen
     */
    @Override
    public void closeClient() {
        System.out.println("Server Klasse... wird nicht geschlossen!");
    }

}
