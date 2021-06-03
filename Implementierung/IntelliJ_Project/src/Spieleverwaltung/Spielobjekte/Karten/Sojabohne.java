package Spieleverwaltung.Spielobjekte.Karten;

import Spieleverwaltung.Spielobjekte.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Sojabohne Klasse - Spielobjekt für Sojabohne
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class Sojabohne extends UnicastRemoteObject implements Karte {

    public Sojabohne() throws RemoteException {
    }

    @Override
    public String getBohnenArt() throws RemoteException {
        return "Sojabohne";
    }

    @Override
    public int getHaeufigkeit() throws RemoteException {
        return 12;
    }

    @Override
    public int getTalerwert(int anzahl) throws RemoteException {
        if (0 <= anzahl && anzahl <= 1) {
            return 0;
        }
        if (2 <= anzahl && anzahl <= 3) {
            return 1;
        }
        if (4 <= anzahl && anzahl <= 5) {
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
        return ("/GUI/Bilder/Sojabohne.jpg");
    }

    @Override
    public int getMaxWert() throws RemoteException {
        return 4;
    }
}