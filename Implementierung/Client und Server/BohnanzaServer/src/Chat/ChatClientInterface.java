package Chat;

import User.Benutzer;

import java.rmi.RemoteException;

public interface ChatClientInterface extends Chat{

    public Benutzer getBenutzer() throws RemoteException;

}