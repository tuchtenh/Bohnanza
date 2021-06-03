package Observer.Joiner;

import Raumverwaltung.Spielraum;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Joiner extends Remote {

    /**
     * Startet das Spiel über den Spielraum
     *
     * @param spiel das Spiel, das gestartet werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void starteSpiel(Spielraum spiel) throws RemoteException;

}
