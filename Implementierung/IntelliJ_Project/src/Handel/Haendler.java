package Handel;

import GUI.HandelController;
import GUI.SpielraumController;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielobjekte.Karte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public interface Haendler extends Remote {

    void setHC(HandelController hc) throws RemoteException;

    void setSC(SpielraumController sc) throws RemoteException;

    Akteur getSpieler() throws RemoteException;

    Vector<Karte> getAngebot() throws RemoteException;

    void setAngebot(Vector<Karte> angebot) throws RemoteException;

    /**
     * startet einen Handel mit dem übergebenen Haendler
     *
     * @param partner der Handelspartner
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    void starteHandel(Haendler partner) throws IOException;

    /**
     * Teilt dem Handelcontroller mit, das Angebot des Gegenspielers zu aktualiseren
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn URL falsch ist
     */
    void receiveAngebot() throws RemoteException, MalformedURLException;

    /**
     * Teilt dem Handelcontroller mit, den Handel zu akzeptieren
     *
     * @return boolean, ob Handel angenommen wurde
     * @throws RemoteException      wenn bei Verbindung zum Server etwas schiefläuft
     * @throws ExecutionException   wenn bei der Ausführung ein Fehler auftritt
     * @throws InterruptedException wenn die Ausführung unterbrochen wird
     */
    boolean acceptHandel() throws RemoteException, ExecutionException, InterruptedException;

    /**
     * Teilt dem Handelcontroller mit, das Fenster zu schließen
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws ExecutionException    wenn bei der Ausführung ein Fehler auftritt
     * @throws InterruptedException  wenn die Ausführung unterbrochen wird
     */
    void schließeFenster() throws RemoteException, MalformedURLException, ExecutionException, InterruptedException;

    Haendler getPartner() throws RemoteException;

    HandelController getHc() throws RemoteException;

    SpielraumController getSc() throws RemoteException;
}
