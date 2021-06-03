package Observer.Refresher.Spielraum;


import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Refresher Server für Spielraum
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class RefresherSpielraumServer extends UnicastRemoteObject implements RefresherSpielraumServerInterface {

    Vector<RefresherSpielraum> clients;

    public RefresherSpielraumServer() throws RemoteException {
        clients = new Vector<>();
    }

    /**
     * Fügt ein Client dem Server hinzu
     *
     * @param client der hinzugefügt wird
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void join(RefresherSpielraum client) throws RemoteException {
        clients.add(client);
    }

    /**
     * Entfernt einen Bot aus dem Server
     *
     * @param client der entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void leave(RefresherSpielraum client) throws RemoteException {
        clients.remove(client);
    }

    /**
     * Ruft die Refresher-Methode der Clients auf, um Stapel zu aktualisieren
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Override
    public void refreshStapel() throws RemoteException, MalformedURLException {
        for (RefresherSpielraum c : clients) {
            c.refreshStapel();
        }
    }

    /**
     * Ruft die Refresher-Methode der Clients auf, um nächste Phase einzuleiten
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Override
    public void refreshPhase() throws RemoteException, MalformedURLException {
        for (RefresherSpielraum c : clients) {
            c.refreshPhase();
        }
    }

    /**
     * Ruft die Refresher-Methode der Clients auf, um Bohnenfelder zu aktualisieren
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Override
    public void refreshBohnenfelder() throws RemoteException, MalformedURLException {
        for (RefresherSpielraum c : clients) {
            c.refreshBohnenfelder();
        }
    }

    /**
     * Ruft die Refresher-Methode der Clients auf, um Taler zu aktualisieren
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refreshTaler() throws RemoteException {
        for (RefresherSpielraum c : clients) {
            c.refreshTaler();
        }
    }

    /**
     * Ruft die Refresher-Methode der Clients auf, um Spieler am Zug zu aktualisieren
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refreshSpielerAmZug() throws RemoteException {
        for (RefresherSpielraum c : clients) {
            c.refreshSpielerAmZug();
        }
    }
}
