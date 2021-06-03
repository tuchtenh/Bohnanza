package Raumverwaltung;


import Bots.Bot;
import Spieleverwaltung.Spieler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 *
 */
public class Spielvorraum_Impl extends UnicastRemoteObject implements Spielvorraum {

  private String name;             //Name des Vorraums

  private Vector<Spieler> spieler; //speichert alle Spieler im Vorraum
  private Vector<Bot> bots; //speichert alle Spieler im Vorraum

  public Spielvorraum_Impl(String name) throws RemoteException{
    this.name=name;
    this.spieler = new Vector<Spieler>();
    this.bots = new Vector<Bot>();
  }


  /**Setzt den Namen des Spielraumes. Darf nicht null sein.
   * @return Gibt zurueck den Namen des Spielraumes.
   */
  @Override
  public String getName(){
    return this.name;
  }

  /** Fuegt einen Spieler zum Spielvorraum aus der Lobby
   * @param b der Spieler der hingefuegt werden soll. darf nicht null sein
   */
  @Override
  public void addSpieler(Spieler b) {

  }

  /** Seatzt den Namen des Spielvorraeumes der dann fuer alle sichbar ist
   * @param s beliebiger name. darf nicht null sein.
   */
  @Override
  public void setName(String s) {

  }

  /** Entfernt einen Bot aus dem Spielvorraum
   * @param b Ein Bot, der sich im Spielvorraum befindent
   */
  @Override
  public void removeBot(Bot b) {

  }

  /** Startet das Spiel mit den ausgewaehlten Einstellungen
   * @return Gibt zueruck einen nach den Einstellungen erstellten Spielraum.
   */
  @Override
  public Spielraum starteSpiel() {
    return null;
  }

  /** Fuegt einen Bot in den Spielvorraum ein
   * @param b Ein Bot der ausgewaehlten Schwierigkeit
   */
  @Override
  public void addBot(Bot b) {

  }


  /** Gibt den Vecotr mit den Spielern die im Spielvorraum sind
   * @return Vector mit Spielern im Spielvorraum
   */
  public Vector<Spieler> getSpieler() {
    return spieler;
  }


  /** Gibt den Vecotr mit den Bots die im Spielvorraum sind
   * @return Vector mit Bots im Spielvorraum
   */
  public Vector<Bot> getBots() {
    return bots;
  }
}