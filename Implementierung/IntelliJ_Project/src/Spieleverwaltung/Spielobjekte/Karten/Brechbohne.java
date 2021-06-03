package Spieleverwaltung.Spielobjekte.Karten;

import Spieleverwaltung.Spielobjekte.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Brechnbohne Klasse - Spielobjekt für Brechnbohne
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class Brechbohne extends UnicastRemoteObject implements Karte {

    public Brechbohne() throws RemoteException {
    }

    @Override
    public String getBohnenArt() throws RemoteException {
        return "Brechbohne";
    }

    @Override
    public int getHaeufigkeit() throws RemoteException {
        return 14;
    }

    @Override
    public int getTalerwert(int anzahl) throws RemoteException {
        if (0 <= anzahl && anzahl <= 2) {
            return 0;
        }
        if (3 <= anzahl && anzahl <= 4) {
            return 1;
        }
        if (anzahl == 5) {
            return 2;
        }
        if (anzahl == 6) {
            return 3;
        }
        if (anzahl >= 7) {
            return 4;
        }


        return 0;
    }

    @Override
    public String getImgURL() throws RemoteException {
        return ("/GUI/Bilder/Brechbohne.jpg");
    }

    @Override
    public int getMaxWert() throws RemoteException {
        return 4;
    }
}
