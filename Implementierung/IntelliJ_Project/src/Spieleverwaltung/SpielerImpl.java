package Spieleverwaltung;

import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.BohnenfeldImpl;
import Spieleverwaltung.Spielobjekte.Karte;
import User.Benutzer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Ein Spieler, der im Spiel agieren kann.
 *
 * @author Lukas Bayer
 * @version 1.0
 */

public class SpielerImpl extends UnicastRemoteObject implements Spieler {

    private String name;
    private Benutzer user;
    private Vector<Bohnenfeld> felder;
    private Vector<Karte> handkarten;
    private Vector<Karte> gezogeneKarten;

    private Vector<Karte> gehandelteKarten;

    private int hatAngebaut;

    private int taler;

    /**
     * Konstruktor, dem der Benutzer, der den Spieler steuert, übergeben wird.
     *
     * @param u der Benutzer, der den Spieler steuert
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     * @see User.Benutzer
     */
    public SpielerImpl(Benutzer u) throws RemoteException {
        this.user = u;
        this.name = user.getName();

        this.taler = 0;

        this.handkarten = new Vector<Karte>();
        this.gezogeneKarten = new Vector<Karte>();
        this.felder = new Vector<Bohnenfeld>();

        this.gehandelteKarten = new Vector<Karte>();

        this.hatAngebaut = 0;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String s) {
        this.name = s;
    }

    @Override
    public Benutzer getUser() {
        return user;
    }

    @Override
    public Vector<Bohnenfeld> getFelder() {
        return felder;
    }

    @Override
    public Vector<Karte> getHandkarten() {
        return handkarten;
    }

    /**
     * Fügt eine Karte in die Hand des Spielers hinzu. Die Karte darf nicht null sein
     *
     * @param k die zur Hand hinzuzufügende KarteImpl
     */
    @Override
    public void addKarte(Karte k) {
        if (k != null) {
            handkarten.add(k);
        }
    }

    /**
     * Entfernt eine Karte aus der Hand des Spielers. Die Karte darf nicht null sein
     * und muss sich vorher in der Hand des Spielers befunden haben.
     *
     * @param index der Index der aus der Hand zu entfernende Karte
     */
    @Override
    public void removeKarte(int index) {
        if (index < handkarten.size() && index >= 0) {
            handkarten.remove(index);
        }
    }

    /**
     * Entfernt mehrere Handkarten aus der Hand des Spielers
     *
     * @param indices der Karten, die entfernt werden sollen
     */
    @Override
    public void removeKarten(Vector<Integer> indices) {
        Vector<Karte> toBeRemoved = new Vector<>();

        for (Integer i : indices) {
            if (i >= 0 && i < handkarten.size()) {
                System.out.println(i);
                toBeRemoved.add(handkarten.get(i));
            }

        }
        handkarten.removeAll(toBeRemoved);
    }

    /**
     * Gibt true zurück, da Spieler ein aktiver Benutzer ist
     *
     * @return true
     */
    @Override
    public boolean isUser() {
        return true;
    }

    /**
     * Fügt Bohnenfelder dem Spieler hinzu
     *
     * @param anzahlFelder Anzahl der zuzufügenden Felder
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void addFelder(int anzahlFelder) throws RemoteException {
        for (int i = 0; i < anzahlFelder; i++) {
            this.felder.add(new BohnenfeldImpl());
        }
    }

    /**
     * Fügt dem Spieler Taler hinzu
     *
     * @param t Anzahl der Taler
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void addTaler(int t) throws RemoteException {
        if (t >= 0) {
            taler += t;
        }
    }

    @Override
    public int getTaler() throws RemoteException {
        return taler;
    }


    @Override
    public Vector<Karte> getGehandelteKarten() throws RemoteException {
        return gehandelteKarten;
    }

    /**
     * Fügt dem Spieler die gehandelten Karten hinzu
     *
     * @param k Karten, die hinzugefügt werden
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void addGehandelteKarten(Karte k) throws RemoteException {
        gehandelteKarten.add(k);
    }

    /**
     * Entfernt die gehandelten Karten, entsprechend des Index
     *
     * @param index der Karte, die entfernt werden soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void removeGehandelteKarte(int index) throws RemoteException {
        if (index < gehandelteKarten.size() && index >= 0) {
            gehandelteKarten.remove(index);
        }
    }

    @Override
    public int getHatAngebaut() throws RemoteException {
        return hatAngebaut;
    }

    @Override
    public void setHatAngebaut(int hatAngebaut) throws RemoteException {
        if (hatAngebaut >= 0) {
            this.hatAngebaut = hatAngebaut;
        }
    }
}