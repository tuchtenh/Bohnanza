package Bestenliste;

import User.Benutzer;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * erstellt eine Bestenliste mit den Spielern, die die meisten Spiele gewonnen haben
 */

public interface Bestenliste extends Remote, Serializable {

    /**
     * Liefert die Bestenliste von Spielern, die die meisten Spiele gewonnen haben, sind ganz oben.
     *
     * @return Vector von Benutzern.
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     * @throws SQLException    wenn bei Verbindung zur DB etwas schiefläuft
     */

    Vector<Benutzer> getListe() throws RemoteException, SQLException;
}
