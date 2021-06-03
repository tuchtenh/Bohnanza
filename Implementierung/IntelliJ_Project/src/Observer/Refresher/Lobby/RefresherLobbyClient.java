package Observer.Refresher.Lobby;

import GUI.LobbyController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * RefreherClient für Lobby
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class RefresherLobbyClient extends UnicastRemoteObject implements RefresherLobby {

    LobbyController lc;
    String name;

    public RefresherLobbyClient(LobbyController lc) throws RemoteException {
        this.lc = lc;
        name = lc.aktiverBenutzer.getName();
    }

    /**
     * Refresht den LobbyController
     *
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void refresh() throws RemoteException {
        lc.refresh();
        lc.refreshNumber();
    }

    @Override
    public String clientName() {
        return name;
    }
}
