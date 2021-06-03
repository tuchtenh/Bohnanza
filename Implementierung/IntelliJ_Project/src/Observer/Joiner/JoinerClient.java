package Observer.Joiner;

import GUI.SpielvorraumController;
import Raumverwaltung.Spielraum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * JoinerClient - zum Hinzufügen der Spieler
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class JoinerClient extends UnicastRemoteObject implements Joiner {
    private SpielvorraumController sc;

    public JoinerClient(SpielvorraumController sc) throws RemoteException {
        this.sc = sc;

    }

    /**
     * Startet das Spiel über den Spielraum
     *
     * @param spiel das Spiel, das gestartet werden soll
     */
    @Override
    public void starteSpiel(Spielraum spiel) {
        sc.spielstart(spiel);
    }
}
