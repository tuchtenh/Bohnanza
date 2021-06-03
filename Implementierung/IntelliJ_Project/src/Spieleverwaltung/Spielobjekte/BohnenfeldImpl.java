package Spieleverwaltung.Spielobjekte;

import Exception.AbbauException;
import Exception.FeldVollException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Ein BohnenfeldImpl ist ein Feld, auf dem ein Spieler eine Bohnenart anbauen kann.
 * Es speichert sowohl die Art der Bohnen, als auch die Anzahl der angebauten Bohnen
 * und ist einem Spieler zugehörig.
 *
 * @author Lukas Bayer, Alexander Wilhelm
 * @version 1.0
 */
public class BohnenfeldImpl extends UnicastRemoteObject implements Bohnenfeld {

    private String bohnenArt;

    private Vector<Karte> kartenfeld;

    public BohnenfeldImpl() throws RemoteException {
        this.kartenfeld = new Vector<Karte>();
        bohnenArt = null;
    }


    /**
     * "Erntet" ein Feld des Bohnenfeldes ab, indem die ausgewählte KarteImpl vom
     * Feld entfernt wird, dem AblagestapelImpl hinzugefügt wird und die Anzahl der
     * Karten auf 0 gesetzt wird.
     *
     * @return taler - die Anzahl der Taler, die dem Spieler gutgeschrieben werden
     * @throws AbbauException wenn Karten nicht abgebaut werden können oder das
     *                        Feld leer ist
     * @see Karte
     */

    //TODO: AbbauException, kind of done für tests lmao
    @Override
    public Vector<Karte> ernten() throws AbbauException, RemoteException {
        if (bohnenArt == null) {
            throw new AbbauException();
        }
        Vector<Karte> Ernte = (Vector<Karte>) kartenfeld.clone();
        kartenfeld = new Vector<Karte>();
        bohnenArt = null;
        return Ernte;
    }

    /**
     * Liefert die Anzahl der Karten gleicher Bohnensorte, die auf einem der Felder
     * des Bohnenfeldes liegen
     *
     * @return anzahl - die Anzahl der Karten auf einem Feld des Bohnenfeldes
     */
    @Override
    public int getAnzahlKarten() throws RemoteException {
        return kartenfeld.size();
    }


    /**
     * Wenn das Feld leer ist, wird eine neue Bohnenart angebaut und erhöht die Anzahl der Karten um 1.
     * Ist es bereits voll, so wird nur die Anzahl der Karten um 1 erhöht.
     *
     * @param k - die anzubauende KarteImpl
     * @throws FeldVollException wenn das Feld bereits mit einer anderen Bohnenart belegt
     *                           ist, als jene, die angebaut werden soll
     */
    @Override
    public void anbauen(Karte k) throws FeldVollException, RemoteException {
        if (kartenfeld.size() == 0) {

            kartenfeld.add(k);
            bohnenArt = k.getBohnenArt();

        }  //Vergleiche mit erster Karte
        else if (kartenfeld.get(0).getBohnenArt().equals(k.getBohnenArt())) {

            kartenfeld.add(k);
            bohnenArt = k.getBohnenArt();

        } else {
            //Feld ist belegt mit anderer Bohnenart

            throw new FeldVollException();
        }

    }

    public String getBohnenArt() {
        return this.bohnenArt;
    }

    @Override
    public String getImgURL() throws RemoteException {
        if (kartenfeld.size() > 0) {
            return kartenfeld.firstElement().getImgURL();
        } else {
            return null;
        }
    }

    public Karte getBohne() throws RemoteException {
        if (kartenfeld.size() > 0) {
            return kartenfeld.firstElement();
        }
        return null;
    }

}