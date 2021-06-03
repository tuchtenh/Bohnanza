package Spieleverwaltung.Spielobjekte;

import Spieleverwaltung.Spielemanager_Impl;

/**
 * Ein Ablagestapel ist ein Kartenstapel, auf dem Karten abgelegt werden, die
 * auf dem Bohnenfeld waren und dort vom Spieler abgebaut wurden. Ist das Deck leer,
 * so werden die Karten aus dem Ablagestapel in das Deck gemischt.
 */
public class Ablagestapel extends Kartenstapel {

  private int anzahlLeerung;

  Ablagestapel () {
    this.anzahlLeerung = 0;
  }

  public int getAnzahlLeerung() {
    return 0;
  }

}