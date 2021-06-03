import Spieleverwaltung.void;
import java.util.Vector;
import Spieleverwaltung.Spielemanager_Impl;

public interface Bot {

    public Vector  myKarte;
    /**
   * 
   * @element-type Bohnenfeld
   */
  public Vector  myBohnenfeld;
    /**
   * 
   * @element-type Karte
   */
  public Vector  myKarte;
    public Spielemanager_Impl mySpielemanager_Impl;

  public void handeln();

  public void anbauen();

}