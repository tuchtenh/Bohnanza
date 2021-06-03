package Spieleverwaltung.Spielobjekte.Karten;

import Spieleverwaltung.Spielobjekte.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Saubohne Klasse - Spielobjekt für Saubohne
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class Saubohne extends UnicastRemoteObject implements Karte {
    public Saubohne() throws RemoteException {
    }

    @Override
    public String getBohnenArt() throws RemoteException {
        return "Saubohne";
    }

    @Override
    public int getHaeufigkeit() throws RemoteException {
        return 16;
    }

    @Override
    public int getTalerwert(int anzahl) throws RemoteException {
        if (0 <= anzahl && anzahl <= 2) {
            return 0;
        }
        if (3 <= anzahl && anzahl <= 4) {
            return 1;
        }
        if (5 <= anzahl && anzahl <= 6) {
            return 2;
        }
        if (anzahl == 7) {
            return 3;
        }
        if (anzahl >= 8) {
            return 4;
        }


        return 0;
    }

    @Override
    public String getImgURL() throws RemoteException {
        return ("/GUI/Bilder/Saubohne.jpg");
    }

    @Override
    public int getMaxWert() throws RemoteException {
        return 4;
    }

}
