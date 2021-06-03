package Spieleverwaltung;

import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Akteur extends Remote {
    /**
     * gibt den Namen des Spielers zurück
     *
     * @return name - der Name des Spielers
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    String getName() throws RemoteException;

    /**
     * verändert den Namen des Spielers
     *
     * @param s der String, der nun Name des Spielers sein soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void setName(String s) throws RemoteException;

    /**
     * gibt die Felder des Spielers zurück
     *
     * @return felder - die Felder des Spielers
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    Vector<Bohnenfeld> getFelder() throws RemoteException;

    /**
     * gibt die Handkarten des Spielers zurück
     *
     * @return handkarten - die Handkarten des Spielers
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    Vector<Karte> getHandkarten() throws RemoteException;

    /**
     * Fügt eine Karte in die Hand des Spielers hinzu. Die KarteImpl darf nicht null sein
     *
     * @param k die zur Hand hinzuzufügende Karte
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void addKarte(Karte k) throws RemoteException;

    /**
     * Entfernt eine Karte aus der Hand des Spielers. Die KarteImpl darf nicht null sein
     * und muss sich vorher in der Hand des Spielers befunden haben.
     *
     * @param index der Index der aus der Hand zu entfernende Karte
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void removeKarte(int index) throws RemoteException;

    /**
     * entfernt Karten mit den Indices im Vektor aus der Hand des Akteurs
     *
     * @param indices die zu entfernenden Indices
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void removeKarten(Vector<Integer> indices) throws RemoteException;

    /**
     * gibt zurück, ob der Spieler von einem Benutzer gesteuert wird oder Bot ist
     *
     * @return isUser - true, falls kein Bot, sonst false
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    boolean isUser() throws RemoteException;

    /**
     * fügt Felder zu einem Spieler hinzu
     *
     * @param anzahlFelder die Anzahl der Felder, die hinzugefügt werden sollen
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void addFelder(int anzahlFelder) throws RemoteException;

    /**
     * fügt Taler zum Spieler hinzu
     *
     * @param t die Anzahl der Taler, die dem Spieler gutgeschrieben werden
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void addTaler(int t) throws RemoteException;

    /**
     * gibt die Anzahl der Taler des Spielers zurück
     *
     * @return taler - die Taler des Spielers
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    int getTaler() throws RemoteException;

    /**
     * gibt die gehandelten Karten als Vector zurück
     *
     * @return gehandelteKarten - die gehandelten Karten
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    Vector<Karte> getGehandelteKarten() throws RemoteException;

    /**
     * fügt eine Karte zu den gehandelten Karten hinzu
     *
     * @param k die hinzuzuzfügende Karte
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void addGehandelteKarten(Karte k) throws RemoteException;

    /**
     * löscht ein Karte aus den gehandelten Karten
     *
     * @param index der Index der zu entfernenden Karte
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void removeGehandelteKarte(int index) throws RemoteException;

    /**
     * gibt zurück, wie viele Karten der Spieler angebaut hat
     *
     * @return hatAngebaut - Anzahl der Karten, die der Spieler angebaut hat
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    int getHatAngebaut() throws RemoteException;

    /**
     * verändert die Anzahl der Karten, die der Spieler angebaut hat
     *
     * @param hatAngebaut die Anzahl der Karten
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    void setHatAngebaut(int hatAngebaut) throws RemoteException;

}
