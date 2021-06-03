package Spieleverwaltung.Spielobjekte;

import Exception.StapelLeerException;
import Spieleverwaltung.Spielobjekte.Karten.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.Vector;

/**
 * Ein DeckImpl ist der Kartenstapel der Spielkarten, von dem innerhalb des Spiels gezogen wird.
 *
 * @author Lukas Bayer, Alexander Wilhelm
 * @version 1.0
 */
public class DeckImpl extends UnicastRemoteObject implements Deck {

    private Vector<Karte> stapel;

    private int anzahlLeerung;


    //Erstelle neues Deck
    public DeckImpl() throws RemoteException {
        this.stapel = new Vector<>();

 /*  // für Tests:
        for (int i = 0; i < 16; i++) {
            stapel.add(new Augenbohne());
        }
*/
        //Füge Augenbohnen hinzu
        for (int i = 0; i < (new Augenbohne()).getHaeufigkeit(); i++) {
            stapel.add(new Augenbohne());
        }
        //Füge Blaue Bohnen hinzu
        for (int i = 0; i < (new BlaueBohne()).getHaeufigkeit(); i++) {
            stapel.add(new BlaueBohne());
        }
        //Füge Brechbohnen hinzu
        for (int i = 0; i < (new Brechbohne()).getHaeufigkeit(); i++) {
            stapel.add(new Brechbohne());
        }
        //Füge Feuerbohnen hinzu
        for (int i = 0; i < (new Feuerbohne()).getHaeufigkeit(); i++) {
            stapel.add(new Feuerbohne());
        }
        //Füge Gartenbohnen hinzu
        for (int i = 0; i < (new Gartenbohne()).getHaeufigkeit(); i++) {
            stapel.add(new Gartenbohne());
        }
        //Füge Rote Bohnen hinzu
        for (int i = 0; i < (new RoteBohne()).getHaeufigkeit(); i++) {
            stapel.add(new RoteBohne());
        }
        //Füge Saubohnen hinzu
        for (int i = 0; i < (new Saubohne()).getHaeufigkeit(); i++) {
            stapel.add(new Saubohne());
        }
        //Füge Sojabohnen hinzu
        for (int i = 0; i < (new Sojabohne()).getHaeufigkeit(); i++) {
            stapel.add(new Sojabohne());
        }

        mischen();
    }


    /**
     * Erstellt ein Deck aus vorgegeben Karten
     *
     * @param karten Vector aus Karten
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public DeckImpl(Vector<Karte> karten) throws RemoteException {
        this.stapel = karten;
        mischen();
    }


    /**
     * Zieht die oberste KarteImpl aus dem DeckImpl, indem die oberste KarteImpl ausgewählt wird
     * und mit removeKarte() aus dem DeckImpl entfernt wird. Die gezogene KarteImpl wird
     * anschließend zurückgegeben
     *
     * @return karte - die gezogene KarteImpl
     * @throws StapelLeerException wenn das DeckImpl bereits leer ist
     * @see Kartenstapel +removeKarte()
     */
    @Override
    public Karte ziehe() throws StapelLeerException, RemoteException {
        if (stapel.size() > 0) {
            Karte k = stapel.get(0);
            stapel.remove(0);
            return k;
        } else {
            throw new StapelLeerException();
        }
    }


    /**
     * Veraendert die Reihenfolge der Karten im Kartenstapel, behaelt dabei ihre Anzahl bei und
     * fuegt weder neue Karten hinzu, noch werden Karten entfernt
     */
    @Override
    public void mischen() {
        Collections.shuffle(stapel);
    }

    @Override
    public int getAnzahlKarten() {
        return stapel.size();
    }

    /**
     * Überprüft, ob der kartenstapel leer ist
     *
     * @return true falls stapel leer ist, fals sonst
     */
    @Override
    public boolean isempty() {
        return stapel.size() == 0;
    }

    @Override
    public Vector<Karte> getKarten() {
        return stapel;
    }

    @Override
    public void setKarten(Vector<Karte> karten) throws RemoteException {
        this.stapel = karten;
    }
}