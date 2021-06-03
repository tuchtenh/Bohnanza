package Spieleverwaltung;

import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;
import User.Benutzer;

import java.util.Vector;

/**
 * Ein Spieler, der im Spiel agieren kann.
 */

public class SpielerImpl implements Spieler{

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
  public SpielerImpl(Benutzer u) {
    this.user = u;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public void setName(String s) {
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
   * @param k - die zur Hand hinzuzufügende Karte
   */

  @Override
  public void addKarte(Karte k){

  }

  @Override
  public boolean isUser() {
    return true;
  }


}