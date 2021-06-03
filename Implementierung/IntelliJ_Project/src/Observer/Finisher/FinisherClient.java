package Observer.Finisher;

import GUI.SpielraumController;
import Spieleverwaltung.Akteur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Finisher Client - beendet das Spiel über den SpielraumController
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class FinisherClient extends UnicastRemoteObject implements Finisher {

    private SpielraumController sc;

    public FinisherClient(SpielraumController sc) throws RemoteException {
        this.sc = sc;
    }


    /**
     * Ruft die Methode spielBeenden im Spielraumcontroller auf
     *
     * @param gewinner Spieler oder Bot
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void spielBeenden(Akteur gewinner) throws RemoteException {
        sc.spielBeenden(gewinner);
    }

    /**
     * Ruft die Methode spielAbbrechen im Spielraumcontroller auf
     *
     * @param leaver Spieler der den Raum verlässt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void spielAbbrechen(Akteur leaver) throws RemoteException {
        sc.spielAbbrechen(leaver);
    }
}
