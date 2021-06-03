package Spieleverwaltung.Spielobjekte;

import Exception.AbbauException;
import Exception.FeldVollException;

import java.util.Vector;

/**
 * Ein Bohnenfeld ist ein Feld, auf dem ein Spieler eine Bohnenart anbauen kann.
 * Es speichert sowohl die Art der Bohnen, als auch die Anzahl der angebauten Bohnen
 * und ist einem Spieler zugehörig.
 */
public class Bohnenfeld {

  private int anzahlKarten;

  private String bohnenArt;

  private Vector<Karte> kartenfeld;


  /**
   * "Erntet" ein Feld des Bohnenfeldes ab, indem die ausgewählte Karte vom
   * Feld entfernt wird, dem Ablagestapel hinzugefügt wird und die Anzahl der
   * Karten auf 0 gesetzt wird.
   *
   * @exception AbbauException - wenn Karten nicht abgebaut werden können oder das
   * Feld leer ist
   * @return taler - die Anzahl der Taler, die dem Spieler gutgeschrieben werden
   * @see Karte
   */
  public int ernten() throws AbbauException {
  return 0;
  }

  /**
   * Liefert die Anzahl der Karten gleicher Bohnensorte, die auf einem der Felder
   * des Bohnenfeldes liegen
   *
   * @return anzahl - die Anzahl der Karten auf einem Feld des Bohnenfeldes
   */
  public int getAnzahlKarten() {
  return 0;
  }

  /**
   * Wenn das Feld leer ist, wird eine neue Bohnenart angebaut und erhöht die Anzahl der Karten um 1.
   * Ist es bereits voll, so wird nur die Anzahl der Karten um 1 erhöht.
   *
   * @param k - die anzubauende Karte
   * @exception FeldVollException - wenn das Feld bereits mit einer anderen Bohnenart belegt
   * ist, als jene, die angebaut werden soll
   */
  public void anbauen(Karte k) throws FeldVollException {
  }

  public String getBohnenArt() {
    return this.bohnenArt;
  }

}