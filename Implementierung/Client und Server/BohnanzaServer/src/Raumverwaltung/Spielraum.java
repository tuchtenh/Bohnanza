package Raumverwaltung;

import java.rmi.Remote;
import java.util.Vector;


public interface Spielraum extends Remote {


  public void loescheSpiel();

  public Vector getSpieler();

  public Vector getBots();

  public int getId();

  public String getName();

}