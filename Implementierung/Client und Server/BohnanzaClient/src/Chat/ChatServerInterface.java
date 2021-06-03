package Chat;


import java.rmi.RemoteException;

public interface ChatServerInterface extends Chat{

    public void joinChat(ChatClientInterface c) throws RemoteException;

    public void verlasseChat(ChatClientInterface c) throws RemoteException;

}