package Observer.Finisher;

import java.rmi.RemoteException;

public interface FinisherServerInterface extends Finisher {

    /**
     * Joint dem FinisherServer
     *
     * @param client der hinzugefügt wird
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void join(Finisher client) throws RemoteException;

    /**
     * Verlässt den FinisherServer
     *
     * @param client der den Server verlässt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void leave(Finisher client) throws RemoteException;

}
