package Spieleverwaltung;

import remote;

public interface Spielemanager extends remote {

  public boolean spielBeenden();

  public void spielStarten();

  public boolean spielVerlassen(Spieler s);

  public void spielMain();

}