package Raumverwaltung;

import User.Benutzer;
import java.util.Vector;

public class Lobby_Impl implements Lobby {

    private Vector<Spielvorraum_Impl> raeume;   //alle Raeume in der Lobby


    /** Der User wird abgemeldet und der Client schliest.
     * @return gibt zurueck eine bestaetigung das der User abgemeldet hat.
     */
    @Override
    public boolean abmelden() {
        return true;
    }

    /** Methode loesch den User aus der Datenbank und schliest den Client.
     * @param b Benutzer der eingelogged ist und die Methode aufruft. darf nicht null sein.
     * @return gibt zuruck eine bestaetigung und der Client schliest sich.
     */
    @Override
    public boolean loescheSpieler(Benutzer b) {
        return true;
    }

    /** Erstellt einen neuen Spielraum mit dem gegebenen Namen
     * @param name belibiger Name des Spielraumes
     */
    @Override
    public void spielRaumErstellen(String name) {

    }

    //TODO
    /**
     * Zeig Information ueber einen ausgewaehlten Spielraum
     */
    @Override
    public void zeigeSpielraum() {

    }

    /** Zeigt alle aktive Spielreaume die erstellt sind.
     * @return gibt aus eine Liste von Spielraeumen die aktiv sind.
     */
    public Vector<Spielvorraum_Impl> getRaeume(){
        return raeume;
    }

}