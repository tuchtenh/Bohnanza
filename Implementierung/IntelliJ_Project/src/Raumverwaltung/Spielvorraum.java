package Raumverwaltung;

import Spieleverwaltung.Spieler;
import Bots.Bot;
import java.rmi.Remote;


public interface Spielvorraum extends Remote {

  public void addSpieler(Spieler b);


  public void setName(String s);


  public void removeBot(Bot b);


  public void addBot(Bot b);


  public Spielraum starteSpiel();

}