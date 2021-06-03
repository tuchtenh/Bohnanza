package Spieleverwaltung.Spielobjekte.Karten;

import Spieleverwaltung.Spielobjekte.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Rotebohne Klasse - Spielobjekt für Rotebohne
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class RoteBohne extends UnicastRemoteObject implements Karte {
    public RoteBohne() throws RemoteException {
    }

    @Override
    public String getBohnenArt() throws RemoteException {
        return "Rote Bohne";
    }

    @Override
    public int getHaeufigkeit() throws RemoteException {
        return 8;
    }

    @Override
    public int getTalerwert(int anzahl) throws RemoteException {
        if (0 <= anzahl && anzahl <= 1) {
            return 0;
        }
        if (anzahl == 2) {
            return 1;
        }
        if (anzahl == 3) {
            return 2;
        }
        if (anzahl == 4) {
            return 3;
        }
        if (anzahl >= 5) {
            return 4;
        }

        return 0;
    }

    @Override
    public String getImgURL() throws RemoteException {
        return ("/GUI/Bilder/RoteBohne.jpg");
    }

    @Override
    public int getMaxWert() throws RemoteException {
        return 4;
    }

}
