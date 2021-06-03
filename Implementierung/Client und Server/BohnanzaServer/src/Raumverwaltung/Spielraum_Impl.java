package Raumverwaltung;


import java.util.Vector;
import Spieleverwaltung.Spieler;
import Bots.Bot;
import User.Benutzer;

public class Spielraum_Impl implements Spielraum {


  private Vector<Spieler> spieler;  //alle Spieler im Spielraum

  private Vector<Bot> bots;         //alle Bots im Spielraum

  private int id;

  private String name;

  public void setName(String s) {
  }

  public void setId(int id) {
  }

  /**
   * Endet das Spiel und der Spielraum wird geloescht
   */
  @Override
  public void loescheSpiel() {

  }

  @Override
  public Vector<Spieler> getSpieler() {
    return null;
  }

  @Override
  public Vector<Bot> getBots() {
    return null;
  }

  @Override
  public int getId() {
    return 0;
  }

  @Override
  public String getName() {
    return null;
  }
}