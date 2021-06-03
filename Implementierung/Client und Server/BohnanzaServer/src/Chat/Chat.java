package Chat;

import User.Benutzer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Chat extends Remote {

    public void receiveMessage(String n, ChatClientInterface c) throws RemoteException;


}