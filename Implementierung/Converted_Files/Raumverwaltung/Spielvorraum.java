package Raumverwaltung;

import Raumverwaltung.Lobby.Spieler;
import Raumverwaltung.Lobby.Bot;
import String;
import remote;

/**
 *
 */
public interface Spielvorraum extends remote {

  //  public Lobby myLobby;
  //  public Spielraum mySpielraum;

    /**
     * @param sp
     */
  public void addSpieler(Spieler sp);

    /**
     * @param s
     */
  public void setName(String s);

    /**
     * @param b
     */
  public void removeBot(Bot b);

    /**
     * @param b
     */
  public void addBot(Bot b);

    /**
     *
     */
  public void starteSpiel();

}