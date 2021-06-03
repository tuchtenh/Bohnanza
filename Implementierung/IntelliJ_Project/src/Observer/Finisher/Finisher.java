package Observer.Finisher;

import Spieleverwaltung.Akteur;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Finisher extends Remote {

    /**
     * Ruft die Methode spielBeenden im Spielraumcontroller auf
     *
     * @param gewinner Spieler oder Bot
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void spielBeenden(Akteur gewinner) throws RemoteException;

    /**
     * Ruft die Methode spielAbbrechen im Spielraumcontroller auf
     *
     * @param leaver Spieler der den Raum verlässt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void spielAbbrechen(Akteur leaver) throws RemoteException;

}
