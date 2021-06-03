package Bestenliste;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import User.Benutzer;


public interface Bestenliste extends Remote {
    Vector<Benutzer> getListe() throws RemoteException;
}
