package Chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {

    /**
     * empfängt eine Nachricht n von ChatClientInterface c
     *
     * @param n das ChatClientInterface, von dem die Nachricht stammt
     * @param c die gesendete Nachricht
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void receiveMessage(String n, ChatClientInterface c) throws RemoteException;


}