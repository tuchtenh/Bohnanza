package Spieleverwaltung.Spielobjekte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Ein AblagestapelImpl ist ein Kartenstapel, auf dem Karten abgelegt werden, die
 * auf dem BohnenfeldImpl waren und dort vom Spieler abgebaut wurden. Ist das DeckImpl leer,
 * so werden die Karten aus dem AblagestapelImpl in das DeckImpl gemischt.
 *
 * @author Lukas Bayer, Alexander Wilhelm
 * @version 1.0
 */
public class AblagestapelImpl extends UnicastRemoteObject implements Ablagestapel {
    private Vector<Karte> stapel;

    public AblagestapelImpl() throws RemoteException {
        this.stapel = new Vector<Karte>();
    }


    @Override
    public int getAnzahlKarten() {
        return stapel.size();
    }


    /**
     * Fügt die übergebene KarteImpl dem Kartenstapel zu und erhöht die Anzahl der Karten um 1
     *
     * @param k die einzufügende KarteImpl. Sollte nicht null sein
     * @throws RemoteException wenn bei der Verbindung zum Server etwas schiefläuft
     * @see Karte
     */
    @Override
    public void addKarte(Karte k) throws RemoteException {
        stapel.add(k);

    }

    /**
     * Überpüft ob Stapel leer ist
     *
     * @return true, wenn Stapel leer
     */
    @Override
    public boolean isempty() {
        return stapel.size() == 0;
    }

    @Override
    public Vector<Karte> getKarten() {
        return stapel;
    }
}