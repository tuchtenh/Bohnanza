package Spieleverwaltung.Spielobjekte;

import java.util.Vector;
import Exception.NichtAbgelegtException;

/**
 * Ein Kartenstapel ist eine Datenstruktur, die Karten verwaltet. Karten können vom Stapel
 * gezogen werden, sowie hinzugefügt werden. Ein Kartenstapel merkt sich, wie viele Karten er
 * beinhaltet und lässt sich mischen.
 */
public class Kartenstapel {

  private int anzahlKarten;
  private Vector<Karte> stapel;

  Kartenstapel() {
    stapel = new Vector<Karte>();
  }

  /**
   * Verändert die Reihenfolge der Karten im Kartenstapel, behält dabei ihre Anzahl bei &
   * fügt weder neue Karten hinzu, noch werden Karten entfernt
   */
  public void mischen() {

  }

  public int getAnzahlKarten() {
    return 0;
  }

  /**
   * Entfernt die übergebene Karte aus dem Kartenstapel und senkt die Anzahl der Karten um 1
   * @param k - die zu entfernende Karte. Sollte nicht null sein
   * @see Karte
   */
  public void removeKarte(Karte k) {

  }

  /**
   * Fügt die übergebene Karte dem Kartenstapel zu und erhöht die Anzahl der Karten um 1
   *
   * @exception NichtAbgelegtException - wenn eine Karte dem Stapel nicht hinzugefügt werden kann
   * @param k - die einzufügende Karte. Sollte nicht null sein
   * @see Karte
   */
  public void addKarte(Karte k) throws NichtAbgelegtException {

  }

  /**
   * Überprüft, ob der kartenstapel leer ist
   *
   * @return true falls stapel leer ist, fals sonst
   */
  public boolean isempty(){
    return false;
  }

}