package Observer.Refresher.Lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RefresherLobby extends Remote {

    /**
     * Refresht den LobbyController
     *
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     */
    void refresh() throws RemoteException;

    String clientName() throws RemoteException;

}
