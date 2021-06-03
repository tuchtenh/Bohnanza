package Observer.Refresher.Spielraum;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RefresherSpielraum extends Remote {

    /**
     * Ruft die Refresher-Methode der Clients auf, um Stapel zu aktualisieren
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    void refreshStapel() throws RemoteException, MalformedURLException;

    /**
     * Ruft die Refresher-Methode der Clients auf, um nächste Phase einzuleiten
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    void refreshPhase() throws RemoteException, MalformedURLException;

    /**
     * Ruft die Refresher-Methode der Clients auf, um Bohnenfelder zu aktualisieren
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    void refreshBohnenfelder() throws RemoteException, MalformedURLException;

    /**
     * Ruft die Refresher-Methode der Clients auf, um Taler zu aktualisieren
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void refreshTaler() throws RemoteException;

    /**
     * Ruft die Refresher-Methode der Clients auf, um Spieler am Zug zu aktualisieren
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void refreshSpielerAmZug() throws RemoteException;

}
