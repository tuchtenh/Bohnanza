package Raumverwaltung;

import remote;

/**
 *
 */
public interface Spielraum extends remote {


  public void loescheSpiel();

  //public List getSpieler();

  //public List getBots();

  public int getId();

  public String getName();

}