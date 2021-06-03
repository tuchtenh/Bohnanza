package Raumverwaltung;

import User.Benutzer;
import java.rmi.Remote;


public interface Lobby extends Remote {

  public boolean abmelden();

  public boolean loescheSpieler(Benutzer b);

  public void spielRaumErstellen(String name);

  public void zeigeSpielraum();

}