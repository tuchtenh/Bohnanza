package Chat;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chatserver, der Nachrichten verwaltet und an alle Clients verteilt
 *
 * @author Lukas Bayer
 * @version 1.0
 */

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {
    Map<String, ChatClientInterface> clients = new ConcurrentHashMap<String, ChatClientInterface>();

    /**
     * Leerer Konstruktor für Interface
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public ChatServer() throws RemoteException {
    }

    /**
     * Sendet die Nachricht, die empfangen wurde an alle Clients, die dem Chat beigetreten sind.
     *
     * @param n Nachricht als String die geschickt wird.
     * @param c ChatClient, der gesendet hat
     */
    @Override
    public void receiveMessage(String n, ChatClientInterface c) throws RemoteException {
        //Schicke an alle verbundenen Clients
        for (Map.Entry<String, ChatClientInterface> chatClientEntry : clients.entrySet()) {
            String clientName = chatClientEntry.getKey();

            ChatClientInterface chatClient = chatClientEntry.getValue();
            chatClient.receiveMessage(n, c);

        }
    }

    /**
     * Fuegt den Benutzer in einem Chatraum ein
     *
     * @param c Chatraum den der Benutzer eintraeten moechte
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void joinChat(ChatClientInterface c) throws RemoteException {
        clients.put(c.getBenutzer().getName(), c);
    }

    /**
     * Nimmt den Benutzer aus einem Chatraum aus
     *
     * @param c Chatraum aus dem der Benutzer geloescht werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void verlasseChat(ChatClientInterface c) throws RemoteException {
        clients.remove(c.getBenutzer().getName());
    }

    /**
     * Entfernt den Benutzer aus dem Chat
     *
     * @param name des Benutzers
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void verlasseChat(String name) throws RemoteException {
        clients.remove(name);
    }

    /**
     * Returnt die Namen der Benutzer, die im Chat registriert sind
     *
     * @return Namne der Benutzer im Chat
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public Vector<String> getNamen() throws RemoteException {
        Vector<String> namen = new Vector<>();
        for (Map.Entry<String, ChatClientInterface> chatClientEntry : clients.entrySet()) {
            String clientName = chatClientEntry.getKey();
            namen.add(clientName);
        }
        return namen;
    }

}