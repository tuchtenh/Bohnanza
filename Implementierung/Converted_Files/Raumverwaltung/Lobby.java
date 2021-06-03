package Raumverwaltung;

import Users.Benutzer;
import Raumverwaltung.Lobby.Spieler;
import remote;

/**
 *
 */
public interface Lobby extends remote {


  /**
   * @return
   */
  public boolean abmelden();

  /**
   * @param b
   * @return
   */
  public boolean loescheSpieler(Benutzer b);

  /**
   * @param name
   */
  public void spielRaumErstellen(String name);

  /**
   *
   */
  public void zeigeSpielraum();

}