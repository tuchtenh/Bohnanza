package Observer.Refresher.Spielraum;

import GUI.SpielraumController;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Refresher Client für Spielraum
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class RefresherSpielraumClient extends UnicastRemoteObject implements RefresherSpielraum {

    SpielraumController sc;

    public RefresherSpielraumClient(SpielraumController sc) throws RemoteException {
        this.sc = sc;
    }

    /**
     * Aktualisiert den Stapel im SpielraumController
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refreshStapel() throws RemoteException {
        sc.refreshStapel();
    }

    /**
     * Aktualisiert die Phase im SpielraumController
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Override
    public void refreshPhase() throws RemoteException, MalformedURLException {
        sc.refreshPhase();
    }

    /**
     * Aktualisiert die Bohnenfelder im SpielraumController
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @Override
    public void refreshBohnenfelder() throws RemoteException, MalformedURLException {
        sc.refreshBohnenfelder();
    }

    /**
     * Aktualisiert die Taler im SpielraumController
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refreshTaler() throws RemoteException {
        sc.refreshTaler();
    }

    /**
     * Aktualisiert das TextField "Spieler am Zug" im SpielraumController
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refreshSpielerAmZug() throws RemoteException {
        sc.refreshSpielerAmZug();
    }
}
