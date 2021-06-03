package Chat;



import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ChatServer extends UnicastRemoteObject implements ChatServerInterface{

    Map<String, ChatClientInterface> clients = new ConcurrentHashMap<String, ChatClientInterface>();


    public ChatServer() throws RemoteException {
    }

    /**
     * Sendet die Nachricht, die empfangen wurde an alle Clients, die dem Chat beigetreten sind.
     * @param n Nachricht als String die geschickt wird.
     * @param c ChatClient, der gesendet hat
     */
    @Override
    public void receiveMessage(String n, ChatClientInterface c) throws RemoteException {
        /*DEBUG STUFF
        n = "";

        for (Map.Entry<String, ChatClientInterface> chatClientEntry : clients.entrySet()) {
            n=n+" "+chatClientEntry.getKey()+"\n";
        }
        */

        //Schicke an alle verbundenen Clients
        for (Map.Entry<String, ChatClientInterface> chatClientEntry : clients.entrySet()) {
            String clientName = chatClientEntry.getKey();

            ChatClientInterface chatClient = chatClientEntry.getValue();
            chatClient.receiveMessage(n,c);

        }
    }

    @Override
    public void joinChat(ChatClientInterface c) throws RemoteException {
        clients.put(c.getBenutzer().getName(), c);
    }

    @Override
    public void verlasseChat(ChatClientInterface c) throws RemoteException {
        clients.remove(c.getBenutzer().getName());
    }

}