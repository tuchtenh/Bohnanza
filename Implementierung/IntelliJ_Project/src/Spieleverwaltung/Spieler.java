package Spieleverwaltung;

import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;
import User.Benutzer;

import java.util.Vector;

/**
 * Ein Spieler, der im Spiel agieren kann.
 */

public class Spieler {

  private String name;

  private Benutzer user;

  private Vector<Bohnenfeld>  felder;

  private Vector<Karte>  handkarten;

  /**
   * Konstruktor, dem der Benutzer, der den Spieler steuert, übergeben wird.
   *
   * @param u - der Benutzer, der den Spieler steuert
   * @see User.Benutzer
   */
  public Spieler(Benutzer u) {
    this.user = u;
  }

  public String getName() {
    return null;
  }

  public void setName(String s) {
  }

  public Benutzer getUser() {
    return user;
  }

  public Vector<Bohnenfeld> getFelder() {
    return felder;
  }

  public Vector<Karte> getHandkarten() {
    return handkarten;
  }

  /**
   * Fügt eine Karte in die Hand des Spielers hinzu. Die Karte darf nicht null sein
   *
   * @param k - die zur Hand hinzuzufügende Karte
   */

  public void addKarte(Karte k){

  }



}