package Chat;


import java.rmi.RemoteException;
import java.util.Vector;

public interface ChatServerInterface extends Chat {

    /**
     * Fuegt den Benutzer in einem Chatraum ein
     *
     * @param c Chatraum den der Benutzer eintraeten moechte
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void joinChat(ChatClientInterface c) throws RemoteException;

    /**
     * Nimmt den Benutzer aus einem Chatraum raus
     *
     * @param c Chatraum aus dem der Benutzer geloescht werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void verlasseChat(ChatClientInterface c) throws RemoteException;

    /**
     * Entfernt den Benutzer aus dem Chat
     *
     * @param name des Benutzers
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void verlasseChat(String name) throws RemoteException;

    /**
     * Returnt die Namen der Benutzer, die im Chat registriert sind
     *
     * @return Namne der Benutzer im Chat
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    Vector<String> getNamen() throws RemoteException;

}