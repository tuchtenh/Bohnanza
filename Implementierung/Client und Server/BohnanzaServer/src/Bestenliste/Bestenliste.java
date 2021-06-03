package Bestenliste;

import User.Benutzer;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


public interface Bestenliste extends Remote, Serializable {

    Vector<Benutzer> getListe() throws RemoteException, SQLException;
}
