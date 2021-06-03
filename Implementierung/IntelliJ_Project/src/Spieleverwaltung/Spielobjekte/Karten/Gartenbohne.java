package Spieleverwaltung.Spielobjekte.Karten;

import Spieleverwaltung.Spielobjekte.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Gartenbohne Klasse - Spielobjekt für Gartenbohne
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class Gartenbohne extends UnicastRemoteObject implements Karte {
    public Gartenbohne() throws RemoteException {
    }

    @Override
    public String getBohnenArt() throws RemoteException {
        return "Gartenbohne";
    }

    @Override
    public int getHaeufigkeit() throws RemoteException {
        return 6;
    }

    @Override
    public int getTalerwert(int anzahl) throws RemoteException {
        if (0 <= anzahl && anzahl <= 1) {
            return 0;
        }
        if (anzahl == 2) {
            return 2;
        }
        if (anzahl >= 3) {
            return 3;
        }

        return 0;
    }

    @Override
    public String getImgURL() throws RemoteException {
        return ("/GUI/Bilder/Gartenbohne.jpg");
    }

    @Override
    public int getMaxWert() throws RemoteException {
        return 3;
    }

}
