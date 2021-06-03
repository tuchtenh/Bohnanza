package Spieleverwaltung.Spielobjekte.Karten;

import Spieleverwaltung.Spielobjekte.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * BlaueBohne Klasse - Spielobjekt für Blauebohne
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class BlaueBohne extends UnicastRemoteObject implements Karte {

    public BlaueBohne() throws RemoteException {
    }

    @Override
    public String getBohnenArt() throws RemoteException {
        return "Blaue Bohne";
    }

    @Override
    public int getHaeufigkeit() throws RemoteException {
        return 20;
    }

    @Override
    public int getTalerwert(int anzahl) throws RemoteException {

        if (4 <= anzahl && anzahl <= 5) {
            return 1;
        }
        if (6 <= anzahl && anzahl <= 7) {
            return 2;
        }
        if (8 <= anzahl && anzahl <= 9) {
            return 3;
        }
        if (anzahl >= 10) {
            return 4;
        }

        return 0;
    }

    @Override
    public String getImgURL() throws RemoteException {
        return ("/GUI/Bilder/BlaueBohne.jpg");
    }

    @Override
    public int getMaxWert() throws RemoteException {
        return 4;
    }
}
