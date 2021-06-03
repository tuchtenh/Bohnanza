package Observer.Refresher.Spielvorraum;

import GUI.SpielvorraumController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Refresher Client für den Spielvorraum
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class RefresherSpielvorraumClient extends UnicastRemoteObject implements RefresherSpielvorraum {

    SpielvorraumController svc;
    String name;

    public RefresherSpielvorraumClient(SpielvorraumController svc, String name) throws RemoteException {
        this.svc = svc;
        this.name = name;
    }

    /**
     * Refreshermethode für die Liste der Bots
     */
    @Override
    public void refreshBots() {
        svc.refreshBots();
    }

    /**
     * Refrehsermethode für die Liste der Spieler
     *
     * @throws RemoteException - wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refreshSpieler() throws RemoteException {
        svc.refreshSpieler();
    }


    @Override
    public String getClientName() throws RemoteException {
        return name;
    }

    /**
     * Schließt die aktuelle Stage des Clients
     */
    @Override
    public void closeClient() {
        svc.closeStage();
    }

}
