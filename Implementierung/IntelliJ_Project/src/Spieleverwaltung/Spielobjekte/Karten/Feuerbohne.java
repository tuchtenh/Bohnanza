package Spieleverwaltung.Spielobjekte.Karten;

import Spieleverwaltung.Spielobjekte.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Feuerbohne Klasse - Spielobjekt für Feuerbohne
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class Feuerbohne extends UnicastRemoteObject implements Karte {
    public Feuerbohne() throws RemoteException {
    }

    @Override
    public String getBohnenArt() throws RemoteException {
        return "Feuerbohne";
    }

    @Override
    public int getHaeufigkeit() throws RemoteException {
        return 18;
    }

    @Override
    public int getTalerwert(int anzahl) throws RemoteException {
        if (0 <= anzahl && anzahl <= 2) {
            return 0;
        }
        if (3 <= anzahl && anzahl <= 5) {
            return 1;
        }
        if (6 <= anzahl && anzahl <= 7) {
            return 2;
        }
        if (anzahl == 8) {
            return 3;
        }
        if (anzahl >= 9) {
            return 4;
        }


        return 0;
    }

    @Override
    public String getImgURL() throws RemoteException {
        return ("/GUI/Bilder/Feuerbohne.jpg");
    }

    @Override
    public int getMaxWert() throws RemoteException {
        return 4;
    }

}
