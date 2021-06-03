package Chat;

import User.Benutzer;

import java.rmi.RemoteException;

public interface ChatClientInterface extends Chat {

    /**
     * gibt den zum ChatClient gehörigen Benutzer zurück
     *
     * @return benutzer der zum ChatClient gehörige Benutzer
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    Benutzer getBenutzer() throws RemoteException;

}