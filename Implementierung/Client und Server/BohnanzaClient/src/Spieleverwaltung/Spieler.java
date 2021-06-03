package Spieleverwaltung;

import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;
import User.Benutzer;

import java.util.Vector;

public interface Spieler{


  public String getName();

  public void setName(String s);

  public Benutzer getUser();

  public Vector<Bohnenfeld> getFelder();

  public Vector<Karte> getHandkarten();

  /**
   * Fügt eine Karte in die Hand des Spielers hinzu. Die Karte darf nicht null sein
   *
   * @param k - die zur Hand hinzuzufügende Karte
   */

  public void addKarte(Karte k);


  public boolean isUser();

}